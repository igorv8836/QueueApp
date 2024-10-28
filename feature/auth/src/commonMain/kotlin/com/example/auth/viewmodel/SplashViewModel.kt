package com.example.auth.viewmodel

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import com.example.auth.repository.AuthRepository
import com.example.common.MyResult
import com.example.orbit_mvi.viewmodel.container
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.ContainerHost

internal class SplashViewModel(
    private val authRepository: AuthRepository
) : ContainerHost<SplashState, Nothing>, ViewModel() {
    override val container = container<SplashState, Nothing>(SplashState.Loading) {
        getUserInfo()
    }

    private fun getUserInfo() {
        intent {
            when (val res = authRepository.getUser()) {
                is MyResult.Success -> {
                    if (!res.data.isActive) {
                        reduce { SplashState.Error("Ban: ${res.data.banReason ?: "-"}") }
                    } else {
                        delay(3000)
                        reduce { SplashState.Success }
                    }
                }

                is MyResult.Error -> {
                    reduce {
                        SplashState.Error(res.exception.message ?: "Error")
                    }
                }

                MyResult.Loading -> {}
            }
        }
    }

    fun onEvent(event: SplashEvent) {
        when (event) {
            is SplashEvent.Retry -> {
                intent { reduce { SplashState.Loading } }
                getUserInfo()
            }
        }
    }
}

internal sealed interface SplashEvent {
    data object Retry : SplashEvent
}

@Stable
internal sealed interface SplashState {
    data object Loading : SplashState
    data object Success : SplashState
    data class Error(val message: String) : SplashState
}