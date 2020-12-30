package com.android.timesheetchecker.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.timesheetchecker.R
import com.android.timesheetchecker.TimesheetCheckerViewModelFactory
import com.android.timesheetchecker.databinding.FragmentLoginBinding
import com.android.timesheetchecker.utils.EventObserver
import com.android.timesheetchecker.utils.snackBar
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class LoginFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: TimesheetCheckerViewModelFactory
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels { viewModelFactory }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (viewModel.isLoggedIn()) {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            return null
        } else {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
            return binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loginSuccessEvent.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        })

        viewModel.errorEvent.observe(viewLifecycleOwner, EventObserver {
            snackBar(it.message)
        })

        viewModel.loading.observe(viewLifecycleOwner, EventObserver {
            if (it) {
                binding.loginLoader.visibility = View.VISIBLE
                binding.loginBtn.visibility = View.INVISIBLE
            } else {
                binding.loginLoader.visibility = View.VISIBLE
                binding.loginBtn.visibility = View.INVISIBLE
            }
        })

        binding.loginBtn.setOnClickListener {
            binding.apply {
                viewModel.login(
                    emailEt.text.toString(),
                    passwordEt.text.toString(),
                    slackOAuthToken.text.toString()
                )
            }
        }
    }
}
