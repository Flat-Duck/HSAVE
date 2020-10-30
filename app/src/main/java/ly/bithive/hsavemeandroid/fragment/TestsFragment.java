package ly.bithive.hsavemeandroid.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import ly.bithive.hsavemeandroid.R;

/**
 * import android.view.ViewGroup;
 * A simple {@link Fragment} subclass.
 * Use the {@link DoctorsFragment #newInstance} factory method to
 * create an instance of this fragment.
 */
public class TestsFragment extends Fragment {

    public TestsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctors, container, false);
    }

}