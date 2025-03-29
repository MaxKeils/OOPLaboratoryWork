package org.example.project.models

import org.example.project.CarOperations

data class Truck(
    override var id: Int = UNDEFINED_ID,
    override val weight: Int,
    override val maxSpeed: Int,
    val maxLoadCapacity: Float
) : Car(weight = weight, maxSpeed = maxSpeed), CarOperations {
    override fun drive(): String = "Еду на базу"

    override fun refill(): String = "Заправка топлива"

    fun loadCargo(): String = "Загрузка груза"

    fun attachedTrailer() = "Трейлер прицеплен"

    override fun performSpecialAction(): String = loadCargo()
}