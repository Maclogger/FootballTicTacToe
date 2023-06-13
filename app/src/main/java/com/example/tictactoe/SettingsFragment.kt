package com.example.tictactoe

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation
import java.util.Locale


class SettingsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        view.findViewById<ImageView>(R.id.anglickaVlajka).setOnClickListener() {
            nastavJazyk("en", view)
        }
        view.findViewById<ImageView>(R.id.slovenskaVlajka).setOnClickListener() {
            nastavJazyk("sk", view)
        }
        return view
    }

    private fun nastavJazyk(skratka_jazyka: String, view: View) {
        val locale = Locale(skratka_jazyka)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        resources.updateConfiguration(config, resources.displayMetrics)
        Navigation.findNavController(view).navigate(R.id.navigateToTitleFragment)
    }
}