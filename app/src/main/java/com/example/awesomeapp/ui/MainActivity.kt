package com.example.awesomeapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.awesomeapp.adapter.MainAdapter
import com.example.awesomeapp.adapter.MainGridAdapter
import com.example.awesomeapp.databinding.ActivityMainBinding
import com.example.awesomeapp.model.Photo
import com.example.awesomeapp.viewmodel.MainViewModel

@SuppressLint("NotifyDataSetChanged")
class MainActivity : AppCompatActivity(), MainAdapter.OnItemClickCallback,
    MainGridAdapter.OnItemClickCallback {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var mainAdapter: MainAdapter
    private lateinit var mainGridAdapter: MainGridAdapter

    private var data: MutableList<Photo> = ArrayList()
    private var page = 1
    private var nextPage = true
    private var isEmpty = true
    private var loading = false

    private var isGrid = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        getData()
        setAdapter()
        setClickMode()
    }

    private fun getData() {
        getLoading()
        getPhotos()
    }

    private fun setClickMode() {
        binding.imgList.setOnClickListener {
            isGrid = false
            binding.rvImage.adapter = mainAdapter
            binding.rvImage.layoutManager = LinearLayoutManager(this)
            mainAdapter.setData(data)
        }

        binding.imgGrid.setOnClickListener {
            isGrid = true
            binding.rvImage.adapter = mainGridAdapter
            binding.rvImage.layoutManager = GridLayoutManager(this, 2)
            mainGridAdapter.setData(data)
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private fun getLoading() {
        viewModel.getLoading().observe(this, {
            loading = it
            if (loading) binding.progressCircular.visibility = View.VISIBLE
            else binding.progressCircular.visibility = View.GONE
        })

        viewModel.getStatus().observe(this, {
            if (it >= 400) binding.lineNodata.visibility = View.GONE
        })

        viewModel.getMessage().observe(this, {
            if (!it.isNullOrEmpty()) binding.lineNodata.visibility = View.VISIBLE
            else binding.lineNodata.visibility = View.GONE
        })

        binding.rvImage.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val countItem = linearLayoutManager.itemCount
                if (countItem.minus(1) == linearLayoutManager.findLastVisibleItemPosition()) {
                    if (!loading && nextPage) {
                        page++
                        getData()
                    }
                }
            }
        })
    }

    private fun getPhotos() {
        viewModel.getImage(page).observe(this, {
            nextPage = !it.isNullOrEmpty()
            if (page == 0) {
                data = it
                isEmpty = it.isEmpty()
                binding.lineNodata.visibility = View.VISIBLE
                Toast.makeText(this, "$isEmpty", Toast.LENGTH_SHORT).show()

            } else {
                binding.lineNodata.visibility = View.GONE
                binding.rvImage.visibility = View.VISIBLE
                data.addAll(it)
            }

            if (isGrid) mainGridAdapter.setData(data)
            else mainAdapter.setData(data)
        })
    }

    private fun setAdapter() {
        mainAdapter = MainAdapter(ArrayList(), this)
        binding.rvImage.layoutManager = LinearLayoutManager(this)
        binding.rvImage.adapter = mainAdapter

        mainGridAdapter = MainGridAdapter(ArrayList(), this)
        binding.rvImage.layoutManager = GridLayoutManager(this, 2)
        binding.rvImage.adapter = mainGridAdapter
    }

    override fun onItemClick(data: Photo) {
        startActivity(
            Intent(this, DetailActivity::class.java)
                .putExtra(DetailActivity.NAME, data.photographer)
                .putExtra(DetailActivity.PHOTO, data.src.original)
        )
    }

    override fun onItemClicked(data: Photo) {
        startActivity(
            Intent(this, DetailActivity::class.java)
                .putExtra(DetailActivity.NAME, data.photographer)
                .putExtra(DetailActivity.PHOTO, data.src.original)
        )
    }
}