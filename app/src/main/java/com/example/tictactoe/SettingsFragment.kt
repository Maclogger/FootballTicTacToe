package com.example.tictactoe

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import java.util.Locale

/*
    Slúži na spracovanie obrazovky nastavenia. Obsahuje listenery na tlačidlá.
    Nastavuje rôzne štýly, jazyk. Presmeruváva do iných aplikácií.
 */
class SettingsFragment : Fragment() {
    private val sharedViewModel by activityViewModels<SharedViewModel>()

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
        view.findViewById<View>(R.id.greenStyleButton).setOnClickListener() {
            nastavStyle(view, 0)
        }
        view.findViewById<View>(R.id.purpleStyleButton).setOnClickListener() {
            nastavStyle(view, 1)
        }
        view.findViewById<View>(R.id.darkStyleButton).setOnClickListener() {
            nastavStyle(view, 2)
        }
        view.findViewById<View>(R.id.mailButton).setOnClickListener() {
            presmerujNaMail()
        }
        view.findViewById<View>(R.id.discordButton).setOnClickListener() {
            presmerujNaWeb("https://discord.com/")
        }
        view.findViewById<View>(R.id.githubButton).setOnClickListener() {
            presmerujNaWeb("https://github.com/Maclogger/FootballTicTacToe")
        }
        view.findViewById<RelativeLayout>(R.id.faq).setOnClickListener() {
            presmerujNaWeb("https://github.com/Maclogger/FootballTicTacToe")
        }
        view.findViewById<RelativeLayout>(R.id.Terms_of_Conditions).setOnClickListener() {
            presmerujNaWeb("https://github.com/Maclogger/FootballTicTacToe")
        }


        sharedViewModel.getCurrentMainStyle().observe(viewLifecycleOwner) { style ->
            // Nastav štýl pre hlavný fragment
            val a = context?.obtainStyledAttributes(style, intArrayOf(android.R.attr.background))
            val backgroundColor = a?.getColor(0, 0)
            a?.recycle()
            backgroundColor?.let { view.setBackgroundColor(it) }
            // Nastav štýl pre jednotlivé komponenty programovo
        }

        sharedViewModel.getCurrentSecondaryStyle().observe(viewLifecycleOwner) { style ->
            // Nastav štýl pre jednotlivé komponenty programovo
            view.findViewById<TextView>(R.id.stylText).setTextAppearance(style)
            view.findViewById<TextView>(R.id.stylJazyk).setTextAppearance(style)
            view.findViewById<TextView>(R.id.stylKontakt).setTextAppearance(style)
            view.findViewById<TextView>(R.id.casteOtazkyText).setTextAppearance(style)
            view.findViewById<TextView>(R.id.zmluvnePodmienkyText).setTextAppearance(style)
        }


        return view
    }

    private fun presmerujNaWeb(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    private fun presmerujNaMail() {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:") // iba emailové aplikácie by mali spracovať tento intent
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("rucki@stud.uniza.sk"))
        intent.putExtra(Intent.EXTRA_SUBJECT, "Správa pre vývojara aplikácie")
        if (activity?.packageManager?.let { intent.resolveActivity(it) } != null) {
            startActivity(intent)
        }
    }

    private fun nastavStyle(view: View, idStylu: Int) {

        if (idStylu == 0) {
            sharedViewModel.setCurrentMainStyle(R.style.mainGreenStyle)
            sharedViewModel.setCurrentSecondaryStyle(R.style.secondaryGreenStyle)
        } else if (idStylu == 1) {
            sharedViewModel.setCurrentMainStyle(R.style.mainPurpleStyle)
            sharedViewModel.setCurrentSecondaryStyle(R.style.secondaryPurpleStyle)
        } else if (idStylu == 2) {
            sharedViewModel.setCurrentMainStyle(R.style.mainDarkStyle)
            sharedViewModel.setCurrentSecondaryStyle(R.style.secondaryDarkStyle)
        }
        Navigation.findNavController(view).navigate(R.id.navigateToTitleFragment)
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