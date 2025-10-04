package com.disruptoroffice.financetracker.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.disruptoroffice.financetracker.data.endpoints.responses.FinanceRecordResponse
import com.disruptoroffice.financetracker.presentation.composables.FinanceRow
import com.disruptoroffice.financetracker.presentation.states.DashboardState
import com.disruptoroffice.financetracker.presentation.viewmodel.DashboardViewmodel
import com.disruptoroffice.financetracker.presentation.viewmodel.SharedRecordViewModel

@Composable
fun DashboardScreen(
    viewModel: DashboardViewmodel,
    sharedViewModel: SharedRecordViewModel,
    navigateToNewRecord: () -> Unit,
) {
    val state = viewModel.state.collectAsState()
    val shouldRefresh by sharedViewModel.shouldRefresh.collectAsState()

    LaunchedEffect(shouldRefresh) {
        if (shouldRefresh) {
            viewModel.fetchData()
            sharedViewModel.resetRefresh()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchData()
    }

    Scaffold(
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
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(financeItems) { item ->
                                FinanceRow(item) {
                                    //todo: open detail of finance record
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}
