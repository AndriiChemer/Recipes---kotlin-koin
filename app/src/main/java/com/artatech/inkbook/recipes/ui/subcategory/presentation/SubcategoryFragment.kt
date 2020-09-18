package com.artatech.inkbook.recipes.ui.subcategory.presentation

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.artatech.inkbook.recipes.R
import com.artatech.inkbook.recipes.api.response.models.category.CategoryModel
import com.artatech.inkbook.recipes.ui.FragmentNavigationListener
import com.artatech.inkbook.recipes.ui.category.presentation.CategoriesFragment
import com.artatech.inkbook.recipes.ui.recipeslist.presentation.RecipesActivity
import com.artatech.inkbook.recipes.ui.subcategory.presentation.adapter.SubcategoryAdapter
import kotlinx.android.synthetic.main.subcategory_activity.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class SubcategoryFragment : Fragment() {

    private val ANIMATION_DURATION = 250L
    private val viewModel: SubcategoryViewModel by viewModel()
    private val subcategoryAdapter: SubcategoryAdapter by inject()

    private var fragmentNavigation: FragmentNavigationListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            fragmentNavigation = context as FragmentNavigationListener?
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_subcategory, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
        observeData()
        observeError()
        viewModel.onViewCreated(SUBCATEGORY_KEY, arguments)
    }

    private fun initRecycler() {
        val animation = DefaultItemAnimator()
        animation.supportsChangeAnimations = false
        animation.removeDuration = ANIMATION_DURATION

        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = subcategoryAdapter
            itemAnimator = animation
            isNestedScrollingEnabled = false
        }

        nestedScrollView.isNestedScrollingEnabled = false
        nestedScrollView.post {nestedScrollView.scrollTo(0, 0)}
    }

    private fun observeData() {
        val adapterListener = object : SubcategoryAdapter.SubcategoryListener {
            override fun scrollTo(y: Int, delay: Int) {
                val runnable = Runnable {
                    nestedScrollView.smoothScrollTo(0, y)
                }
                Handler().postDelayed(runnable, delay.toLong())
            }

            override fun onRecipeCategoryClick(subcategoryId: Int, recipeCategoryId: Int, title: String) {
                viewModel.onSubcategoryClicked(subcategoryId, recipeCategoryId, title)
            }

            override fun onSubcategoryClick(subcategoryId: Int, title: String) {
                viewModel.onSubcategoryClicked(subcategoryId, null, title)
            }
        }

        viewModel.toolbarTitle.observe(this) {
            categoryName.text = it
        }

        viewModel.subcategoryData.observe(this) {
            subcategoryAdapter.setItems(it, adapterListener)
        }

        viewModel.categoryData.observe(this) {
//            fragmentNavigation?.onNavigate()
        }
    }

    private fun observeError() {
        viewModel.error.observe(this) {
            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
        }
    }

    fun setArguments(categoryModel: CategoryModel) {
        val bundle = Bundle()
        bundle.putSerializable(SUBCATEGORY_KEY, categoryModel)
        arguments = bundle
    }

    companion object {
        private const val SUBCATEGORY_KEY = "subcategory_key"

        @JvmStatic
        fun newInstance() = SubcategoryFragment()
    }
}