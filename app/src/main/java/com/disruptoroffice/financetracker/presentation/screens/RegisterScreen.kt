package com.disruptoroffice.financetracker.presentation.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.disruptoroffice.financetracker.presentation.states.RegisterState
import com.disruptoroffice.financetracker.presentation.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(viewModel: RegisterViewModel, onNavigateToLogin: (Boolean) -> Unit) {

    val state by viewModel.state.collectAsState()
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(state) {
        if (state is RegisterState.Success) {
            onNavigateToLogin(true)
            viewModel.resetState()
        }
    }

    if (state is RegisterState.Error) {
        ErrorDialog(
            (state as RegisterState.Error).message,
            onDismissRequest =  { viewModel.resetState() },
            onConfirmButton = { viewModel.resetState() })
    }

    BackHandler {
        onNavigateToLogin(false)
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Registrate",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(25.dp))

        TextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("Nombre(s)") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(15.dp))

        TextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Apellido(s)") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            )
        )
        Spacer(modifier = Modifier.height(15.dp))

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Nombre de usuario") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(15.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            )


        )
        Spacer(modifier = Modifier.height(25.dp))

        Button(
            onClick = { viewModel.registerUser(firstName, lastName, username, password) },
            content = { Text("Registrate") },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 16.dp)

        )
    }
}

@Composable
private fun ErrorDialog(message: String,
                        onDismissRequest: () -> Unit,
                        onConfirmButton: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = "Error al iniciar sesion") },
        text = { Text(text = message) },
        confirmButton = {
            TextButton(onClick = onConfirmButton) { Text("Aceptar") }
        }
    )
}