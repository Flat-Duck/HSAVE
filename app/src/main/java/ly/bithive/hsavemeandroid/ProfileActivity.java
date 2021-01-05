package ly.bithive.hsavemeandroid;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ly.bithive.hsavemeandroid.adapter.AppointmentsAdapter;
import ly.bithive.hsavemeandroid.adapter.ProfileTabAdapter;
import ly.bithive.hsavemeandroid.adapter.SpecialtiesAdapter;
import ly.bithive.hsavemeandroid.adapter.TestsAdapter;
import ly.bithive.hsavemeandroid.fragment.AppointmentsFragment;
import ly.bithive.hsavemeandroid.fragment.DevicesFragment;
import ly.bithive.hsavemeandroid.fragment.DoctorsFragment;
import ly.bithive.hsavemeandroid.fragment.SpecialtiesFragment;
import ly.bithive.hsavemeandroid.interf.OnAppointmentsDataReceivedListener;
import ly.bithive.hsavemeandroid.interf.OnDevicesDataReceivedListener;
import ly.bithive.hsavemeandroid.interf.OnDoctorsDataReceivedListener;
import ly.bithive.hsavemeandroid.interf.OnSpecialtiesDataReceivedListener;
import ly.bithive.hsavemeandroid.model.Appointment;
import ly.bithive.hsavemeandroid.model.Clink;
import ly.bithive.hsavemeandroid.model.Device;
import ly.bithive.hsavemeandroid.model.Doctor;
import ly.bithive.hsavemeandroid.model.Specialty;
import ly.bithive.hsavemeandroid.model.Test;

import static ly.bithive.hsavemeandroid.util.COMMON.AP;
import static ly.bithive.hsavemeandroid.util.COMMON.BASE_URL;
import static ly.bithive.hsavemeandroid.util.COMMON.CL;
import static ly.bithive.hsavemeandroid.util.COMMON.DR;
import static ly.bithive.hsavemeandroid.util.COMMON.DV;
import static ly.bithive.hsavemeandroid.util.COMMON.GET_CLINKS_URL;
import static ly.bithive.hsavemeandroid.util.COMMON.SP;
import static ly.bithive.hsavemeandroid.util.COMMON.STORAGE_URL;
import static ly.bithive.hsavemeandroid.util.COMMON.TS;
import static ly.bithive.hsavemeandroid.util.Utils.getClinkItemLink;
import static ly.bithive.hsavemeandroid.util.Utils.getItemLink;


