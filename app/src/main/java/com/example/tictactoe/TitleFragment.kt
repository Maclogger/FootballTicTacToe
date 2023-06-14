package com.example.tictactoe

import android.os.Bundle
import android.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.ViewGroupUtils
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation

class TitleFragment : Fragment() {
    private val sharedViewModel by activityViewModels<SharedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_title, container, false)
        view.findViewById<Button>(R.id.buttonMultiPlayer).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.navigateToVyberObtiaznostiFragment)
        }
        view.findViewById<Button>(R.id.buttonSettings).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.navigateToSettingsFragment)
        }
        sharedViewModel.getCurrentMainStyle().observe(viewLifecycleOwner) { style ->
            // Nastav štýl pre hlavný fragment
            val a = context?.obtainStyledAttributes(style, intArrayOf(android.R.attr.background))
            val backgroundColor = a?.getColor(0, 0)
            a?.recycle()
            backgroundColor?.let { view.setBackgroundColor(it) }
            // Nastav štýl pre jednotlivé komponenty programovo
            view.findViewById<TextView>(R.id.nazovHryMain).setTextAppearance(style)
        }

        sharedViewModel.getCurrentSecondaryStyle().observe(viewLifecycleOwner) { style ->
            // Nastav štýl pre jednotlivé komponenty programovo
            view.findViewById<TextView>(R.id.buttonMultiPlayer).setTextAppearance(style)
            view.findViewById<TextView>(R.id.buttonSettings).setTextAppearance(style)
        }

        view.invalidate()
        view.postInvalidate()
        return view
    }
}
