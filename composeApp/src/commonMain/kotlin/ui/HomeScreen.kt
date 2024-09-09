package ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import domain.Movie
import utils.Dimens.TMDb_140_dp
import utils.Dimens.TMDb_8_dp
import viewmodel.TMDbViewModel

@Composable
fun HomeScreen(
    viewModel: TMDbViewModel = viewModel { TMDbViewModel() },
    onClick: (Movie) -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    if (state.movies.isNotEmpty()) {
        HomeScreen(state.movies, onClick)
    }
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