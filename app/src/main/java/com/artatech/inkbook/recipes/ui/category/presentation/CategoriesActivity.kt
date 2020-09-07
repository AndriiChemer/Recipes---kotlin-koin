package com.artatech.inkbook.recipes.ui.category.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.artatech.inkbook.recipes.R
import com.artatech.inkbook.recipes.api.response.models.category.CategoryModel
import com.artatech.inkbook.recipes.ui.subcategory.presentation.SubcategoryActivity
import kotlinx.android.synthetic.main.categories_activity.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class CategoriesActivity : AppCompatActivity() {

    private val viewModel: CategoriesViewModel by viewModel()
    private val categoriesAdapter: CategoriesAdapter by inject()
    private val linearLayoutManager: RecyclerView.LayoutManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.categories_activity)

        initRecycler()
        observeData()
        observeError()
        observeCategoryNavigation()

        viewModel.onViewCreated()
    }

    private fun observeCategoryNavigation() {
        viewModel.navigateToSubcategory.observe(this) {
            SubcategoryActivity.start(this, it)
        }
    }

    private fun initRecycler() {
        val onCategoryClicked: (category: CategoryModel) -> Unit = { category ->
            viewModel.categoryClicked(category)
        }

        categoriesAdapter.setClickListener(onCategoryClicked)

        recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = categoriesAdapter
        }
    }

    private fun observeData() {
        viewModel.categories.observe(this) {
            categoriesAdapter.setCategories(it)
        }
    }

    private fun observeError() {
        viewModel.error.observe(this) { throwable ->
            throwable.message
                ?.let { showErrorMessage(it) }
        }
    }

    private fun showErrorMessage(message: String) {
        Toast.makeText(
            this,
            "An error occurred, code: $message}",
            Toast.LENGTH_SHORT
        ).show()
    }
}
