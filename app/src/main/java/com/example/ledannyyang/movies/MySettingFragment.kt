package com.example.ledannyyang.movies

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import org.jetbrains.anko.toast

class MySettingFragment : PreferenceFragmentCompat(){
    private lateinit var region: ListPreference
    private lateinit var deleteCache: Preference
    private lateinit var sendFeedback: Preference

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference_setting, rootKey)
        region = findPreference("region") as ListPreference
        deleteCache = findPreference("cache") as Preference
        sendFeedback = findPreference("feedback") as Preference

        region.setOnPreferenceChangeListener { oldValue , newValue ->
            region.context.toast(getString(R.string.restartApp))
            true
        }

        deleteCache.setOnPreferenceClickListener {
            deleteCache.context.cacheDir.deleteRecursively()
            deleteCache.context.toast(getString(R.string.deleteCache))
            true
        }

        sendFeedback.setOnPreferenceClickListener {
            sendFeedback.context.toast(getString(R.string.sendFeedback))
            true
        }
    }
}