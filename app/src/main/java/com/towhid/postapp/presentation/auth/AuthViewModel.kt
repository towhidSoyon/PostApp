package com.towhid.postapp.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.towhid.postapp.domain.model.User
import com.towhid.postapp.domain.usecase.RegisterUserUseCase
import com.towhid.postapp.presentation.auth.AuthState.*
import com.towhid.postapp.utils.LoginResult
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.aakira.napier.Napier
import io.github.aakira.napier.log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.security.MessageDigest
import javax.annotation.meta.When
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val registerUseCase: RegisterUserUseCase) :
    ViewModel() {
    private val _state = MutableStateFlow<AuthState>(AuthState.Idle)
    val state: StateFlow<AuthState> = _state


    fun register(email: String, password: String, confirm: String) {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _state.value = Error("Invalid email"); return
        }
        if (password.length < 6) {
            _state.value = Error("Password too weak"); return
        }
        if (password != confirm) {
            _state.value = Error("Passwords don't match"); return
        }


        viewModelScope.launch {
            _state.value = Loading
            try {
                val hashed = hash(password)
                registerUseCase.execute(User(email, hashed))
                _state.value = Success("Registered")
            } catch (e: Exception) {
                _state.value = Error(e.localizedMessage ?: "Failed")
            }
        }
    }

    fun login(email: String, password: String) {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _state.value = Error("Invalid email"); return
        }

        viewModelScope.launch {
            _state.value = Loading

            try {
                val hashed = hash(password)
                val loginResponse = registerUseCase.login(email, hashed)
                _state.value = when(loginResponse){
                    LoginResult.SUCCESS -> Success("Login Success")
                    LoginResult.WRONG_EMAIL -> Error("Wrong Email")
                    LoginResult.WRONG_PASSWORD -> Error("Wrong Password")
                    LoginResult.FAILED -> Error("Login Failed")
                    else -> Error("Login Failed")
                }
            } catch (e: Exception) {
                _state.value = Error(e.localizedMessage ?: "Failed")
            }
        }
    }


    private fun hash(input: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}

sealed class AuthState {
    object Idle : AuthState();
    object Loading : AuthState();
    data class Success(val msg: String) : AuthState();
    data class Error(val msg: String) : AuthState()
}

