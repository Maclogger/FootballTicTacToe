package com.example.tictactoe

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.GridLayout
import android.widget.ImageView

class GameFragment : Fragment() {

    private var kliknutePolicko: ImageView? = null
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
        val hra = Hra(view, difficulty, this)
        aktualizujGui(hra, view, gridLayout)
        nastavListeneryNaPolicka(view, hra, gridLayout)
        nastavListenerNaPotvrdenieTextu(view, hra)
    }

    private fun nastavListenerNaPotvrdenieTextu(view: View, hra: Hra) {
        val editText = view.findViewById<EditText>(R.id.textovePole)
        editText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                var i = 0
                for (r in 0..hra.policka.size - 1) {
                    for (s in 0..hra.policka[r].size - 1) {
                        val resourceId = view.context.resources.getIdentifier(hra.policka[r][s], "drawable", view.context.packageName)
                        val gridLayout = view.findViewById<GridLayout>(R.id.polickaGrid)
                        if (gridLayout != null) {
                            if (gridLayout.getChildAt(i) == kliknutePolicko) {
                                hra.napisaloSaSlovo(editText.text.toString(), kliknutePolicko, r, s, gridLayout)
                                return@setOnEditorActionListener true
                            }
                        }
                        i++
                    }
                }
            }
            false
        }

        /*editText.setOnClickListener { v ->
            val editText = v as EditText
            val drawable = editText.compoundDrawables[2] // 2 is the index for the drawableEnd
            if (drawable != null && event.rawX >= (editText.right - drawable.bounds.width())) {
                // Handle click on magnifying glass icon here
            }
        }*/

    }

    private fun nastavListeneryNaPolicka(view: View, hra: Hra, gridLayout: GridLayout) {
        var i = 5
        for (r in 1..hra.policka.size - 1) {
            for (s in 1..hra.policka[r].size - 1) {
                val imageView = gridLayout.getChildAt(i) as ImageView
                imageView.setOnClickListener {
                    kliknutieNaPolicko(hra, view, gridLayout, imageView)
                }
                i++
                if (i % 4 == 0) {
                    i++
                }
            }
        }
    }

    private fun kliknutieNaPolicko(
        hra: Hra,
        view: View,
        gridLayout: GridLayout,
        imageView: ImageView
    ) {
        aktualizujGui(hra, view, gridLayout)
        imageView.setImageResource(R.drawable.zvyraznene)
        //aktivovať písanie na klávesnicu do textového poľa: "android:id="@+id/textovePole""
        val editText = view.findViewById<EditText>(R.id.textovePole)
        editText.setEnabled(true)
        kliknutePolicko = imageView
        editText.requestFocus()
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun aktualizujGui(hra: Hra, view: View, gridLayout: GridLayout?) {
        aktualizujPolicka(hra, view, gridLayout)
        aktualizujHracaNaRade(hra, view)
        view.findViewById<EditText>(R.id.textovePole).let { it.isEnabled = kliknutePolicko != null }
    }

    private fun aktualizujHracaNaRade(hra: Hra, view: View) {
        if (hra.hracNaRade == 0) {
            (view.findViewById(R.id.hracNaRadeObrazok) as ImageView).setImageResource(R.drawable.ocko)
        } else if (hra.hracNaRade == 1) {
            (view.findViewById(R.id.hracNaRadeObrazok) as ImageView).setImageResource(R.drawable.xko)
        }
    }

    private fun aktualizujPolicka(hra: Hra, view: View, gridLayout: GridLayout?) {
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