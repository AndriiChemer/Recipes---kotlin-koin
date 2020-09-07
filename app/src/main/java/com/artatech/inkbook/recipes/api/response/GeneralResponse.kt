package com.artatech.inkbook.recipes.api.response

class GeneralResponse<T>(
    val statusCode: Int,
    val status: String,
    val errorBody: String,
    val body: T
)