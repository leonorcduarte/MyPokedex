# MyPokedex
## About MyPokedex
MyPokedex is a small demo app that lists Pokémons allowing the user to find out "Who's that Pokémon" and learn more about its most relevant characteristics.
This application is prepared for different screens of different sizes as well as screen orientation changes.

MyPokedex is built on MVVM (model-view-viewmodel), which allows you to separate developments from the backend logic (the model) of the graphical interface (the view); and so the ViewModel deals with the view's display logic. Furthermore, Hilt is used as dependency injection library to speed up the process of creating and providing dependencies. In order to be able to consult this information about Pokémons, Retrofit is used to make the connection between the app and the RESTful services of the API [pokeapi](https://pokeapi.co/ "Poke API").

## MyPokedex previews
### Splash Screen
![SplahScreen](/screenshots/Splash.png)
### Pokémon List Screen
![ListScreen](/screenshots/List.png) | ![ListScreenWithImage](/screenshots/ListWithPokémon.png)
### Pokémon Detail Screen
![Detail](/screenshots/Detail.png) | ![Detail](/screenshots/DetailWithEvolutionChain.png)
#### Pokémon Detail Screen on Large screen
![Detail](/screenshots/DetailOnLargeScreen.png)
