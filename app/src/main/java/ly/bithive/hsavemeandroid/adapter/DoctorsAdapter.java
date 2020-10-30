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
import ly.bithive.hsavemeandroid.model.Doctor;

public class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.MyViewHolder> implements Filterable {

    private List<Doctor> doctorsList;
    private List<Doctor> filteredList;
    public SelectedItem selectedItem;
    public DoctorsAdapter(List<Doctor> mDoctorsList, SelectedItem mSelectedItem) {

        this.doctorsList = mDoctorsList;
        this.filteredList = mDoctorsList;
        this.selectedItem = mSelectedItem;
    }


    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();

                if(charSequence == null || charSequence.length() == 0){
                    filterResults.count = filteredList.size();
                    filterResults.values = filteredList;
                }else {
                    String searchChr = charSequence.toString().toLowerCase();
                    List<Doctor> result = new ArrayList<>();
                    for(Doctor doctor: filteredList){
                        if (doctor.getName().toLowerCase().contains(searchChr)){
                            result.add(doctor);
                        }
                    }
                    filterResults.count = result.size();
                    filterResults.values = result;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                doctorsList = (List<Doctor>) filterResults.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
    public interface SelectedItem{
        void selectedItem(Doctor doctor);
    }
    public  class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, specialty;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.TVTitle);
            specialty = (TextView) view.findViewById(R.id.TVDescription);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedItem.selectedItem(doctorsList.get(getAdapterPosition()));
                }
            });
            //   year = (TextView) view.findViewById(R.id.year);
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_title_description, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Doctor doctor = doctorsList.get(position);
        holder.title.setText(doctor.getName());
        holder.specialty.setText(doctor.getSpecialty());
        //   holder.year.setText(doctor.getYear());
    }

    @Override
    public int getItemCount() {
        return doctorsList.size();
    }
}