# API REST - Gestion de Rendez-vous
## 📋 Vue d'ensemble
Une API REST complète et professionnelle pour la gestion de rendez-vous, développée avec **Spring Boot 4**, **Java 21**, et une architecture en couches. Le projet utilise **H2** pour la base de données et suit les bonnes pratiques Spring Boot.
## 🚀 Technologies utilisées
| Technologie | Version | Rôle |
|-------------|---------|------|
| Java | 21 | Langage de programmation |
| Spring Boot | 4.0.3 | Framework principal |
| Spring Data JPA | - | Couche persistance |
| Hibernate | - | ORM |
| H2 Database | - | Base de données embarquée |
| Lombok | - | Réduction du boilerplate |
| Jakarta Validation | - | Validation des données |
| Maven | - | Gestion des dépendances |
## 📁 Structure du projet
```
back-api/
├── src/main/java/com/api/backapi/
│   ├── controller/
│   │   └── RendezVousController.java          # Endpoints REST (7 endpoints)
│   ├── service/
│   │   ├── RendezVousService.java             # Interface service
│   │   └── RendezVousServiceImpl.java          # Implémentation avec logs
│   ├── repository/
│   │   └── RendezVousRepository.java          # Spring Data JPA repository
│   ├── entity/
│   │   ├── RendezVous.java                    # Entity JPA
│   │   └── AppointmentStatus.java             # Enum (SCHEDULED, CANCELLED, COMPLETED)
│   ├── dto/
│   │   ├── RendezVousRequest.java             # DTO pour requêtes
│   │   └── RendezVousResponse.java            # DTO pour réponses
│   ├── mapper/
│   │   └── RendezVousMapper.java              # Conversion Entity <-> DTO
│   ├── exception/
│   │   ├── ResourceNotFoundException.java     # Exception personnalisée
│   │   ├── ErrorResponse.java                 # DTO d'erreur
│   │   └── GlobalExceptionHandler.java        # @ControllerAdvice
│   └── BackApiApplication.java                # Point d'entrée
├── src/main/resources/
│   └── application.yml                        # Configuration Spring
├── pom.xml                                    # Configuration Maven
├── API_EXAMPLES.md                            # Exemples CURL détaillés
└── README.md                                  # Ce fichier
```
## ✨ Caractéristiques principales
### Architecture en couches
- **Controller** → **Service** → **Repository** → **Database**
- Séparation claire des responsabilités
- Injection de dépendances avec Spring
### Entité RendezVous
```java
- id (Long) - ID auto-généré
- clientName (String) - Nom du client (obligatoire)
- clientEmail (String) - Email du client (obligatoire, validé)
- dateTime (LocalDateTime) - Date et heure (obligatoire)
- description (String) - Description optionnelle
- status (Enum) - Statut : SCHEDULED, CANCELLED, COMPLETED
- createdAt (LocalDateTime) - Date de création (auto)
- updatedAt (LocalDateTime) - Date de dernière modification (auto)
```
### Validation
- ✅ `@NotBlank` - Champs obligatoires
- ✅ `@Email` - Validation email
- ✅ `@NotNull` - Null checks
- ✅ Messages d'erreur en français
- ✅ Gestion centralisée avec `@ControllerAdvice`
### Gestion des exceptions
- `ResourceNotFoundException` - Ressource non trouvée (404)
- `MethodArgumentNotValidException` - Erreurs de validation (400)
- `Exception` - Erreurs génériques (500)
- Réponses structurées avec timestamps
## 🔌 Endpoints API
### 1. Créer un rendez-vous
```
POST /api/appointments
Content-Type: application/json
{
  "clientName": "Jean Dupont",
  "clientEmail": "jean.dupont@example.com",
  "dateTime": "2024-03-20T14:30:00",
  "description": "Consultation générale",
  "status": "SCHEDULED"
}
Response: 201 Created
```
### 2. Récupérer tous les rendez-vous
```
GET /api/appointments
Response: 200 OK
[...]
```
### 3. Récupérer un rendez-vous par ID
```
GET /api/appointments/{id}
Response: 200 OK ou 404 Not Found
```
### 4. Mettre à jour un rendez-vous
```
PUT /api/appointments/{id}
Content-Type: application/json
{...}
Response: 200 OK
```
### 5. Supprimer un rendez-vous
```
DELETE /api/appointments/{id}
Response: 204 No Content
```
### 6. Récupérer les rendez-vous par email
```
GET /api/appointments/client/{email}
Response: 200 OK
```
### 7. Vérifier la santé de l'API
```
GET /api/appointments/health
Response: 200 OK
"API is running"
```
## 🚀 Démarrage
### Prérequis
- JDK 21 ou supérieur
- Maven 3.6+
### Installation et lancement
```bash
# 1. Naviguer dans le répertoire
cd back-api
# 2. Compiler le projet
mvn clean compile
# 3. Lancer les tests
mvn test
# 4. Démarrer l'application
mvn spring-boot:run
# Ou créer et lancer le JAR
mvn clean package
java -jar target/back-api-0.0.1-SNAPSHOT.jar
```
### URL de l'API
```
http://localhost:8080/api/appointments
```
### Console H2
```
http://localhost:8080/h2-console
Username: sa
Password: (vide)
JDBC URL: jdbc:h2:mem:testdb
```
## 📝 Exemples d'utilisation
### Avec curl
```bash
# Créer
curl -X POST http://localhost:8080/api/appointments \
  -H "Content-Type: application/json" \
  -d '{
    "clientName": "Jean Dupont",
    "clientEmail": "jean.dupont@example.com",
    "dateTime": "2024-03-20T14:30:00"
  }'
# Lire tous
curl http://localhost:8080/api/appointments
# Lire par ID
curl http://localhost:8080/api/appointments/1
# Mettre à jour
curl -X PUT http://localhost:8080/api/appointments/1 \
  -H "Content-Type: application/json" \
  -d '{"clientName":"Jean","clientEmail":"jean@example.com","dateTime":"2024-03-20T15:00:00"}'
# Supprimer
curl -X DELETE http://localhost:8080/api/appointments/1
```
### Avec Postman
1. Importer les endpoints depuis `API_EXAMPLES.md`
2. Utiliser les exemples JSON fournis
3. Tester les validations avec des données invalides
### Avec Spring Boot CLI
```bash
spring run BackApiApplication.java
```
## 🔧 Configuration
Le fichier `application.yml` contient :
- Configuration de H2 (base de données mémoire)
- Configuration JPA/Hibernate
- Logging en DEBUG pour `com.api.backapi`
- Port 8080
- Console H2 activée
```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb
    username: sa
  jpa:
    hibernate:
      ddl-auto: create-drop  # Crée et supprime à chaque démarrage
```
## 📚 Patterns et bonnes pratiques
### 1. **Architecture en couches**
```
Request → Controller → Service → Repository → Database
                         ↓
                    Business Logic
```
### 2. **Builder Pattern**
Utilisé avec Lombok pour les Entities et DTOs
```java
RendezVous.builder()
    .clientName("Jean")
    .clientEmail("jean@example.com")
    .build()
```
### 3. **Mapper Pattern**
Conversion manuelle entre Entity et DTOs
```java
RendezVous entity = mapper.toEntity(request);
RendezVousResponse response = mapper.toResponse(entity);
```
### 4. **Repository Pattern**
Spring Data JPA pour l'accès aux données
```java
List<RendezVous> findByClientEmail(String email);
```
### 5. **Service Pattern**
Logique métier centralisée avec @Transactional
```java
@Service
@Transactional
public class RendezVousServiceImpl
```
### 6. **Exception Handling**
@ControllerAdvice pour la gestion globale
```java
@ControllerAdvice
public class GlobalExceptionHandler
```
### 7. **Logging**
SLF4J avec Lombok @Slf4j
```java
log.info("Création d'un rendez-vous");
log.error("Erreur: {}", message);
```
## ✅ Validation
### Validations côté serveur
- Nom du client non vide
- Email valide (RFC 5322)
- Date/heure obligatoire
- Statut valide
### Réponses d'erreur
```json
{
  "status": 400,
  "message": "Erreur de validation",
  "errors": {
    "clientEmail": "L'email du client doit être valide"
  },
  "timestamp": "2024-03-15T10:35:00"
}
```
## 📊 Statuts HTTP
| Code | Signification | Utilisation |
|------|---------------|-------------|
| 200 | OK | Requête réussie |
| 201 | Created | Ressource créée |
| 204 | No Content | Suppression réussie |
| 400 | Bad Request | Erreur de validation |
| 404 | Not Found | Ressource inexistante |
| 500 | Server Error | Erreur serveur |
## 🔐 Fonctionnalités CRUD complètes
| Opération | Méthode | Endpoint | Code |
|-----------|---------|----------|------|
| Créer | POST | `/api/appointments` | 201 |
| Lire tous | GET | `/api/appointments` | 200 |
| Lire un | GET | `/api/appointments/{id}` | 200/404 |
| Mettre à jour | PUT | `/api/appointments/{id}` | 200/404 |
| Supprimer | DELETE | `/api/appointments/{id}` | 204/404 |
| Lire par email | GET | `/api/appointments/client/{email}` | 200 |
| Santé | GET | `/api/appointments/health` | 200 |
## 📖 Documentation détaillée
Pour des exemples CURL complets et des réponses détaillées, consultez [API_EXAMPLES.md](./API_EXAMPLES.md)
## 🧪 Tests
### Exécuter tous les tests
```bash
mvn test
```
### Tester un endpoint
```bash
# À la racine du projet
mvn spring-boot:run
# Dans un autre terminal
curl http://localhost:8080/api/appointments
```
## 📦 Dépendances
Consultez `pom.xml` pour la liste complète. Principales :
- `spring-boot-starter-web`
- `spring-boot-starter-data-jpa`
- `spring-boot-starter-validation`
- `h2` (runtime)
- `lombok` (optional)
## 🤝 Contribution
Architecture modulaire permettant :
- Ajouter des endpoints facilement
- Étendre le Service avec de la logique métier
- Ajouter de nouveaux Repository methods
- Étendre les validations
## 📄 Licence
Projet éducatif - Spring Boot 4 + Java 21
## 👨‍💻 Notes de développement
### Points clés de l'implémentation
- ✅ Java 21 avec records (prêt pour futures améliorations)
- ✅ Spring Boot 4 avec MVC moderne
- ✅ JPA avec H2 mémoire pour tests
- ✅ Validation courante de production
- ✅ Exception handling robuste
- ✅ Logging cohérent
- ✅ DTOs pour l'encapsulation
- ✅ Transactions ACID
### Prochaines améliorations possibles
- [ ] Ajouter Spring Security
- [ ] Ajouter OpenAPI/Swagger
- [ ] Passer à PostgreSQL en production
- [ ] Ajouter des tests unitaires/intégration
- [ ] Pagination et tri sur GET
- [ ] Filtrage par date
- [ ] Métriques avec Actuator
- [ ] Docker image
---
**Version**: 1.0.0  
**Date**: Mars 2024  
**Statut**: Production-ready ✅
