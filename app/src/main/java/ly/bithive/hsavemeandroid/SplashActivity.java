package ly.bithive.hsavemeandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        /****** انشاء ثريد لجعل التطبيق ينام لمدة 5 ثواني  *************/
        Thread background = new Thread() {
            public void run() {

                try {

                    sleep( 1000); // غير في الرقم 5 لتغيير عدد الثواني .. ثواني الانتظار قبل الدخول للتطبيق

                    // بعد 5 ثواني نفذ التالي وهو الانتقال بنا الى الشاشة الرئيسية
                    Intent i = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(i);

                    // اغلاق شاشة السبلاش بشكل كلشي بعد الانتقال
                    finish();

                } catch (Exception e) {

                }
            }
        };

        // تشغيل الثريد السابق
        background.start();
    }
}