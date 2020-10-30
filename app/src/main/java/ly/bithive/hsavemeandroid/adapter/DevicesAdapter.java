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
import ly.bithive.hsavemeandroid.model.Device;
import ly.bithive.hsavemeandroid.model.Doctor;

public class DevicesAdapter extends RecyclerView.Adapter<DevicesAdapter.MyViewHolder> implements Filterable {

    private List<Device> devicesList;
    private List<Device> filteredList;
    public SelectedItem selectedItem;
    public DevicesAdapter(List<Device> mDevicesList, SelectedItem mSelectedItem) {
        this.devicesList = mDevicesList;
        this.filteredList = mDevicesList;
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
                    List<Device> result = new ArrayList<>();
                    for(Device device: filteredList){
                        if (device.getName().toLowerCase().contains(searchChr)){
                            result.add(device);
                        }
                    }
                    filterResults.count = result.size();
                    filterResults.values = result;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                devicesList = (List<Device>) filterResults.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
    public interface SelectedItem{
        void selectedItem(Device device);
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, specialty;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.TVTitle);
            specialty = (TextView) view.findViewById(R.id.TVDescription);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedItem.selectedItem(devicesList.get(getAdapterPosition()));
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
        Device device = devicesList.get(position);
        holder.title.setText(device.getName());
        holder.specialty.setText(device.getDescription());
     //   holder.year.setText(device.getYear());
    }

    @Override
    public int getItemCount() {
        return devicesList.size();
    }
}