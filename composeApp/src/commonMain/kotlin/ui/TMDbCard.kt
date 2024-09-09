package ui

import LocalNavAnimatedVisibilityScope
import LocalSharedTransitionScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil3.compose.AsyncImage
import domain.Movie
import utils.Dimens.TMDb_150_dp
import utils.TMDbSharedElementKey
import utils.TMDbSharedElementType

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TMDbCard(
    movie: Movie,
    onClick: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    val sharedTransitionScope = LocalSharedTransitionScope.current
        ?: throw IllegalStateException("No sharedTransitionScope found")
    val animatedVisibilityScope = LocalNavAnimatedVisibilityScope.current
        ?: throw IllegalStateException("No animatedVisibilityScope found")

    with(sharedTransitionScope) {
        TMDbSurface(
            modifier = modifier
                .sharedBounds(
                    sharedContentState = rememberSharedContentState(
                        key = TMDbSharedElementKey(
                            movieId = movie.id,
                            type = TMDbSharedElementType.Bounds
                        )
                    ),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = TMDbDetailBoundsTransform,
                    enter = fadeIn(),
                    exit = fadeOut()
                ).border(
                    1.dp,
                    Color.Black,
                ).clickable {
                    onClick.invoke(movie)
                }
        ) {
            Column {
                AsyncImage(
                    model = movie.backdropPath,
                    contentDescription = null,
                    modifier = Modifier.sharedBounds(
                        rememberSharedContentState(
                            key = TMDbSharedElementKey(
                                movieId = movie.id,
                                type = TMDbSharedElementType.Image
                            )
                        ),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = TMDbDetailBoundsTransform
                    ).height(TMDb_150_dp)
                        .fillMaxWidth()
                        .clip(shape = MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )
                Text(text = movie.name)
            }
        }
    }
}

@Composable
fun TMDbSurface(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    color: Color = MaterialTheme.colors.background,
    elevation: Dp = 0.dp,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .shadow(elevation = elevation, shape = shape, clip = false)
            .zIndex(elevation.value)
            .background(
                color = color,
                shape = shape
            ).clip(shape)
    ) {
        content.invoke()
    }
}