package com.example.tictactoe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation

class VyberObtiaznostiFragment : Fragment() {
    private val sharedViewModel by activityViewModels<SharedViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_vyber_obtiaznosti, container, false)
        val bundle = Bundle()
        view.findViewById<Button>(R.id.buttonEasy).setOnClickListener {
            bundle.putInt("difficulty", 0) // 0 pre ľahkú obtiažnosť
            Navigation.findNavController(view).navigate(R.id.navigateToGameFragment, bundle)
        }
        view.findViewById<Button>(R.id.buttonMedium).setOnClickListener {
            bundle.putInt("difficulty", 1) // 1 pre strednú obtiažnosť
            androidx.navigation.Navigation.findNavController(view).navigate(com.example.tictactoe.R.id.navigateToGameFragment, bundle)
        }
        sharedViewModel.currentMainStyle.observe(viewLifecycleOwner) { style ->
            // Nastav štýl pre hlavný fragment
            val a = context?.obtainStyledAttributes(style, intArrayOf(android.R.attr.background))
            val backgroundColor = a?.getColor(0, 0)
            a?.recycle()
            backgroundColor?.let { view.setBackgroundColor(it) }
        }

        sharedViewModel.currentSecondaryStyle.observe(viewLifecycleOwner) { style ->
            // Nastav štýl pre jednotlivé komponenty programovo
            view.findViewById<TextView>(R.id.buttonEasy).setTextAppearance(style)
            view.findViewById<TextView>(R.id.buttonMedium).setTextAppearance(style)
        }
        return view
    }

}