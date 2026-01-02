package com.disruptoroffice.financetracker.core

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.disruptoroffice.financetracker.presentation.screens.DashboardScreen
import com.disruptoroffice.financetracker.presentation.screens.FinanceRecordScreen
import com.disruptoroffice.financetracker.presentation.screens.LoginScreen
import com.disruptoroffice.financetracker.presentation.screens.RegisterScreen
import com.disruptoroffice.financetracker.presentation.screens.ScheduledScreen
import com.disruptoroffice.financetracker.presentation.viewmodel.DashboardViewmodel
import com.disruptoroffice.financetracker.presentation.viewmodel.LoginViewModel
import com.disruptoroffice.financetracker.presentation.viewmodel.NewRecordViewModel
import com.disruptoroffice.financetracker.presentation.viewmodel.RegisterViewModel
import com.disruptoroffice.financetracker.presentation.viewmodel.ScheduledViewModel
import com.disruptoroffice.financetracker.presentation.viewmodel.SharedRecordViewModel

@Composable
fun NavigationWrapper(
    loginViewModel: LoginViewModel,
    registerViewModel: RegisterViewModel,
    dashboardViewmodel: DashboardViewmodel,
    newRecordViewModel: NewRecordViewModel,
    sharedRecordViewmodel: SharedRecordViewModel,
    scheduledViewmodel: ScheduledViewModel,
) {
    val navController = rememberNavController()

    NavHost( navController = navController, startDestination = Login(false)) {
        composable<Login>{ entry ->
            val login: Login = entry.toRoute()
            LoginScreen(loginViewModel,
                isRegisterCompleted = login.isRegisterCompleted,
                onSuccessLogin =  {
                    navController.popBackStack()
                    navController.navigate(Dashboard)
                },
                onNavigateToRegister = {
                    navController.navigate(Register)
                })
        }
        composable<Dashboard> {
            DashboardScreen(
                dashboardViewmodel, sharedRecordViewmodel,
                navigateToNewRecord = { navController.navigate(NewRecord) },
                naivageToNewScheduled = { navController.navigate(Scheduled) },
            )
        }
        composable<Register> {
            RegisterScreen(
                viewModel = registerViewModel,
                onNavigateToLogin = { result ->
                    navController.navigate(Login(result)) {
                        popUpTo<Login>{inclusive = true}
                    }
                }
            )
        }

        composable<NewRecord> {
            FinanceRecordScreen(newRecordViewModel, sharedRecordViewmodel) {
                navController.popBackStack()
            }
        }

        composable<Scheduled> {
            ScheduledScreen(scheduledViewmodel) {
                navController.popBackStack()
            }
        }
    }
}