package com.artatech.inkbook.recipes.ui.recipeslist.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.artatech.inkbook.recipes.R
import com.artatech.inkbook.recipes.api.RECIPE_PER_PAGE
import com.artatech.inkbook.recipes.api.response.PaginationRecipesResponse
import com.artatech.inkbook.recipes.api.response.models.FullRecipeResponse
import com.artatech.inkbook.recipes.core.ui.AbstractActivity
import com.artatech.inkbook.recipes.core.ui.adapter.SpacingItemDecoration
import com.artatech.inkbook.recipes.core.utils.RecipePreference
import com.artatech.inkbook.recipes.ui.recipeshortdetail.presentation.RecipeShortDetailActivity
import com.artatech.inkbook.recipes.ui.subcategory.presentation.CategoryIntentModel
import kotlinx.android.synthetic.main.recipes_activity.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.NullPointerException

class RecipesActivity : AbstractActivity() {

    private val TAG = RecipesActivity::class.java.simpleName

    private var isLoading = false
    private var isLastPage = false
    private lateinit var paginationData: PaginationRecipesResponse

    private val viewModel: RecipesViewModel by viewModel()
    private val recipesAdapter: RecipesAdapter by inject()
    private val linearLayoutManager: RecyclerView.LayoutManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipes_activity)

        val categoryRequest = getCategoryRequestFromExtra()

        initToolbar(categoryRequest)
        observeData()
        observeError()
        initRecycler()

        viewModel.getRecipes(categoryRequest)
    }

    private fun initToolbar(categoryIntentModel: CategoryIntentModel) {
        toolbarTitle.text = categoryIntentModel.title

        searchView.setOnSearchClickListener {
            Log.d(TAG, "Search clicked")
            searchView.background = ContextCompat.getDrawable(this, R.drawable.searchview_rounded_background)
            toolbarTitle.visibility = View.INVISIBLE
        }

        searchView.setOnCloseListener {
            searchView.background = null
            toolbarTitle.visibility = View.VISIBLE
            false
        }

    }

    private fun initRecycler() {
        val paginationListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                // number of visible items
                val visibleItemCount: Int = if(recyclerView.layoutManager != null) recyclerView.layoutManager!!.childCount else 0
                // number of items in layout
                val totalItemCount = if(recyclerView.layoutManager != null) recyclerView.layoutManager!!.itemCount else 0
                // the position of first visible item
                val firstVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
                // flag if number of visible items is at the last
                val isLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
                // validate non negative values
                val isFirstItemValid = firstVisibleItemPosition >= 0
                // validate total items are more than possible visible items
                val totalIsMoreThanVisible = totalItemCount >= RECIPE_PER_PAGE
                // flag to know whether to load more
                val shouldLoadMore = isFirstItemValid && isLastItem && totalIsMoreThanVisible && isNotLoadingAndNotLastPage

                if (shouldLoadMore) {
                    loadMoreItems()
                }
            }
        }

        recyclerView.apply {
            adapter = recipesAdapter
            layoutManager = linearLayoutManager
            addItemDecoration(SpacingItemDecoration(20))
            addOnScrollListener(paginationListener)
        }
    }

    private fun loadMoreItems() {
        val category = getCategoryRequestFromExtra()
        val nextPage = paginationData.currentPage + 1

        if (nextPage <= paginationData.totalPages) {
            viewModel.loadMoreRecipes(category, nextPage)
        } else {
            Log.d(TAG, "No more items, it was page")
        }
    }

    private fun observeData() {
        viewModel.recipes.observe(this) {
            showData(it)
        }

        viewModel.recipesLoadedMore.observe(this) {
            showData(it, false)
        }
    }

    private fun observeError() {
        viewModel.error.observe(this) { throwable ->
            throwable.message
                ?.let {
                    Toast.makeText(this, it, Toast.LENGTH_LONG).show()
                }
        }
    }

    private fun showData(paginationRecipe: PaginationRecipesResponse, isFirstPage: Boolean = true) {
        Log.d(TAG, "Current page: ${paginationRecipe.currentPage} | Total page: ${paginationRecipe.totalPages}")

        this.paginationData = paginationRecipe

        if (isFirstPage) {
            var favoriteRecipes = RecipePreference.recipeFavoriteMap
            val recipeListener = object : RecipesAdapter.RecipeListener {
                override fun onClick(item: FullRecipeResponse, itemView: View) {
                    showShortDetailFragment(item, itemView)
                }

                override fun onFavoriteClick(position: Int, item: FullRecipeResponse) {
                    RecipePreference.addRecipeToFavorite(item)
                    favoriteRecipes = RecipePreference.recipeFavoriteMap
                }

                override fun isRecipeInFavorite(id: Int) =
                    favoriteRecipes?.keys?.contains(id) ?: false

            }
            recipesAdapter.setItems(paginationRecipe.recipes, recipeListener)
        } else {
            recipesAdapter.addItems(paginationRecipe.recipes)
        }
    }

    private fun showShortDetailFragment(item: FullRecipeResponse, itemView: View) {
        RecipeShortDetailActivity.startWithAnimation(this, item, itemView)
    }

    private fun getCategoryRequestFromExtra(): CategoryIntentModel {
        val extras = intent.extras

        if (extras == null) {
            throw NullPointerException("$intent should not be null!")
        } else {
            return extras.getSerializable(CATEGORY_REQUEST_KEY) as CategoryIntentModel
        }
    }

    companion object {
        private const val CATEGORY_REQUEST_KEY = "category_request_key"

        fun start(context: FragmentActivity, categoryIntentModel: CategoryIntentModel) {
            val intent = Intent(context, RecipesActivity::class.java)
            intent.putExtra(CATEGORY_REQUEST_KEY, categoryIntentModel)
            context.startActivity(intent)
            context.overridePendingTransition(R.anim.slide_in_right,  R.anim.no_animation)
        }
    }
}
