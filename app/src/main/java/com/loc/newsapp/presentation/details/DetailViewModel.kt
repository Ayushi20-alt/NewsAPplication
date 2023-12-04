package com.loc.newsapp.presentation.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loc.newsapp.domain.model.Article
import com.loc.newsapp.domain.usecases.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val useCases: NewsUseCases
) : ViewModel() {

//    The SideEffect function is used to execute code that has side
//    effects during the composition process. It doesn't create a
//    coroutine and is suitable for synchronous side effects.

    var sideEffect by mutableStateOf<String?>(null)
        private set

   fun onEvent(event : DetailsEvent){
       when(event){
           is DetailsEvent.UpsertDeleteEvent ->{
              viewModelScope.launch {
                  val article = useCases.selectArticleDetail(event.article.url)
                  if(article == null){
                      upsertarticle(event.article)
                  }else{
                      deleteArticle(event.article)
                  }
              }
           }
           is DetailsEvent.RemoveSideEffect ->{
               sideEffect = null
           }
       }
   }

    private suspend fun deleteArticle(article: Article) {
        useCases.deleteArticle(article)
        sideEffect = "Article Deleted"
    }

    private suspend fun upsertarticle(article: Article) {
         useCases.upsertArticle(article)
        sideEffect = "Article Saved"
    }
}