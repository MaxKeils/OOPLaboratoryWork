package org.example.project.repository

import org.example.project.models.Car

interface Repository {

    suspend fun addCar(car: Car)

    suspend fun removeCar(car: Car)

    suspend fun updateCar(updatedCar: Car)

    suspend fun insertAction(car: Car)
}