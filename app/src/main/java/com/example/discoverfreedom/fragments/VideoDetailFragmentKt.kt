package com.example.discoverfreedom.fragments

import com.example.discoverfreedom.R
import com.example.discoverfreedom.fragments.detail.VideoDetailFragment
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd

class VideoDetailFragmentKt(private val videoDetailFragment: VideoDetailFragment) {

    private lateinit var interstitialAd: InterstitialAd

    fun onCreate() {
        interstitialAd = videoDetailFragment.run {
            InterstitialAd(requireContext()).apply {
                adUnitId = resources.getString(R.string.ad_unit_interstitial)
                loadAd(AdRequest.Builder().build())
            }
        }
    }

    fun showInterstitial(callback: () -> Unit) {
        if (interstitialAd.isLoaded) {
            interstitialAd.adListener = object : AdListener() {

                override fun onAdClosed() = callback()
            }

            interstitialAd.show()
        } else {
            callback()
        }
    }
}