package com.example.mypokedex

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adevinta.android.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.adevinta.android.barista.internal.matcher.DrawableMatcher.Companion.withDrawable
import com.example.mypokedex.ui.BaseActivity
import com.example.mypokedex.ui.pokelist.adapters.PokemonListAdapter
import kotlinx.coroutines.Delay
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.AllOf.allOf

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class PokemonListFeature {

    val mActivityRule = ActivityScenarioRule(BaseActivity::class.java)
        @Rule get
    
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
        onView(withId(R.id.pokemon_list)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        Thread.sleep(500)
        assertDisplayed(R.id.main_detail_layout)
    }


    /**
     * Utility function
     * when accessing the parent view, in this case the list, we access the nth child
     * if childPosition = 0, returns the first item in the list
     */
    fun nthChildOf(parentMatcher: Matcher<View>, childPosition: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            public override fun matchesSafely(view: View): Boolean {
                if (view.parent !is ViewGroup) return false
                val parent = view.parent as ViewGroup

                return (parentMatcher.matches(parent)
                        && parent.childCount > childPosition
                        && parent.getChildAt(childPosition) == view)
            }

            override fun describeTo(description: org.hamcrest.Description?) {
                description?.appendText("position $childPosition of parent ")
                parentMatcher.describeTo(description)
            }
        }
    }
}