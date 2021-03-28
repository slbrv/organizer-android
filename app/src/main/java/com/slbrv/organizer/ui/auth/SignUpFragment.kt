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
import com.slbrv.organizer.data.auth.AuthBody

class SignUpFragment() : Fragment() {

    companion object {
        fun newInstance() = SignUpFragment()
    }

    private val viewModel: AuthViewModel by activityViewModels()

    private lateinit var usernameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var repeatPasswordEditText: EditText
    private lateinit var signUpButton: Button
    private lateinit var haveAccountTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)

        usernameEditText = view.findViewById(R.id.sign_up_username_edit_view)
        emailEditText = view.findViewById(R.id.sign_up_email_edit_view)
        passwordEditText = view.findViewById(R.id.sign_up_pwd_edit_view)
        repeatPasswordEditText = view.findViewById(R.id.sign_up_rep_pwd_edit_view)
        signUpButton = view.findViewById(R.id.sign_up_button)
        haveAccountTextView = view.findViewById(R.id.sign_up_have_account_text_view)

        signUpButton.setOnClickListener { onSignUp() }
        haveAccountTextView.setOnClickListener { toSignInFragment() }

        return view
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
                    R.id.activity_main_container,
                    SignInFragment.newInstance(),
                    "sign_in_fragment"
                )
                ?.hide(this)
                ?.commit()
        }
    }

    private fun onSignUp() {
        val username = usernameEditText.text.toString()
        val email = emailEditText.text.toString()
        val pwd = passwordEditText.text.toString()
        val repPwd = repeatPasswordEditText.text.toString()
        signUpButton.isEnabled = false

        if (validate(username, email, pwd, repPwd)) {
            viewModel.signUp(AuthBody(username, email, pwd))
        } else {
            Log.i("APP", "Not valid")
        }

        Handler(Looper.getMainLooper()).postDelayed({
            signUpButton.isEnabled = true
        }, 3000)
    }
}