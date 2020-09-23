package com.artatech.inkbook.recipes.core.ui

import androidx.appcompat.app.AppCompatActivity
import com.artatech.inkbook.recipes.R

abstract class AbstractActivity: AppCompatActivity() {

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.no_animation, R.anim.slide_out_right)
    }
}