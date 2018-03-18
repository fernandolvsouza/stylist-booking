# stylist-booking
Service for booking meetings with amazing stylists

## Tech Stack
* Spring boot 2.0.0.RELEASE
* hsqldb for database
* junit for tests

## Endpoints
http://localhost:8080/swagger-ui.htm

* POST /api/v1/customer
* POST /api/v1/stylist
* POST /api/v1/stylist/{stylistId}/ready
* POST /api/v1/stylist/{stylistId}/sick
* POST /api/v1/stylist/{stylistId}/holiday
* POST /api/v1/stylist/{stylistId}/offboarding
* POST /api/v1/stylist-booking
* GET /api/v1/stylist-booking
* POST /api/v1/stylist-booking-batch-task
* GET /api/v1/stylist-availability



## Issues
* Optmistic locking
* More Javadocs
* Consider only ready stylist in /api/v1/stylist-availability

