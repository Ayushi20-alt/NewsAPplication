package com.loc.newsapp.presentation.onboarding

import androidx.annotation.DrawableRes
import com.loc.newsapp.R

data class Page(
    val title : String,
    val descripton : String,
    @DrawableRes val image : Int
)


val pages = listOf(
    Page(
        title = "First",
        descripton = "Hello this is First page",
        image =  R.drawable.onboarding1
    ),
    Page(
        title = "Second",
        descripton = "Hello this is Second page",
        image = R.drawable.onboarding2
    ),
    Page(
        title = "Third",
        descripton = "Hello this is Third page",
        image = R.drawable.onboarding3
    )
)