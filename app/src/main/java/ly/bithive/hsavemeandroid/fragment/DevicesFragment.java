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
import ly.bithive.hsavemeandroid.adapter.DevicesAdapter;
import ly.bithive.hsavemeandroid.interf.OnDevicesDataReceivedListener;
import ly.bithive.hsavemeandroid.model.Device;


/**
 * import android.view.ViewGroup;
 * A simple {@link Fragment} subclass.
 * Use the {@link DoctorsFragment #newInstance} factory method to
 * create an instance of this fragment.
 */
public class DevicesFragment extends Fragment implements OnDevicesDataReceivedListener {
    private RecyclerView recyclerView;
    Context mContext;
    boolean clickable = true;

    public DevicesFragment() {
    }

    public DevicesFragment(boolean mClickable) {
        this.clickable = mClickable;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProfileActivity mActivity = (ProfileActivity) getActivity();
        mActivity.setDevicesDataListener(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctors, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.RVDoctors);
        return view;
    }


    @Override
    public void onDeviceDataReceived(List<Device> deviceList) {

        Log.d("WWWWWWWW", "XX" + deviceList.size());
        DevicesAdapter mAdapter = new DevicesAdapter(deviceList, null);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setClickable(clickable);

        mAdapter.notifyDataSetChanged();

        Log.d("hhhhhhhhhh", "XX" + mAdapter.getItemCount());
    }

}