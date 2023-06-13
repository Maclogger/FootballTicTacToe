package com.example.tictactoe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation

class KoniecHryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_koniec_hry, container, false)
        val vysledok = arguments?.getInt("vysledok")
        if (vysledok == 0) {
            view.findViewById<TextView>(R.id.hlavnyText).setText(getString(R.string.winner_o))
        } else if (vysledok == 1) {
            view.findViewById<TextView>(R.id.hlavnyText).setText(getString(R.string.winner_x))
        }
        //remíza je spravená defaulnte, netreba ju vypisovať tu...

        view.findViewById<Button>(R.id.pokracovatButton).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.navigateToTitleFragment)
        }
        return view
    }
}