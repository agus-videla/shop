package com.example.shop.ui.login.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.shop.R
import com.example.shop.databinding.FragmentSignUpBinding
import com.example.shop.ui.login.digest
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvLogIn.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_signUpFragment_to_logInFragment)
        }

        lifecycleScope.launch {
            viewModel.uiState.collect {
                it.apply {
                    handleUiState(isUserValid, binding.ivUserState)
                    handleUiState(isPasswordValid, binding.ivPasswordState)
                    handleUiState(arePasswordsEqual, binding.ivRepeatState)
                    binding.btnSignUp.setOnClickListener {
                        if (isUserValid == isPasswordValid == arePasswordsEqual) {
                            val action =
                                SignUpFragmentDirections.actionSignUpFragmentToAddInfoFragment(
                                    binding.etUsername.text.toString(),
                                    digest(binding.etPassword.text.toString())
                                )
                            Navigation.findNavController(binding.root).navigate(action)
                        }
                    }
                }
            }
        }

        initTextWatcher(binding.etUsername) { s ->
            lifecycleScope.launch {
                if ((s?.length ?: 0) < 4 || !viewModel.validateUsername(s)) {
                    viewModel.isUserValid(false)
                } else {
                    viewModel.isUserValid(true)
                }
            }
        }
        initTextWatcher(binding.etPassword) { s ->
            if ((s?.length ?: 0) < 4)
                viewModel.isPasswordValid(false)
            else
                viewModel.isPasswordValid(true)
        }
        initTextWatcher(binding.etRepeatPassword) { s ->
            if (s.toString() == binding.etPassword.text.toString())
                viewModel.arePasswordsEqual(true)
            else
                viewModel.arePasswordsEqual(false)
        }
    }


    private fun initTextWatcher(et: EditText, onTextChanged: (CharSequence?) -> Unit) {
        et.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                onTextChanged(s)
            }
        })
    }

    private fun handleUiState(b: Boolean?, iv: ImageView) {
        b?.let {
            iv.visibility = View.VISIBLE
        }
        when (b) {
            true -> iv.setImageResource(R.drawable.ic_check)
            false -> iv.setImageResource(R.drawable.ic_close)
            null -> iv.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}