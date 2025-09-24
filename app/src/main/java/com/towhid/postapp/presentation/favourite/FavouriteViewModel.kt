package com.towhid.postapp.presentation.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.towhid.postapp.domain.model.Post
import com.towhid.postapp.domain.repository.PostsRepository
import com.towhid.postapp.domain.usecase.ToggleFavouriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    repo: PostsRepository,
    private val toggleFav: ToggleFavouriteUseCase
) : ViewModel() {

    val favourites: StateFlow<List<Post>> = repo.observeFavourites()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun toggleFavourite(postId: Int, current: Boolean) {
        viewModelScope.launch { toggleFav.execute(postId, !current) }
    }
}
