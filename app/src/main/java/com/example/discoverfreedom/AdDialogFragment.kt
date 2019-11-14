package com.example.discoverfreedom

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class AdDialogFragment : DialogFragment() {

    companion object {

        fun newInstance() = AdDialogFragment()

        private const val FREE_DOM_PACKAGE = "org.mozilla.firefox"
    }

    @Suppress("DEPRECATION")
    override fun onCreateDialog(savedInstanceState: Bundle?) = AlertDialog.Builder(requireContext())
            .setTitle(R.string.app_name)
            .setMessage(Html.fromHtml("<b>Remove all disruptive ads with Free.dom App.</b><br><br>Download Free.dom app and enjoy your experience without any ads."))
            .setNegativeButton("No, thanks") { _, _ -> }
            .setPositiveButton("Get App") { _, _ ->
                fun open(uri: String) = startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri)))

                try {
                    open("market://details?id=$FREE_DOM_PACKAGE")
                } catch (exception: android.content.ActivityNotFoundException) {
                    open("https://play.google.com/store/apps/details?id=$FREE_DOM_PACKAGE")
                }
            }
            .create()
}