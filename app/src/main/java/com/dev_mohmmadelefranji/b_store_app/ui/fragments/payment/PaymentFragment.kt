package com.dev_mohmmadelefranji.b_store_app.ui.fragments.payment

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev_mohmmadelefranji.b_store_app.R
import com.dev_mohmmadelefranji.b_store_app.databinding.FragmentPaymentBinding
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.repository.PaymentCardRepository
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.common_response.ApiResponse
import com.dev_mohmmadelefranji.b_store_app.other.createFactory
import com.dev_mohmmadelefranji.b_store_app.other.hide
import com.dev_mohmmadelefranji.b_store_app.other.show
import com.dev_mohmmadelefranji.b_store_app.ui.adapters.PaymentCardItemAdapter
import com.dev_mohmmadelefranji.b_store_app.utils.Constants

class PaymentFragment : Fragment() {

    private lateinit var viewModel: PaymentViewModel

    /*----------------------------------------*/
    private val paymentCardItemAdapter: PaymentCardItemAdapter by lazy {
        PaymentCardItemAdapter()
    }

    /*----------------------------------------*/
    private val sharedPreferences: SharedPreferences by lazy {
        requireContext().getSharedPreferences(
            Constants.USER_PREFERENCES,
            Context.MODE_PRIVATE
        )
    }

    /*----------------------------------------*/
    private lateinit var binding: FragmentPaymentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_payment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //region createFactory
        val factory = PaymentViewModel(PaymentCardRepository()).createFactory()
        viewModel = ViewModelProvider(this, factory)[PaymentViewModel::class.java]
        //endregion

        setRecyclerView()

        viewModel.getAllPaymentCards(getUserToken())

        observeGetAllPaymentCards()
    }

    private fun setRecyclerView() {

        binding.rvPaymentCards.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = paymentCardItemAdapter
            isNestedScrollingEnabled = false
        }
    }

    private fun observeGetAllPaymentCards() {

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.getAllPaymentCards.collect { it ->
                when (it) {
                    is ApiResponse.Loading -> {
                        binding.progressBar.show()

                    }
                    is ApiResponse.Success -> {
                        binding.progressBar.hide()

                        it.data?.let { data ->

                            if (data.paymentCardList.isNotEmpty()) {

                                paymentCardItemAdapter.submitList(data.paymentCardList)
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

}