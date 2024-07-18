package com.tergeo.testcoordinatepoints.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hebrewerry.data.network.factory.ResultCallAdapterFactory
import com.tergeo.data.network.api.Api
import com.tergeo.data.repository.PointsRepositoryImpl
import com.tergeo.domain.repository.PointsRepository
import com.tergeo.domain.usecase.GetPointsUseCase
import com.tergeo.testcoordinatepoints.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single { createGson() }
    single { createHttpLoggingInterceptor() }
    single { createOkHttpClient(get()) }
    single {
        createWebService<Api>(
            okHttpClient = get(),
            gson = get(),
            url = BuildConfig.BASE_URL
        )
    }

    single <PointsRepository>{ PointsRepositoryImpl(get()) }

    singleOf(::GetPointsUseCase)
}

fun createOkHttpClient(
    httpLoggingInterceptor: HttpLoggingInterceptor,
): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()
}

fun createHttpLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }
}

inline fun <reified T> createWebService(
    okHttpClient: OkHttpClient,
    gson: Gson,
    url: String
): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addCallAdapterFactory(ResultCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    return retrofit.create(T::class.java)
}

private fun createGson(): Gson =
    GsonBuilder()
        .serializeNulls()
        .create()