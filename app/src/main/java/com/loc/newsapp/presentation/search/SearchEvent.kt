package com.loc.newsapp.presentation.search

//Sealed classes are used to represent restricted class hierarchies.
sealed class SearchEvent {

//    This data class is a subclass of SearchEvent.
    data class UpdateSearchQuery(val searchQuery : String): SearchEvent()

    object SearchNews : SearchEvent()
}