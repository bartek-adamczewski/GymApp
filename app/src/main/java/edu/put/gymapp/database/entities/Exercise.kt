package edu.put.gymapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = false) val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val link: String = "",
)