package com.example.discoverfreedom

import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class AdDialogFragment : DialogFragment() {

    companion object {

        fun newInstance() = AdDialogFragment()
    }

    @Suppress("DEPRECATION")
    override fun onCreateDialog(savedInstanceState: Bundle?) = AlertDialog.Builder(requireContext())
            .setTitle(R.string.app_name)
            .setMessage(Html.fromHtml("<b>Remove all disruptive ads with Free.dom App.</b><br><br>Download Free.dom app and enjoy your experience without any ads."))
            .setNegativeButton("No, thanks") { _, _ -> }
            .setPositiveButton("Get App") { _, _ -> }
            .create()
}