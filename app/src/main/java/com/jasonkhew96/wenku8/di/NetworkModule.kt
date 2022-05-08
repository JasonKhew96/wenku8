package com.jasonkhew96.wenku8.di

import com.jasonkhew96.wenku8.Constants.BASE_URL
import com.jasonkhew96.wenku8.Wenku8Application
import com.jasonkhew96.wenku8.data.remote.Wenku8Service
import com.thomasbouvier.persistentcookiejar.PersistentCookieJar
import com.thomasbouvier.persistentcookiejar.cache.SetCookieCache
import com.thomasbouvier.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        val cookieJar = PersistentCookieJar(
            SetCookieCache(), SharedPrefsCookiePersistor(Wenku8Application.applicationContext())
        )
        return OkHttpClient.Builder().addInterceptor(logging).cookieJar(cookieJar)
            .readTimeout(15, TimeUnit.SECONDS).connectTimeout(15, TimeUnit.SECONDS).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient).build()
    }

    @Provides
    @Singleton
    fun provideWenku8Service(retrofit: Retrofit): Wenku8Service {
        return retrofit.create(Wenku8Service::class.java)
    }
}
