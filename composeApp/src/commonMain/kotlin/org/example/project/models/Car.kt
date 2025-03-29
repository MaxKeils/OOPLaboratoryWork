package org.example.project.models

sealed class Car(
    open var id: Int = UNDEFINED_ID,
    open val weight: Int,
    open val maxSpeed: Int
) {
    abstract fun drive(): String

    abstract fun refill(): String

    fun beep() = "Beep"

    fun park() = "Машина припаркована"

    companion object {
        const val UNDEFINED_ID = 0
    }

}
