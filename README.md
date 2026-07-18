# EduPlus — API REST de gestion scolaire

Projet L3 IPD — Module PDWA-L3 — Spring Boot 3 / Java 17 / PostgreSQL / JWT

## Stack technique

- Java 17, Spring Boot 3.3.2
- Spring Security + JWT (jjwt 0.12.5)
- Spring Data JPA (Hibernate) + PostgreSQL
- Bean Validation (`@Valid`)
- Swagger / OpenAPI 3 (springdoc)
- Upload de fichiers (photo profil + PDF)

## Démarrage rapide

### 1. Lancer PostgreSQL

Ce projet a été développé et testé avec **PostgreSQL installé en local** (via pgAdmin4).

**Configuration avec PostgreSQL local (pgAdmin) :**

1. Ouvre pgAdmin4, connecte-toi à ton serveur PostgreSQL (mot de passe défini à l'installation)
2. Clic droit sur **Databases** → **Create** → **Database...** → nomme-la `eduplus_db` → Save
3. Vérifie le port de ton serveur : clic droit sur le serveur → **Properties** → onglet **Connection** → note la valeur du champ **Port** (par défaut `5432`, mais peut être différent si plusieurs versions de PostgreSQL sont installées — par exemple `5433`)

### 2. Configurer les variables d'environnement

Le projet lit sa configuration via des variables d'environnement (voir `application.yml`). Adapte les valeurs ci-dessous à ta config PostgreSQL réelle :

```
DB_HOST=localhost
DB_PORT=5433
DB_NAME=eduplus_db
DB_USER=postgres
DB_PASSWORD=Cheikh2001
JWT_SECRET=un-secret-long-et-unique
JWT_EXPIRATION_MS=86400000
FILE_UPLOAD_DIR=./uploads
```

### 3. Lancer l'application

```bash
mvn spring-boot:run
```

L'API démarre sur `http://localhost:8080`.

### 4. Documentation Swagger

`http://localhost:8080/swagger-ui.html`

## Données de test

Au premier démarrage, `data.sql` insère automatiquement :
- 3 enseignants, 3 cours, 5 étudiants
- 1 compte admin : `username=admin` / `password=Admin@123`

## Tester rapidement (curl)

```bash
# Login admin
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"Admin@123"}'

# Copier le token retourné puis :
curl http://localhost:8080/etudiants \
  -H "Authorization: Bearer <TOKEN>"
```

## Rôles et sécurité

| Rôle | Accès |
|---|---|
| ADMIN | Accès complet (étudiants, enseignants, cours, inscriptions) |
| ENSEIGNANT | Lecture des étudiants et cours |
| ETUDIANT | Upload de sa propre photo/documents, lecture des cours |

Le détail des permissions par endpoint est dans `SecurityConfig.java`.

## Upload de fichiers

- Photo de profil : `POST /etudiants/{id}/photo` — JPEG/PNG, 2 Mo max
- Document PDF : `POST /etudiants/{id}/docs` — PDF, 5 Mo max
- Champ multipart attendu : `file`
- Fichiers stockés dans `./uploads/photos` et `./uploads/docs`, servis via `/uploads/**`

## Structure du projet

```
src/main/java/sn/ipd/eduplus/
├── config/       -> SecurityConfig, OpenApiConfig, WebConfig
├── security/     -> JwtUtil, JwtAuthFilter, UserDetailsServiceImpl, UserPrincipal
├── entity/       -> User, Role, Etudiant, Enseignant, Cours, Inscription
├── repository/   -> Repositories Spring Data JPA
├── dto/          -> Requests / Responses
├── service/      -> Logique métier
├── controller/   -> Endpoints REST
└── exception/    -> GlobalExceptionHandler + exceptions custom
```

## Notes

- `ddl-auto: update` génère automatiquement le schéma au démarrage (adapté pour un projet étudiant ; en production on utiliserait Flyway/Liquibase).
- Le mot de passe est haché avec BCrypt avant stockage.
- Toutes les erreurs API renvoient un format JSON uniforme : `{ timestamp, status, error, message, path, details }`.
