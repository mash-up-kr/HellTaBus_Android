package com.mashup.healthyup.di

import com.mashup.healthyup.core.Empty
import com.mashup.healthyup.data.api.DumApi
import com.mashup.healthyup.data.response.WrapperResponseConvertFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }
    
    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(String.Empty)
            .client(okHttpClient)
            .addConverterFactory(WrapperResponseConvertFactory(gsonConverterFactory))
            .build()
    }

    @Provides
    @Singleton
    fun provideDumApi(retrofit: Retrofit): DumApi {
        return retrofit.create(DumApi::class.java)
    }
}
