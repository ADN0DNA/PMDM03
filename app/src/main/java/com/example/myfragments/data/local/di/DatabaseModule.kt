package com.example.myfragments.data.local.di

import android.content.Context
import androidx.room.Room
import com.example.myfragments.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        )
            .build()
    }

    //Se llaman por la inyección de dependencias
    @Provides
    fun provideScpsDao(database: AppDatabase) = database.scpDao()

    @Provides
    fun provideSiteDao(database: AppDatabase) = database.siteDao()

    @Provides
    fun provideSiteScpCrossRefDao(database: AppDatabase) = database.sitScpCrossRefDao()
}