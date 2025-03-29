package org.example.project.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import org.example.project.CarOperations
import org.example.project.models.Car
import org.example.project.viewmodel.CarViewModel

@Composable
fun CarScreen(viewModel: CarViewModel) {
    val cars by viewModel.cars.collectAsState()
    val actions by viewModel.actions.collectAsState()
    var editingCar by remember { mutableStateOf<Car?>(null) }
    var isDialogOpen by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()

    if (isDialogOpen) {
        CarAddScreen(
            initialCar = editingCar,
            onCarCreated = { car ->
                if (editingCar == null) {
                    viewModel.addCar(car)
                } else {
                    viewModel.updateCar(car)
                }
                editingCar = null
                isDialogOpen = false
            },
            onDismiss = {
                editingCar = null
                isDialogOpen = false
            }
        )
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                editingCar = null
                isDialogOpen = true
            }) {
                Icon(
                    Icons.Default
                        .Add, contentDescription = "Add Car"
                )
            }
        }
    ) {
        Row {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(it)
            ) {
                items(cars) { car ->
                    CarItem(
                        car as CarOperations,
                        onDelete = { viewModel.removeCar(car) },
                        onEdit = {
                            editingCar = car
                            isDialogOpen = true
                        },
                        onSpecialAction = {
                            viewModel.insertAction(car)
                            scope.launch {
                                snackbarHostState.showSnackbar(car.performSpecialAction())
                            }
                        }
                    )
                }
            }
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(actions) { action ->
                    ActionItem(action)
                }
            }
        }
    }
}