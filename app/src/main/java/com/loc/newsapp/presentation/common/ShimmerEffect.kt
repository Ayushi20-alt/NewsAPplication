package com.loc.newsapp.presentation.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.loc.newsapp.R
import com.loc.newsapp.presentation.Dimens
import com.loc.newsapp.presentation.Dimens.MediumPadding1
import com.loc.newsapp.ui.theme.NewsAppTheme

// we creating custom modifier
fun Modifier.shimmerEffect() = composed{
         val transition = rememberInfiniteTransition()
         val alpha = transition.animateFloat(initialValue = 0.2f, targetValue = 0.9f, animationSpec = infiniteRepeatable(
             animation = tween(durationMillis = 1000),
             repeatMode = RepeatMode.Reverse
         )).value
    background(color = colorResource(id = R.color.shimmer).copy(alpha = alpha))
    //In Android's Jetpack Compose, the "alpha" value is often used to represent the transparency or opacity of a UI element.
}

@Composable
fun ArticleCardShimmerEffect(
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier
    ) {
        // we using coil to load image
        Box(modifier = Modifier
            .size(Dimens.ArticleCardSize)
            .clip(MaterialTheme.shapes.medium)
            .shimmerEffect()
           )

        Column(verticalArrangement = Arrangement.SpaceAround, modifier = Modifier
            .padding(horizontal = Dimens.ExtraSmallPadding)
            .height(
                Dimens.ArticleCardSize
            )) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .padding(horizontal = MediumPadding1)
                .shimmerEffect()
            )

            Row(verticalAlignment = Alignment.CenterVertically){
                Box(modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(15.dp)
                    .padding(horizontal = MediumPadding1)
                    .shimmerEffect()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ArticleCardShimmerEffectpreview(){
    NewsAppTheme {
        ArticleCardShimmerEffect()
    }
}