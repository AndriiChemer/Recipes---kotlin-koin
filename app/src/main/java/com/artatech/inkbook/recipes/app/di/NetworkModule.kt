package com.artatech.inkbook.recipes.app.di

import com.artatech.inkbook.recipes.api.RecipesApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


//const val RECIPE_GENERAL_API_URL = "http://192.168.0.165:3000/"
const val RECIPE_GENERAL_API_URL = "http://192.168.80.81:3000/"

val networkModule = module {
    // single instance as a singleton
    single<Interceptor> {
        HttpLoggingInterceptor()
            .apply { level = HttpLoggingInterceptor.Level.BODY }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<Interceptor>())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(RECIPE_GENERAL_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get<OkHttpClient>())
            .build()
    }

    single { get<Retrofit>().create(RecipesApi::class.java) }
}