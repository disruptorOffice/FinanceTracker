package com.disruptoroffice.financetracker.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.disruptoroffice.financetracker.data.endpoints.responses.FinanceRecordResponse
import com.disruptoroffice.financetracker.presentation.composables.FinanceRow
import com.disruptoroffice.financetracker.presentation.states.DashboardState
import com.disruptoroffice.financetracker.presentation.states.DashboardState2
import com.disruptoroffice.financetracker.presentation.viewmodel.DashboardViewmodel
import com.disruptoroffice.financetracker.presentation.viewmodel.SharedRecordViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewmodel,
    sharedViewModel: SharedRecordViewModel,
    navigateToNewRecord: () -> Unit,
) {
    val state = viewModel.state.collectAsState()
    val shouldRefresh by sharedViewModel.shouldRefresh.collectAsState()
    val usernameState by viewModel.uiState.collectAsState()
    var username by remember { mutableStateOf("") }

    LaunchedEffect(shouldRefresh) {
        if (shouldRefresh) {
            viewModel.fetchData()
            sharedViewModel.resetRefresh()
        }
    }

    LaunchedEffect(usernameState) {
        if (usernameState is DashboardState2.UserData) {
            username = (usernameState as DashboardState2.UserData).username
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchData()
        viewModel.loadUSerData()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Bienvenido, $username")
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navigateToNewRecord()
                }
            ) {
                Icon(Icons.Filled.Add, "Add record")
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when(state.value) {
                is DashboardState.Loading -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                    ) {
                    CircularProgressIndicator()
                }

                is DashboardState.Error -> Text((state.value as DashboardState.Error).message)
                is DashboardState.Success -> {
                    val financeItems = (state.value as DashboardState.Success<List<FinanceRecordResponse>>).data
                    if (financeItems.isEmpty()) {
                        Text("No tienes datos registrados ", modifier = Modifier.align(Alignment.Center))
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .padding(top = 16.dp)
                                .fillMaxSize()
                        ) {
                            items(financeItems) { item ->
                                FinanceRow(item) {
                                    //todo: open detail of finance record
                                }
                            }
                        }
                    }
                }

                DashboardState.Idle -> {}
                is DashboardState.UserData -> {

                }
            }
        }
    }
}
