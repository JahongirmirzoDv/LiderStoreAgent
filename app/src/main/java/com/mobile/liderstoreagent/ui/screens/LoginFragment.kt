package com.mobile.liderstoreagent.ui.screens

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mobile.liderstoreagent.R
import com.mobile.liderstoreagent.data.models.loginmodel.LoginResponse
import com.mobile.liderstoreagent.data.source.local.TokenSaver
import com.mobile.liderstoreagent.ui.viewmodels.LoginViewModel
import com.mobile.liderstoreagent.utils.SharedPref
import com.mobile.liderstoreagent.utils.showToast
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : Fragment(R.layout.login_fragment) {
    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SharedPref.getInstanceDis(requireContext())

        if (TokenSaver.getLogin()!!.isNotEmpty()) {
            input_login.setText(TokenSaver.getLogin())
            loginLayout.visibility = View.GONE
            login.text = "Кириш"
        }

        login.setOnClickListener {

            val password = input_password.text.toString()
            val username = input_login.text.toString()

            when {
                username.isEmpty() -> {
                    input_login.error = "Логинни киритинг!"
                }
                password.isEmpty() -> {
                    input_password.error = "Паролни киритинг!"
                }
                TokenSaver.token.isEmpty() -> {
                    viewModel.login(username, password)
                }
                TokenSaver.getPassword() == input_password.text.toString() -> {
                    findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                }
                else -> {
                    requireActivity().showToast("Парол хато!")
                }
            }

        }
        viewModel.progressLiveData.observe(viewLifecycleOwner, progressObserver)
        viewModel.errorLoginLiveData.observe(viewLifecycleOwner, errorLoginObserver)
        viewModel.connectionErrorLiveData.observe(viewLifecycleOwner, connectionErrorObserver)
        viewModel.successLiveData.observe(viewLifecycleOwner, successObserver)
        viewModel.errorTimeOutLive.observe(viewLifecycleOwner, errorTimeOutObserver)
    }


    private val progressObserver = Observer<Boolean> {
        if (it) {
            loginProgressBar.visibility = View.VISIBLE
        } else {
            loginProgressBar.visibility = View.GONE
        }
    }

    private val errorTimeOutObserver = Observer<Unit> {
        requireActivity().showToast("Интернет жуда паст!")
        loginProgressBar.visibility = View.GONE
    }


    private val errorLoginObserver = Observer<String> {
        requireActivity().showToast("Парол ёки Логин хато!")
        loginProgressBar.visibility = View.GONE
    }
    private val connectionErrorObserver = Observer<Unit> {
        requireActivity().showToast("Интернет юқ!")
    }

    private val successObserver = Observer<LoginResponse> {
        SharedPref.user = it.user?.id.toString()
        AlertDialog.Builder(requireContext())
            .setTitle("Диққат!")
            .setMessage("Тизимга кирдингиз !")
            .setPositiveButton("Ок") { dialog, _ ->
                findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                dialog.cancel()
            }.show()
    }
}