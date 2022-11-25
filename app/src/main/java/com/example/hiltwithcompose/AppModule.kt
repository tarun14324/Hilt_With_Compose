package com.example.hiltwithcompose

import android.content.Context
import androidx.room.Room
import com.example.hiltwithcompose.room.UserDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesRetrofit(httpClient: OkHttpClient): Retrofit {
        val baseUrl = "https://rickandmortyapi.com/api/"
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()).client(httpClient).build()
    }

    @Provides
    @Singleton
    fun providesRetrofitService(retrofit: Retrofit): RetrofitInterface {
        return retrofit.create(RetrofitInterface::class.java)
    }

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun providesHttpClient(logging: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(logging).build()
    }

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): UserDataBase {
        return Room.databaseBuilder(context, UserDataBase::class.java, "user")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }
}
