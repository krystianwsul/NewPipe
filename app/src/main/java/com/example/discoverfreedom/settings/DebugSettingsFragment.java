package com.example.discoverfreedom.settings;

import android.os.Bundle;

import com.example.discoverfreedom.R;

public class DebugSettingsFragment extends BasePreferenceFragment {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.debug_settings);
    }
}
