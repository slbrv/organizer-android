package com.slbrv.organizer.ui.auth

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.slbrv.organizer.R
import com.slbrv.organizer.data.entity.AuthBody

class SignUpFragment() : Fragment() {

    companion object {
        fun newInstance() = SignUpFragment()
    }

    private val _viewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)

        val usernameEditView = view.findViewById<EditText>(R.id.sign_up_username_edit_view)
        val emailEditView = view.findViewById<EditText>(R.id.sign_up_email_edit_view)
        val passwordEditView = view.findViewById<EditText>(R.id.sign_up_pwd_edit_view)
        val repeatPasswordEditView = view.findViewById<EditText>(R.id.sign_up_rep_pwd_edit_view)
        val signUpButton = view.findViewById<Button>(R.id.sign_up_button)
        val haveAccountTextView = view.findViewById<TextView>(R.id.sign_up_have_account_text_view)

        signUpButton.setOnClickListener {
            val username = usernameEditView.text.toString()
            val email = emailEditView.text.toString()
            val pwd = passwordEditView.text.toString()
            val repPwd = repeatPasswordEditView.text.toString()
            signUpButton.isEnabled = false

            if (validate(username, email, pwd, repPwd)) {
                _viewModel.signUp(AuthBody(username, email, pwd))
            } else {
                Log.i("APP", "Not valid")
            }

            Handler(Looper.getMainLooper()).postDelayed({
                signUpButton.isEnabled = true
            }, 3000)
        }

        haveAccountTextView.setOnClickListener { v ->
            run {
                toSignInFragment()
            }
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    private fun validate(username: String, email: String, pwd: String, repPwd: String): Boolean {
        fun invalid(msg: Int) : Boolean{
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            return false
        }

        val userNameRegex = Regex("[a-zA-Z0-9_]*")
        val emailRegex = Regex("[a-zA-Z0-9_]+@[a-zA-Z0-9_]+\\.[a-z]+")

        return when {
            username.isEmpty() -> invalid(R.string.you_must_enter_a_username)
            username.length < 3 -> invalid(R.string.username_must_be_at_least_3_characters_long)
            !userNameRegex.matches(username) -> invalid(R.string.username_can_only_contain)
            email.isEmpty() -> invalid(R.string.you_must_enter_a_email)
            !emailRegex.matches(email) -> invalid(R.string.invalid_email_entered)
            pwd.length < 6 -> invalid(R.string.password_must_be_at_least_6_characters_long)
            repPwd.isEmpty() -> invalid(R.string.you_must_repeat_a_password)
            pwd != repPwd -> invalid(R.string.the_passwords_must_match)
            else -> true
        }
    }

    private fun toSignInFragment() {
        val manager = activity?.supportFragmentManager

        val fragment = manager?.findFragmentByTag("sign_in_fragment")
        if (fragment != null) {
            manager
                .beginTransaction()
                .show(fragment)
                .hide(this)
                .commit()
        } else {
            manager
                ?.beginTransaction()
                ?.add(
                    R.id.auth_fragment_container,
                    SignInFragment.newInstance(),
                    "sign_in_fragment"
                )
                ?.hide(this)
                ?.commit()
        }
    }
}