package com.example.shared_features_impl.news_screen

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import com.example.common.*
import com.example.orbit_mvi.viewmodel.container
import com.example.shared_features_api.data.NewsRepository
import com.example.shared_features_api.data.model.NewsModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import org.orbitmvi.orbit.ContainerHost

internal class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel(), ContainerHost<NewsState, Nothing> {
    override val container = container<NewsState, Nothing>(NewsState.Loading) {
        loadNews()
    }

    private fun loadNews() {
        intent {
            newsRepository.getNews()
                .asResult()
                .map { result ->
                    when (result) {
                        is MyResult.Success -> NewsState.Success(
                            result.getOrNull() ?: emptyList(),
                            isRefreshing = state.isRefreshing
                        )

                        is MyResult.Loading -> NewsState.Loading
                        is MyResult.Error -> NewsState.Error(
                            result.exceptionOrNull()?.message ?: "Ошибка"
                        )
                    }
                }.collect {
                    reduce { it }
                }
        }
    }

    private fun refreshNews() {
        intent {
            updateRefreshingState(true)
            newsRepository.updateNews()
            delay(500)
            updateRefreshingState(false)
        }
    }

    private fun updateRefreshingState(isRefreshing: Boolean) {
        intent {
            reduce {
                when (val currentState = state) {
                    is NewsState.Success -> currentState.copy(isRefreshing = isRefreshing)
                    is NewsState.Error -> currentState.copy(isRefreshing = isRefreshing)
                    else -> currentState
                }
            }
        }
    }

    fun onEvent(event: NewsEvent) {
        when (event) {
            NewsEvent.RefreshNews -> refreshNews()
        }
    }
}

internal sealed interface NewsEvent {
    data object RefreshNews : NewsEvent
}

@Stable
internal interface NewsState {
    val isRefreshing: Boolean

    data object Loading : NewsState {
        override val isRefreshing: Boolean = false
    }

    data class Error(
        val exception: String,
        override val isRefreshing: Boolean = false
    ) : NewsState

    data class Success(
        val news: List<NewsModel>,
        override val isRefreshing: Boolean = false
    ) : NewsState
}
