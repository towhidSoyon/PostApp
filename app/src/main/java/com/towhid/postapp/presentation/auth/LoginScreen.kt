package com.towhid.postapp.presentation.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    onLogin: () -> Unit,
    goRegistration: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Login", style = MaterialTheme.typography.headlineLarge)
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            pass,
            onValueChange = { pass = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        Button(
            enabled = email.isNotEmpty() && pass.isNotEmpty(),
            colors = ButtonColors(
                containerColor = Color.Blue,
                contentColor = Color.White,
                disabledContentColor = Color.Gray,
                disabledContainerColor = Color.LightGray
            ),
            onClick = { viewModel.login(email, pass) },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Login") }


        when (state) {
            is AuthState.Loading -> CircularProgressIndicator()
            is AuthState.Error -> Text(
                (state as AuthState.Error).msg,
                color = MaterialTheme.colorScheme.error
            )

            is AuthState.Success -> {
                onLogin()
            }

            else -> {}
        }


        Spacer(Modifier.height(8.dp))
        TextButton(
            modifier = Modifier.align(Alignment.End),
            onClick = { goRegistration() }
        ) { Text("Don't have account? Register") }
    }
}