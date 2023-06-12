package com.example.tictactoe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView

class GameFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_game, container, false)
        val difficulty = arguments?.getInt("difficulty")
        this.spustiHru(view, difficulty)
        //todo koniec hry navigate ->
        return view
    }

    private fun spustiHru(view: View, difficulty: Int?) {
        val gridLayout = view.findViewById<GridLayout>(R.id.polickaGrid)
        val hra = Hra(view, difficulty)
        nastavPolickaPodlaHry(hra, view, gridLayout)
    }

    private fun nastavPolickaPodlaHry(hra: Hra, view: View, gridLayout: GridLayout?) {
        var i = 0
        for (r in 0..hra.policka.size - 1) {
            for (s in 0..hra.policka[r].size - 1) {
                val resourceId = view.context.resources.getIdentifier(hra.policka[r][s], "drawable", view.context.packageName)
                if (gridLayout != null) {
                    (gridLayout.getChildAt(i) as ImageView).setImageResource(resourceId)
                }
                i++
            }
        }
    }


}