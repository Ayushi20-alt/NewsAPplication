package com.loc.newsapp.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.paging.compose.LazyPagingItems
import com.loc.newsapp.domain.model.Article
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.loc.newsapp.R
import com.loc.newsapp.presentation.Dimens.ExtraSmallPadding2
import com.loc.newsapp.presentation.Dimens.MediumPadding1
import com.loc.newsapp.presentation.common.ArticlesList
import com.loc.newsapp.presentation.common.SearchBar
import com.loc.newsapp.presentation.nvgraph.Route

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(articles : LazyPagingItems<Article>, navigate:(String)->Unit){
    val titles by remember {
//        It's a derived state that is recomposed whenever the underlying data changes.
//        This block of code calculates the titles string by joining the titles of the first
//        10 articles with a "\uD83d\uDFE5" separator. If there are fewer than 10 articles, an empty string is assigned.
        derivedStateOf {
            if(articles.itemCount > 10){
                articles.itemSnapshotList.items
                    .slice(IntRange(start = 0, endInclusive = 9))
                    .joinToString(separator = "\uD83d\uDFE5"){it.title}
            }else{
                ""
            }
        }
    }
    
    // lets actually built the screen
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = MediumPadding1)
        .statusBarsPadding()
    ) {
       Image(painter = painterResource(id = R.drawable.news_logo), contentDescription = null,
       modifier = Modifier
           .width(250.dp)
           .height(70.dp),
           alignment = Alignment.TopStart
       )
       // Spacer(modifier = Modifier.height(MediumPadding1))

        SearchBar(
            modifier = Modifier.padding(horizontal = ExtraSmallPadding2),
            text = "",
            readOnly = true,
            onValueChange = {},
            onClick = {
                      navigate(Route.SearchScreen.route)
            },
            onSearch ={}
        )
        
        Spacer(modifier = Modifier.height(MediumPadding1))

        // to add the text animation we use basic marquee
        Text(text = titles, modifier = Modifier
            .fillMaxWidth()
            .padding(start = MediumPadding1)
            .basicMarquee(),
        fontSize = 12.sp,
        color = colorResource(id = R.color.placeholder))

        Spacer(modifier = Modifier.height(MediumPadding1))
        
        ArticlesList(modifier = Modifier.padding(horizontal = MediumPadding1),
            articles = articles, onClick = {
            navigate(Route.DetailsScreen.route)
        })

    }
}