package edu.put.gymapp.repository

import edu.put.gymapp.database.Dao
import edu.put.gymapp.database.entities.ExerciseEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val dao: Dao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend fun getTrails(): Flow<List<ExerciseEntity>> = withContext(ioDispatcher) {
        dao.getAllTrails()
    }

    suspend fun getTrail(id: Int): Flow<ExerciseEntity> = withContext(ioDispatcher) {
        dao.getTrailById(id)
    }

    suspend fun insertAll(trails: List<ExerciseEntity>) {
        withContext(ioDispatcher) {
            dao.insertAll(*trails.toTypedArray())
        }
    }

    suspend fun initializeDatabaseIfNeeded() {
        if (dao.countTrails() == 0) {
            initializeDatabaseFromJson()
        }
    }

    private suspend fun initializeDatabaseFromJson() {
        val waleniemniewdupe = "waleniemniewdupsko"
    }
}