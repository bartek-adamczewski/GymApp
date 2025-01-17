package edu.put.gymapp.repository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
class ThreadsModule {
    @IoDispatcher
    @Provides
    fun ioDispatcher() = Dispatchers.IO
}


@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher