package com.mailerdaemon.app

import android.content.Intent
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.mailerdaemon.app.utils.ChromeTab

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.pref_settings, rootKey)
    }

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {
        return when (preference?.key) {
            getString(R.string.pref_key_privacy_policy) -> {
                ChromeTab(activity).openTab("https://drive.google.com/file/d/1RqZjZB8q-q-Wo0HAo0HgQzo7d3AY1_Cb" +
                    "/view?usp=sharing")
                true
            }
            getString(R.string.pref_key_report) -> {
                ChromeTab(activity).openTab("https://docs.google.com/forms/d/e/1FAIpQLScrYmD4pCmBc35QkkebFC9AC" +
                    "-HT45t-r5SAzsYja6-TleSVAQ/viewf̥orm?usp=sf_link")
                true
            }
            getString(R.string.pref_key_logout) -> {
                FirebaseAuth.getInstance().signOut()
                LoginManager.getInstance().logOut()
                startActivity(Intent(activity, LoginActivity::class.java))
                activity?.finish()
                true
            }
            else -> {
                super.onPreferenceTreeClick(preference)
            }
        }
    }
}
