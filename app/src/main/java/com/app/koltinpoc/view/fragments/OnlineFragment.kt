package com.app.koltinpoc.view.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.koltinpoc.R
import com.app.koltinpoc.databinding.FragmentOnlineBinding
import com.app.koltinpoc.utils.DataHandler
import com.app.koltinpoc.utils.LogData
import com.app.koltinpoc.view.adapter.HorizontalAdapter
import com.app.koltinpoc.view.adapter.VerticalAdapter
import com.app.koltinpoc.viewModel.OnlineViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OnlineFragment : Fragment(R.layout.fragment_online) {

    private lateinit var binding: FragmentOnlineBinding

    @Inject
    lateinit var horizontalAdapter: HorizontalAdapter

    @Inject
    lateinit var verticalAdapter: VerticalAdapter

    val viewModel: OnlineViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOnlineBinding.bind(view)
        val toolbar = binding.toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        init()

        val state = arguments?.getBoolean("delete_state", false)

        viewModel.animeTop.observe(viewLifecycleOwner) { dataHandler ->
            when (dataHandler) {
                is DataHandler.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    horizontalAdapter.differ.submitList(dataHandler.data)
                    binding.swipeRefresh.isRefreshing = false
                }

                is DataHandler.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    LogData("onViewCreated: ERROR " + dataHandler.message)
                }

                is DataHandler.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    LogData("onViewCreated: LOADING..")

                }
            }

        }

        viewModel.animeSeasonsNowTop.observe(viewLifecycleOwner) { dataHandler ->
            when (dataHandler) {
                is DataHandler.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    verticalAdapter.differ.submitList(dataHandler.data)
                    binding.swipeRefresh.isRefreshing = false
                }

                is DataHandler.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    LogData("onViewCreated: ERROR " + dataHandler.message)
                }

                is DataHandler.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    LogData("onViewCreated: LOADING..")

                }
            }

        }


        if (state!!.not()) {
            viewModel.getAnimeTop()
            viewModel.getAnimeSeasonsNow()
        } else {
            viewModel.getAllLocalAnimeInfo()
            viewModel.getAllLocalAnimeSeasonNowInfo()
        }
    }

    private fun init() {

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getAnimeTop()
        }

        verticalAdapter.onAnimeInfoClicked {
            val bundle = Bundle().apply {
                putParcelable("article_data", it)
            }
            findNavController().navigate(
                R.id.action_onlineFragment_to_articleDetailsFragment,
                bundle
            )
        }

        binding.recyclerView.apply {
            adapter = horizontalAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }

        binding.recyclerviewAnimeNewestSeasons.apply {
            adapter = verticalAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        }

        val searchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Código para manejar la consulta de búsqueda
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Código para manejar cambios en el texto de búsqueda
                return true
            }
        })

    }
}