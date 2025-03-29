package org.example.project.db

import org.example.project.models.Action
import org.example.project.models.Car
import org.example.project.models.Trolleybus
import org.example.project.models.Truck
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

object CarDao {

    fun getAllActions(): List<Action> = transaction {
        Actions.selectAll().map { row ->
            val id = row[Actions.id].value
            val carId = row[Actions.vehicleId].value
            val name = row[Actions.actionName]

            Action(
                id, carId, name
            )
        }
    }

    fun getAllCars(): List<Car> = transaction {
        Cars.selectAll().map { row ->
            val id = row[Cars.id].value
            val weight = row[Cars.weight]
            val maxSpeed = row[Cars.maxSpeed]

            val truckRow = Trucks.select { Trucks.vehicleId eq id }.singleOrNull()
            val trolleybusRow = Trolleybuses.select { Trolleybuses.vehicleId eq id }.singleOrNull()

            when {
                truckRow != null -> Truck(id, weight, maxSpeed, truckRow[Trucks.maxLoadCapacity])
                trolleybusRow != null -> Trolleybus(
                    id,
                    weight,
                    maxSpeed,
                    trolleybusRow[Trolleybuses.maxPassengers],
                    trolleybusRow[Trolleybuses.numberRoute]
                )

                else -> error("Unknown vehicle type")
            }
        }
    }

    fun insertAction(car: Car, onActionInsert: (Action) -> Unit) {
         when (car) {
            is Truck -> {
                Actions.insertAndGetId {
                    it[vehicleId] = car.id
                    it[actionName] = car.performSpecialAction()
                }.let {
                    Action(
                        id = it.value,
                        carId = car.id,
                        action = car.performSpecialAction()
                    ).let(onActionInsert)
                }
            }

            is Trolleybus -> {
                Actions.insertAndGetId {
                    it[vehicleId] = car.id
                    it[actionName] = car.performSpecialAction()
                }.let {
                    Action(
                        id = it.value,
                        carId = car.id,
                        action = car.performSpecialAction()
                    ).let(onActionInsert)
                }
            }
        }
    }

    fun insertCar(car: Car): Int = transaction {
        val id = Cars.insertAndGetId {
            it[weight] = car.weight
            it[maxSpeed] = car.maxSpeed
        }.value

        when (car) {
            is Truck -> Trucks.insert {
                it[vehicleId] = id
                it[maxLoadCapacity] = car.maxLoadCapacity
            }

            is Trolleybus -> Trolleybuses.insert {
                it[vehicleId] = id
                it[maxPassengers] = car.maxPassengers
                it[numberRoute] = car.numberRoute
            }
        }
        id
    }

    fun deleteCar(id: Int) = transaction {
        Cars.deleteWhere { Cars.id eq id }
    }

    fun updateCar(car: Car) = transaction {
        Cars.update({ Cars.id eq car.id }) {
            it[weight] = car.weight
            it[maxSpeed] = car.maxSpeed
        }

        when (car) {
            is Truck -> Trucks.update({ Trucks.vehicleId eq car.id }) {
                it[maxLoadCapacity] = car.maxLoadCapacity
            }

            is Trolleybus -> Trolleybuses.update({ Trolleybuses.vehicleId eq car.id }) {
                it[maxPassengers] = car.maxPassengers
                it[numberRoute] = car.numberRoute
            }
        }
    }
}