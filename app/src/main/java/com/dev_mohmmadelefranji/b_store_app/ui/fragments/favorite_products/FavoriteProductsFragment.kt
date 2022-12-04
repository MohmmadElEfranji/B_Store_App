package com.dev_mohmmadelefranji.b_store_app.ui.fragments.favorite_products

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev_mohmmadelefranji.b_store_app.R
import com.dev_mohmmadelefranji.b_store_app.databinding.FragmentFavoriteProductsBinding
import com.dev_mohmmadelefranji.b_store_app.model.entity.Product
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.repository.ProductRepository
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.common_response.ApiResponse
import com.dev_mohmmadelefranji.b_store_app.other.createFactory
import com.dev_mohmmadelefranji.b_store_app.other.hide
import com.dev_mohmmadelefranji.b_store_app.other.show
import com.dev_mohmmadelefranji.b_store_app.ui.adapters.FavoriteProductItemAdapter
import com.dev_mohmmadelefranji.b_store_app.utils.Constants


class FavoriteProductsFragment : Fragment(), FavoriteProductItemAdapter.OnLikeProductListener {
    /*----------------------------------------*/
    private val favoriteProductItemAdapter by lazy { FavoriteProductItemAdapter(this) }

    /*----------------------------------------*/
    private val sharedPreferences: SharedPreferences by lazy {
        requireContext().getSharedPreferences(
            Constants.USER_PREFERENCES,
            Context.MODE_PRIVATE
        )
    }

    /*----------------------------------------*/
    private var favoriteProductsList: MutableList<Product> = arrayListOf()

    /*----------------------------------------*/
    private lateinit var viewModel: FavoriteProductsViewModel

    /*----------------------------------------*/
    private lateinit var binding: FragmentFavoriteProductsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_favorite_products, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*----------------------------------------*/

        //region createFactory
        val factory = FavoriteProductsViewModel(ProductRepository()).createFactory()
        viewModel = ViewModelProvider(this, factory)[FavoriteProductsViewModel::class.java]
        //endregion
        /*----------------------------------------*/

        setRecyclerView()


        viewModel.getFavoriteProducts(getUserToken())
        observeGetFavoriteProducts()

    }

    private fun setRecyclerView() {
        binding.rvProducts.apply {
            layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
            adapter = favoriteProductItemAdapter
        }
    }

    private fun observeGetFavoriteProducts() {

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.getFavoriteProducts.collect { it ->
                when (it) {
                    is ApiResponse.Loading -> {
                        binding.progressBar.show()

                    }
                    is ApiResponse.Success -> {
                        binding.progressBar.hide()

                        it.data?.let { data ->

                            if (data.listOfProduct.isNotEmpty()) {

                                favoriteProductsList = data.listOfProduct
                                favoriteProductItemAdapter.submitList(favoriteProductsList)
                            }

                        }
                    }
                    is ApiResponse.Failure -> {
                        binding.progressBar.hide()
                        Toast.makeText(
                            requireContext(),
                            it.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is ApiResponse.Empty -> Unit
                }
            }
        }
    }

    private fun getUserToken(): String {
        return sharedPreferences.getString(Constants.KEY_TOKEN, null)!!
    }


    override fun likedListener(productID: Int, position: Int) {
        viewModel.addAndRemoveFavoriteProducts(getUserToken(), productID)

        if (favoriteProductItemAdapter.currentList[position].isFavorite) {
            removeItemAtIndex(position)
        }
    }


    private fun removeItemAtIndex(index: Int) {
        favoriteProductsList.removeAt(index)
        favoriteProductItemAdapter.notifyItemRemoved(index)
        favoriteProductItemAdapter.notifyItemRangeChanged(
            index,
            favoriteProductItemAdapter.itemCount
        )
    }
}