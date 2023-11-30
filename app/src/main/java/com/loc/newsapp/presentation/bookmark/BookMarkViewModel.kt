package com.loc.newsapp.presentation.bookmark

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loc.newsapp.domain.usecases.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BookMarkViewModel @Inject constructor(
   private val newsUseCases: NewsUseCases
): ViewModel() {

   private val _state = mutableStateOf(BookMarkState())
   val state : State<BookMarkState> = _state

//   It separates the initialization logic from the property declarations and constructor, making the code structure more organized.

   init {
      getArticles()
   }

   private fun getArticles(){
      newsUseCases.selectArticle().onEach {
         _state.value = _state.value.copy(articles = it)
      }.launchIn(viewModelScope)   // we are collecting this
   }
}