package com.artatech.inkbook.recipes.app.di

import com.artatech.inkbook.recipes.ui.recipeshortdetail.presentation.RecipeShortDetailViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val recipeShortDetailsModule = module {
    viewModel { RecipeShortDetailViewModel() }
}