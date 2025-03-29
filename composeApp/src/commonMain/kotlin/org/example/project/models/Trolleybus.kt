package org.example.project.models

import org.example.project.CarOperations

data class Trolleybus(
    override var id: Int = UNDEFINED_ID,
    override val maxSpeed: Int,
    override val weight: Int,
    val maxPassengers: Int,
    val numberRoute: Int,
) : Car(weight = weight, maxSpeed = maxSpeed), CarOperations {
    override fun drive() = "Еду по маршруту $numberRoute"

    override fun refill() = "Зарядка батареи"

    fun pickUpPassenger() = "Пассажир посажен"

    fun announceStop() = "Остановка!"

    override fun performSpecialAction(): String = pickUpPassenger()
}