package com.artatech.inkbook.recipes.ui.subcategory.presentation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.artatech.inkbook.recipes.R
import com.artatech.inkbook.recipes.api.response.models.category.CategoryModel
import com.artatech.inkbook.recipes.core.ui.adapter.SpacingItemDecoration
import com.artatech.inkbook.recipes.ui.FragmentNavigationListener
import com.artatech.inkbook.recipes.ui.subcategory.presentation.adapter.SubcategoriesAdapter
import com.artatech.inkbook.recipes.ui.subcategory.presentation.adapter.SubcategoryWithoutRecCatAdapter
import kotlinx.android.synthetic.main.fragment_subcategories.*
import org.koin.android.viewmodel.ext.android.viewModel

class SubcategoryFragment : Fragment() {

    private val viewModel: SubcategoryViewModel by viewModel()
    private val subcategoryAdapter: SubcategoriesAdapter = SubcategoriesAdapter()
    private val subcategoryWithoutRecCatAdapter: SubcategoryWithoutRecCatAdapter = SubcategoryWithoutRecCatAdapter()

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
            = inflater.inflate(R.layout.fragment_subcategories, container, false)

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

        sybcatWithoutRecipeCatRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false)
            adapter = subcategoryWithoutRecCatAdapter
            addItemDecoration(SpacingItemDecoration(40))
        }


        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = subcategoryAdapter
            addItemDecoration(SpacingItemDecoration(40))
        }
    }

    private fun observeData() {
        viewModel.toolbarTitle.observe(this) {

        }

        viewModel.subcategoryData.observe(this) {
            subcategoryAdapter.setItems(it, object : SubcategoriesAdapter.SubcategoryListener {
                override fun onClick(categoryId: Int, subcategoryId: Int, recipeCategoryId: Int, title: String) {
                    viewModel.onSubcategoryClicked(categoryId, subcategoryId, recipeCategoryId, title)
                }

            })
        }

        viewModel.recipeCategoryData.observe(this) {
            if (it.isEmpty()) {
                hideSubcategoryWithoutRecCat()
            } else {
                subcategoryWithoutRecCatAdapter.setItems(it, object : SubcategoryWithoutRecCatAdapter.Listener {
                    override fun onSubcategoryClicked(categoryId: Int, subcategoryId: Int, title: String) {
                        viewModel.onSubcategoryClicked(categoryId, subcategoryId, null, title)
                    }

                })
            }
        }

        viewModel.categoryData.observe(this) {
//            fragmentNavigation?.onNavigate()
        }
    }

    private fun hideSubcategoryWithoutRecCat() {
        val params: ConstraintLayout.LayoutParams = recyclerGuidLine.layoutParams as ConstraintLayout.LayoutParams
        params.guidePercent = 0.0f
        recyclerGuidLine.layoutParams = params
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