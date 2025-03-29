package org.example.project.db

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object Cars : IntIdTable("vehicle") {
    val weight = integer("weight")
    val maxSpeed = integer("max_speed")
}

object Trucks : Table("truck") {
    val vehicleId = reference(
        "vehicle_id",
        Cars,
        onDelete = ReferenceOption.CASCADE
    ).uniqueIndex()
    val maxLoadCapacity = float("max_load_capacity")
}

object Trolleybuses : Table("trolleybus") {
    val vehicleId =
        reference(
            "vehicle_id",
            Cars,
            onDelete = ReferenceOption.CASCADE
        ).uniqueIndex()
    val maxPassengers = integer("max_passengers")
    val numberRoute = integer("number_route")
}

object Actions : IntIdTable("actions") {
    val vehicleId = reference(
        "vehicle_id",
        Cars,
        onDelete = ReferenceOption.CASCADE
    )
    val actionName = text("action")
}