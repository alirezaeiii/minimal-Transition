package ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import domain.Movie
import utils.Dimens.TMDb_140_dp
import utils.Dimens.TMDb_8_dp

@Composable
fun HomeScreen(
    onClick: (Movie) -> Unit
) {
        HomeScreen(listOf(
            Movie(
                1, "Hellooooo","http://image.tmdb.org/t/p/w780/yDHYTfA3R0jFYba16jBB1ef8oIt.jpg"
            )), onClick)
}

@Composable
private fun HomeScreen(
    movies: List<Movie>,
    onClick: (Movie) -> Unit
) {
    VerticalCollection(
        movies,
        onClick
    )

}

@Composable
private fun VerticalCollection(
    movies: List<Movie>,
    onClick: (Movie) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = TMDb_140_dp),
        contentPadding = PaddingValues(
            start = TMDb_8_dp,
            end = TMDb_8_dp,
            bottom = TMDb_8_dp
        ),
        content = {
            items(
                items = movies,
                itemContent = { movie ->
                    TMDbCard(
                        movie,
                        onClick,
                        Modifier.padding(vertical = TMDb_8_dp)
                    )
                }
            )
        })
}