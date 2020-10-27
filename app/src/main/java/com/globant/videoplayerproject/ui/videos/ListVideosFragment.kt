package com.globant.videoplayerproject.ui.videos

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
import com.globant.videoplayerproject.ui.topStream.TopStreamAdapter
import com.globant.videoplayerproject.ui.topStream.TopStreamFragmentArgs
import com.globant.videoplayerproject.ui.topStream.TopStreamViewModel
import com.globant.videoplayerproject.utils.Utils
import kotlinx.android.synthetic.main.fragment_top_stream.*

class ListVideosFragment: Fragment() {
    private lateinit var adapter: ListVideoAdapter
    private var recyclerView: RecyclerView? = null
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var actionBarCustom: ActionBar

    private lateinit var listVideoViewModel: ListVideoViewModel
    private val args: ListVideosFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        listVideoViewModel = ViewModelProvider(this).get(ListVideoViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_list_videos, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerView()
        registerListeners()
        registerObservers()

        listVideoViewModel.getListVideos(
            SessionManager(requireContext()).fetchAuthToken().toString(), args.userId
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
            activity?.title = getString(R.string.videoScreenTitle)
        }
    }

    private fun registerListeners() {
        adapter.onClick = {
            Utils().showToast(requireContext(), "Stream touched!")
        }
    }

    private fun registerObservers() {
        listVideoViewModel.listVideos.observe(viewLifecycleOwner, Observer {
            adapter.addStreams(it)
            loading.visibility = View.GONE
        })

        listVideoViewModel.onError.observe(viewLifecycleOwner, Observer {
            Utils().showToast(requireContext(), "Error!")
            recyclerView?.visibility = View.GONE
        })
    }

    private fun initializeRecyclerView() {
        adapter = ListVideoAdapter()
        recyclerView = view?.findViewById(R.id.stream_recyclerView)
        linearLayoutManager = LinearLayoutManager(activity)
        recyclerView?.adapter = adapter
    }
}