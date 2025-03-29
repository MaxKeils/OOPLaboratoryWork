package org.example.project.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.example.project.models.Car
import org.example.project.models.CarType
import org.example.project.models.Trolleybus
import org.example.project.models.Truck

@Composable
fun CarAddScreen(initialCar: Car? = null, onCarCreated: (Car) -> Unit, onDismiss: () -> Unit) {
    var selectedType by remember { mutableStateOf(initialCar?.let { if (it is Truck) CarType.TRUCK else CarType.TROLLEYBUS }) }

    var weight by remember { mutableStateOf(initialCar?.weight?.toString() ?: "") }
    var weightError by remember { mutableStateOf<String?>(null) }

    var maxSpeed by remember { mutableStateOf(initialCar?.maxSpeed?.toString() ?: "") }
    var maxSpeedError by remember { mutableStateOf<String?>(null) }

    var maxLoadCapacity by remember { mutableStateOf((initialCar as? Truck)?.maxLoadCapacity?.toString() ?: "") }
    var maxLoadCapacityError by remember { mutableStateOf<String?>(null) }

    var maxPassengers by remember { mutableStateOf((initialCar as? Trolleybus)?.maxPassengers?.toString() ?: "") }
    var maxPassengersError by remember { mutableStateOf<String?>(null) }

    var numberRoute by remember { mutableStateOf((initialCar as? Trolleybus)?.numberRoute?.toString() ?: "") }
    var numberRouteError by remember { mutableStateOf<String?>(null) }

    fun validateNumber(input: String): Boolean = input.isNotBlank() && input.toIntOrNull() != null
    fun validateFloat(input: String): Boolean = input.isNotBlank() && input.toFloatOrNull() != null

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (initialCar == null) "Добавить машину" else "Редактировать машину") },
        text = {
            Column {
                if (initialCar == null) {
                    Text("Выберите тип машины:")

                    Row {
                        Button(onClick = { selectedType = CarType.TRUCK }) {
                            Text("Грузовик")
                        }
                        Spacer(Modifier.width(8.dp))
                        Button(onClick = { selectedType = CarType.TROLLEYBUS }) {
                            Text("Троллейбус")
                        }
                    }
                }

                if (selectedType != null) {
                    Spacer(Modifier.height(8.dp))

                    OutlinedTextField(
                        value = weight,
                        onValueChange = {
                            weight = it
                            weightError = if (validateNumber(it)) null else "Введите число"
                        },
                        label = { Text("Вес (кг)") },
                        isError = weightError != null,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    ErrorText(weightError)

                    OutlinedTextField(
                        value = maxSpeed,
                        onValueChange = {
                            maxSpeed = it
                            maxSpeedError = if (validateNumber(it)) null else "Введите число"
                        },
                        label = { Text("Макс. скорость (км/ч)") },
                        isError = maxSpeedError != null,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    ErrorText(maxSpeedError)

                    when (selectedType) {
                        CarType.TRUCK -> {
                            OutlinedTextField(
                                value = maxLoadCapacity,
                                onValueChange = {
                                    maxLoadCapacity = it
                                    maxLoadCapacityError = if (validateFloat(it)) null else "Введите число"
                                },
                                label = { Text("Макс. грузоподъёмность (кг)") },
                                isError = maxLoadCapacityError != null,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                            ErrorText(maxLoadCapacityError)

                        }

                        CarType.TROLLEYBUS -> {
                            OutlinedTextField(
                                value = maxPassengers,
                                onValueChange = {
                                    maxPassengers = it
                                    maxPassengersError = if (validateNumber(it)) null else "Введите число"
                                },
                                label = { Text("Макс. пассажиров") },
                                isError = maxPassengersError != null,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                            ErrorText(maxPassengersError)

                            OutlinedTextField(
                                value = numberRoute,
                                onValueChange = {
                                    numberRoute = it
                                    numberRouteError = if (validateNumber(it)) null else "Введите число"
                                },
                                label = { Text("Номер маршрута") },
                                isError = numberRouteError != null,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                            ErrorText(numberRouteError)
                        }

                        null -> {}
                    }
                }
            }
        },
        confirmButton = {
            val isFormValid = weightError == null && maxSpeedError == null &&
                    maxLoadCapacityError == null && maxPassengersError == null &&
                    numberRouteError == null &&
                    weight.isNotBlank() && maxSpeed.isNotBlank() &&
                    (selectedType != CarType.TRUCK || maxLoadCapacity.isNotBlank()) &&
                    (selectedType != CarType.TROLLEYBUS || (maxPassengers.isNotBlank() && numberRoute.isNotBlank()))

            Button(
                onClick = {
                    val id = initialCar?.id ?: 0

                    val car = when (selectedType) {
                        CarType.TRUCK -> Truck(
                            id = id,
                            weight = weight.toInt(),
                            maxSpeed = maxSpeed.toInt(),
                            maxLoadCapacity = maxLoadCapacity.toFloat(),
                        )

                        CarType.TROLLEYBUS -> Trolleybus(
                            id = id,
                            weight = weight.toInt(),
                            maxSpeed = maxSpeed.toInt(),
                            maxPassengers = maxPassengers.toInt(),
                            numberRoute = numberRoute.toInt()
                        )

                        null -> return@Button
                    }
                    onCarCreated(car)
                },
                enabled = isFormValid
            ) {
                Text(if (initialCar == null) "Создать" else "Сохранить")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    )
}

@Composable
private fun ErrorText(message: String?, modifier: Modifier = Modifier) {
    message?.let {
        Text(it, color = Color.Red, modifier = modifier)
    }
}