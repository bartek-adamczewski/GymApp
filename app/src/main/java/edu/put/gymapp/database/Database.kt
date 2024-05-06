package edu.put.gymapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.put.gymapp.database.entities.ExerciseEntity

@Database(entities = [ExerciseEntity::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun trailDao(): Dao
}