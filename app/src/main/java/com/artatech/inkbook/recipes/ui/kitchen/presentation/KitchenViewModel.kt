package com.artatech.inkbook.recipes.ui.kitchen.presentation

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.artatech.inkbook.recipes.api.response.models.recipe.KitchenResponse
import com.artatech.inkbook.recipes.api.response.models.recipe.TastyResponse
import com.artatech.inkbook.recipes.core.utils.SingleLiveEvent

class KitchenViewModel : ViewModel() {

    val error = SingleLiveEvent<Throwable>()
    val tastes = MutableLiveData<List<TastyResponse>>()
    val kitchens = MutableLiveData<List<KitchenResponse>>()

    fun onViewCreated(key: String, arguments: Bundle?) {
        if (arguments != null) {
            val kitchenModel = arguments.getSerializable(key) as KitchenBundleModel
            kitchens.value = kitchenModel.kitchens
            tastes.value = kitchenModel.tastes

        } else {
            error.value = Throwable("Argument with $key key should not be null!")
        }
    }

    fun onKitchenClick(kitchenModel: KitchenResponse) {
        TODO("Not yet implemented")
    }

    fun onTastyClick(tastyModel: TastyResponse) {
        TODO("Not yet implemented")
    }
}