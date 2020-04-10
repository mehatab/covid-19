package info.covid.info

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import info.covid.R

class AboutFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference, rootKey)
    }
}