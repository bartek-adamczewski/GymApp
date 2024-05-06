package edu.put.gymapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import edu.put.gymapp.database.entities.ExerciseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    @Query("SELECT * FROM ExerciseEntity")
    fun getAllTrails(): Flow<List<ExerciseEntity>>

    @Query("SELECT * FROM ExerciseEntity WHERE id = :id")
    fun getTrailById(id: Int): Flow<ExerciseEntity>

    @Insert
    suspend fun insertAll(vararg trails: ExerciseEntity)

    @Query("SELECT COUNT(*) FROM ExerciseEntity")
    suspend fun countTrails(): Int

}