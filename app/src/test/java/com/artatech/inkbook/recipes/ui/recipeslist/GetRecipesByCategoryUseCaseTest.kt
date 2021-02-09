package com.artatech.inkbook.recipes.ui.recipeslist

import com.artatech.inkbook.recipes.TestCoroutineRule
import com.artatech.inkbook.recipes.api.Repository
import com.artatech.inkbook.recipes.api.request.CategoryRequest
import com.artatech.inkbook.recipes.api.response.PaginationRecipesResponse
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*

import org.mockito.MockitoAnnotations

class GetRecipesByCategoryUseCaseTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    lateinit var repository: Repository

    @Mock
    lateinit var recipeCallback: (Result<PaginationRecipesResponse>) -> Unit

    lateinit var useCase: GetRecipesByCategoryUseCase

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        useCase = GetRecipesByCategoryUseCase(repository)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `retrieve recipe list successfully`() = runBlocking {
        val categoryRequest = CategoryRequest(3,3,3)
        val model = PaginationRecipesResponse(0,0,0, emptyList())
        val result = Result.success(model)

        `when`(repository.getRecipesByCategoryId(categoryRequest)).thenReturn(model)
        `when`(recipeCallback.invoke(result)).thenAnswer {
            val arg = it.arguments[1]
            val completion = arg as PaginationRecipesResponse
            recipeCallback.invoke(Result.success(completion))
        }

        useCase(categoryRequest, this){
            assertEquals(result, it)
        }
    }

//    @ExperimentalCoroutinesApi
//    @Test
//    fun `retrieve recipe list fail`() = runBlocking {
//        val categoryRequest = CategoryRequest(3,3,3)
//        val exception = Throwable("Exception")
//        val result = Result.failure<Exception>(exception)
//
//
//        runBlockingTest {
//            `when`(repository.getRecipesByCategoryId(categoryRequest)).thenThrow(exception)
//
//            `when`(recipeCallback.invoke(Result.failure(exception))).thenAnswer {
//                val arg = it.arguments[1]
//                val completion = arg as Throwable
//                recipeCallback.invoke(Result.failure(completion))
//            }
//
//            useCase(categoryRequest, this){
//                assertEquals(result, it)
//            }
//        }
//    }
}