package com.towhid.postapp.presentation.post

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.towhid.postapp.domain.model.Post

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostsScreen(
    viewModel: PostsViewModel,
    openFavs: () -> Unit,
    onLogout: ()-> Unit
) {
    val state by viewModel.uiState.collectAsState()
    var query by remember { mutableStateOf("") }


    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Posts") },
            actions = {
                TextButton(onClick = openFavs) { Text("Favourites") }
                IconButton(onClick = onLogout) {
                    Text("⎋", fontSize = 20.sp)
                }})
        OutlinedTextField(
            query,
            onValueChange = { query = it; viewModel.setQuery(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = { Text("Search") })


        when (state) {
            is PostsUiState.Loading -> Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) { CircularProgressIndicator() }

            is PostsUiState.Empty -> Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) { Text("No posts") }

            is PostsUiState.Error -> Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) { Text((state as PostsUiState.Error).msg) }

            is PostsUiState.Success -> {
                val posts = (state as PostsUiState.Success).posts
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    itemsIndexed(posts) { index, p ->
                        PostRow(p, onToggleFav = { viewModel.toggleFavourite(p.id, p.isFavourite) })
                        if (index >= posts.size - 3) {
                            LaunchedEffect(Unit) { viewModel.loadNextPage() }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun PostRow(post: Post, onToggleFav: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    post.title,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    modifier = Modifier,
                    onClick = onToggleFav
                ) { Text(if (post.isFavourite) "★" else "☆") }
            }
            Spacer(Modifier.height(8.dp))
            Text(
                post.body,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W400,
                )
            )
        }
    }
}