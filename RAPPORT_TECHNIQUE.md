# Rapport technique — Projet EduPlus (API REST Spring Boot)

> À compléter avec vos noms, votre vécu réel du projet et vos captures d'écran.
> Objectif : 2-3 pages. Le prof veut voir que vous comprenez vos choix, pas juste que ça marche.

## 1. Présentation du projet

- Contexte : système de gestion scolaire pour l'établissement EduPlus (étudiants, enseignants, cours, inscriptions).
- Membres du groupe : ...
- Répartition des tâches : *(qui a fait quoi — sécurité, CRUD, upload, tests...)*

## 2. Architecture technique

- Stack : Java 17, Spring Boot 3.3, Spring Security, Spring Data JPA, PostgreSQL, JWT (jjwt), Swagger/OpenAPI.
- Architecture en couches : `controller` → `service` → `repository` → `entity`, avec des `dto` pour ne jamais exposer les entités directement.
- Modèle de données : *(insérer votre schéma / diagramme de classes — User/Role, Etudiant, Enseignant, Cours, Inscription)*

## 3. Sécurité — JWT

- Authentification stateless : à la connexion (`/auth/login`), un token JWT signé (HMAC) est généré et contient le rôle de l'utilisateur.
- Un filtre (`JwtAuthFilter`) intercepte chaque requête, valide le token et peuple le `SecurityContext`.
- Contrôle d'accès par rôle (`ADMIN`, `ENSEIGNANT`, `ETUDIANT`) configuré dans `SecurityConfig` via `authorizeHttpRequests`.
- *(Expliquez ici pourquoi JWT plutôt que sessions, et une difficulté que vous avez rencontrée)*

## 4. Gestion des exceptions

- Un `@RestControllerAdvice` centralise toutes les erreurs et renvoie un format JSON uniforme (`timestamp`, `status`, `error`, `message`, `path`).
- Exceptions métier custom : `ResourceNotFoundException` (404), `DuplicateResourceException` (409), `InvalidFileException` (400).
- *(Un exemple concret d'erreur que ça a permis de gérer proprement)*

## 5. Upload de fichiers

- Photo de profil (JPEG/PNG, 2 Mo max) et documents PDF, validés par type MIME et taille avant stockage sur disque.
- *(Difficulté rencontrée : par exemple la gestion des noms de fichiers, la validation du type réel vs extension...)*

## 6. Difficultés rencontrées

- *(À remplir honnêtement — c'est souvent ce que le jury regarde le plus : ex. gestion du temps, compréhension de Spring Security, relations JPA...)*

## 7. Pistes d'amélioration

- Tests unitaires/intégration plus poussés (JUnit + Mockito, Testcontainers pour Postgres).
- Refresh token, migration Flyway au lieu de `ddl-auto: update`.
- Rôle ENSEIGNANT plus fin (voir seulement ses propres cours).

## 8. Conclusion

*(2-3 phrases sur ce que le projet vous a appris)*
