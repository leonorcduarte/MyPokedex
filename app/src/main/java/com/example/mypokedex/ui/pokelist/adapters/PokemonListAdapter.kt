package com.example.mypokedex.ui.pokelist.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mypokedex.R
import com.example.mypokedex.data.model.secondarymodels.BaseModel
import com.example.mypokedex.util.StringUtils

class PokemonListAdapter(
    private var pokeList: List<BaseModel>,
    private var listener: OnItemClickListener,
    private var context: Context
) : RecyclerView.Adapter<PokemonListAdapter.ViewHolder>(){

    interface OnItemClickListener{
        fun onPokeBallClick(position: Int, pokemonName: String)
    }

    private var mListener: OnItemClickListener = listener

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val pokeImage: ImageView = itemView.findViewById(R.id.poke_image)
        val id: TextView = itemView.findViewById(R.id.poke_id)
        val name: TextView = itemView.findViewById(R.id.poke_name)
        val default_image: ConstraintLayout = itemView.findViewById(R.id.default_image)


        fun bind(position: Int, pokemon: BaseModel){
            name.text = pokemon.name
            id.text = context.resources.getString(R.string.id_code, StringUtils.getSubstring(pokemon.url, "/", 6))

            if(pokemon.image != null){
                default_image.visibility = View.GONE
                pokeImage.visibility = View.VISIBLE
                Glide.with(context).load(pokemon.image).into(pokeImage)
            } else{
                default_image.visibility = View.VISIBLE
                pokeImage.visibility = View.GONE
            }

            default_image.setOnClickListener{
                mListener.onPokeBallClick(position = position, pokemonName = pokemon.name)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.pokemon_item_view, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon: BaseModel = pokeList[position]

        holder.bind(position, pokemon)
    }

    override fun getItemCount() = pokeList.size

    fun setList(newPokeList : List<BaseModel>, position: Int){
        pokeList = newPokeList
        notifyItemChanged(position)
    }

}