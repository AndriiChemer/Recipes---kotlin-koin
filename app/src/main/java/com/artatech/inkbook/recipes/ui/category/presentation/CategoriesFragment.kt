package com.artatech.inkbook.recipes.ui.category.presentation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.artatech.inkbook.recipes.R
import com.artatech.inkbook.recipes.api.response.models.category.CategoryModel
import com.artatech.inkbook.recipes.core.ui.adapter.SpacingItemDecoration
import com.artatech.inkbook.recipes.ui.FragmentNavigationListener
import com.artatech.inkbook.recipes.ui.subcategory.presentation.SubcategoryActivity
import com.artatech.inkbook.recipes.ui.subcategory.presentation.SubcategoryFragment
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

    private val subcategoriesFragment: SubcategoryFragment by inject()

    private val viewModel: CategoriesViewModel by viewModel()
    private val categoriesAdapter: CategoriesAdapter by inject()
    private var fragmentNavigation: FragmentNavigationListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            fragmentNavigation = context as FragmentNavigationListener?
        } catch (e: ClassCastException) {
            e.printStackTrace()
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
        observeCategoryNavigation()

        viewModel.onViewCreated(CATEGORIES_KEY, arguments)
    }

    private fun initRecycler() {
        val onCategoryClicked: (category: CategoryModel) -> Unit = { category ->
            viewModel.categoryClicked(category)
        }

        categoriesAdapter.setClickListener(onCategoryClicked)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity())
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
//            SubcategoryActivity.start(requireContext(), it)
            subcategoriesFragment.setArguments(it)
            fragmentNavigation?.onNavigate(subcategoriesFragment)
        }
    }

    fun setArguments(categories: List<CategoryModel>) {
        val gson = Gson()
        val bundle = Bundle()
        bundle.putString(CATEGORIES_KEY, gson.toJson(categories))
        arguments = bundle
    }
}