package com.loc.newsapp.domain.usecases.news

import com.loc.newsapp.data.local.NewsDao
import com.loc.newsapp.domain.model.Article

class UpsertArticle(
    private val newsdao : NewsDao
) {

    suspend operator fun invoke(article: Article){
        newsdao.upsert(article)
    }
}