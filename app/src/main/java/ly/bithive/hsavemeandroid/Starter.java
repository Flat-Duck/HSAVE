package ly.bithive.hsavemeandroid;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import ly.bithive.hsavemeandroid.interf.OnClinksDataReceivedListener;

import static ly.bithive.hsavemeandroid.util.COMMON.CHECK_CONNECTION_URL;

public class Starter {
    RequestQueue requestQueue;
    Context context;
    boolean status = true;
    long timeStamp;

    public Starter(Context mContext) {
        this.context = mContext;
        requestQueue = Volley.newRequestQueue(mContext);
    }

    public OnClinksDataReceivedListener clinkDataListener;

    public void setDataListener(OnClinksDataReceivedListener mDataListener) {
        this.clinkDataListener = mDataListener;
    }

    public void callServer(String address) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, address, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                clinkDataListener.onDeviceDataReceived(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VOLLEY ERROR", error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

}


