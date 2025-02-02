package com.benetatos.core.domain

import android.content.SharedPreferences
import com.benetatos.core.domain.models.FavoriteCategory
import com.benetatos.core.domain.models.UserInfo
import com.benetatos.core.domain.preferences.Preferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DefaultPreferences(
    private val sharedPreferences: SharedPreferences
) : Preferences {

    override fun saveFavoriteCategory(favoriteCategories: List<FavoriteCategory>) {
        val favoriteCategoriesString = Gson().toJson(favoriteCategories)
        sharedPreferences.edit()
            .putString(Preferences.KEY_FAVORITE_CATEGORIES, favoriteCategoriesString).apply()
    }

    override fun loadUserInfo(): UserInfo {
        val favoriteCategoriesString =
            sharedPreferences.getString(Preferences.KEY_FAVORITE_CATEGORIES, null)
        val favoriteCategories = favoriteCategoriesString?.let {
            val type = object : TypeToken<List<FavoriteCategory>>() {}.type
            Gson().fromJson(it, type)
        } ?: emptyList<FavoriteCategory>()



        return UserInfo(favoriteCategories = FavoriteCategory.fromString(favoriteCategories))
    }

}