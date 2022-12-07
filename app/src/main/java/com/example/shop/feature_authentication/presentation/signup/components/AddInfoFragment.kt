package com.example.shop.feature_authentication.presentation.signup.components

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.shop.R
import com.example.shop.core.domain.model.User
import com.example.shop.databinding.FragmentAddInfoBinding
import com.example.shop.feature_authentication.presentation.signup.AddInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class AddInfoFragment : Fragment() {
    private var _binding: FragmentAddInfoBinding? = null
    private val binding get() = _binding!!
    private val args: AddInfoFragmentArgs by navArgs()
    private val viewModel: AddInfoViewModel by viewModels()

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                openCameraForResult()
            } else {
                Toast.makeText(this.context,
                    "You won't be able to take a profile picture without camera permissions",
                    Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnTake.setOnClickListener {
            requestPermission()
        }
        binding.btnContinue.setOnClickListener {
            lifecycleScope.launch {
                viewModel.addNewUser(User(
                    username = args.username,
                    pass = args.passHash,
                    address = null,
                    pfp = binding.ivProfile.drawable.toBitmap()
                ))
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@AddInfoFragment.context,
                        "User Created!",
                        Toast.LENGTH_SHORT).show()
                    Navigation.findNavController(binding.root)
                        .navigate(R.id.action_addInfoFragment_to_logInFragment)
                }
            }
        }
    }

    private fun requestPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                openCameraForResult()
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                this.requireActivity(),
                Manifest.permission.CAMERA
            ) -> {
                Toast.makeText(this.context,
                    "To take a picture you should grant camera permissions",
                    Toast.LENGTH_SHORT).show()
                requestPermissionLauncher.launch(
                    Manifest.permission.CAMERA
                )
            }
            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.CAMERA
                )
            }
        }
    }


    private fun openCameraForResult() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        resultLauncher.launch(intent)
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val thumbnail: Bitmap = data?.extras?.get("data") as Bitmap
                binding.ivProfile.setImageBitmap(thumbnail)
            }

        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
