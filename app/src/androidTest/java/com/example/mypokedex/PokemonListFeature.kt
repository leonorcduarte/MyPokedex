package com.example.mypokedex

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.adevinta.android.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.adevinta.android.barista.internal.matcher.DrawableMatcher.Companion.withDrawable
import com.example.mypokedex.ui.BaseActivity
import org.hamcrest.core.AllOf.allOf

import org.junit.Test


class PokemonListFeature : BaseUITest(){
    
    @Test
    fun displayScreenTitle() {
        assertDisplayed("My Pokédex")
    }

    @Test
    fun displayStickyDialogView(){
        Thread.sleep(2000)
        assertDisplayed(R.id.dialog)
    }

    @Test
    fun hidesLoader(){
        Thread.sleep(2000)
        assertNotDisplayed(R.id.loading)
    }

    @Test
    fun displaysListOfPokemons(){
        Thread.sleep(2000)

        assertRecyclerViewItemCount(R.id.pokemon_list, 10)

        onView(
            allOf(
                withId(R.id.poke_id),
                isDescendantOfA(nthChildOf(withId(R.id.pokemon_list), 0))
            )
        ).check(matches(withText("#001")))
            .check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.poke_name),
                isDescendantOfA(nthChildOf(withId(R.id.pokemon_list), 0))
            )
        ).check(matches(withText("bulbasaur")))
            .check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.pokeball),
                isDescendantOfA(nthChildOf(withId(R.id.pokemon_list), 0))
            )
        ).check(matches(withDrawable(R.drawable.pokeball_icon)))
            .check(matches(isDisplayed()))
    }

    @Test
    fun navigateToDetailScreen(){
        Thread.sleep(2000)
        onView(withId(R.id.pokemon_list)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        Thread.sleep(1000)
        assertDisplayed(R.id.main_detail_layout)
    }
}