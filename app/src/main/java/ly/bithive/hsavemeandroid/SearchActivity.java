package ly.bithive.hsavemeandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

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
import ly.bithive.hsavemeandroid.adapter.DoctorsAdapter;
import ly.bithive.hsavemeandroid.adapter.SpecialtiesAdapter;
import ly.bithive.hsavemeandroid.adapter.TestsAdapter;
import ly.bithive.hsavemeandroid.model.Clink;
import ly.bithive.hsavemeandroid.model.Device;
import ly.bithive.hsavemeandroid.model.Doctor;
import ly.bithive.hsavemeandroid.model.Specialty;
import ly.bithive.hsavemeandroid.model.Test;

import static ly.bithive.hsavemeandroid.COMMON.*;
import static ly.bithive.hsavemeandroid.Utils.getItemLink;

public class SearchActivity extends AppCompatActivity implements DoctorsAdapter.SelectedItem,
        DevicesAdapter.SelectedItem, TestsAdapter.SelectedItem, SpecialtiesAdapter.SelectedItem,
        ClinksAdapter.SelectedItem {

    Context context;
    ImageButton FilterBtn;
    RadioGroup radioGroup;
    TextView searchType;
    ImageView searchImage;
    android.widget.SearchView searchInput;
    int searchTypeValue = 0;
    int rad;
    RecyclerView recyclerView;
    //   List<Item> items;
    RequestQueue requestQueue;
    List<Doctor> doctorList;
    List<Clink> clinkList;
    List<Specialty> specialtyList;
    List<Test> testList;
    List<Device> deviceList;
    DoctorsAdapter doctorsAdapter;
    DevicesAdapter devicesAdapter;
    ClinksAdapter clinksAdapter;
    TestsAdapter testsAdapter;
    SpecialtiesAdapter specialtiesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        context = this;
        requestQueue = Volley.newRequestQueue(this);
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.RVDoctors);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        ImageButton button = (ImageButton) findViewById(R.id.search_type);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSelectSearchType();
            }
        });

