package ly.bithive.hsavemeandroid;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static ly.bithive.hsavemeandroid.util.COMMON.CHECK_CONNECTION_URL;

public class Starter {//implements Response.Listener<JSONObject> ,Response.ErrorListener{
    RequestQueue requestQueue;
    Context context;
    boolean status = true;
    long timeStamp;
    public Starter(Context mContext) {
        this.context = mContext;
        requestQueue = Volley.newRequestQueue(mContext);
    }
    public interface ResultListener {
         void onResult(JSONObject result);
    }

    private ResultListener listener;


    public boolean checkServerConnectivity() {
        return status;
    }
    public void setResultListener(ResultListener listener){
        this.listener = listener;
    }
    public long checkServerUpdates() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, CHECK_CONNECTION_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse( JSONObject response) {
                listener.onResult(response);
            }
        }, null);
        requestQueue.add(jsonObjectRequest);

        return timeStamp;
    }

    public void callServer(String address) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, address, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.onResult(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VOLLEY ERROR",error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);

    }

}


