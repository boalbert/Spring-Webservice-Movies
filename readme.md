# Webservices och integrationer -  Lab 2
## Upppgift
Använda ett ramverk (Spring Boot) för att skapa webservices.

Uppgift: Bestäm något som du vill lagra i en databas. (Böcker, Bilar, Djur, Växter, Produkter, Länder...) Skapa en RestController i spring som implementerar GET(one, all), POST, PUT, PATCH och DELETE Lägg även till möjlighet att söka med GET search=searchterm Helt automatgenererade RestApier är inte godkänt! Skapa Tester för alla funktionerna på kontrollern. @WebMvcTest
## Om
- Laddar in data till databas vid uppstart (Top 10 IMDB filmer).

- Kör på on port 5050 default. MariaDB använder port 3306.

- Använder @WebMvcTests.

- `PATCH`-metod ändrar "imdbRating". Måste vara i spannet 0.0 - 10.0, annars kastas ResponeStatusException.

- `/movies/search?title=MovieTitle` - Söker med 'title contains'.

## Endpoints

- `/movies/` - (GET, POST)

- `/movies/{id}` - (GET, PUT, PATCH, DELETE)

- `/movies/search?title=MovieTitle` - (GET)

## Installation
Klona projektet.

Kör applikationen via Docker:
1. `mvn clean package`
3. `docker-compose up --build -d`