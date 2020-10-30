package ly.bithive.hsavemeandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ly.bithive.hsavemeandroid.adapter.ClinksAdapter;
import ly.bithive.hsavemeandroid.adapter.DevicesAdapter;
import ly.bithive.hsavemeandroid.adapter.ClinksAdapter;
import ly.bithive.hsavemeandroid.model.Clink;
import ly.bithive.hsavemeandroid.model.Device;
import ly.bithive.hsavemeandroid.model.Clink;

import static ly.bithive.hsavemeandroid.COMMON.GET_CLINKS_URL;
import static ly.bithive.hsavemeandroid.Utils.getItemLink;

public class ClinksActivity extends AppCompatActivity implements ClinksAdapter.SelectedItem {
    Context context;
    ImageButton FilterBtn;
  //  RadioGroup radioGroup;
  //  TextView searchType;
  //  ImageView searchImage;
    android.widget.SearchView searchInput;
    int searchTypeValue = 0;
    int rad;
    RecyclerView recyclerView;
    RequestQueue requestQueue;
    List<Clink> clinkList;
    ClinksAdapter clinksAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinks);
        requestQueue = Volley.newRequestQueue(this);
        context = this;
        clinkList = new ArrayList<>();
        clinksAdapter = new ClinksAdapter(clinkList,this);
        String Id = getIntent().getStringExtra("ITEM_ID");
        int itemType = getIntent().getIntExtra("ITEM_TYPE",1);
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    //    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView = (RecyclerView) findViewById(R.id.RVDoctors);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(clinksAdapter);
        getItemProfile(itemType,Id);
    }

    private void getItemProfile(int itemType, String id) {
        String url = getItemLink(itemType);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url + "/" + id + "/clinks", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                //    JSONObject item = response.getJSONObject("data");
                    parseClinks(response.getJSONArray("data"));
                 //   parseClinks(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VOLLEY ERROR", error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        final SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setMaxWidth(800);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                clinksAdapter.getFilter().filter(newText);
                return true;
            }
        });
        searchView.setQueryHint("ابحت هنا");
        return true;
    }

    private void parseClinks(JSONArray clinks) {

        for (int i = 0; i < clinks.length(); i++) {
            try {
                JSONObject clnk = clinks.getJSONObject(i);
                Clink clink = new Clink();
                clink.setId(clnk.getInt("id")).setName(clnk.getString("name")).setAddress(clnk.getString("address"));
                clinkList.add(clink);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        clinksAdapter.notifyDataSetChanged();

    }

    @Override
    public void selectedItem(Clink clink) {
        startActivity(new Intent(ClinksActivity.this,ProfileActivity.class)
                .putExtra("CLINIC_ID", String.valueOf(clink.getId())));
    }
}