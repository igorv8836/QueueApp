package com.example.auth_impl.viewmodel

import androidx.compose.runtime.Stable
import androidx.lifecycle.*
import com.example.auth_api.data.AuthRepository
import com.example.common.MyResult
import com.example.orbit_mvi.viewmodel.container
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost

internal class RegisterViewModel(
    private val authRepository: AuthRepository
) : ContainerHost<RegisterState, RegisterEffect>, ViewModel() {
    override val container = container<RegisterState, RegisterEffect>(RegisterState())

    fun onEvent(event: RegisterEvent) {
        viewModelScope.launch {
            when (event) {
                is RegisterEvent.Register -> {
                    register(event.email, event.password, event.nickname)
                }
            }
        }
    }

    private fun register(email: String, password: String, nickname: String) = intent {
        reduce { state.copy(isLoading = true) }
        when (val result = authRepository.signUp(email, password, nickname)) {
            is MyResult.Success -> {
                reduce { RegisterState(false, null, null, null) }
                postSideEffect(RegisterEffect.NavigateToMain)
            }

            is MyResult.Error -> {
                postSideEffect(RegisterEffect.ShowError(result.exception.message ?: "Error"))
                reduce { state.copy(isLoading = false) }
            }

            is MyResult.Loading -> {
                reduce { state.copy(isLoading = true) }
            }
        }
    }
}

internal sealed interface RegisterEffect {
    data class ShowError(val message: String) : RegisterEffect
    data object NavigateToMain: RegisterEffect

}

internal sealed interface RegisterEvent {
    data class Register(val email: String, val password: String, val nickname: String) : RegisterEvent
}

@Stable
internal data class RegisterState(
    val isLoading: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null,
    val nicknameError: String? = null,
)