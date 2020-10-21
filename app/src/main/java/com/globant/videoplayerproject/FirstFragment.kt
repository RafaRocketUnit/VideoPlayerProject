package com.globant.videoplayerproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.globant.videoplayerproject.ui.TopGamesViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private lateinit var topGamesViewModel: TopGamesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        topGamesViewModel = ViewModelProvider(this).get(TopGamesViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? =
         inflater.inflate(R.layout.fragment_first, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerObservers()

        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    private fun registerObservers() {
        topGamesViewModel.listGames.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), "List top games!", Toast.LENGTH_LONG).show()
        })

        topGamesViewModel.onError.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), "Error :(", Toast.LENGTH_LONG).show()
        })
    }
}