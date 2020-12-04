package ly.bithive.hsavemeandroid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ly.bithive.hsavemeandroid.R;
import ly.bithive.hsavemeandroid.model.Appointment;


public class AppointmentsAdapter extends RecyclerView.Adapter<AppointmentsAdapter.MyViewHolder> {

    private List<Appointment> appointmentsList;
    private List<Appointment> filteredList;
    public SelectedItem selectedItem;
    public AppointmentsAdapter(List<Appointment> mAppointmentsList, SelectedItem mSelectedItem ) {

        this.appointmentsList = mAppointmentsList;
        this.filteredList = mAppointmentsList;
        this.selectedItem = mSelectedItem;
    }

//    @Override
//    public Filter getFilter() {
//        Filter filter = new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                FilterResults filterResults = new FilterResults();
//
//                if (charSequence == null || charSequence.length() == 0) {
//                    filterResults.count = filteredList.size();
//                    filterResults.values = filteredList;
//                } else {
//                    String searchChr = charSequence.toString().toLowerCase();
//                    List<Appointment> result = new ArrayList<>();
//                    for (Appointment appointment : filteredList) {
//                        if (appointment.getName().toLowerCase().contains(searchChr)) {
//                            result.add(appointment);
//                        }
//                    }
//                    filterResults.count = result.size();
//                    filterResults.values = result;
//                }
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                appointmentsList = (List<Appointment>) filterResults.values;
//                notifyDataSetChanged();
//            }
//        };
//        return filter;
//    }

    public interface SelectedItem{
        void selectedItem(Appointment appointment);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView drName,sTime,eTime, appDay;

        public MyViewHolder(View view) {
            super(view);
            drName = (TextView) view.findViewById(R.id.dr_name);
            appDay = (TextView) view.findViewById(R.id.app_day);
            sTime = (TextView) view.findViewById(R.id.s_time);
            eTime = (TextView) view.findViewById(R.id.e_time);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedItem.selectedItem(appointmentsList.get(getAdapterPosition()));
                }
            });
            //   year = (TextView) view.findViewById(R.id.year);
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_appointment, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Appointment appointment = appointmentsList.get(position);
        holder.drName.setText(appointment.getDrName());
        holder.appDay.setText(appointment.getaDay());
        holder.sTime.setText(appointment.getStarTime());
        holder.eTime.setText(appointment.getEndTime());

        //   holder.year.setText(appointment.getYear());
    }

    @Override
    public int getItemCount() {
        return appointmentsList.size();
    }
}