package com.artatech.inkbook.recipes.ui.recipeslist

import android.icu.util.Output
import com.artatech.inkbook.recipes.api.Repository
import com.artatech.inkbook.recipes.api.request.CategoryRequest
import com.artatech.inkbook.recipes.api.response.GeneralResponse
import com.artatech.inkbook.recipes.api.response.PaginationRecipesResponse
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class GetRecipesByCategoryUseCaseTest {

    @Mock
    lateinit var repository: Repository

    lateinit var useCase: GetRecipesByCategoryUseCase

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        useCase = GetRecipesByCategoryUseCase(repository)
    }

    @Test
    fun `retrieve recipe list successfully`() {}

    @Test
    suspend fun `retrieve recipe list fail`() {
        val categoryRequest = CategoryRequest(3,3,3)
        val paginationListResponse = PaginationRecipesResponse(3,3,3, emptyList())
        val response = GeneralResponse<PaginationRecipesResponse>(404,"Error","Error", paginationListResponse)

        Mockito.`when`(repository.getRecipesByCategoryId(categoryRequest)).thenReturn(
            response.body
        )
    }

}