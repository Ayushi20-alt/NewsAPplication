package com.loc.newsapp.presentation.search

import androidx.paging.PagingData
import com.loc.newsapp.domain.model.Article
import kotlinx.coroutines.flow.Flow

// we have not created this state in home screen cause we do not have any state other than
// paging 3 state but here we have search query
data class SearchState(
    val searchQuery : String = "",
    val articles : Flow<PagingData<Article>>? = null
)