package com.loc.newsapp.presentation.nvgraph


sealed class Route(
    val route : String
){

    object OnBoardingScreen : Route(route = "onBoardingScreen")
    object HomeScreen : Route(route = "homeScreen")
    object SearchScreen : Route(route = "searchScreen")
    object BookMarkScreen : Route(route = "bookMarkScreen")
    object DetailsScreen : Route(route = "DetailsScreen")
    object AppStartNavigation : Route(route = "appStartNavigation")
    object NewsNavigation : Route(route = "newsNaviagtion")
    object NewsNavigatorScreen : Route(route = "newsNavigator")

}
