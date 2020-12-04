package ly.bithive.hsavemeandroid.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ly.bithive.hsavemeandroid.ProfileActivity;
import ly.bithive.hsavemeandroid.R;
import ly.bithive.hsavemeandroid.adapter.AppointmentsAdapter;
import ly.bithive.hsavemeandroid.adapter.DoctorsAdapter;
import ly.bithive.hsavemeandroid.interf.OnAppointmentsDataReceivedListener;
import ly.bithive.hsavemeandroid.interf.OnDoctorsDataReceivedListener;
import ly.bithive.hsavemeandroid.model.Appointment;
import ly.bithive.hsavemeandroid.model.Doctor;

public class AppointmentsFragment extends Fragment implements OnAppointmentsDataReceivedListener {
    private RecyclerView recyclerView;
    Context mContext;

    public AppointmentsFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProfileActivity mActivity = (ProfileActivity) getActivity();
        mActivity.setDataListener(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_doctors, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.RVDoctors);
        return view;
    }



    @Override
    public void setDataListener(List<Appointment> appointmentList) {
        Log.d("WWWWWWWW", "XX" + appointmentList.size());
        AppointmentsAdapter mAdapter = new AppointmentsAdapter(appointmentList,null);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();


        Log.d("hhhhhhhhhh", "XX" + mAdapter.getItemCount());

    }

//    @Override
//    public void onDeviceDataReceived(List<Device> deviceList) {
//
//    }
}