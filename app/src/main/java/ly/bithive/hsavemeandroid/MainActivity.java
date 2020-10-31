package ly.bithive.hsavemeandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import ly.bithive.hsavemeandroid.interf.OnClinksDataReceivedListener;
import ly.bithive.hsavemeandroid.model.Clink;

import static ly.bithive.hsavemeandroid.util.COMMON.GET_CLINKS_URL;

public class MainActivity extends AppCompatActivity implements OnClinksDataReceivedListener {
    Button onlineBtn, offlineBtn;
    DatabaseHelper helper;
    List<Clink> clinkList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Starter starter = new Starter(this);
        helper = new DatabaseHelper(this);
        starter.setDataListener(this);
        starter.callServer(GET_CLINKS_URL);

        onlineBtn = findViewById(R.id.onlineSearch);
        offlineBtn = findViewById(R.id.directory);
        onlineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SearchActivity.class).putExtra("ONLINE",true));
            }
        });

        offlineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SearchActivity.class).putExtra("ONLINE",false));
            }
        });
    }

    @Override
    public void onDeviceDataReceived(JSONObject result) {
        try {
            Long timeStamp = result.getLong("timeStamp");
            JSONArray item = result.getJSONArray("data");

            if (helper.getTimeStamp() == 0L || helper.getTimeStamp() != timeStamp) //
            {
                for (int i = 0; i < item.length(); i++) {
                    try {
                        JSONObject clnk = item.getJSONObject(i);
                        Clink clink = new Clink();
                        clink.setId(clnk.getInt("id"))
                                .setName(clnk.getString("name"))
                                .setAddress(clnk.getString("address"))
                                .setLatitude(clnk.getString("latitude"))
                                .setLongitude(clnk.getString("longitude"))
                                .setPhone_number(clnk.getString("phone_number"));
                        helper.createClink(clink);
                        Log.d("HHH", clink.toString());
                        //  clinkList.add(clink);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                helper.resetTimeStamp(timeStamp);

                Log.d("HHH", result.toString());
                Log.d("DBTS", String.valueOf(helper.getTimeStamp()));
                Log.d("ONTS", String.valueOf(timeStamp));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}