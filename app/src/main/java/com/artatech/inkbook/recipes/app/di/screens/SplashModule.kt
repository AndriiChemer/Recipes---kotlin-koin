package com.artatech.inkbook.recipes.app.di.screens

import com.artatech.inkbook.recipes.ui.splash.GetCategoriesKitchensTastyUseCase
import com.artatech.inkbook.recipes.ui.splash.presentation.SplashViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val splashModule = module {
    viewModel { SplashViewModel(get()) }
    factory { GetCategoriesKitchensTastyUseCase(get()) }
}