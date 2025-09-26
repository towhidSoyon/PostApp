package com.towhid.postapp.presentation.favourite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.towhid.postapp.presentation.post.PostRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouritesScreen(vm: FavouritesViewModel = hiltViewModel()) {
    val favourite by vm.favourites.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Favourite") })

        if (favourite.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No favourites yet",
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn {
                items(favourite) { post ->
                    PostRow(
                        post,
                        onToggleFav = { vm.toggleFavourite(post.id, post.isFavourite) }
                    )
                }
            }
        }
    }
}