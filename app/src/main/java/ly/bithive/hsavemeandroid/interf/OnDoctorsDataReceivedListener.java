package ly.bithive.hsavemeandroid.interf;

import java.util.List;

import ly.bithive.hsavemeandroid.model.Doctor;


public interface OnDoctorsDataReceivedListener {
    void onDoctorDataReceived(List<Doctor> doctorList);
}
