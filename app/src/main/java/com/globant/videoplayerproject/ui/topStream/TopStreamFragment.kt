package com.globant.videoplayerproject.ui.topStream

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.globant.videoplayerproject.MainActivity
import com.globant.videoplayerproject.R
import com.globant.videoplayerproject.api.SessionManager
import com.globant.videoplayerproject.utils.Utils
import kotlinx.android.synthetic.main.fragment_top_stream.*

class TopStreamFragment : Fragment() {
    private lateinit var adapter: TopStreamAdapter
    private var recyclerView: RecyclerView? = null
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var actionBarCustom: ActionBar

    private lateinit var topStreamViewModel: TopStreamViewModel
    private val args: TopStreamFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        topStreamViewModel = ViewModelProvider(this).get(TopStreamViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_top_stream, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerView()
        registerListeners()
        registerObservers()

        topStreamViewModel.getListTopStream(
            SessionManager(requireContext()).fetchAuthToken().toString(), args.gameId
        )
    }

    override fun onResume() {
        super.onResume()

        setupToolbar()
    }

    private fun setupToolbar() {
        with(activity){
            if (this is MainActivity) actionBarCustom = supportActionBar as ActionBar
            actionBarCustom.setDisplayHomeAsUpEnabled(true)
            activity?.title = getString(R.string.topStreamTitle)
        }
    }

    private fun registerListeners() {
        adapter.onClick = {
            Utils().showToast(requireContext(), "Stream touched!")
        }
    }

    private fun registerObservers() {
        topStreamViewModel.listTopStream.observe(viewLifecycleOwner, Observer {
            adapter.addStreams(it)
            loading.visibility = View.GONE
        })

        topStreamViewModel.onError.observe(viewLifecycleOwner, Observer {
            Utils().showToast(requireContext(), "Error!")
            recyclerView?.visibility = View.GONE
        })
    }

    private fun initializeRecyclerView() {
        adapter = TopStreamAdapter()
        recyclerView = view?.findViewById(R.id.stream_recyclerView)
        linearLayoutManager = LinearLayoutManager(activity)
        recyclerView?.adapter = adapter
    }
}