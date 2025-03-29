package org.example.project

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import org.example.project.ui.CarScreen
import org.example.project.viewmodel.CarViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(viewModel: CarViewModel) {
    MaterialTheme {
        CarScreen(viewModel)
    }
}