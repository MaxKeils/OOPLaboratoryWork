package org.example.project.db

import org.example.project.models.Action
import org.example.project.models.Car

interface DatabaseProvider {
    suspend fun getAllCars(): List<Car>

    suspend fun getAllActions(): List<Action>

    suspend fun insertCar(car: Car): Int

    suspend fun deleteCarById(carId: Int)

    suspend fun updateCar(newCar: Car)

    suspend fun insertAction(car: Car, onActionInsert: (Action) -> Unit)

}