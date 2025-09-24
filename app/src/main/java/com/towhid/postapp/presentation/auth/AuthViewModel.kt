package com.towhid.postapp.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.towhid.postapp.domain.model.User
import com.towhid.postapp.domain.usecase.RegisterUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.security.MessageDigest
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val registerUseCase: RegisterUserUseCase) : ViewModel() {
    private val _state = MutableStateFlow<AuthState>(AuthState.Idle)
    val state: StateFlow<AuthState> = _state


    fun register(email: String, password: String, confirm: String) {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) { _state.value = AuthState.Error("Invalid email"); return }
        if (password.length < 6) { _state.value = AuthState.Error("Password too weak"); return }
        if (password != confirm) { _state.value = AuthState.Error("Passwords don't match"); return }


        viewModelScope.launch {
            _state.value = AuthState.Loading
            try {
                val hashed = hash(password)
                registerUseCase.execute(User(email, hashed))
                _state.value = AuthState.Success("Registered")
            } catch (e: Exception) {
                _state.value = AuthState.Error(e.localizedMessage ?: "Failed")
            }
        }
    }

    fun login(email: String, password: String){
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) { _state.value = AuthState.Error("Invalid email"); return }

        viewModelScope.launch {
            _state.value = AuthState.Loading

            try {
                val hashed = hash(password)
                registerUseCase.login(email)
                _state.value = AuthState.Success("Logged In")
            } catch (e: Exception) {
                _state.value = AuthState.Error(e.localizedMessage ?: "Failed")
            }
        }
    }


    private fun hash(input: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}

sealed class AuthState { object Idle: AuthState(); object Loading: AuthState(); data class Success(val msg:String): AuthState(); data class Error(val msg:String): AuthState() }

