package com.globant.videoplayerproject.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.globant.videoplayerproject.R
import com.globant.videoplayerproject.api.SessionManager
import com.globant.videoplayerproject.utils.Utils
import kotlinx.android.synthetic.main.fragment_first.*

class TopGamesFragment : Fragment() {
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
    }

    private fun registerObservers() {
        topGamesViewModel.accessToken.observe(viewLifecycleOwner, Observer {
            val type = it.token_type.replace("b", "B")
            val type2= type.plus("")
            SessionManager(requireContext()).saveAuthToken("$type2 ${it.access_token}")
            topGamesViewModel.getListGames(SessionManager(requireContext()).fetchAuthToken().toString())
        })

        topGamesViewModel.onErrorAccessToken.observe(viewLifecycleOwner, Observer {
            Utils().showToast(requireContext(),"Error AccessToken!")
        })

        topGamesViewModel.listGames.observe(viewLifecycleOwner, Observer {
            Utils().showToast(requireContext(),"List top games!")
            loading.visibility = View.GONE
        })

        topGamesViewModel.onError.observe(viewLifecycleOwner, Observer {
            Utils().showToast(requireContext(),"Error!")
            message_empty_list_layout.visibility = View.VISIBLE
        })
    }
}