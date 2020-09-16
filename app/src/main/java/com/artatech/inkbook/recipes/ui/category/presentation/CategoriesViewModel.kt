package com.artatech.inkbook.recipes.ui.category.presentation

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artatech.inkbook.recipes.MainActivity
import com.artatech.inkbook.recipes.api.response.models.CategoryKitchenTastyResponse
import com.artatech.inkbook.recipes.api.response.models.category.CategoryModel
import com.artatech.inkbook.recipes.core.extentions.getFromExtras
import com.artatech.inkbook.recipes.ui.category.GetCategoriesUseCase
import com.artatech.inkbook.recipes.core.utils.SingleLiveEvent
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.Exception
import java.lang.reflect.Type

class CategoriesViewModel(private val getCategoriesUseCase: GetCategoriesUseCase) : ViewModel() {
    val categories = MutableLiveData<List<CategoryModel>>()
    val error = SingleLiveEvent<Throwable>()
    val navigateToSubcategory = SingleLiveEvent<CategoryModel>()

    fun onViewCreated() {
        getCategories()
    }

    fun onViewCreated(categoriesKey: String, arguments: Bundle?) {
//        getBundleValue(categoriesKey, arguments)
        getFromExtras(categoriesKey, arguments)
    }

    private fun getFromExtras(key: String, bundle: Bundle?) {
        val gson = Gson()
        val jsonObject = bundle?.getString(key)
        if (jsonObject != null) {
            val type: Type = object : TypeToken<List<CategoryModel>>() {}.type
            categories.value = gson.fromJson(jsonObject, type)
        } else {
            error.value = Throwable("Bundle does't have categories value")
        }
    }

    private fun getCategories() {
        getCategoriesUseCase(viewModelScope) { result ->
            result.onSuccess { categories.value = it }
            result.onFailure { error.value = it }
        }
    }

    fun categoryClicked(category: CategoryModel) {
        navigateToSubcategory.value = category
    }
}