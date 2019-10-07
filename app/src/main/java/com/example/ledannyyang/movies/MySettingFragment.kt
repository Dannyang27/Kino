package com.example.ledannyyang.movies

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.ledannyyang.movies.FragmentController.WatchListController
import com.example.ledannyyang.movies.Room.MyRoomDatabase
import org.jetbrains.anko.toast

class MySettingFragment : PreferenceFragmentCompat(){
    private lateinit var region: ListPreference
    private lateinit var clearWatchlist: Preference
    private lateinit var deleteCache: Preference
    private lateinit var sendFeedback: Preference

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference_setting, rootKey)
        region = findPreference("region") as ListPreference
        clearWatchlist  = findPreference("watchlist") as Preference
        deleteCache = findPreference("cache") as Preference
        sendFeedback = findPreference("feedback") as Preference

        region.setOnPreferenceChangeListener { oldValue , newValue ->
            region.context.toast(getString(R.string.restartApp))
            true
        }

        clearWatchlist.setOnPreferenceClickListener {
            MyRoomDatabase.getMyRoomDatabase(clearWatchlist.context)?.clearWatchlist()
            WatchListController.updateList(mutableListOf())
            clearWatchlist.context.toast(getString(R.string.watchlist_clear))
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