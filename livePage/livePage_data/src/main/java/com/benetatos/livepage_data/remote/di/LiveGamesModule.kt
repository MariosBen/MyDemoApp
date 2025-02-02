package com.benetatos.livepage_data.remote.di

import android.app.Application
import androidx.room.Room
import com.benetatos.livepage_data.remote.LiveGamesApi
import com.benetatos.livepage_data.remote.local.FavoriteGamesDao
import com.benetatos.livepage_data.remote.local.FavoriteGamesDatabase
import com.benetatos.livepage_data.remote.repository.LiveGamesRepositoryImpl
import com.benetatos.livepage_domain.repository.LiveGamesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LiveGamesModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
            .build()
    }

    @Provides
    @Singleton
    fun provideLiveGamesApi(client: OkHttpClient): LiveGamesApi {
        return Retrofit.Builder()
            .baseUrl(LiveGamesApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun providesFavoriteGamesDatabase(application: Application): FavoriteGamesDatabase {
        return Room.databaseBuilder(
            application,
            FavoriteGamesDatabase::class.java,
            "favorite_games_db"
        ).build()
    }

    @Provides
    fun provideFavoriteGamesDao(database: FavoriteGamesDatabase): FavoriteGamesDao {
        return database.dao
    }

    @Provides
    @Singleton
    fun providesLiveGamesRepository(
        api: LiveGamesApi,
        database: FavoriteGamesDatabase
    ): LiveGamesRepository {
        return LiveGamesRepositoryImpl(api = api, dao = database.dao)
    }
}