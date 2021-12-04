package com.mashup.healthyup.di

import com.mashup.healthyup.Config
import com.mashup.healthyup.Config.BASE_URL
import com.mashup.healthyup.data.api.ExerciseApi
import com.mashup.healthyup.data.api.ExerciseHistoryApi
import com.mashup.healthyup.data.api.UserApi
import com.mashup.healthyup.data.response.wrapper.ResponseConverterWrapperFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpBuilder = OkHttpClient.Builder()
        okHttpBuilder.addInterceptor(HeaderInterceptor(Config.access_key)).build()
        okHttpBuilder.addInterceptor(interceptor)
        return okHttpBuilder.build()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }


    class HeaderInterceptor(private val clientId: String) : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            var request: Request = chain.request()
            request = request.newBuilder()
                .addHeader("Authorization", "Client-ID $clientId")
                .build()
            return chain.proceed(request)
        }
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
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(ResponseConverterWrapperFactory(gsonConverterFactory))
            .build()
    }

    @Provides
    @Singleton
    fun provideExerciseApi(retrofit: Retrofit): ExerciseApi {
        return retrofit.create(ExerciseApi::class.java)
    }

    @Provides
    @Singleton
    fun provideExerciseHistoryApi(retrofit: Retrofit): ExerciseHistoryApi {
        return retrofit.create(ExerciseHistoryApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }
}
