package com.benetatos.core.domain.models

sealed class FavoriteCategory(val sportName : String) {

    data object Soccer : FavoriteCategory("SOCCER")

    companion object {
        fun fromString ( sportName: List<FavoriteCategory>) : List<FavoriteCategory> {
            val favoriteCategory : MutableList<FavoriteCategory> = mutableListOf()

            sportName.map {
               when(it.sportName){
                   "SOCCER" -> favoriteCategory.add(Soccer)
                   else -> Soccer // that will never happened
               }
            }
            return favoriteCategory
        }
    }
}