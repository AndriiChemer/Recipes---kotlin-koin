package com.artatech.inkbook.recipes.app.di.screens

import com.artatech.inkbook.recipes.ui.recipesfavoritelist.presentation.RecipesFavoriteFragment
import com.artatech.inkbook.recipes.ui.recipesfavoritelist.presentation.RecipesFavoriteViewModel
import com.artatech.inkbook.recipes.ui.recipeslist.presentation.RecipesAdapter
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteRecipesModule = module {
    factory { RecipesFavoriteFragment.newInstance() }
    viewModel { RecipesFavoriteViewModel() }
}