package com.loc.newsapp.presentation.details

import com.loc.newsapp.domain.model.Article

sealed class DetailsEvent {

    data class UpsertDeleteEvent(val article : Article) : DetailsEvent()

    object RemoveSideEffect : DetailsEvent()
}