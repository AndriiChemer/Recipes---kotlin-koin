package com.artatech.inkbook.recipes.ui.splash

import com.artatech.inkbook.recipes.api.Repository
import com.artatech.inkbook.recipes.api.response.models.CategoryKitchenTastyResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GetCategoriesKitchensTastyUseCase(private val repository: Repository) {

    operator fun invoke(coroutineScope: CoroutineScope, onResult: (Result<CategoryKitchenTastyResponse>) -> Unit) {
        coroutineScope.launch {
            val result = withContext(Dispatchers.IO) {
                runCatching { repository.getCategoriesKitchensTasties() }
            }

            onResult(result)
        }
    }
}