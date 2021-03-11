package com.slbrv.organizer.ui.auth

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.slbrv.organizer.R

class SignUpFragment : Fragment() {
    
    companion object {
        fun newInstance() = SignUpFragment()
    }

    private lateinit var _viewModel: SignUpViewModel

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
        signUpButton.setOnClickListener {
            val username = usernameEditView.text.toString()
            val email = emailEditView.text.toString()
            val pwd = passwordEditView.text.toString()
            val repPwd = repeatPasswordEditView.text.toString()

            if (isValid(username, email, pwd, repPwd)) {
                Log.i("APP", "Valid")
            } else {
                Log.i("APP", "Not valid")
            }
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        _viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
    }

    private fun isValid(username: String, email: String, pwd: String, repPwd: String) : Boolean{
        var valid = false;
        val userNameRegex = Regex("[a-zA-Z0-9_]*")
        val emailRegex = Regex("[a-zA-Z0-9_]+@[a-zA-Z0-9_]+\\.[a-z]+")
        when {
            username.isEmpty() -> Toast.makeText(
                context,
                R.string.you_must_enter_a_username,
                Toast.LENGTH_LONG
            ).show()

            username.length < 5 -> Toast.makeText(
                context,
                R.string.username_must_be_at_least_5_characters_long,
                Toast.LENGTH_LONG
            ).show()

            !userNameRegex.matches(username) -> Toast.makeText(
                context,
                R.string.username_can_only_contain_letters_numbers_and_underscore_signs,
                Toast.LENGTH_LONG
            ).show()

            email.isEmpty() -> Toast.makeText(
                context,
                R.string.you_must_enter_a_email,
                Toast.LENGTH_LONG
            ).show()

            !emailRegex.matches(email) -> Toast.makeText(
                context,
                R.string.invalid_email_entered,
                Toast.LENGTH_LONG
            ).show()

            pwd.isEmpty() -> Toast.makeText(
                context,
                R.string.you_must_enter_a_password,
                Toast.LENGTH_LONG
            ).show()

            repPwd.isEmpty() -> Toast.makeText(
                context,
                R.string.you_must_repeat_a_password,
                Toast.LENGTH_LONG
            ).show()

            pwd != repPwd -> Toast.makeText(
                context,
                R.string.the_passwords_must_match,
                Toast.LENGTH_LONG
            ).show()

            else -> valid = true
        }
        return valid
    }
}