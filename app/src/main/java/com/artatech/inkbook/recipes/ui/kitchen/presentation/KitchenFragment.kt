package com.artatech.inkbook.recipes.ui.kitchen.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.observe
import com.artatech.inkbook.recipes.R
import com.artatech.inkbook.recipes.api.response.models.recipe.CountryResponse
import com.artatech.inkbook.recipes.api.response.models.recipe.TastyResponse
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.kitchen_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

const val COUNT_OF_CHIP_GROUP = 4

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
            createChipGroup(it, countryGroupContainer) { item ->
                viewModel.onKitchenClick(item)
            }
        }

        viewModel.tastes.observe(this) {
            createChipGroup(it, tastyGroupContainer) {item ->
                viewModel.onTastyClick(item)
            }
        }
    }

    private fun <T: KitchenModel>createChipGroup(items: List<T>, container: LinearLayout, callback: (T) -> Unit) {
        val chipGroupsItems = getListGroupItems(items, COUNT_OF_CHIP_GROUP)

        chipGroupsItems.forEach { groupItems ->

            val chipGrout = ChipGroup(requireContext())
            chipGrout.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)

            for (item in groupItems) {
                addChip(chipGrout, item) {
                    callback.invoke(item)
                }
            }
            container.addView(chipGrout)
        }
    }

    private fun <T: KitchenModel>addChip(chipGrout: ChipGroup, model: T, callback: (T) -> Unit) {
        val inflater = LayoutInflater.from(requireContext())
        val chip = inflater.inflate(R.layout.item_chip, chipGrout, false) as Chip
        chip.text = model.getNameForTitle()

        chipGrout.addView(chip)
        chip.setOnClickListener {
            callback.invoke(model)
        }
    }

    private fun observeError() {
        viewModel.error.observe(this) {
            Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_LONG).show()
        }
    }

    fun setArguments(countryList: List<CountryResponse>, tastyList: List<TastyResponse>) {
        val bundle = Bundle()
        val model = KitchenBundleModel(countryList, tastyList)
        bundle.putSerializable(KITCHEN_KEY, model)
        arguments = bundle
    }

    companion object {
        private const val KITCHEN_KEY = "kitchen_key"

        fun newInstance() = KitchenFragment()
    }
}

fun <T>getListGroupItems(items: List<T>, rowCount: Int): List<List<T>> {
    val groups = arrayListOf<List<T>>()
    val itemsInGroup = items.size / rowCount

    for (i in 0 until rowCount) {
        val from = i * itemsInGroup
        var to = (i + 1) * itemsInGroup

        if (i == itemsInGroup - 1) {
            to = items.lastIndex + 1
        }

        val group = items.subList(from, to)
        groups.add(group)
    }

    return groups
}