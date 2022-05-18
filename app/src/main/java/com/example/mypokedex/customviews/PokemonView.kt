package com.example.mypokedex.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.example.mypokedex.R
import com.example.mypokedex.data.customviewmodel.PokemonModelView

class PokemonView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : CardView(context, attrs) {

    private val pokeImage: ImageView
    private val id: TextView
    private val name: TextView

    init {
        val view = LayoutInflater
            .from(context)
            .inflate(R.layout.pokemon_item_view, this, true)

        pokeImage = view.findViewById(R.id.poke_image)
        id = view.findViewById(R.id.poke_id)
        name = view.findViewById(R.id.poke_name)
    }

    fun setPokemonView(pokemonDetail: PokemonModelView){
        name.text = pokemonDetail.name
        id.text = pokemonDetail.id.toString()

        Glide.with(context).load(pokemonDetail.imageUrl).into(pokeImage)
    }

}