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
import kotlinx.android.synthetic.main.fragment_sign_up.*
import top.qvisa.faceCheck.R
import top.qvisa.faceCheck.viewModel.SignViewModel


class SignUpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(requireActivity())[SignViewModel::class.java]

        viewModel.resultCode.observe(viewLifecycleOwner, Observer {
            it?.let {
                when (it) {
                    0 -> Toast.makeText(
                        requireContext(),
                        "未知错误",
                        Toast.LENGTH_SHORT
                    ).show()
                    400 -> Toast.makeText(
                        requireContext(),
                        "注册失败",
                        Toast.LENGTH_SHORT
                    ).show()
                    201 -> {
                        Navigation.findNavController(requireActivity(), R.id.fragment_host_login)
                            .navigateUp()
                        viewModel.setUserId(eT_signUp_userId.text.toString())
                    }
                    else -> return@let
                }
                pB_SignUp_SignUp.isVisible = false
                bt_signUp_signUp.text = getString(R.string.str_signIn_login)
                eT_signUp_userId.text.clear()
                eT_signUp_userId.requestFocus()
            }
            viewModel.setResultCode()
        })


        eT_signUp_userId.doAfterTextChanged { isBtEnable() }
        eT_signUp_userName.doAfterTextChanged { isBtEnable() }
        eT_signUp_password.doAfterTextChanged { isBtEnable() }
        eT_signUp_checkPassword.doAfterTextChanged {
            isBtEnable()
            if (eT_signUp_checkPassword.text.isNotEmpty() and eT_signUp_checkPassword.text.isNotEmpty()) {
                if (eT_signUp_checkPassword.text.toString() == eT_signUp_password.text.toString()) {
                    iV_signUp_check.setImageResource(R.drawable.ic_check_circle_black_24dp)
                } else {
                    iV_signUp_check.setImageResource(R.drawable.ic_remove_circle_black_24dp)
                }
            }
        }

        bt_signUp_signUp.setOnClickListener {
            pB_SignUp_SignUp.isVisible = true
            bt_signUp_signUp.text = ""
            viewModel.signUp(
                eT_signUp_userId.text.toString(),
                eT_signUp_password.text.toString(),
                eT_signUp_userName.text.toString()
            )
        }
    }

    private fun isBtEnable() {
        bt_signUp_signUp.isEnabled = eT_signUp_password.text.isNotEmpty() and
                eT_signUp_checkPassword.text.isNotEmpty() and
                eT_signUp_userId.text.isNotEmpty() and
                eT_signUp_userName.text.isNotEmpty() and
                (eT_signUp_checkPassword.text.toString() == eT_signUp_password.text.toString())
    }

}
