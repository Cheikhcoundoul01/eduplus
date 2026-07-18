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

### 1. Lancer PostgreSQL (Docker)

```bash
docker compose up -d
```

Cela démarre PostgreSQL sur `localhost:5432` avec :
- DB : `eduplus_db`
- User : `eduplus_user`
- Password : `eduplus_pass`

### 2. Variables d'environnement (optionnel, valeurs par défaut déjà correctes pour Docker)

```
DB_HOST=localhost
DB_PORT=5432
DB_NAME=eduplus_db
DB_USER=eduplus_user
DB_PASSWORD=eduplus_pass
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
