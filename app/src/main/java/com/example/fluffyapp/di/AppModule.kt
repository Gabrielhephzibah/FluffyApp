package com.example.fluffyapp.di

import android.content.Context
import androidx.room.Room
import com.example.fluffyapp.BuildConfig
import com.example.fluffyapp.data.local.dao.BreedDao
import com.example.fluffyapp.data.local.dao.FavouriteBreedDao
import com.example.fluffyapp.data.local.database.BreedDatabase
import com.example.fluffyapp.data.remote.BreedApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideBreedApi(retrofit: Retrofit): BreedApi = retrofit.create(BreedApi::class.java)

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context) : BreedDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            BreedDatabase::class.java,
            "cat-breed-database"
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideBreedDao(database: BreedDatabase): BreedDao = database.breedDao()

    @Singleton
    @Provides
    fun provideFavouriteBreedDao(database: BreedDatabase): FavouriteBreedDao = database.favouriteBreedDao()

    @Singleton
    @Provides
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

}