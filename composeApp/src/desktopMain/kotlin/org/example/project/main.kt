package org.example.project

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.example.project.db.DatabaseFactory
import org.example.project.db.DesktopDatabaseProvider
import org.example.project.repository.CarRepository
import org.example.project.viewmodel.CarViewModel
import org.jetbrains.exposed.sql.Database

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "OOP",
    ) {

        DatabaseFactory.init()
        val databaseProvider = DesktopDatabaseProvider()
        val repository = CarRepository(databaseProvider)
        val viewModel = CarViewModel(repository)

        App(viewModel)
    }
}