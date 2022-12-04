package com.dev_mohmmadelefranji.b_store_app.ui.activites.sign_in.forget_password

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dev_mohmmadelefranji.b_store_app.R
import com.dev_mohmmadelefranji.b_store_app.databinding.ActivityForgetPasswordBinding
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.repository.AuthRepository
import com.dev_mohmmadelefranji.b_store_app.model.remote.api.response.common_response.ApiResponse
import com.dev_mohmmadelefranji.b_store_app.other.createFactory
import com.dev_mohmmadelefranji.b_store_app.other.hide
import com.dev_mohmmadelefranji.b_store_app.other.show
import com.dev_mohmmadelefranji.b_store_app.ui.activites.sign_in.SignInActivity

class ForgetPasswordActivity : AppCompatActivity() {

    private lateinit var viewModel: ForgetPasswordViewModel

    /*----------------------------------------*/
    private var mobileNumber: String? = null
    private lateinit var binding: ActivityForgetPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forget_password)
        /*----------------------------------------*/
        val factory = ForgetPasswordViewModel(AuthRepository()).createFactory()
        viewModel = ViewModelProvider(this, factory)[ForgetPasswordViewModel::class.java]
        /*----------------------------------------*/
        mobileNumber = intent.getStringExtra("mobileNumber")

        mobileNumber?.let {
            viewModel.sendVerificationCode(mobileNumber = it)
        }
        observeSendVerificationCode()

        binding.btnResetPassword.setOnClickListener {
            checkFieldsThenResetPassword()
        }
        observeResetPasswordResponse()
    }

    private fun observeSendVerificationCode() {
        lifecycleScope.launchWhenCreated {
            viewModel.getVerificationCode.collect {
                when (it) {
                    is ApiResponse.Loading -> {
                        binding.progressBar.show()
                    }
                    is ApiResponse.Success -> {
                        binding.progressBar.hide()

                        it.data?.let { data ->

                            Handler(Looper.getMainLooper()).postDelayed(
                                {
                                    binding.pinView.setText(data.code.toString())
                                },
                                3000
                            )
                        }
                    }
                    is ApiResponse.Failure -> {
                        binding.progressBar.hide()
                        Toast.makeText(
                            this@ForgetPasswordActivity,
                            it.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is ApiResponse.Empty -> Unit
                }
            }
        }
    }

    //region Reset password
    private fun checkFieldsThenResetPassword() {
        val verificationCode = binding.pinView.text.toString().trim()
        val password = binding.edPassword.text.toString().trim()
        val confirmPassword = binding.edConfirmPassword.text.toString().trim()

        when {
            password.isEmpty() -> {
                binding.edPassword.error = "Fill password"
            }
            confirmPassword.isEmpty() -> {
                binding.edConfirmPassword.error = "Fill confirm password"
            }
            else -> {
                mobileNumber?.let {
                    viewModel.resetPassword(
                        it,
                        verificationCode,
                        password,
                        confirmPassword
                    )
                }
            }
        }
    }

    private fun observeResetPasswordResponse() {
        lifecycleScope.launchWhenCreated {
            viewModel.resetPassword.collect {
                when (it) {
                    is ApiResponse.Loading -> {
                        binding.progressBar.show()
                    }
                    is ApiResponse.Success -> {
                        binding.progressBar.hide()

                        it.data?.let { data ->
                            Toast.makeText(
                                this@ForgetPasswordActivity,
                                data.message,
                                Toast.LENGTH_SHORT
                            ).show()

                            goToSignInActivity()
                        }
                    }
                    is ApiResponse.Failure -> {
                        binding.progressBar.hide()
                        Toast.makeText(
                            this@ForgetPasswordActivity,
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
    //endregion
}