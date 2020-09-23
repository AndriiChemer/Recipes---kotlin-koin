package com.artatech.inkbook.recipes.ui.kitchen.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.observe
import com.artatech.inkbook.recipes.R
import com.artatech.inkbook.recipes.api.response.models.recipe.KitchenResponse
import com.artatech.inkbook.recipes.api.response.models.recipe.TastyResponse
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.kitchen_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class KitchenFragment : Fragment() {

    private val viewModel: KitchenViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.kitchen_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        observeError()
        viewModel.onViewCreated(KITCHEN_KEY, arguments)
    }

    private fun observeData() {
        viewModel.kitchens.observe(this) {
            it.forEach { kitchen ->
                addKitchenChip(kitchen)
            }
        }

        viewModel.tastes.observe(this) {
            it.forEach { tasty ->
                addTastyChip(tasty)
            }
        }
    }

    private fun observeError() {
        viewModel.error.observe(this) {
            Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_LONG).show()
        }
    }

    private fun addKitchenChip(kitchenModel: KitchenResponse) {
        val inflater = LayoutInflater.from(requireContext())
        val chip = inflater.inflate(R.layout.item_chip, kitchenChipGroup, false) as Chip
        chip.text = kitchenModel.name

        kitchenChipGroup.addView(chip)
        chip.setOnClickListener {
            viewModel.onKitchenClick(kitchenModel)
        }

    }

    private fun addTastyChip(tastyModel: TastyResponse) {
        val inflater = LayoutInflater.from(requireContext())
        val chip = inflater.inflate(R.layout.item_chip, tastyChipGroup, false) as Chip
        chip.text = tastyModel.name

        tastyChipGroup.addView(chip)
        chip.setOnClickListener {
            viewModel.onTastyClick(tastyModel)
        }
    }

    fun setArguments(kitchenList: List<KitchenResponse>, tastyList: List<TastyResponse>) {
        val bundle = Bundle()
        val model = KitchenBundleModel(kitchenList, tastyList)
        bundle.putSerializable(KITCHEN_KEY, model)
        arguments = bundle
    }

    companion object {
        private const val KITCHEN_KEY = "kitchen_key"

        fun newInstance() = KitchenFragment()
    }
}