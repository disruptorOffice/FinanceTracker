package com.disruptoroffice.financetracker.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.disruptoroffice.financetracker.core.NavigationWrapper
import com.disruptoroffice.financetracker.presentation.viewmodel.DashboardViewmodel
import com.disruptoroffice.financetracker.presentation.viewmodel.LoginViewModel
import com.disruptoroffice.financetracker.presentation.viewmodel.NewRecordViewModel
import com.disruptoroffice.financetracker.presentation.viewmodel.RegisterViewModel
import com.disruptoroffice.financetracker.presentation.viewmodel.ScheduledViewModel
import com.disruptoroffice.financetracker.presentation.viewmodel.SharedRecordViewModel
import com.disruptoroffice.financetracker.ui.theme.FinanceTrackerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val loginViewModel by viewModels<LoginViewModel>()
    private val registerViewModel by viewModels<RegisterViewModel>()
    private val dashboardViewmodel by viewModels<DashboardViewmodel>()
    private val newRecordViewmodel by viewModels<NewRecordViewModel>()
    private val sharedRecordViewmodel by viewModels<SharedRecordViewModel>()
    private val scheduledViewmodel by viewModels<ScheduledViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FinanceTrackerTheme {
                NavigationWrapper(
                    loginViewModel = loginViewModel,
                    registerViewModel = registerViewModel,
                    dashboardViewmodel = dashboardViewmodel,
                    newRecordViewModel = newRecordViewmodel,
                    sharedRecordViewmodel = sharedRecordViewmodel,
                    scheduledViewmodel = scheduledViewmodel,
                )
        }
    }
}}