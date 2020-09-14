package com.artatech.inkbook.recipes.app.di.screens

import com.artatech.inkbook.recipes.ui.category.GetCategoriesUseCase
import com.artatech.inkbook.recipes.ui.category.presentation.CategoriesAdapter
import com.artatech.inkbook.recipes.ui.category.presentation.CategoriesViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val categoriesModule = module {
    factory { GetCategoriesUseCase(get()) }
    factory { CategoriesAdapter() }
    viewModel { CategoriesViewModel(get()) }
}