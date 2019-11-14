package com.example.discoverfreedom

import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.activity_main.*

class MainActivityKt(private val mainActivity: MainActivity) {

    companion object {

        private const val TAG_AD_DIALOG = "adDialog"
    }

    fun onCreate() = mainActivity.run {
        mainAdView.loadAd(AdRequest.Builder().build())

        mainAdClose.setOnClickListener {
            AdDialogFragment.newInstance().show(supportFragmentManager, TAG_AD_DIALOG)
        }
    }
}