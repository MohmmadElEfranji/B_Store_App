package com.dev_mohmmadelefranji.b_store_app.ui.activites.sign_up.activate_account

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dev_mohmmadelefranji.b_store_app.R
import com.dev_mohmmadelefranji.b_store_app.databinding.ActivityActivateAccountBinding
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.repository.AuthRepository
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.common_response.ApiResponse
import com.dev_mohmmadelefranji.b_store_app.other.createFactory
import com.dev_mohmmadelefranji.b_store_app.other.hide
import com.dev_mohmmadelefranji.b_store_app.other.show
import com.dev_mohmmadelefranji.b_store_app.ui.activites.sign_in.SignInActivity


class ActivateAccountActivity : AppCompatActivity() {

    private lateinit var viewModel: ActivateAccountViewModel

    /*----------------------------------------*/
    private lateinit var binding: ActivityActivateAccountBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_activate_account)

        /*----------------------------------------*/
        val factory = ActivateAccountViewModel(AuthRepository()).createFactory()
        viewModel = ViewModelProvider(this, factory)[ActivateAccountViewModel::class.java]
        /*----------------------------------------*/

        val activateCode = intent.getStringExtra("activateCode")
        val mobileNumber = intent.getStringExtra("mobileNumber")

        activateCode?.let {
            binding.pinView.setText(it)
        }
        /*----------------------------------------*/

        binding.btnActivate.setOnClickListener {

            viewModel.activateAccount(mobileNumber!!, binding.pinView.text.toString())

        }
        observeActivateAccount()
        /*----------------------------------------*/
    }

    private fun observeActivateAccount() {

        lifecycleScope.launchWhenCreated {
            viewModel.activateAccount.collect {
                when (it) {
                    is ApiResponse.Loading -> {
                        binding.progressBar.show()
                    }
                    is ApiResponse.Success -> {
                        binding.progressBar.hide()

                        it.data?.let { data ->
                            Toast.makeText(
                                this@ActivateAccountActivity,
                                data.message,
                                Toast.LENGTH_SHORT
                            ).show()

                            goToSignInActivity()
                        }

                    }
                    is ApiResponse.Failure -> {
                        binding.progressBar.hide()
                        Toast.makeText(
                            this@ActivateAccountActivity,
                            it.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is ApiResponse.Empty -> Unit
                }
            }
        }
    }

    private fun goToSignInActivity() {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }
}