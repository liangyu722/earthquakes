# Earthquakes

### Introduction

An Android application that will read JSON data from an earthquakes API and populate a list within the main 
Activity/View Controller.  Each earthquake will occupy one item in the list.  Please pick some identifying 
attributes from each earthquake to display.  If the magnitude of the quake is equal or greater than 8.0, 
then distinguish that visually from other earthquakes on the list.  Tapping an earthquake in the list should 
open a secondary screen with the location depicted on a map.

URL for the earthquake data:  http://api.geonames.org/earthquakesJSON?formatted=true&north=44.1&south=-9.9&east=-22.4&west=55.2&username=mkoppelman

### Architecture
DataBinding / XML <br />
Activity/Fragment <br />
ViewModel <br />
Repository <br />
Service <br />

### Technologies
Dagger2<br />
Retrofit<br />
Coroutines<br />
Jetpack Navigation<br />
Mockk<br />
Kluent<br />
ConstraintLayout <br />
CardView <br />
RecyclerView <br />
Play Service Map<br />

### Out of Scope
Changing input parameters for earthquake URL. Username of URL is treated as an API key which is included in Retrofit inteceptor, the position of 4 direction is added as a default parameter for the Service.

### Improvement 
Additional UseCase layer between ViewModel and Repository layer to handle business logic

Room database for local caching
