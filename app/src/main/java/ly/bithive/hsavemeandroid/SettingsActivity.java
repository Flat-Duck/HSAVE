package ly.bithive.hsavemeandroid;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    RadioButton arab, english;
    DatabaseHelper helper;
    String locale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        helper = new DatabaseHelper(this);

        // locale = ;
        arab = findViewById(R.id.radArb);
        english = findViewById(R.id.radEng);
        locale =helper.getLocale();
        if (helper.getLocale().equals("ar")) {
            arab.setChecked(true);
        } else {
            english.setChecked(true);

        }
        arab.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    resetLocale("ar");
                }
            }
        });
        english.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    resetLocale("en");
                }
            }
        });
    }

    private void resetLocale(String locale) {

        Toast.makeText(this, locale, Toast.LENGTH_SHORT).show();
        helper.resetLocale(locale);
        setApplicationLocale(locale);

    }
    private void setApplicationLocale(String locale) {
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        config.setLocale(new Locale(locale.toLowerCase()));
        resources.updateConfiguration(config, dm);
    }
}