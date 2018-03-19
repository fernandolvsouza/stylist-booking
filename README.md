# stylist-booking
Service for booking meetings with amazing stylists

## Tech Stack
* Spring boot 2.0.0.RELEASE
* hsqldb for database
* junit for tests

## Running the application
```
git clone git@github.com:fernandolvsouza/stylist-booking.git
cd stylist-booking
mvn package
jar -jar target/stylist-booking-1.0-SNAPSHOT.jar
```

## Endpoints
http://localhost:8080/swagger-ui.html
```
POST /api/v1/customer
POST /api/v1/stylist
POST /api/v1/stylist/{stylistId}/ready
POST /api/v1/stylist/{stylistId}/sick
POST /api/v1/stylist/{stylistId}/holiday
POST /api/v1/stylist/{stylistId}/offboarding
POST /api/v1/stylist-booking
GET /api/v1/stylist-booking
POST /api/v1/stylist-booking-batch-task
GET /api/v1/stylist-availability
```


## Issues and Ideas
* Optimistic locking when updating stylist state.
* Create unique index for (stylist/Timelot) in booking, to avoid overbooking
* Check if customer already has a booking
* Create custom exception handler for display nice responses on restful api
* Create test for verify that when one booking fails, it does not rollback the whole batch operation 
* More Javadocs
* About sick and holiday notification, should be considered as planned unavailabilities.
* Create separate service of stylist availability that would receive events when sick-note, holiday-notes, booking-notes were registered. This service would be able to calculate availability in time considering that all those events have a period as properties. Like: Stylist is available for next week, on Monday, at 3 pm? 


