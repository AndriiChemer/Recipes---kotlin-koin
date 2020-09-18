package com.artatech.inkbook.recipes.ui.subcategory.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.artatech.inkbook.recipes.R

class SubcategoriesFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
        = inflater.inflate(R.layout.fragment_subcategories, container, false)


    companion object {
        @JvmStatic
        fun newInstance() = SubcategoriesFragment()
    }
}