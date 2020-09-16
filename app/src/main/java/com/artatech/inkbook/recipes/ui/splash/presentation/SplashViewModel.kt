package com.artatech.inkbook.recipes.ui.splash.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artatech.inkbook.recipes.api.response.models.CategoryKitchenTastyResponse
import com.artatech.inkbook.recipes.core.utils.SingleLiveEvent
import com.artatech.inkbook.recipes.ui.splash.GetCategoriesKitchensTastyUseCase

class SplashViewModel(private val useCase: GetCategoriesKitchensTastyUseCase): ViewModel() {
    val data = MutableLiveData<CategoryKitchenTastyResponse>()
    val error = SingleLiveEvent<Throwable>()

    fun onViewCreated() {
        getCategKitchTast()
    }

    private fun getCategKitchTast() {
        useCase(viewModelScope) { result ->
            result.onSuccess { data.value = it }
            result.onFailure { error.value = it }
        }
    }
}