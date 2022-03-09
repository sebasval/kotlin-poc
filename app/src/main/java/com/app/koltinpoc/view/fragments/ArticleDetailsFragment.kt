package com.app.koltinpoc.view.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.app.koltinpoc.R
import com.app.koltinpoc.databinding.FragmentArticleDetailsBinding
import com.app.koltinpoc.viewModel.OfflineViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleDetailsFragment : Fragment(R.layout.fragment_article_details) {

    val viewModel: OfflineViewModel by viewModels()

    private lateinit var binding: FragmentArticleDetailsBinding
    val args: ArticleDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.onBackPressedDispatcher?.addCallback {}

        val article = args.articleData
        binding = FragmentArticleDetailsBinding.bind(view)
        binding.webview.apply {
            webChromeClient = WebChromeClient()
            article?.let { data ->
                loadUrl(data.data.thumbnail)
            }
        }

        viewModel.updateReadStatus(article!!)

        binding.fab.setOnClickListener {
            viewModel.deleteElement(article)
        }

        binding.backToList.setOnClickListener {
            val bundle = bundleOf("delete_state" to true)
            findNavController().navigate(
                R.id.action_detailFragment_to_onlineFragment,
                bundle
            )
        }

        viewModel.deleteState.observe(viewLifecycleOwner, {
            if (it) {
                val bundle = bundleOf("delete_state" to true)
                findNavController().navigate(
                    R.id.action_detailFragment_to_onlineFragment,
                    bundle
                )
                Snackbar.make(binding.root, "Article Deleted ", Snackbar.LENGTH_LONG).show()
            } else {
                Snackbar.make(binding.root, "Could not delete element ", Snackbar.LENGTH_LONG)
                    .show()
            }
        })
    }
}