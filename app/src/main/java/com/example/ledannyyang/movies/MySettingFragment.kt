package com.example.ledannyyang.movies

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
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
            val builder = AlertDialog.Builder(activity)
            builder.setTitle(getString(R.string.delete_watchlist))
            builder.setMessage(getString(R.string.delete_sure))

            builder.setPositiveButton("Continue", DialogInterface.OnClickListener { dialog, which ->
                MyRoomDatabase.getMyRoomDatabase(clearWatchlist.context)?.clearWatchlist()
                    WatchListController.updateList(mutableListOf())
                    clearWatchlist.context.toast(getString(R.string.watchlist_clear))
                    true
            })

            builder.setNegativeButton("Cancel", null)
            val dialog = builder.create()
            dialog.show()

            true
        }

        deleteCache.setOnPreferenceClickListener {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle(getString(R.string.delete_cache))
            builder.setMessage(getString(R.string.delete_sure_cache))

            builder.setPositiveButton("Continue", DialogInterface.OnClickListener { dialog, which ->
                deleteCache.context.cacheDir.deleteRecursively()
                deleteCache.context.toast(getString(R.string.deleteCache))
                true
            })

            builder.setNegativeButton("Cancel", null)
            val dialog = builder.create()
            dialog.show()

            true
        }

        sendFeedback.setOnPreferenceClickListener {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", getString(R.string.owner_email), null))
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.kino_feedback))
            startActivity(Intent.createChooser(intent, getString(R.string.send_email)))
            true
        }
    }
}