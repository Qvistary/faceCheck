package top.qvisa.faceCheck.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_sign_in.*


import top.qvisa.faceCheck.R
import top.qvisa.faceCheck.view.activity.LoginActivity
import top.qvisa.faceCheck.viewModel.SignViewModel

class SignInFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val viewModel = ViewModelProvider(requireActivity())[SignViewModel::class.java]

        viewModel.userId.observe(viewLifecycleOwner, Observer {
            it?.let {
                eT_login_account.setText(it)
                eT_login_password.requestFocus()
            }
        })

        viewModel.resultCode.observe(this.viewLifecycleOwner, Observer {
            it?.let {
                when (it) {
                    0 -> {
                        Toast.makeText(
                            requireContext(),
                            "未知错误",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    400 -> {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.str_signIn_error),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    200, 520 -> {
                        val userId = eT_login_account.text.toString()
                        val bundle = Bundle()
                        bundle.putString("userId", userId)
                        Navigation.findNavController(
                            requireActivity(),
                            R.id.fragment_host_login
                        ).navigate(R.id.action_signInFragment_to_mainActivity, bundle)
                        LoginActivity().finish()

                    }
                    else -> return@let
                }
                eT_login_password.text.clear()
                eT_login_password.requestFocus()
                pB_SignIn_SignIn.isVisible = false
                bt_signIn_signIn.text = getString(R.string.str_signIn_login)
            }
            viewModel.setResultCode()
        })

        eT_login_password.doAfterTextChanged {
            isBtEnable()
        }

        eT_login_account.doAfterTextChanged {
            isBtEnable()
        }

        bt_signIn_signIn.setOnClickListener {
            pB_SignIn_SignIn.isVisible = true
            bt_signIn_signIn.text = ""
            viewModel.signIn(eT_login_account.text.toString(), eT_login_password.text.toString())
        }

        tV_signIn_signUp.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fragment_host_login)
                .navigate(R.id.action_signInFragment_to_signUpFragment)
        }

    }

    private fun isBtEnable() {
        bt_signIn_signIn.isEnabled =
            eT_login_account.text.isNotEmpty() and eT_login_password.text.isNotEmpty()
    }

}
