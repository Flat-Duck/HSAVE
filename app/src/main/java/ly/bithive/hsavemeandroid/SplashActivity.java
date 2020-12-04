package ly.bithive.hsavemeandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;

import org.json.JSONObject;

import java.util.Locale;

import ly.bithive.hsavemeandroid.interf.OnClinksDataReceivedListener;

public class SplashActivity extends AppCompatActivity {

    DatabaseHelper helper;
    String local = "en";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        helper = new DatabaseHelper(this);
        local = helper.getLocale();

        setApplicationLocale(local);

        Thread background = new Thread() {
            public void run() {

                try {
                    sleep(1000); // غير في الرقم 5 لتغيير عدد الثواني .. ثواني الانتظار قبل الدخول للتطبيق
                    // بعد 5 ثواني نفذ التالي وهو الانتقال بنا الى الشاشة الرئيسية
                    Intent i = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(i);
                    // اغلاق شاشة السبلاش بشكل كلشي بعد الانتقال
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        // تشغيل الثريد السابق
        background.start();
    }

    private void setApplicationLocale(String locale) {
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        config.setLocale(new Locale(locale.toLowerCase()));
        resources.updateConfiguration(config, dm);
    }
}