public class ProfileActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    RequestQueue requestQueue;
    List<Doctor> doctorList;
    List<Device> deviceList;
    List<Test> testList;
    List<Specialty> specialityList;
    List<Appointment> appointmentList;

    TextView test,spec,appo,beds;
    public OnDoctorsDataReceivedListener DataListener;

    public void setDataListener(OnDoctorsDataReceivedListener mDataListener) {
        this.DataListener = mDataListener;
    }
    public OnDevicesDataReceivedListener DeviceDataListener;

    public void setDevicesDataListener(OnDevicesDataReceivedListener mDataListener) {
        this.DeviceDataListener = mDataListener;
    }

    public OnSpecialtiesDataReceivedListener specialtiesDataListener;

    public void setSpecialityDataListener(OnSpecialtiesDataReceivedListener mDataListener) {
        this.specialtiesDataListener = mDataListener;
    }

    public OnAppointmentsDataReceivedListener AppointmentDataListener;

    public void setDataListener(OnAppointmentsDataReceivedListener mDataListener) {
        this.AppointmentDataListener = mDataListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        requestQueue = Volley.newRequestQueue(this);
        String Id = getIntent().getStringExtra("CLINIC_ID");

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        test = (TextView) findViewById(R.id.test_btn);
        spec = (TextView) findViewById(R.id.spec_btn);
        appo = (TextView) findViewById(R.id.appo_btn);
        beds = (TextView) findViewById(R.id.beds);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        getClinkProfile(Id);

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialouge(testList,1);
            }
        });
        spec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialouge(specialityList,2);
            }
        });
        appo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialouge(appointmentList,3);
            }
        });
    }

    public void showDialouge(List list,int i ){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View picker_layout = LayoutInflater.from(this).inflate(R.layout.fragment_doctors, null);
        RecyclerView recyclerView = (RecyclerView) picker_layout.findViewById(R.id.RVDoctors);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setClickable(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        builder.setView(picker_layout);
        if(i==1){
            TestsAdapter testsAdapter = new TestsAdapter(list, null);
            recyclerView.setAdapter(testsAdapter);
            testsAdapter.notifyDataSetChanged();
        }else if(i==2){
            SpecialtiesAdapter specialtiesAdapter = new SpecialtiesAdapter(list, null);
            recyclerView.setAdapter(specialtiesAdapter);
            specialtiesAdapter.notifyDataSetChanged();
        }
        else if(i==3){
            AppointmentsAdapter appointmentsAdapter = new AppointmentsAdapter(list, null);
            recyclerView.setAdapter(appointmentsAdapter);
            appointmentsAdapter.notifyDataSetChanged();
        }

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }
    private void setupViewPager(ViewPager viewPager) {
        ProfileTabAdapter adapter = new ProfileTabAdapter(getSupportFragmentManager());

        adapter.addFrag(new DevicesFragment(false), getString(R.string.Devices));
        adapter.addFrag(new DoctorsFragment(false), getString(R.string.Doctors));
        viewPager.setAdapter(adapter);
    }

    private void getClinkProfile(String id) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, GET_CLINKS_URL + "/" + id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    parseData(response);
                    Log.d("XXx", response.toString());
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

    private void parseData(@NonNull JSONObject response) throws JSONException {
        Toast.makeText(ProfileActivity.this, "// Start to parse", Toast.LENGTH_SHORT).show();
        Clink clink = new Clink();
        JSONObject item = response.getJSONObject("data");
        clink.setId(item.getInt("id"))
                .setName(item.getString("name"))
                .setAddress(item.getString("address"))
                .setLatitude(item.getString("latitude"))
                .setLongitude(item.getString("longitude"))
                .setPhone_number(item.getString("phone_number"))
                .setStatus(Boolean.parseBoolean(item.getString("status")))
                .setVisible(Boolean.parseBoolean(item.getString("visible")));

        CircleImageView imageView = (CircleImageView) findViewById(R.id.profile);

       String loader = item.getString("cover");
       String imgUrl = loader.replace("http://saveme.test/","");


       if (item.getInt("beds")==1){
            beds.setText("يوجد اسرة");
       }else {
           beds.setText("لايوجد اسرة");
       }
        Glide.with(this).load(STORAGE_URL+imgUrl).into(imageView);

        TextView tvName = findViewById(R.id.name);
        TextView tvAddress = findViewById(R.id.designation);
        tvName.setText(clink.getName());
        tvAddress.setText(clink.getAddress());

        parseDevices(item.getJSONArray("devices"));
       parseDoctors(item.getJSONArray("doctors"));
        parseSpecialty(item.getJSONArray("specialties"));
        parseAppointments(item.getJSONArray("appointments"));
        parseTests(item.getJSONArray("tests"));

        Toast.makeText(ProfileActivity.this, "// Data Sent", Toast.LENGTH_SHORT).show();
    }

    private void parseTests(JSONArray tests) {
        testList = new ArrayList<>();
        for (int i = 0; i < tests.length(); i++) {
            try {
                JSONObject ts = tests.getJSONObject(i);
                Test test = new Test();
                test.setName(ts.getString("name"));//.setDescription(dv.getString("description"));
                testList.add(test);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private void parseDoctors(JSONArray doctors) {
        doctorList = new ArrayList<>();
        for (int i = 0; i < doctors.length(); i++) {
            try {
                JSONObject dr = doctors.getJSONObject(i);
                Doctor doctor = new Doctor();
                doctor.setName(dr.getString("name")).setSpecialty(dr.getString("specialty_name"));
                doctorList.add(doctor);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        DataListener.onDoctorDataReceived(doctorList);
    }

    private void parseAppointments(JSONArray appointments) {
        appointmentList = new ArrayList<>();
        for (int i = 0; i < appointments.length(); i++) {
            try {
                JSONObject dv = appointments.getJSONObject(i);
                Appointment appointment = new Appointment();
                appointment.setDrName(dv.getString("name")).setaDay(dv.getString("day"))
                        .setStarTime(dv.getString("start_time")).setEndTime(dv.getString("finish_time"));
                appointmentList.add(appointment);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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
        DeviceDataListener.onDeviceDataReceived(deviceList);
    }

    private void parseSpecialty(JSONArray specialties) {
        specialityList = new ArrayList<>();
        for (int i = 0; i < specialties.length(); i++) {
            try {
                JSONObject sp = specialties.getJSONObject(i);
                Specialty specialty = new Specialty();
                specialty.setName(sp.getString("name"));//.setDescription(sp.getString("description"));
                specialityList.add(specialty);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
