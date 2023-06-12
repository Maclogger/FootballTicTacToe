package com.example.tictactoe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation

class TitleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_title, container, false)
        view.findViewById<Button>(R.id.buttonSinglePlayer).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.navigateToVyberObtiaznostiFragment)
        }

        return view

    }




}
