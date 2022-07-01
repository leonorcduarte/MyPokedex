package com.example.mypokedex

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import org.junit.Test

class PokemonDetailFeature : BaseUITest() {

    @Test
    fun displayPokemonTitleNameAndId(){
        Thread.sleep(2000)
        onView(withId(R.id.pokemon_list)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        Thread.sleep(1000)

        assertDisplayed("Pokémon Detail")
        assertDisplayed("bulbasaur")
        assertDisplayed("#001")
    }
}