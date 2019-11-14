package com.example.discoverfreedom

import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.activity_main.*

class MainActivityKt(private val mainActivity: MainActivity) {

    fun onCreate() {
        mainActivity.mainAdView.loadAd(AdRequest.Builder().build())
    }
}