package com.artatech.inkbook.recipes.core.extentions

import android.os.Bundle
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

fun <T> Bundle.getFromExtras(tag: String): T {
    val gson = Gson()
    val jsonObject = getString(tag)
    if (jsonObject != null) {
        val type: Type = object : TypeToken<T>() {}.type
        return gson.fromJson(jsonObject, type)
    } else {
        throw NullPointerException("param $tag should not be null!")
    }
}