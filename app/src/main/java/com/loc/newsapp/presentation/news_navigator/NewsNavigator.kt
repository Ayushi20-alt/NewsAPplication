package com.loc.newsapp.presentation.news_navigator

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.loc.newsapp.R
import com.loc.newsapp.domain.model.Article
import com.loc.newsapp.presentation.bookmark.BookMarkScreen
import com.loc.newsapp.presentation.bookmark.BookMarkViewModel
import com.loc.newsapp.presentation.details.DetailViewModel
import com.loc.newsapp.presentation.details.DetailsEvent
import com.loc.newsapp.presentation.details.DetailsScreen
import com.loc.newsapp.presentation.home.HomeScreen
import com.loc.newsapp.presentation.home.HomeViewModel
import com.loc.newsapp.presentation.news_navigator.components.BottomNavigationItem
import com.loc.newsapp.presentation.news_navigator.components.NewsBottomNavigation
import com.loc.newsapp.presentation.nvgraph.Route
import com.loc.newsapp.presentation.search.SearchScreen
import com.loc.newsapp.presentation.search.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsNavigator(){

    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.ic_home, text = "Home"),
            BottomNavigationItem(icon = R.drawable.ic_search, text = "Search"),
            BottomNavigationItem(icon = R.drawable.ic_bookmark, text = "Bookmark")
        )
    }

    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableStateOf(0)
    }

    selectedItem = remember(key1 = backStackState) {
        when(backStackState?.destination?.route){
            Route.HomeScreen.route -> 0
            Route.SearchScreen.route -> 1
            Route.BookMarkScreen.route -> 2
            else -> 0
        }
    }

    //Hide the bottom navigation when the user is in the details screen
    val isBottomBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == Route.HomeScreen.route ||
                backStackState?.destination?.route == Route.SearchScreen.route ||
                backStackState?.destination?.route == Route.BookMarkScreen.route
    }

//    The Scaffold is part of the Compose library and provides a layout structure
//    that includes an AppBar, a BottomAppBar (optional), and the main content area.
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isBottomBarVisible){
            NewsBottomNavigation(
                item = bottomNavigationItems,
                selected = selectedItem,
                onItemClick = { index ->
                    when (index) {
                        0 -> navigateToTab(
                            navController = navController,
                            route = Route.HomeScreen.route
                        )

                        1 -> navigateToTab(
                            navController = navController,
                            route = Route.SearchScreen.route
                        )

                        2 -> navigateToTab(
                            navController = navController,
                            route = Route.BookMarkScreen.route
                        )
                    }
                }
            )
        }
        }
    ) {
       val bottomPadding = it.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ){
            // make a composable function
            composable(route = Route.HomeScreen.route){ backStackEntry ->
                val viewModel: HomeViewModel = hiltViewModel()
                val articles = viewModel.news.collectAsLazyPagingItems()
                HomeScreen(
                    articles = articles,
                    navigateToSearch = {
                         navigateToTab(
                             navController = navController,
                             route =  Route.SearchScreen.route
                         )
                    },
                    navigateTodetails = { article ->
                        navigateToDetails(
                            navController = navController,
                            article = article
                        )
                    }
                )
            }

            composable(route = Route.SearchScreen.route){
                val viewModel: SearchViewModel = hiltViewModel()
                val state = viewModel.state.value
                OnBackClickStateSaver(navController = navController)
                SearchScreen(state = state, event = viewModel::onEvent,
                    navigateToDetail = {article ->
                        navigateToDetails(
                        navController = navController,
                        article = article
                    ) })
            }

            composable(route = Route.DetailsScreen.route){
                val viewModel: DetailViewModel = hiltViewModel()
                if(viewModel.sideEffect != null){
                    Toast.makeText( LocalContext.current,viewModel.sideEffect, Toast.LENGTH_SHORT).show()
                    viewModel.onEvent(DetailsEvent.RemoveSideEffect)
                }
                navController.previousBackStackEntry?.savedStateHandle?.get<Article?>("article")?.let { article ->
                    DetailsScreen(
                        article = article,
                        event = viewModel::onEvent,
                        navigateUp = { navController.navigateUp() },
                    )
                }
            }

            composable(route = Route.BookMarkScreen.route){
                val viewModel: BookMarkViewModel = hiltViewModel()
                val state = viewModel.state.value
                OnBackClickStateSaver(navController = navController)
                BookMarkScreen(
                    state = state,
                    navigateToDetails = { article ->
                        navigateToDetails(navController = navController, article = article)
                    }
                )
            }

        }
    }

}

@Composable
fun OnBackClickStateSaver(navController: NavHostController) {
    BackHandler(true) {
        navigateToTab(
            navController = navController,
            route = Route.HomeScreen.route
        )
    }
}

// we will create a helper function that will naviagte to the tab
private fun navigateToTab(navController: NavController, route : String){
    // we wanna pop to the backstack until we reach the homescreen
    navController.navigate(route){
        navController.graph.startDestinationRoute?.let { homeScreen ->
            popUpTo(homeScreen){
                saveState = true
            }
            restoreState = true
            // lets say u r at the homescreen and clicked on it many times so it does not create the instance of the screen and
            // it will launch the same screen
            launchSingleTop = true
        }
    }
}

private fun navigateToDetails(navController: NavController, article: Article){
     // it is quite different because we want to share the article object to the next screen
    // but we can only pass the primitive data type as string, bool so if we want to pass the
    // object we can do it with the help of backstackentry and make data class parcelize
    navController.currentBackStackEntry?.savedStateHandle?.set("article",article)
    navController.navigate(
        route = Route.DetailsScreen.route
    )
}
