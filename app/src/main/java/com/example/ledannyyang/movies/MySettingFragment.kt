package com.example.ledannyyang.movies

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import org.jetbrains.anko.toast

class MySettingFragment : PreferenceFragmentCompat(){
    private lateinit var region: ListPreference
    private lateinit var adultSwitch: SwitchPreference
    private lateinit var deleteCache: Preference
    private lateinit var sendFeedback: Preference

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference_setting, rootKey)
        region = findPreference("region") as ListPreference
        adultSwitch = findPreference("adultContent") as SwitchPreference
        deleteCache = findPreference("cache") as Preference
        sendFeedback = findPreference("feedback") as Preference

        region.setOnPreferenceChangeListener { oldValue , newValue ->
            region.context.toast(getString(R.string.restartApp))
            true
        }

        adultSwitch.setOnPreferenceClickListener {
            adultSwitch.context.toast(getString(R.string.restartApp))
            true
        }

        deleteCache.setOnPreferenceClickListener {
            deleteCache.context.toast(getString(R.string.deleteCache))
            true
        }

        sendFeedback.setOnPreferenceClickListener {
            sendFeedback.context.toast(getString(R.string.sendFeedback))
            true
        }
    }
}