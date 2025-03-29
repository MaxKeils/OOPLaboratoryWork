package org.example.project.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.example.project.models.Car
import org.example.project.repository.CarRepository
import org.example.project.repository.Repository

class CarViewModel(private val repository: CarRepository) {

    val cars = repository.cars
    val actions = repository.actions

    fun addCar(car: Car) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.addCar(car)
        }
    }

    fun removeCar(car: Car) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.removeCar(car)
        }
    }

    fun updateCar(car: Car) {
        CoroutineScope(Dispatchers.IO).launch{
            repository.updateCar(car)
        }
    }

    fun insertAction(car: Car) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.insertAction(car)
        }
    }

}