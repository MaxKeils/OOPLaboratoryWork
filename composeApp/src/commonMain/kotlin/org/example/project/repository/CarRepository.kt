package org.example.project.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.db.DatabaseProvider
import org.example.project.models.Action
import org.example.project.models.Car

class CarRepository(private val databaseProvider: DatabaseProvider) : Repository {

    private val _cars = MutableStateFlow<List<Car>>(listOf())
    val cars
        get() = _cars.asStateFlow()

    private val _actions = MutableStateFlow<List<Action>>(listOf())
    val actions
        get() = _actions.asStateFlow()

    init {
        observeDatabaseChanges()
    }

    private fun observeDatabaseChanges() {
        CoroutineScope(Dispatchers.IO).launch {
            _cars.value = databaseProvider.getAllCars()
            _actions.value = databaseProvider.getAllActions()
        }
    }

    override suspend fun addCar(car: Car) {
        val newId = databaseProvider.insertCar(car)
        car.id = newId
        _cars.value += car
    }

    override suspend fun removeCar(car: Car) {
        databaseProvider.deleteCarById(car.id)
        _cars.value -= car
    }

    override suspend fun updateCar(updatedCar: Car) {
        databaseProvider.updateCar(updatedCar)
        _cars.value = _cars.value.map { if (it.id == updatedCar.id) updatedCar else it }
    }

    override suspend fun insertAction(car: Car) {
        databaseProvider.insertAction(car) {
            _actions.value += it
        }
    }

}