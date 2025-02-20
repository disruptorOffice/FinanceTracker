package com.disruptoroffice.financetracker.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.disruptoroffice.financetracker.presentation.screens.LoginScreen
import com.disruptoroffice.financetracker.presentation.states.LoginState
import com.disruptoroffice.financetracker.presentation.viewmodel.LoginViewModel
import com.disruptoroffice.financetracker.ui.theme.FinanceTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<LoginViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val state by viewModel.uiState.collectAsState()
            FinanceTrackerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginScreen(innerPadding, viewModel, state)

                    if (state is LoginState.Success) {
                        startActivity(Intent(this, DashboardActivity::class.java))
                        finish()
                    }
            }
        }
    }
}}