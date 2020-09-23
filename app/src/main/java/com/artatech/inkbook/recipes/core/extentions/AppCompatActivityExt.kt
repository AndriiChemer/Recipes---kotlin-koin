package com.artatech.inkbook.recipes.core.extentions

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.artatech.inkbook.recipes.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

fun <T> AppCompatActivity.getFromExtras(tag: String): T {
    val gson = Gson()
    val jsonObject = intent.extras?.getString(tag)
    if (jsonObject != null) {
        val type: Type = object : TypeToken<T>() {}.type
        return gson.fromJson(jsonObject, type)
    } else {
        throw NullPointerException("param $tag should not be null!")
    }
}

fun <T> Intent.getFromExtras(tag: String): T {
    val gson = Gson()
    val jsonObject = extras?.getString(tag)
    if (jsonObject != null) {
        val type: Type = object : TypeToken<T>() {}.type
        return gson.fromJson(jsonObject, type)
    } else {
        throw NullPointerException("param $tag should not be null!")
    }
}

fun AppCompatActivity.animationFinish() {
    finish()
    overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_left)
}