package com.disruptoroffice.financetracker.presentation.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.disruptoroffice.financetracker.R
import com.disruptoroffice.financetracker.presentation.states.LoginState
import com.disruptoroffice.financetracker.presentation.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    isRegisterCompleted: Boolean,
    onSuccessLogin: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(state) {
        if (state is LoginState.Success)
            onSuccessLogin()
    }

    LaunchedEffect(isRegisterCompleted) {
        if (isRegisterCompleted)
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = "Registro completado!!!"
                )
            }
    }

    if (state is LoginState.Error) {
        ErrorDialog(
            (state as LoginState.Error).message,
            onDismissRequest =  { viewModel.resetState() },
            onConfirmButton = { viewModel.resetState() })
    }



    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(paddingValues)
                .background(color = MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Finance Tracker Logo",
                modifier = Modifier.padding(16.dp)
                    .background(color = Color.Red)
            )

            Spacer(modifier = Modifier.height(25.dp))

            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Nombre de usuario") },
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                )
            )
            Spacer(modifier = Modifier.height(25.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                )


            )
            Spacer(modifier = Modifier.height(25.dp))

            Button(
                onClick = { viewModel.login(username, password) },
                content = { Text("Iniciar sesión") },
                modifier = Modifier.fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 16.dp)

            )

            Spacer(modifier = Modifier.height(24.dp))

            ClickableText(
                text = annotatedString,
                style = MaterialTheme.typography.displaySmall.copy(
                    fontSize = 15.sp,
                    color = Color(0xFFFFFFFF),
                    textDecoration = TextDecoration.None,
                    textAlign = TextAlign.Center,
                ),
                onClick = { offset ->
                    annotatedString.getStringAnnotations(
                        tag = "register",
                        start = offset,
                        end = offset
                    ).firstOrNull()?.let {
                        onNavigateToRegister()
                    }
                },
                modifier = Modifier.padding(horizontal = 10.dp)
            )

        }
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

private val annotatedString = buildAnnotatedString {
    append("¿No tienes una cuenta?")

    pushStringAnnotation(
        tag = "register",
        annotation = ""
    )
    withStyle(
        style = SpanStyle(
            color = Color.Black,
            textDecoration = TextDecoration.Underline,
            fontSize = 15.sp
        )
    ) {
        append(" Registrate")
    }
}