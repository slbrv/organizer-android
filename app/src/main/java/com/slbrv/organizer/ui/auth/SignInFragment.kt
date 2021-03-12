package com.slbrv.organizer.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import com.slbrv.organizer.R

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

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    fun isValid(nameField: String, password: String): Boolean {
        var valid = false
        val userNameRegex = Regex("[a-zA-Z0-9_]*")
        val emailRegex = Regex("[a-zA-Z0-9_]+@[a-zA-Z0-9_]+\\.[a-z]+")


    }

}