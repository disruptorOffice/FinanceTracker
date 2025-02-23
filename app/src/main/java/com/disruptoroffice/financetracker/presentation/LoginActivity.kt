package com.disruptoroffice.financetracker.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.disruptoroffice.financetracker.core.NavigationWrapper
import com.disruptoroffice.financetracker.presentation.viewmodel.LoginViewModel
import com.disruptoroffice.financetracker.presentation.viewmodel.RegisterViewModel
import com.disruptoroffice.financetracker.ui.theme.FinanceTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val loginViewModel by viewModels<LoginViewModel>()
    private val registerViewModel by viewModels<RegisterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FinanceTrackerTheme {
                NavigationWrapper(
                    loginViewModel = loginViewModel,
                    registerViewModel = registerViewModel
                )
        }
    }
}}