package com.globant.videoplayerproject.ui.topGames

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.globant.videoplayerproject.MainActivity
import com.globant.videoplayerproject.R
import com.globant.videoplayerproject.api.SessionManager
import com.globant.videoplayerproject.utils.Utils
import kotlinx.android.synthetic.main.fragment_top_games.*

class TopGamesFragment : Fragment() {
    private lateinit var linearLayoutManager: GridLayoutManager
    private lateinit var adapter: TopGamesAdapter
    private var recyclerView: RecyclerView? = null

    private lateinit var topGamesViewModel: TopGamesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        topGamesViewModel = ViewModelProvider(this).get(TopGamesViewModel::class.java)

        activity?.title = resources.getString(R.string.app_name)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_top_games, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerView()
        registerObservers()
        registerListeners()
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }

    private fun setupToolbar(){
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        activity?.title = getString(R.string.topGameTitle)
    }

    private fun registerListeners() {
        adapter.onClick = {
            findNavController().navigate(TopGamesFragmentDirections.navigateToListStreamFragment(it.id))
        }
    }

    private fun registerObservers() {
        topGamesViewModel.accessToken.observe(viewLifecycleOwner, Observer {
            SessionManager(requireContext()).saveAuthToken("${Utils().adaptTypeToken(it.token_type)} ${it.access_token}")
            topGamesViewModel.getListGames(
                SessionManager(requireContext()).fetchAuthToken().toString()
            )
        })

        topGamesViewModel.onErrorAccessToken.observe(viewLifecycleOwner, Observer {
            Utils().showToast(requireContext(), "Error AccessToken!")
        })

        topGamesViewModel.listGames.observe(viewLifecycleOwner, Observer {
            adapter.addGames(it)
            loading.visibility = View.GONE
        })

        topGamesViewModel.onError.observe(viewLifecycleOwner, Observer {
            Utils().showToast(requireContext(), "Error!")
            message_empty_list_layout.visibility = View.VISIBLE
            recyclerView?.visibility = View.GONE
        })
    }

    private fun initializeRecyclerView() {
        adapter = TopGamesAdapter()
        recyclerView = view?.findViewById(R.id.recyclerView)
        linearLayoutManager = GridLayoutManager(activity, 2)
        recyclerView?.layoutManager = linearLayoutManager
        recyclerView?.adapter = adapter
    }
}