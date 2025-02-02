package com.benetatos.core.domain.preferences

import com.benetatos.core.domain.models.FavoriteCategory
import com.benetatos.core.domain.models.UserInfo

interface Preferences {

    fun saveFavoriteCategory(favoriteCategories : List<FavoriteCategory>)

    fun loadUserInfo() : UserInfo

    companion object{
        const val KEY_FAVORITE_CATEGORIES = "favoriteCategories"
    }
}