//        Typeface iconFont = FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME);
//        FontManager.markAsIconContainer(findViewById(R.id.icons_container), iconFont);
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
                switch (searchTypeValue) {
                    case DR:
                        doctorsAdapter.getFilter().filter(newText);
                        break;
                    case DV:
                        devicesAdapter.getFilter().filter(newText);
                        break;
                    case SP:
                        specialtiesAdapter.getFilter().filter(newText);
                        break;
                    case TS:
                        testsAdapter.getFilter().filter(newText);
                        break;
                    case CL:
                        clinksAdapter.getFilter().filter(newText);
                        break;
                    default:
                        searchView.clearFocus();
                }

                //  Toast.makeText(SearchActivity.this, newText, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        searchView.setQueryHint("ابحت هنا");
        return true;
    }

    void showSelectSearchType() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final View picker_layout = LayoutInflater.from(context).inflate(R.layout.dialoge_search_type, null);
        builder.setView(picker_layout);
        radioGroup = picker_layout.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                rad = radioGroup.getCheckedRadioButtonId();
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkSelected(rad);
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }


    void checkSelected(int id) {
        switch (id) {
            case R.id.doctor:
                searchTypeValue = DR;
                getData(getItemLink(DR), searchTypeValue);
                break;
            case R.id.device:
                getData(getItemLink(DV), searchTypeValue = DV);
                break;
            case R.id.department:
                getData(getItemLink(SP), searchTypeValue = SP);
                break;
            case R.id.clinic:
                getData(getItemLink(CL), searchTypeValue = CL);
                break;
            case R.id.medical:
                getData(getItemLink(TS), searchTypeValue = TS);
                break;
//            case R.id.location: getData(getItemLink(dv),searchTypeValue = dv);break;
        }
    }

    private void getData(String url, final int searchTypeValue) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url + "/", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray item = response.getJSONArray("data");

                    //   JSONObject item = response.getJSONObject("data");
                    switch (searchTypeValue) {
                        case CL:
                            parseClinks(item);
                            break;
                        case DR:
                            parseDoctors(item);
                            break;
                        case SP:
                            parseSpecialties(item);
                            break;
                        case TS:
                            parseTests(item);
                            break;
                        case DV:
                            parseDevices(item);
                            break;
                        //   case sp: parseData(response,cl);break;
                        // case ts: parseData(response,cl);break;
                    }
                    //parseData(response,searchTypeValue);
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

    private void parseSpecialties(JSONArray specialties) {
        specialtyList = new ArrayList<>();
        for (int i = 0; i < specialties.length(); i++) {
            try {
                JSONObject ts = specialties.getJSONObject(i);
                Specialty specialty = new Specialty();
                specialty.setName(ts.getString("name"));//.setAddress(dv.getString("address"));
                specialtyList.add(specialty);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        specialtiesAdapter = new SpecialtiesAdapter(specialtyList, this);
        specialtiesAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(specialtiesAdapter);
    }

    private void parseClinks(JSONArray clinks) {
        clinkList = new ArrayList<>();
        for (int i = 0; i < clinks.length(); i++) {
            try {
                JSONObject cl = clinks.getJSONObject(i);
                Clink clink = new Clink();
                clink.setId(cl.getInt("id")).setName(cl.getString("name")).setAddress(cl.getString("address"));
                clinkList.add(clink);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        clinksAdapter = new ClinksAdapter(clinkList, this);
        clinksAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(clinksAdapter);
    }

    private void parseTests(JSONArray tests) {
        testList = new ArrayList<>();
        for (int i = 0; i < tests.length(); i++) {
            try {
                JSONObject ts = tests.getJSONObject(i);
                Test test = new Test();
                test.setName(ts.getString("name"));//.setAddress(dv.getString("address"));
                testList.add(test);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        testsAdapter = new TestsAdapter(testList, this);
        testsAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(testsAdapter);
    }

    private void parseDevices(JSONArray devices) {
        deviceList = new ArrayList<>();
        for (int i = 0; i < devices.length(); i++) {
            try {
                JSONObject dv = devices.getJSONObject(i);
                Device device = new Device();
                device.setName(dv.getString("name")).setDescription(dv.getString("description"));
                deviceList.add(device);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        devicesAdapter = new DevicesAdapter(deviceList, this);
        devicesAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(devicesAdapter);
    }

    private void parseDoctors(JSONArray doctors) {
        doctorList = new ArrayList<>();
        for (int i = 0; i < doctors.length(); i++) {
            try {
                JSONObject dr = doctors.getJSONObject(i);
                Doctor doctor = new Doctor();
                doctor.setId(dr.getInt("id")).setName(dr.getString("name")).setSpecialty(dr.getString("specialty_name"));
                doctorList.add(doctor);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        doctorsAdapter = new DoctorsAdapter(doctorList, this);
        doctorsAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(doctorsAdapter);
    }

    @Override
    public void selectedItem(Doctor doctor) {
        startActivityWithData(DR, String.valueOf(doctor.getId()));
    }

    @Override
    public void selectedItem(Device device) {
        startActivityWithData(DV, String.valueOf(device.getId()));
    }

    @Override
    public void selectedItem(Clink clink) {
        startActivity(new Intent(SearchActivity.this, ProfileActivity.class).putExtra("CLINIC_ID", String.valueOf(clink.getId())));


    }

    @Override
    public void selectedItem(Specialty specialty) {
        startActivityWithData(SP, String.valueOf(specialty.getId()));
    }

    @Override
    public void selectedItem(Test test) {
        startActivityWithData(TS, String.valueOf(test.getId()));
    }

    void startActivityWithData(int type, String id) {
        startActivity(new Intent(SearchActivity.this, ClinksActivity.class).putExtra("ITEM_TYPE", type).putExtra("ITEM_ID", id));
    }
}