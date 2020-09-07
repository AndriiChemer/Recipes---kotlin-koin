package com.artatech.inkbook.recipes.app.di

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.artatech.inkbook.recipes.api.Repository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    factory { Repository(get()) }
    factory<RecyclerView.LayoutManager> { LinearLayoutManager(androidContext()) }
}