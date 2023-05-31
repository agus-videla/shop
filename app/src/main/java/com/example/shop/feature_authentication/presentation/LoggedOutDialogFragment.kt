package com.example.shop.feature_authentication.presentation

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.shop.R

class LoggedOutDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setNeutralButton(R.string.ok) { _,_ -> }
            .setTitle(getString(R.string.session_expired))
            .setMessage(R.string.you_have_been_logged_out)
            .create()
    companion object {
        const val TAG = "LoggedOutDialog"
    }
}