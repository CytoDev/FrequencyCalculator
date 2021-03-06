package io.cytodev.freqcalc.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;

import androidx.fragment.app.FragmentManager;

import io.cytodev.freqcalc.R;
import io.cytodev.freqcalc.fragments.NestedPreferenceFragment;
import io.cytodev.themedactivity.ThemedActivity;

/**
 * io.cytodev.freqcalc.activities "Frequency Calculator"
 * 2016/01/14 @ 13:40
 *
 * @author Roel Walraven <mail@cytodev.io>
 */
public class PreferencesActivity extends ThemedActivity {
    private Activity        thisActivity;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.setDefaults();

        thisActivity = PreferencesActivity.this;
        manager      = getSupportFragmentManager();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(thisActivity);
        String            themeName   = preferences.getString("pref_appearance_theme", "WhiteSmoke");
        boolean           themeLight  = !preferences.getBoolean("pref_appearance_theme_dark", false);

        if(!super.getCurrentThemeName().equals(themeName)) {
            super.setTheme(themeLight, themeName);
        }

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_preferences);
        setupToolbar();
        setupUserInterface();
    }

    @Override
    public void onBackPressed() {
        back();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home) {
            back();
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);

        if(getSupportActionBar() == null) {
            setSupportActionBar(toolbar);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupUserInterface() {
        manager.beginTransaction()
                .replace(R.id.rootView, NestedPreferenceFragment.newInstance(R.xml.prefs, getResources().getString(R.string.action_settings)))
                .commit();

        if(getIntent().getExtras() != null) {
            int          preferenceFile = getIntent().getExtras().getInt("pref");
            CharSequence preferenceName = getIntent().getExtras().getCharSequence("name");

            // zero seems to work... Not at all tested on enough devices.
            manager.beginTransaction()
                    .setCustomAnimations(0, 0, R.animator.push_right_in, R.animator.push_right_out)
                    .replace(R.id.rootView, NestedPreferenceFragment.newInstance(preferenceFile, preferenceName))
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void back() {
        if(manager.getBackStackEntryCount() > 0) {
            manager.popBackStack();

            if(getSupportActionBar() != null) {
                getSupportActionBar().setSubtitle(null);
            }
        } else {
            Intent back = new Intent(thisActivity, MainActivity.class);

            back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_ANIMATION);

            thisActivity.startActivity(back);
            thisActivity.finish();

            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        }
    }

}
