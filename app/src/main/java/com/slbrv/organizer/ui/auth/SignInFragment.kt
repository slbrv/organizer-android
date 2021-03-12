package com.slbrv.organizer.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.slbrv.organizer.R
import com.slbrv.organizer.data.entity.AuthBody

class SignInFragment : Fragment() {

    companion object {
        fun newInstance() = SignInFragment()
    }

    private val _viewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)

        val nameEditView = view.findViewById<EditText>(R.id.sign_in_username_or_email_edit_view)
        val passwordEditView = view.findViewById<EditText>(R.id.sign_in_pwd_edit_view)
        val signInButton = view.findViewById<Button>(R.id.sign_in_button)
        val haveAccountTextView = view.findViewById<TextView>(R.id.sign_in_have_acc_text_view)

        signInButton.setOnClickListener { v ->
            run {
                val field = nameEditView.text.toString()
                val pwd = passwordEditView.text.toString()

                val body = validate(field, pwd)
                if (body != null) {
                    _viewModel.signIn(body)
                } else {
                    Toast.makeText(context, R.string.auth_error, Toast.LENGTH_LONG).show()
                    Log.i("APP", "Body: $body")
                }
            }
        }

        haveAccountTextView.setOnClickListener { v ->
            run {
                toSignUpFragment()
            }
        }


        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    private fun validate(field: String, pwd: String): AuthBody? {
        val nameRegex = Regex("[a-zA-Z0-9_]*")
        val emailRegex = Regex("[a-zA-Z0-9_]+@[a-zA-Z0-9_]+\\.[a-z]+")

        when {
            pwd.length < 6 -> {
                Toast.makeText(
                    context,
                    R.string.password_must_be_at_least_6_characters_long,
                    Toast.LENGTH_LONG
                ).show()
                return null
            }

            nameRegex.matches(field) -> AuthBody(field, "", pwd)
            emailRegex.matches(field) -> AuthBody("", field, pwd)

            else -> {
                Toast.makeText(
                    context,
                    R.string.invalid_username_or_email_entered,
                    Toast.LENGTH_LONG
                ).show()
                return null
            }
        }
        return null
    }

    private fun toSignUpFragment() {
        val manager = activity?.supportFragmentManager

        val fragment = manager?.findFragmentByTag("sign_up_fragment")
        if (fragment != null) {
            manager.beginTransaction().show(fragment).hide(this).commit()
        } else {
            manager
                ?.beginTransaction()
                ?.add(
                    R.id.auth_fragment_container,
                    SignUpFragment.newInstance(),
                    "sign_up_fragment"
                )
                ?.hide(this)
                ?.commit()
        }

    }
}