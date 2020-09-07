package com.artatech.inkbook.recipes.app.di

import com.artatech.inkbook.recipes.ui.recipeslist.GetRecipesByCategoryUseCase
import com.artatech.inkbook.recipes.ui.recipeslist.presentation.RecipesAdapter
import com.artatech.inkbook.recipes.ui.recipeslist.presentation.RecipesViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val recipesModule = module {
    factory { RecipesAdapter() }
    factory { GetRecipesByCategoryUseCase(get()) }
    viewModel { RecipesViewModel(get()) }
}