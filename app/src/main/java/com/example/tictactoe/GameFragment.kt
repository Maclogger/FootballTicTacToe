package com.example.tictactoe

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar

class GameFragment : Fragment() {
    private var kliknutePolicko: ImageView? = null
    private lateinit var rootview: View
    private val sharedViewModel by activityViewModels<SharedViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_game, container, false)
        val difficulty = arguments?.getInt("difficulty")
        this.spustiHru(rootview, difficulty)

        sharedViewModel.getCurrentMainStyle().observe(viewLifecycleOwner) { style ->
            // Nastav štýl pre hlavný fragment
            val a = context?.obtainStyledAttributes(style, intArrayOf(android.R.attr.background))
            val backgroundColor = a?.getColor(0, 0)
            a?.recycle()
            backgroundColor?.let { rootview.setBackgroundColor(it) }
        }
        return rootview
    }


    private fun spustiHru(view: View, difficulty: Int?) {
        val gridLayout = view.findViewById<GridLayout>(R.id.polickaGrid)
        val hra = Hra(view, difficulty, this)
        nastavListenerNaKoniecButton(gridLayout)
        aktualizujGui(hra, view, gridLayout)
        nastavListeneryNaPolicka(view, hra, gridLayout)
        nastavListenerNaPotvrdenieTextu(view, hra)
    }

    private fun nastavListenerNaKoniecButton(gridLayout: GridLayout) {
        val imageView = gridLayout.getChildAt(0) as ImageView
        imageView.setOnClickListener {
            val snackbar = Snackbar.make(rootview, getString(R.string.naozaj_skoncit_hru), Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.ano)) {
                    // User confirmed action
                    Navigation.findNavController(rootview).navigate(R.id.navigateToTitleFragment)
                }
            val snackbarView = snackbar.view
            val textView = snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
            textView.setTextColor(Color.parseColor("#FFFFFF"))
            textView.textSize = 28f
            val params = snackbarView.layoutParams as FrameLayout.LayoutParams
            params.gravity = Gravity.CENTER
            snackbarView.layoutParams = params

            snackbarView.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
            val typeface = context?.let { ResourcesCompat.getFont(it, R.font.dekko) }
            textView.typeface = typeface
            snackbar.show()
        }
    }

    private fun nastavListenerNaPotvrdenieTextu(view: View, hra: Hra) {
        val editText = view.findViewById<EditText>(R.id.textovePole)
        editText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                var i = 0
                for (r in 0..hra.getPolicka().size - 1) {
                    for (s in 0..hra.getPolicka()[r].size - 1) {
                        val resourceId = view.context.resources.getIdentifier(hra.getPolicka()[r][s], "drawable", view.context.packageName)
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
    }

    private fun nastavListeneryNaPolicka(view: View, hra: Hra, gridLayout: GridLayout) {
        var i = 5
        for (r in 1..hra.getPolicka().size - 1) {
            for (s in 1..hra.getPolicka()[r].size - 1) {
                val imageView = gridLayout.getChildAt(i) as ImageView
                if (imageView.getTag() != "polozene") {
                    imageView.setOnClickListener {
                        kliknutieNaPolicko(hra, view, gridLayout, imageView)
                    }
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
        val editText = nastavPovolenieTextInputu(view, true)
        kliknutePolicko = imageView
        editText.requestFocus()
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

    fun aktualizujGui(hra: Hra, view: View, gridLayout: GridLayout?) {
        zrusKlavesnicu()
        val handler = Handler(Looper.getMainLooper())
        nastavPovolenieTextInputu(view, kliknutePolicko != null)
        aktualizujPolicka(hra, view, gridLayout)
        handler.postDelayed({
            aktualizujHracaNaRade(hra, view)
        }, 2300) // 2000 milisekúnd = 2 sekundy

    }

    private fun nastavPovolenieTextInputu(view: View, b: Boolean): EditText {
        val editText = view.findViewById<EditText>(R.id.textovePole)
        editText.setEnabled(b)
        return editText
    }

    private fun zrusKlavesnicu() {
        val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(rootview?.rootView?.windowToken, 0)
    }

    private fun aktualizujHracaNaRade(hra: Hra, view: View) {
        if (hra.getHracaNaRade() == 0) {
            (view.findViewById(R.id.hracNaRadeObrazok) as ImageView).setImageResource(R.drawable.ocko)
        } else if (hra.getHracaNaRade() == 1) {
            (view.findViewById(R.id.hracNaRadeObrazok) as ImageView).setImageResource(R.drawable.xko)
        }
    }

    private fun aktualizujPolicka(hra: Hra, view: View, gridLayout: GridLayout?) {
        var i = 0
        for (r in 0..hra.getPolicka().size - 1) {
            for (s in 0..hra.getPolicka()[r].size - 1) {
                val resourceId = view.context.resources.getIdentifier(hra.getPolicka()[r][s], "drawable", view.context.packageName)
                if (gridLayout != null) {
                    (gridLayout.getChildAt(i) as ImageView).setImageResource(resourceId)
                }
                i++
            }
        }
    }

    fun nespravnaOdpoved() {

        val snackbar = Snackbar.make(rootview, "Nesprávne, ide ďalší hráč.", Snackbar.LENGTH_SHORT)
        val snackbarView = snackbar.view
        val textView = snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        textView.setTextColor(Color.parseColor("#FFFFFF"))
        textView.textSize = 28f
        snackbarView.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
        val typeface = context?.let { ResourcesCompat.getFont(it, R.font.dekko) }
        textView.typeface = typeface

        val params = snackbarView.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL
        snackbarView.layoutParams = params
        snackbar.show()
    }

    fun spravnaOdpoved() {
        val textView = rootview?.findViewById<TextView>(R.id.nadpisHracNaRade)
        val handler = Handler(Looper.getMainLooper())
        if (textView != null) {
            textView.text = getString(R.string.vyborne)
        }
        handler.postDelayed({
            if (textView != null) {
                textView.text = getString(R.string.hrac_na_rade)
            }
        }, 2000) // 2000 milisekúnd = 2 sekundy
    }

    fun koniecHry(vysledokKola: Int) {
        val bundle = Bundle()
        bundle.putInt("vysledok", vysledokKola)
        Navigation.findNavController(rootview).navigate(R.id.navigateToKoniecHry, bundle)
    }

    fun setKliknutePolicko(policko: ImageView?) {
        kliknutePolicko = policko
    }
}