package com.artatech.inkbook.recipes.api

import com.artatech.inkbook.recipes.api.request.CategoryRequest
import com.artatech.inkbook.recipes.api.response.GeneralResponse
import com.artatech.inkbook.recipes.api.response.PaginationRecipesResponse
import com.artatech.inkbook.recipes.api.response.models.CategoryKitchenTastyResponse
import com.artatech.inkbook.recipes.api.response.models.category.CategoryModel

const val RECIPE_PER_PAGE = 7
class Repository(private val api: RecipesApi) {
    private val TAG = Repository::class.java.simpleName

    //Suspend is used to await the result from Deferred
    suspend fun getCategoryAndSubcategory(): List<CategoryModel> {
        val response: GeneralResponse<List<CategoryModel>> = api.getCategoriesAndSubcategories()
        return response.body
    }

    suspend fun getRecipesByCategoryId(categoryRequest: CategoryRequest): PaginationRecipesResponse {
        return api.getRecipesByCategoryId(categoryRequest).body
    }

    suspend fun getCategoriesKitchensTasties(): CategoryKitchenTastyResponse {
        return api.getCategoriesKitchensTasties().body
    }

    private fun <T : Any> handleError(response: GeneralResponse<T>) {
        when(response.statusCode) {
            200 -> true
            404 -> false
            400 -> false
            401 -> false
            402 -> false
        }

    }
}