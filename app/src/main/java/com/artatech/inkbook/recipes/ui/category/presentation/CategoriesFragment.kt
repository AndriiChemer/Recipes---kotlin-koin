package com.artatech.inkbook.recipes.ui.category.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.artatech.inkbook.recipes.R
import com.artatech.inkbook.recipes.api.response.models.category.CategoryModel
import com.artatech.inkbook.recipes.ui.subcategory.presentation.SubcategoryActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.categories_activity.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.reflect.Type

class CategoriesFragment : Fragment() {

    companion object {
        private const val CATEGORIES_KEY = "CATEGORIES_KEY"
        fun newInstance() = CategoriesFragment()
    }

    private val viewModel: CategoriesViewModel by viewModel()
    private val categoriesAdapter: CategoriesAdapter by inject()
    private val linearLayoutManager: RecyclerView.LayoutManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        getFromExtras(CATEGORIES_KEY, arguments)
    }

    private fun getFromExtras(key: String, bundle: Bundle?) {
        val gson = Gson()
        val jsonObject = bundle?.getString(key)
        if (jsonObject != null) {
            val type: Type = object : TypeToken<List<CategoryModel>>() {}.type
            val categoryList: List<CategoryModel> = gson.fromJson(jsonObject, type)
            Toast.makeText(requireContext(), "List size: ${categoryList.size}", Toast.LENGTH_LONG).show()
        } else {
            throw Throwable("Bundle does't have categories value")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.categories_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
        observeData()
        observeError()

        viewModel.onViewCreated(CATEGORIES_KEY, arguments)
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
            categoriesAdapter.setCategories(requireContext(), it)
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
            requireContext(),
            "An error occurred, code: $message}",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun observeCategoryNavigation() {
        viewModel.navigateToSubcategory.observe(this) {
            SubcategoryActivity.start(requireContext(), it)
        }
    }

    fun setArguments(categories: List<CategoryModel>) {
        val gson = Gson()
        val bundle = Bundle()
        bundle.putString(CATEGORIES_KEY, gson.toJson(categories))
        arguments = bundle
    }
}