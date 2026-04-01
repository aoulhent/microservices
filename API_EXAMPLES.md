# API REST - Gestion de Rendez-vous
## Informations sur l'API
- **Base URL**: `http://localhost:8080`
- **Version**: 1.0.0
- **Architecture**: Architecture en couches (Controller -> Service -> Repository)
## Endpoints disponibles
### 1. Créer un rendez-vous
**POST** `/api/appointments`
**Request:**
```bash
curl -X POST http://localhost:8080/api/appointments \
  -H "Content-Type: application/json" \
  -d '{
    "clientName": "Jean Dupont",
    "clientEmail": "jean.dupont@example.com",
    "dateTime": "2024-03-20T14:30:00",
    "description": "Consultation générale",
    "status": "SCHEDULED"
  }'
```
**Response (201 Created):**
```json
{
  "id": 1,
  "clientName": "Jean Dupont",
  "clientEmail": "jean.dupont@example.com",
  "dateTime": "2024-03-20T14:30:00",
  "description": "Consultation générale",
  "status": "SCHEDULED",
  "createdAt": "2024-03-15T10:15:30",
  "updatedAt": null
}
```
---
### 2. Récupérer tous les rendez-vous
**GET** `/api/appointments`
**Request:**
```bash
curl -X GET http://localhost:8080/api/appointments \
  -H "Accept: application/json"
```
**Response (200 OK):**
```json
[
  {
    "id": 1,
    "clientName": "Jean Dupont",
    "clientEmail": "jean.dupont@example.com",
    "dateTime": "2024-03-20T14:30:00",
    "description": "Consultation générale",
    "status": "SCHEDULED",
    "createdAt": "2024-03-15T10:15:30",
    "updatedAt": null
  },
  {
    "id": 2,
    "clientName": "Marie Martin",
    "clientEmail": "marie.martin@example.com",
    "dateTime": "2024-03-21T15:00:00",
    "description": "Suivi",
    "status": "SCHEDULED",
    "createdAt": "2024-03-15T10:20:00",
    "updatedAt": null
  }
]
```
---
### 3. Récupérer un rendez-vous par ID
**GET** `/api/appointments/{id}`
**Request:**
```bash
curl -X GET http://localhost:8080/api/appointments/1 \
  -H "Accept: application/json"
```
**Response (200 OK):**
```json
{
  "id": 1,
  "clientName": "Jean Dupont",
  "clientEmail": "jean.dupont@example.com",
  "dateTime": "2024-03-20T14:30:00",
  "description": "Consultation générale",
  "status": "SCHEDULED",
  "createdAt": "2024-03-15T10:15:30",
  "updatedAt": null
}
```
**Response (404 Not Found):**
```json
{
  "status": 404,
  "message": "Rendez-vous avec l'ID 999 non trouvé",
  "error": "Resource Not Found",
  "timestamp": "2024-03-15T10:25:00"
}
```
---
### 4. Mettre à jour un rendez-vous
**PUT** `/api/appointments/{id}`
**Request:**
```bash
curl -X PUT http://localhost:8080/api/appointments/1 \
  -H "Content-Type: application/json" \
  -d '{
    "clientName": "Jean Dupont",
    "clientEmail": "jean.dupont@example.com",
    "dateTime": "2024-03-20T16:00:00",
    "description": "Consultation générale - Reportée",
    "status": "SCHEDULED"
  }'
```
**Response (200 OK):**
```json
{
  "id": 1,
  "clientName": "Jean Dupont",
  "clientEmail": "jean.dupont@example.com",
  "dateTime": "2024-03-20T16:00:00",
  "description": "Consultation générale - Reportée",
  "status": "SCHEDULED",
  "createdAt": "2024-03-15T10:15:30",
  "updatedAt": "2024-03-15T10:30:00"
}
```
---
### 5. Supprimer un rendez-vous
**DELETE** `/api/appointments/{id}`
**Request:**
```bash
curl -X DELETE http://localhost:8080/api/appointments/1
```
**Response (204 No Content):**
```
(pas de contenu)
```
---
### 6. Récupérer les rendez-vous par email
**GET** `/api/appointments/client/{email}`
**Request:**
```bash
curl -X GET "http://localhost:8080/api/appointments/client/jean.dupont@example.com" \
  -H "Accept: application/json"
```
**Response (200 OK):**
```json
[
  {
    "id": 1,
    "clientName": "Jean Dupont",
    "clientEmail": "jean.dupont@example.com",
    "dateTime": "2024-03-20T14:30:00",
    "description": "Consultation générale",
    "status": "SCHEDULED",
    "createdAt": "2024-03-15T10:15:30",
    "updatedAt": null
  }
]
```
---
### 7. Vérifier la santé de l'API
**GET** `/api/appointments/health`
**Request:**
```bash
curl -X GET http://localhost:8080/api/appointments/health
```
**Response (200 OK):**
```
API is running
```
---
## Erreurs de validation
### Exemple d'erreur de validation
**Request:**
```bash
curl -X POST http://localhost:8080/api/appointments \
  -H "Content-Type: application/json" \
  -d '{
    "clientName": "",
    "clientEmail": "email-invalide",
    "dateTime": null,
    "description": "Test"
  }'
```
**Response (400 Bad Request):**
```json
{
  "status": 400,
  "message": "Erreur de validation",
  "errors": {
    "clientName": "Le nom du client est obligatoire",
    "clientEmail": "L'email du client doit être valide",
    "dateTime": "La date et l'heure du rendez-vous sont obligatoires"
  },
  "timestamp": "2024-03-15T10:35:00"
}
```
---
## Statuts HTTP
| Code | Description |
|------|-------------|
| 200 | OK - Requête réussie |
| 201 | Created - Ressource créée |
| 204 | No Content - Succès sans contenu |
| 400 | Bad Request - Erreur de validation |
| 404 | Not Found - Ressource non trouvée |
| 500 | Internal Server Error - Erreur serveur |
---
## Énumérations
### AppointmentStatus
- `SCHEDULED` - Rendez-vous planifié
- `CANCELLED` - Rendez-vous annulé
- `COMPLETED` - Rendez-vous complété
---
## Structure du projet
```
back-api/
├── src/
│   ├── main/
│   │   ├── java/com/api/backapi/
│   │   │   ├── controller/          # Couche présentation
│   │   │   │   └── RendezVousController.java
│   │   │   ├── service/              # Couche métier
│   │   │   │   ├── RendezVousService.java
│   │   │   │   └── RendezVousServiceImpl.java
│   │   │   ├── repository/           # Couche accès données
│   │   │   │   └── RendezVousRepository.java
│   │   │   ├── entity/               # Entités JPA
│   │   │   │   ├── RendezVous.java
│   │   │   │   └── AppointmentStatus.java
│   │   │   ├── dto/                  # Objets de transfert
│   │   │   │   ├── RendezVousRequest.java
│   │   │   │   └── RendezVousResponse.java
│   │   │   ├── mapper/               # Convertisseurs
│   │   │   │   └── RendezVousMapper.java
│   │   │   ├── exception/            # Gestion erreurs
│   │   │   │   ├── ResourceNotFoundException.java
│   │   │   │   ├── ErrorResponse.java
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   └── BackApiApplication.java
│   │   └── resources/
│   │       ├── application.yml
│   │       ├── static/
│   │       └── templates/
│   └── test/
│       └── java/com/api/backapi/
│           └── BackApiApplicationTests.java
├── pom.xml
└── README.md
```
---
## Dépendances principales
- **Spring Boot 4.0.3**
- **Java 21**
- **Spring Data JPA** - ORM et persistance
- **Spring Web** - REST API
- **Spring Validation** - Validation des données
- **H2 Database** - Base de données embarquée
- **Lombok** - Réduction du boilerplate
- **Hibernate** - JPA Implementation
---
## Configuration
### Base de données H2
- **URL**: `jdbc:h2:mem:testdb`
- **Console H2**: http://localhost:8080/h2-console
- **Username**: sa
- **Password**: (vide)
- **Mode création**: `create-drop` (crée et supprime à chaque démarrage)
### Ports
- **API**: Port 8080
- **H2 Console**: http://localhost:8080/h2-console
---
## Test des endpoints
### Avec curl
```bash
# Créer
curl -X POST http://localhost:8080/api/appointments \
  -H "Content-Type: application/json" \
  -d '{"clientName":"Test","clientEmail":"test@example.com","dateTime":"2024-03-20T14:30:00"}'
# Lire tous
curl -X GET http://localhost:8080/api/appointments
# Lire par ID
curl -X GET http://localhost:8080/api/appointments/1
# Mettre à jour
curl -X PUT http://localhost:8080/api/appointments/1 \
  -H "Content-Type: application/json" \
  -d '{"clientName":"Test","clientEmail":"test@example.com","dateTime":"2024-03-20T15:00:00"}'
# Supprimer
curl -X DELETE http://localhost:8080/api/appointments/1
```
### Avec Postman
1. Créer une collection "API Rendez-vous"
2. Ajouter les requests avec les endpoints ci-dessus
3. Utiliser les JSON du guide pour les bodies
---
## Notes d'implémentation
- ✅ Architecture en couches
- ✅ Séparation Entity/DTO
- ✅ Validation avec Jakarta Validation
- ✅ Exception handling avec @ControllerAdvice
- ✅ Logging avec SLF4J/Logback
- ✅ Lombok pour réduire le code
- ✅ Transactions avec @Transactional
- ✅ Patterns: Builder, Mapper, Service
- ✅ Database H2 (mode mémoire)
- ✅ ResponseEntity pour les réponses HTTP
