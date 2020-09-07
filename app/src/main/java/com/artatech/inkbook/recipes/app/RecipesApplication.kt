package com.artatech.inkbook.recipes.app

import android.app.Application
import com.artatech.inkbook.recipes.app.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RecipesApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@RecipesApplication)
            modules(
                listOf(
                    networkModule,
                    appModule,
                    categoriesModule,
                    subcategoryModule,
                    recipesModule
                )
            )
        }
    }
}