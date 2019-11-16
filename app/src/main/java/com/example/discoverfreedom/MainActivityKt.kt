package com.example.discoverfreedom

import com.example.discoverfreedom.util.addOneShotGlobalLayoutListener
import com.example.discoverfreedom.util.pxToDp
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivityKt(private val mainActivity: MainActivity) {

    companion object {

        private const val TAG_AD_DIALOG = "adDialog"
    }

    fun onCreate() = mainActivity.run {
        val adView = AdView(this)
        mainAdFrame.addView(adView)

        mainAdFrame.addOneShotGlobalLayoutListener {
            val widthPx = mainAdFrame.width
            val widthDp = pxToDp(widthPx)

            adView.adSize = AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, widthDp.toInt())
            adView.adUnitId = resources.getString(R.string.ad_unit_banner)

            adView.loadAd(AdRequest.Builder().build())
        }

        mainAdClose.setOnClickListener { launchAdDialog() }
    }

    fun launchAdDialog() = AdDialogFragment.newInstance().show(mainActivity.supportFragmentManager, TAG_AD_DIALOG)
}