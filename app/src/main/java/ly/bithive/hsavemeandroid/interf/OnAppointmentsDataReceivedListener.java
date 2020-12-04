package ly.bithive.hsavemeandroid.interf;

import java.util.List;

import ly.bithive.hsavemeandroid.model.Appointment;
import ly.bithive.hsavemeandroid.model.Specialty;


public interface OnAppointmentsDataReceivedListener {
    void setDataListener(List<Appointment> appointmentList);
}
