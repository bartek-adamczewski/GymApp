package edu.put.gymapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import edu.put.gymapp.database.entities.ExerciseEntity
import edu.put.gymapp.ui.theme.GymAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GymAppTheme {
                ExerciseScreen()
            }
        }
    }
}

@Composable
fun ExerciseScreen(viewModel: ViewModel = hiltViewModel()) {
    val state = viewModel.uiState.collectAsState().value

    Column {
        ExerciseList(state.exercises)
        AddExerciseForm(viewModel)
        EventDisplay(viewModel.events.collectAsState(initial = null).value)
    }
}

@Composable
fun ExerciseList(exercises: List<ExerciseEntity>) {
    LazyColumn {
        items(exercises.size) { exercise ->
            Text("Exercise ID: ${exercises[exercise].id}, Name: ${exercises[exercise].name}")
        }
    }
}

@Composable
fun AddExerciseForm(viewModel: ViewModel) {
    var exerciseId by remember { mutableStateOf("") }
    var exerciseName by remember { mutableStateOf("") }
    var exerciseDescription by remember { mutableStateOf("") }
    var exerciseLink by remember { mutableStateOf("") }

    Column {
        OutlinedTextField(
            value = exerciseId,
            onValueChange = { exerciseId = it },
            label = { Text("Exercise ID") }
        )
        OutlinedTextField(
            value = exerciseName,
            onValueChange = { exerciseName = it },
            label = { Text("Name") }
        )
        OutlinedTextField(
            value = exerciseDescription,
            onValueChange = { exerciseDescription = it },
            label = { Text("Description") }
        )
        OutlinedTextField(
            value = exerciseLink,
            onValueChange = { exerciseLink = it },
            label = { Text("Link") }
        )
        Button(onClick = {
            viewModel.addExercise(ExerciseEntity(
                id = exerciseId.toInt(),
                name = exerciseName,
                description = exerciseDescription,
                link = exerciseLink
            ))
        }) {
            Text("Add Exercise")
        }
    }
}

@Composable
fun EventDisplay(event: ViewModel.Event?) {
    when (event) {
        is ViewModel.Event.ExerciseAdded -> Text("Exercise Added Successfully!")
        is ViewModel.Event.Error -> Text("Error: ${event.message}")
        else -> {}
    }
}