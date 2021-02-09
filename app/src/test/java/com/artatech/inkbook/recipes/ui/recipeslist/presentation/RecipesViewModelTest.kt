package com.artatech.inkbook.recipes.ui.recipeslist.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.artatech.inkbook.recipes.api.Repository
import com.artatech.inkbook.recipes.api.request.CategoryRequest
import com.artatech.inkbook.recipes.api.response.PaginationRecipesResponse
import com.artatech.inkbook.recipes.ui.recipeslist.GetRecipesByCategoryUseCase
import com.artatech.inkbook.recipes.ui.subcategory.presentation.CategoryIntentModel
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.mockito.InjectMocks
import org.mockito.Mock

import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.invocation.InvocationOnMock
import org.mockito.stubbing.Answer
import java.lang.NullPointerException

class RecipesViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var useCaseMock: GetRecipesByCategoryUseCase

    lateinit var viewModel: RecipesViewModel

    @Mock
    lateinit var callbackMock: (Result<PaginationRecipesResponse>) -> Unit

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = RecipesViewModel(useCaseMock)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `retrieve recipe list successfully`() {
        val paginationListResponse = PaginationRecipesResponse(3,3,3, emptyList())
        val categoryRequest = CategoryRequest(3,3,3)
        val category = CategoryIntentModel(1,1,null, "title")
        val result =  Result.success(paginationListResponse)


        runBlocking {

            `when`(callbackMock.invoke(result)).thenAnswer {
                val arg = it.arguments[1]
                val completion = arg as PaginationRecipesResponse
                callbackMock(Result.success(completion))
            }

            `when`(useCaseMock.invoke(categoryRequest, this, callbackMock)).then {
                callbackMock(result)
            }

            `when`(viewModel.getRecipes(category)).then {
                useCaseMock.invoke(categoryRequest, this, callbackMock)
            }
        }
        viewModel.getRecipes(category)



    }
}