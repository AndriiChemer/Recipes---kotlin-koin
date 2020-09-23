package com.artatech.inkbook.recipes

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.activity_custom_category.*
import kotlinx.android.synthetic.main.kitchen_fragment.kitchenChipGroup
import kotlinx.android.synthetic.main.kitchen_fragment.tastyChipGroup

class CustomCategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_category)

        val list = arrayListOf("sdfa", "sfda", "afsdf", "aasdavxcv", "acxv", "afs", "avxcv", "aasdavxcv", "a", "aasdavxcv", "a", "fsdfsda", "a", "aasdavxcv", "a", "aasdavxcv", "afs", "avxcv", "aasdavxcv", "a", "aasdavxcv", "a", "fsdfsda", "a", "aasdavxcv", "avxcv", "aasdavxcv", "a", "aasdavxcv", "a", "fsdfsda")
        val group = arrayListOf<List<String>>()
        val step = list.size / 5


        for (i in 0 until step) {

            val from = i * step
            var to = (i + 1) * step
            if (i == step - 1) {
                to = list.lastIndex
            }
            val newList = list.subList(from, to)
            group.add(newList)
        }



        group.forEach { listInGroup ->
            val chopGrout = ChipGroup(this)
            chopGrout.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)

            for (item in listInGroup) {
                addKitchenChip(chopGrout,item)
            }

            groupContainer.addView(chopGrout)
        }



        list.forEach { item ->
            addTastyChip(item)
        }
    }

    private fun addKitchenChip(chopGrout: ChipGroup, kitchenModel: String) {
        val inflater = LayoutInflater.from(this)
        val chip = inflater.inflate(R.layout.item_chip, kitchenChipGroup, false) as Chip
        chip.text = kitchenModel

        chip.setOnClickListener {
        }

        chopGrout.addView(chip)

    }

    private fun addTastyChip(tastyModel: String) {
        val inflater = LayoutInflater.from(this)
        val chip = inflater.inflate(R.layout.item_chip, tastyChipGroup, false) as Chip
        chip.text = tastyModel

        chipGroup.addView(chip)
        chip.setOnClickListener {
        }
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, CustomCategoryActivity::class.java)
            context.startActivity(intent)
        }
    }
}