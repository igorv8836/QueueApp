package com.example.shared_features_impl.news_screen

import androidx.compose.runtime.Stable
import androidx.lifecycle.*
import com.example.common.asResult
import com.example.shared_features_api.data.NewsRepository
import com.example.shared_features_api.data.model.NewsModel
import kotlinx.coroutines.flow.*

internal class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {
    val news: StateFlow<Any> = newsRepository.getNews()
        .asResult()
        .map { result ->
            when {
                result.success -> NewsState.Success(result.getOrNull() ?: emptyList())
                else -> NewsState.Error(result.exceptionOrNull()?.message ?: "Ошибка")
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = NewsState.Loading
        )
}

@Stable
internal interface NewsState {
    data object Loading : NewsState
    data class Error(val exception: String) : NewsState
    data class Success(val news: List<NewsModel>) : NewsState
}