package com.ktx.imagesearch.ui.gallery

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.ktx.imagesearch.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_gallery.*

@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery) {
    private val TAG = "GalleryFragment"

    private val viewmodel by viewModels<GalleryViewModel>()

    private lateinit var adapter: UnsplashPhotoAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        setUpRecyclerView()
        subscribeObserver()
    }

    private fun setUpRecyclerView() {
        recyclerview.setHasFixedSize(true)
        adapter = UnsplashPhotoAdapter()

        recyclerview.adapter =
            adapter.withLoadStateHeaderAndFooter(header = UnsplashPhotoLoadStateAdapter {
                adapter.retry()
            }, footer = UnsplashPhotoLoadStateAdapter {
                adapter.retry()
            })

        adapter.addLoadStateListener { loadState ->
            progressbar.isVisible = loadState.source.refresh is LoadState.Loading
            recyclerview.isVisible = loadState.source.refresh is LoadState.NotLoading

        }
    }

    private fun subscribeObserver() {
        viewmodel.photos.observe(viewLifecycleOwner) {

            adapter.submitData(
                viewLifecycleOwner.lifecycle, it
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu, menu)

        val searchItem = menu.findItem(R.id.app_bar_search)
        val searchView = searchItem.actionView as androidx.appcompat.widget.SearchView

        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    recyclerview.scrollToPosition(0)
                    viewmodel.searchPhotos(it)
                    searchView.clearFocus()
                }

                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

        })
    }

}