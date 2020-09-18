package com.artatech.inkbook.recipes.ui.recipesfavoritelist.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.artatech.inkbook.recipes.R
import com.artatech.inkbook.recipes.api.RECIPE_PER_PAGE
import com.artatech.inkbook.recipes.api.response.models.FullRecipeResponse
import com.artatech.inkbook.recipes.core.utils.RecipePreference
import com.artatech.inkbook.recipes.ui.recipeslist.presentation.RecipesAdapter
import com.artatech.inkbook.recipes.ui.recipeslist.presentation.RecipesFragment
import com.artatech.inkbook.recipes.ui.recipeslist.presentation.RecipesViewModel
import kotlinx.android.synthetic.main.recipes_activity.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class RecipesFavoriteFragment : Fragment() {

    private val viewModel: RecipesFavoriteViewModel by viewModel()
    private val recipesAdapter: RecipesAdapter by inject()
    private val linearLayoutManager: RecyclerView.LayoutManager by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_recipes, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        observeData()
        initRecycler()
        viewModel.onViewCreated()
    }

    private fun initToolbar() {
        toolbarTitle.text = "Любимые рецепты"

        searchView.setOnSearchClickListener {
            Log.d(TAG, "Search clicked")
            searchView.background = ContextCompat.getDrawable(requireContext(), R.drawable.searchview_rounded_background)
            toolbarTitle.visibility = View.INVISIBLE
        }

        searchView.setOnCloseListener {
            searchView.background = null
            toolbarTitle.visibility = View.VISIBLE
            false
        }

    }

    private fun initRecycler() {
        recyclerView.apply {
            adapter = recipesAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }
    }

    private fun observeData() {
        viewModel.favoriteRecipes.observe(this) {
            val recipeListener = object : RecipesAdapter.RecipeListener {
                override fun onClick(item: FullRecipeResponse, itemView: View) {
                    TODO("Feature open short detail not implemented yet")
                }

                override fun onFavoriteClick(position: Int, item: FullRecipeResponse) {
                    recipesAdapter.removeItem(position, item)
                    viewModel.removeFromFavorite(item)
                }

                override fun isRecipeInFavorite(id: Int) = true
            }

            recipesAdapter.setItems(it, recipeListener)

            if (it.isEmpty()) {
                TODO("Show empty message not realize yet!")
            }

        }
    }

    companion object {
        private val TAG = RecipesFavoriteFragment::class.java.simpleName

        @JvmStatic
        fun newInstance() =
            RecipesFavoriteFragment()
    }
}