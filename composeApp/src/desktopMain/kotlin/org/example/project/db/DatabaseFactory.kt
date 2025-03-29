package org.example.project.db

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init() {
        Database.connect(
            url = "jdbc:postgresql://localhost:5432/OOP",
            driver = "org.postgresql.Driver",
            user = "postgres",
            password = "1234555111"
        )
    }

    fun <T> dbQuery(block: () -> T): T = transaction { block() }

}