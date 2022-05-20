package com.example.mypokedex.ui.pokedetail.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.mypokedex.R

class EvolutionChainAdapter(
    private var evolutionsList: List<String>,
    private var colorPair: Pair<String, List<Int?>>
): RecyclerView.Adapter<EvolutionChainAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.evolution_item, parent, false))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val evolutionName: TextView = itemView.findViewById(R.id.pokemon_name)
        private val nameContainer: ConstraintLayout = itemView.findViewById(R.id.name_container)
        private val arrow: ImageView = itemView.findViewById(R.id.next_arrow)

        fun bind(position: Int, name: String){
            evolutionName.text = name
            arrow.visibility = if (position == itemCount - 1) View.GONE else View.VISIBLE

            colorPair.second[0]?.let { nameContainer.background.setTint(it) }
            colorPair.second[1]?.let { arrow.setColorFilter(it) }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position, evolutionsList[position])
    }

    override fun getItemCount() = evolutionsList.size
}