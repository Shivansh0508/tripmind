# TripMind — AI-Powered Travel Itinerary Planner
 
> Plan your perfect trip in seconds with AI-generated day-by-day itineraries, real hotel & restaurant recommendations, and personalized cost estimates in INR.
 
**Live Demo:** [tripmind-production-cc7b.up.railway.app](https://tripmind-production-cc7b.up.railway.app)
 
---

## Features
 
- **AI Itinerary Generation** — Real AI generates complete day-by-day travel plans based on your cities, budget, family size, and food preferences
- **JWT Authentication** — Secure user registration and login with industry-standard JWT tokens
- **Redis Caching** — Popular routes cached for instant responses (reduced latency from ~3s to <50ms)
- **MongoDB Storage** — All user profiles and trip itineraries persisted in MongoDB
- **REST APIs** — Full RESTful backend with documented endpoints
- **CI/CD Pipeline** — Automated builds and deployments via GitHub Actions + Railway
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
