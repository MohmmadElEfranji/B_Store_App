package com.dev_mohmmadelefranji.b_store_app.ui.fragments.product_details

import android.annotation.SuppressLint
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
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.dev_mohmmadelefranji.b_store_app.R
import com.dev_mohmmadelefranji.b_store_app.databinding.FragmentProductDetailsBinding
import com.dev_mohmmadelefranji.b_store_app.model.entity.Cart
import com.dev_mohmmadelefranji.b_store_app.model.local.response.RoomDbResponse
import com.dev_mohmmadelefranji.b_store_app.other.hide
import com.dev_mohmmadelefranji.b_store_app.other.show
import com.dev_mohmmadelefranji.b_store_app.utils.Constants

class ProductDetailsFragment : Fragment() {

    /*----------------------------------------*/
    private val args: ProductDetailsFragmentArgs by navArgs()

    private val sharedPreferences: SharedPreferences by lazy {
        requireContext().getSharedPreferences(
            Constants.USER_PREFERENCES,
            Context.MODE_PRIVATE
        )
    }

    /*----------------------------------------*/
    private lateinit var viewModel: ProductDetailsViewModel

    /*----------------------------------------*/
    private lateinit var binding: FragmentProductDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_product_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fillAllUI()

        viewModel = ViewModelProvider(requireActivity())[ProductDetailsViewModel::class.java]

        observeInsertAndUpdateCart()

        binding.btnAddToCart.setOnClickListener {
            val product = args.product
            viewModel.insertAndUpdateCart(
                Cart(
                    id = product.id,
                    userID = getUserID(),
                    nameEn = product.nameEn,
                    infoEn = product.infoEn,
                    price = product.price.toFloat(),
                    productRate = product.productRate,
                    quantity = product.quantity.toInt(),
                    isFavorite = product.isFavorite,
                    imageUrl = product.imageUrl
                )
            )

        }

    }

    private fun getUserID(): Int {
        return sharedPreferences.getInt(Constants.KEY_USER_ID, 0)
    }

    @SuppressLint("SetTextI18n")
    private fun fillAllUI() {
        val product = args.product
        binding.tvProductName.text = product.nameEn
        binding.tvProductPrice.text = "$  ${product.price}"
        binding.tvProductInfo.text = product.nameEn
        binding.rbNewRatingBar.rating = product.productRate.toFloat()

        Glide.with(requireContext())
            .load(product.imageUrl)
            .circleCrop()
            .into(binding.imageProduct)
    }

    private fun observeInsertAndUpdateCart() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.insertAndUpdateCart.collect {
                when (it) {
                    is RoomDbResponse.Loading -> {
                        binding.progressBar.show()
                    }
                    is RoomDbResponse.Success -> {
                        binding.progressBar.hide()

                        it.data?.let { data ->

                            if (data) {
                                Toast.makeText(
                                    requireContext(),
                                    "Product Add To Cart Successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                    is RoomDbResponse.Failure -> {
                        binding.progressBar.hide()
                        Toast.makeText(
                            requireContext(),
                            it.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is RoomDbResponse.Empty -> Unit
                }
            }
        }
    }
}