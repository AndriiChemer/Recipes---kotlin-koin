package com.artatech.inkbook.recipes.ui.recipeslist.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.artatech.inkbook.recipes.api.request.CategoryRequest
import com.artatech.inkbook.recipes.api.response.PaginationRecipesResponse
import com.artatech.inkbook.recipes.ui.recipeslist.GetRecipesByCategoryUseCase
import com.artatech.inkbook.recipes.ui.subcategory.presentation.CategoryIntentModel
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

    @Mock
    private lateinit var mockObserver: Observer<PaginationRecipesResponse>

//    @InjectMocks
//    var viewModel = RecipesViewModel(useCaseMock)
    lateinit var viewModel: RecipesViewModel

//    @InjectMocks
//    var viewModel = RecipesViewModel(useCaseMock)

    @Mock
    lateinit var callbackMock: (Result<PaginationRecipesResponse>) -> Unit

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = RecipesViewModel(useCaseMock)
    }

    @Test
    fun `retrieve recipe list successfully`() {
        val paginationListResponse = PaginationRecipesResponse(3,3,3, emptyList())
        val categoryRequest = CategoryRequest(3,3,3)
        val category = CategoryIntentModel(1,1,null, "title")

        `when`(useCaseMock.invoke(categoryRequest, viewModel.viewModelScope, callbackMock)).then {
            paginationListResponse
        }

        viewModel.getRecipes(category)
//        liveData.observeForever(mockObserver)

        val result = useCaseMock.invoke(categoryRequest, viewModel.viewModelScope, callbackMock)

        Assert.assertNotEquals(result, viewModel.recipes.value)
    }
}