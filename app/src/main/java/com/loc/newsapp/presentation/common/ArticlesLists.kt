package com.loc.newsapp.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.loc.newsapp.domain.model.Article
import com.loc.newsapp.presentation.Dimens.ExtraSmallPadding
import com.loc.newsapp.presentation.Dimens.ExtraSmallPadding2
import com.loc.newsapp.presentation.Dimens.MediumPadding1

@Composable
fun ArticlesList(
    modifier: Modifier = Modifier,
    articles : LazyPagingItems<Article>,
    onClick: (Article) -> Unit
){
     val handlePagingResult = handlePagingResult(articles = articles)
     if (handlePagingResult){
         LazyColumn(modifier.fillMaxSize(),
         verticalArrangement = Arrangement.spacedBy(MediumPadding1),
         contentPadding = PaddingValues(all = ExtraSmallPadding)
         ){
             items(count = articles.itemCount) {
                 // if articles is not null we will show the article card for that
                 articles[it].let {
                     if (it != null) {
                         ArticleCard(article = it, onCLick = {onClick(it)})
                     }
                 }
             }
         }
     }
}

// for handling the state of the paging
// if this is true we know that the articles retrived sucessfully
//In Jetpack Compose, when working with paging using the Paging library, you can use the LoadState class to
//represent the loading state of a PagingData stream. The LoadState class has different states that reflect
//the loading status of the data, such as loading, error, and not loading.

@Composable
fun handlePagingResult(articles : LazyPagingItems<Article>):Boolean{
     val loadState = articles.loadState
     val error = when{
         loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
         loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
         loadState.append is LoadState.Error -> loadState.append as LoadState.Error
         else -> null
     }
     return when{
         loadState.refresh is LoadState.Loading -> {
           ShimmerEffect()
             false
         }
         error != null ->{
             EmptyScreen()
             false
         }
         else -> {
             true
         }
     }
}

@Composable
private fun ShimmerEffect(){
    Column(verticalArrangement = Arrangement.spacedBy(MediumPadding1)) {
        repeat(10){
            ArticleCardShimmerEffect(
                modifier = Modifier.padding(horizontal = MediumPadding1)
            )
        }
    }
}