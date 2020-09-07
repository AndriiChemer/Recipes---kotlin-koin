package com.artatech.inkbook.recipes.ui.category

import com.artatech.inkbook.recipes.api.Repository
import com.artatech.inkbook.recipes.api.response.models.category.CategoryModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GetCategoriesUseCase(private val repository: Repository) {

    operator fun invoke(coroutineScope: CoroutineScope, onResult: (Result<List<CategoryModel>>) -> Unit) {
        coroutineScope.launch {
            val result = withContext(Dispatchers.IO) {
                runCatching { repository.getCategoryAndSubcategory() }
            }

            onResult(result)
        }
    }
}