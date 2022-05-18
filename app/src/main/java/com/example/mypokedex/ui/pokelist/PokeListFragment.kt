package com.example.mypokedex.ui.pokelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.mypokedex.R
import com.example.mypokedex.databinding.PokeListFragmentLayoutBinding

class PokeListFragment : Fragment() {

    private lateinit var binding: PokeListFragmentLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.poke_list_fragment_layout, container, false)

        //initScreen()
        return binding.root
    }

    /*private fun initScreen() {
        TODO("Not yet implemented")
    }*/
}