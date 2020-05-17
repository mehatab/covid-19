package info.covid.core

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat

class AboutFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference, rootKey)
    }
}