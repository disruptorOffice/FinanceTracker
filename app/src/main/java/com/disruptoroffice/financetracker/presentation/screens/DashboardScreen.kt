package com.disruptoroffice.financetracker.presentation.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DashboardScreen() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        com.disruptoroffice.financetracker.presentation.Greeting(
            name = "Dasboard tracker",
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}