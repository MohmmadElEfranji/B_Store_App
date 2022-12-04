package com.dev_mohmmadelefranji.b_store_app.ui.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev_mohmmadelefranji.b_store_app.R
import com.dev_mohmmadelefranji.b_store_app.databinding.FragmentProfileBinding
import com.dev_mohmmadelefranji.b_store_app.model.entity.ProfileItem
import com.dev_mohmmadelefranji.b_store_app.ui.adapters.ProfileItemAdapter

class ProfileFragment : Fragment(), ProfileItemAdapter.OnItemClickListener {


    private val profileItemAdapter: ProfileItemAdapter by lazy {
        ProfileItemAdapter(this)
    }
    private lateinit var binding: FragmentProfileBinding
    private val profileItem: MutableList<ProfileItem> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        fillDataOfRvList()

    }

    private fun setRecyclerView() {

        binding.rvProfileItem.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = profileItemAdapter
            isNestedScrollingEnabled = false
        }
    }

    //region fillDataOfRvList
    private fun fillDataOfRvList() {
        profileItem.add(
            ProfileItem(
                1,
                getString(R.string.notifications),
                R.drawable.ic_notifications_24
            )
        )
        profileItem.add(
            ProfileItem(
                2,
                getString(R.string.my_orders),
                R.drawable.ic_receipt_24
            )
        )
        profileItem.add(
            ProfileItem(
                3,
                getString(R.string.address),
                R.drawable.ic_home_24
            )
        )
        profileItem.add(
            ProfileItem(
                4,
                getString(R.string.payment),
                R.drawable.ic_payment_24
            )
        )
        profileItem.add(
            ProfileItem(
                5,
                getString(R.string.favourites),
                R.drawable.ic_favorite_blue_24
            )
        )
        profileItem.add(
            ProfileItem(
                6,
                getString(R.string.settings),
                R.drawable.ic_settings_24
            )
        )

        profileItemAdapter.submitList(profileItem)
    }

    //endregion

    override fun onClickListener(position: Int) {

        when (profileItemAdapter.currentList[position].itemId) {

            4 -> {
                val action = ProfileFragmentDirections.actionProfileFragmentToPaymentFragment()
                findNavController().navigate(action)
            }


            5 -> {

                val action =
                    ProfileFragmentDirections.actionProfileFragmentToFavoriteProductsFragment()
                findNavController().navigate(action)
            }
        }


    }
}