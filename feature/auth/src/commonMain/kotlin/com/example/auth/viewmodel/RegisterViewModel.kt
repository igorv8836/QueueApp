package com.example.auth.viewmodel

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth.repository.AuthRepository
import com.example.common.MyResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container

internal class RegisterViewModel(
    private val authRepository: AuthRepository,
    private val dispatcher: CoroutineDispatcher
) : ContainerHost<RegisterState, RegisterEffect>, ViewModel() {
    override val container =
        viewModelScope.container<RegisterState, RegisterEffect>(RegisterState.Loading)

    fun onEvent(event: RegisterEvent) {
        viewModelScope.launch(dispatcher) {
            when (event) {
                is RegisterEvent.NavigateToLoginScreen -> {

                }

                is RegisterEvent.Register -> {
                    register(event)
                }
            }
        }
    }

    private fun register(event: RegisterEvent.Register) = intent {
        when (val result = authRepository.signUp(event.email, event.password, event.nickname)) {
            is MyResult.Success -> {
                reduce { RegisterState.Success }
                postSideEffect(RegisterEffect.ReturnToLogin("Success"))
            }

            is MyResult.Error -> {
                postSideEffect(RegisterEffect.ShowError(result.exception.message ?: "Error"))
            }

            is MyResult.Loading -> {
                reduce { RegisterState.Loading }
            }
        }
    }
}

@Stable
internal sealed interface RegisterEffect {
    data class ShowError(val message: String) : RegisterEffect
    data class ReturnToLogin(val message: String) : RegisterEffect
}

@Stable
internal sealed interface RegisterEvent {
    data object NavigateToLoginScreen : RegisterEvent
    data class Register(val email: String, val password: String, val nickname: String) :
        RegisterEvent
}

@Stable
internal sealed interface RegisterState {
    data object Loading : RegisterState
    data class Error(val message: String) : RegisterState
    data object Success : RegisterState
}