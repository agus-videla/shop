package com.example.shop.feature_authentication.presentation.signup.components

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.shop.R
import com.example.shop.databinding.FragmentSignUpBinding
import com.example.shop.feature_authentication.presentation.signup.SignUpEvent
import com.example.shop.feature_authentication.presentation.signup.SignUpViewModel
import com.example.shop.ui.authentication.digest
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

        viewModel.state.onEach {
            it.apply {
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
        }.launchIn(lifecycleScope)

        initTextWatcher(binding.etUsername) { s ->
            lifecycleScope.launch {
                viewModel.onEvent(SignUpEvent.UserChanged(
                    s.toString(),
                    binding.ivUserState
                ))
            }
        }
        initTextWatcher(binding.etPassword) { s ->
            lifecycleScope.launch {
                viewModel.onEvent(SignUpEvent.PasswordChanged(
                    s.toString(),
                    binding.ivPasswordState
                ))
            }
        }
        initTextWatcher(binding.etRepeatPassword) { s ->
            lifecycleScope.launch {
                viewModel.onEvent(SignUpEvent.RepeatPassChanged(
                    s.toString(),
                    binding.etPassword.text.toString(),
                    binding.ivRepeatState
                ))
            }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}