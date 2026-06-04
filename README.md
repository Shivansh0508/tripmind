# TripMind - AI-Powered Travel Itinerary Planner
 
> Plan your perfect trip in seconds with AI-generated day-by-day itineraries, real hotel & restaurant recommendations, and personalized cost estimates in INR.
 
**Live Demo:** [tripmind-production-cc7b.up.railway.app](https://tripmind-production-cc7b.up.railway.app)
 
---

## Features
 
- **AI Itinerary Generation** - Real AI generates complete day-by-day travel plans based on your cities, budget, family size, and food preferences
- **JWT Authentication** - Secure user registration and login with industry-standard JWT tokens
- **Redis Caching** - Popular routes cached for instant responses (reduced latency from ~3s to <50ms)
- **MongoDB Storage** - All user profiles and trip itineraries persisted in MongoDB
- **REST APIs** - Full RESTful backend with documented endpoints
- **CI/CD Pipeline** - Automated builds and deployments via GitHub Actions + Railway
---

## Tech Stack
 
| Layer | Technology |
|-------|-----------|
| Backend | Java 17, Spring Boot 3.5 |
| Database | MongoDB (Spring Data MongoDB) |
| Cache | Redis (Spring Cache) |
| Auth | JWT (jjwt 0.11.5) |
| AI | OpenRouter API (Mistral) |
| Frontend | Thymeleaf, HTML5, CSS3 |
| Deployment | Railway, Docker |
| Version Control | Git, GitHub |
 
---

## Architecture
 
```
┌─────────────────┐     ┌──────────────────┐     ┌─────────────┐
│   React/HTML    │────▶│  Spring Boot API  │────▶│   MongoDB   │
│   Thymeleaf     │     │   (Port 8080)     │     │  (tripmind) │
└─────────────────┘     └──────────────────┘     └─────────────┘
                                │                        
                    ┌───────────┼───────────┐           
                    ▼           ▼           ▼           
             ┌──────────┐ ┌─────────┐ ┌──────────┐    
             │  Redis   │ │   JWT   │ │OpenRouter│    
             │  Cache   │ │  Auth   │ │  AI API  │    
             └──────────┘ └─────────┘ └──────────┘    
```
 
## API Endpoints
 
### Authentication
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register new user |
| POST | `/api/auth/login` | Login and get JWT token |
 
### Trips
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/trips` | Create new AI-generated trip |
| GET | `/api/trips` | Get all trips for logged-in user |
| DELETE | `/api/trips/{id}` | Delete a trip |
 
### Pages
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/` | Landing page |
| GET | `/plan` | Trip planning form |
| GET | `/my-trips` | User dashboard |
| GET | `/trips/{id}` | View specific itinerary |
 
---

## Getting Started
 
### Prerequisites
- Java 17+
- MongoDB (local or Atlas)
- Redis
- Maven
### Installation
 
```bash
# Clone the repository
git clone https://github.com/Shivansh0508/tripmind.git
cd tripmind/tripmind
 
# Set environment variables
cp src/main/resources/application.properties.example src/main/resources/application.properties
# Edit application.properties with your keys
 
# Run the application
./mvnw spring-boot:run
```

### Environment Variables
 
```properties
spring.data.mongodb.uri=mongodb://localhost:27017/tripmind
spring.data.redis.host=localhost
spring.data.redis.port=6379
app.jwt.secret=your-secret-key
app.jwt.expiration=86400000
openrouter.api.key=your-openrouter-key
```

## How It Works
 
1. **User registers/logs in** → JWT token issued
2. **User fills trip form** → cities, days, family size, budget, food preference
3. **Cache check** → Redis checks if same query was made before
4. **AI generation** → If not cached, OpenRouter AI generates full itinerary
5. **Cache store** → Response cached in Redis for 24 hours
6. **MongoDB save** → Trip saved to user's profile
7. **Display** → Beautiful itinerary shown with day-by-day breakdown
---
 
## Project Structure
 
```
src/main/java/com/example/tripmind/
├── config/          # Security, Redis configuration
├── controller/      # REST API + page controllers
├── dto/             # Request/Response data classes
├── model/           # MongoDB document models
├── repository/      # Database query interfaces
├── service/         # Business logic (AI, Trip, etc.)
└── util/            # JWT utility
 
src/main/resources/
├── templates/       # Thymeleaf HTML pages
└── application.properties
```

 ## Key Technical Highlights
 
**Redis Caching Strategy**
```java
@Cacheable(value = "itineraries",
           key = "#req.cities + '_' + #req.numberOfDays + '_' + #req.budgetLevel")
public String generateItinerary(TripRequest req) { ... }
```
Same city+days+budget combination returns from cache - zero AI API calls after first request.
 
**JWT Authentication Flow**
- Passwords stored as BCrypt hashes
- JWT tokens expire after 24 hours
- Stateless session management (STATELESS policy)
**MongoDB Document Design**
- `users` collection: email, hashed password, saved trip IDs
- `trips` collection: userId (FK), cities, preferences, AI-generated itinerary
---

## Screenshots
 
> Register → Plan → AI generates → View itinerary → Save to dashboard
 
---

## Built By
 
**Shivansh Saxena** - B.Tech Computer Science, BITS Pilani (2027)
 
[![LinkedIn](https://img.shields.io/badge/LinkedIn-Connect-blue)](https://www.linkedin.com/in/shivansh-saxena-8b9880316/)
[![GitHub](https://img.shields.io/badge/GitHub-Follow-black)](https://github.com/Shivansh0508)
 
---

## License
 
MIT License - feel free to use this project for learning and portfolio purposes.
