package com.example.mypokedex.util

import android.app.Activity
import com.example.mypokedex.R

object PokemonColorUtils {

    fun getBackgroundColors(activity: Activity?) : ArrayList<Pair<String, List<Int?>>> {
        val colorPairList = ArrayList<Pair<String, List<Int?>>>()
        colorPairList.add(Pair("black",
            listOf(activity?.resources?.getColor(R.color.primary_black_pokemon, activity.theme),
                activity?.resources?.getColor(R.color.black, activity.theme))))
        colorPairList.add(Pair("blue",
            listOf(activity?.resources?.getColor(R.color.primary_blue_pokemon, activity.theme),
                activity?.resources?.getColor(R.color.secondary_blue_pokemon, activity.theme))))
        colorPairList.add(Pair("brown",
            listOf(activity?.resources?.getColor(R.color.primary_brown_pokemon, activity.theme),
                activity?.resources?.getColor(R.color.secondary_brown_pokemon, activity.theme))))
        colorPairList.add(Pair("gray",
            listOf(activity?.resources?.getColor(R.color.primary_gray_pokemon, activity.theme),
                activity?.resources?.getColor(R.color.secondary_gray_pokemon, activity.theme))))
        colorPairList.add(Pair("green",
            listOf(activity?.resources?.getColor(R.color.primary_green_pokemon, activity.theme),
                activity?.resources?.getColor(R.color.secondary_green_pokemon, activity.theme))))
        colorPairList.add(Pair("pink",
            listOf(activity?.resources?.getColor(R.color.primary_pink_pokemon, activity.theme),
                activity?.resources?.getColor(R.color.secondary_pink_pokemon, activity.theme))))
        colorPairList.add(Pair("purple",
            listOf(activity?.resources?.getColor(R.color.primary_purple_pokemon, activity.theme),
                activity?.resources?.getColor(R.color.secondary_purple_pokemon, activity.theme))))
        colorPairList.add(Pair("red",
            listOf(activity?.resources?.getColor(R.color.primary_red_pokemon, activity.theme),
                activity?.resources?.getColor(R.color.secondary_red_pokemon, activity.theme))))
        colorPairList.add(Pair("yellow",
            listOf(activity?.resources?.getColor(R.color.primary_yellow_pokemon, activity.theme),
                activity?.resources?.getColor(R.color.secondary_yellow_pokemon, activity.theme))))
        colorPairList.add(Pair("white",
            listOf(activity?.resources?.getColor(R.color.white, activity.theme),
                activity?.resources?.getColor(R.color.white, activity.theme))))

        return colorPairList
    }

    fun getTypeColors(activity: Activity?): ArrayList<Pair<String, Int?>> {
        val colorPairList = ArrayList<Pair<String, Int?>>()
        colorPairList.add(Pair("normal", activity?.resources?.getColor(R.color.normal_type_gray, activity.theme)))
        colorPairList.add(Pair("fire", activity?.resources?.getColor(R.color.fire_type_red, activity.theme)))
        colorPairList.add(Pair("water", activity?.resources?.getColor(R.color.water_type_blue, activity.theme)))
        colorPairList.add(Pair("electric", activity?.resources?.getColor(R.color.electric_type_yellow, activity.theme)))
        colorPairList.add(Pair("grass", activity?.resources?.getColor(R.color.grass_type_green, activity.theme)))
        colorPairList.add(Pair("ice", activity?.resources?.getColor(R.color.ice_type_blue, activity.theme)))
        colorPairList.add(Pair("fighting", activity?.resources?.getColor(R.color.fighting_type_red, activity.theme)))
        colorPairList.add(Pair("poison", activity?.resources?.getColor(R.color.poison_type_purple, activity.theme)))
        colorPairList.add(Pair("ground", activity?.resources?.getColor(R.color.ground_type_yellow, activity.theme)))
        colorPairList.add(Pair("flying", activity?.resources?.getColor(R.color.flying_type_purple, activity.theme)))
        colorPairList.add(Pair("psychic", activity?.resources?.getColor(R.color.psychic_type_pink, activity.theme)))
        colorPairList.add(Pair("bug", activity?.resources?.getColor(R.color.bug_type_green, activity.theme)))
        colorPairList.add(Pair("rock", activity?.resources?.getColor(R.color.rock_type_yellow, activity.theme)))
        colorPairList.add(Pair("ghost", activity?.resources?.getColor(R.color.ghost_type_purple, activity.theme)))
        colorPairList.add(Pair("dragon", activity?.resources?.getColor(R.color.dragon_type_purple, activity.theme)))
        colorPairList.add(Pair("dark", activity?.resources?.getColor(R.color.dark_type_brown, activity.theme)))
        colorPairList.add(Pair("steel", activity?.resources?.getColor(R.color.steel_type_gray, activity.theme)))
        colorPairList.add(Pair("fairy", activity?.resources?.getColor(R.color.fairy_type_pink, activity.theme)))

        return colorPairList
    }


}