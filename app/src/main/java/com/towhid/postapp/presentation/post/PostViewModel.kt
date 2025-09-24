package com.towhid.postapp.presentation.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.towhid.postapp.domain.model.Post
import com.towhid.postapp.domain.usecase.FetchPostsUseCase
import com.towhid.postapp.domain.usecase.GetPostsUseCase
import com.towhid.postapp.domain.usecase.SearchPostsUseCase
import com.towhid.postapp.domain.usecase.ToggleFavouriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PostsViewModel @Inject constructor(
    private val getPosts: GetPostsUseCase,
    private val fetchPosts: FetchPostsUseCase,
    private val searchPosts: SearchPostsUseCase,
    private val toggleFav: ToggleFavouriteUseCase
) : ViewModel() {


    private val _query = MutableStateFlow("")
    private val _page = MutableStateFlow(1)


    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<PostsUiState> = _query
        .debounce(300)
        .flatMapLatest { q -> if (q.isBlank()) getPosts.execute() else searchPosts.execute(q) }
        .map { list -> if (list.isEmpty()) PostsUiState.Empty else PostsUiState.Success(list) }
        .catch { emit(PostsUiState.Error(it.localizedMessage ?: "Error")) }
        .stateIn(viewModelScope, SharingStarted.Lazily, PostsUiState.Loading)

    init {
        viewModelScope.launch {
            try {
                fetchPosts.execute(1, 20)
            } catch (_: Exception) {

            }
        }
    }
    fun setQuery(q: String) { _query.value = q }



    fun loadNextPage() {
        viewModelScope.launch {
            val next = _page.value + 1
            try {
                fetchPosts.execute(next, 20)
                _page.value = next
            } catch (_: Exception) { }
        }
    }


    fun toggleFavourite(postId: Int, current: Boolean) {
        viewModelScope.launch { toggleFav.execute(postId, !current) }
    }
}

sealed class PostsUiState { object Loading: PostsUiState(); data class Success(val posts: List<Post>): PostsUiState(); data class Error(val msg:String):PostsUiState(); object Empty: PostsUiState() }

