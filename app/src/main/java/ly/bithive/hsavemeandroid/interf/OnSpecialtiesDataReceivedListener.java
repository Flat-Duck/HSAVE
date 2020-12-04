package ly.bithive.hsavemeandroid.interf;

import java.util.List;

import ly.bithive.hsavemeandroid.model.Doctor;
import ly.bithive.hsavemeandroid.model.Specialty;


public interface OnSpecialtiesDataReceivedListener {
    void onSpecialtyDataReceived(List<Specialty> specialtyList);
}
