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
import ly.bithive.hsavemeandroid.model.Specialty;


public class SpecialtiesAdapter extends RecyclerView.Adapter<SpecialtiesAdapter.MyViewHolder> implements Filterable {

    private List<Specialty> specialtiesList;
    private List<Specialty> filteredList;
    public SelectedItem selectedItem;

    public SpecialtiesAdapter(List<Specialty> mSpecialtiesList, SelectedItem mSelectedItem) {

        this.specialtiesList = mSpecialtiesList;
        this.filteredList = mSpecialtiesList;
        this.selectedItem = mSelectedItem;
    }


    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();

                if (charSequence == null || charSequence.length() == 0) {
                    filterResults.count = filteredList.size();
                    filterResults.values = filteredList;
                } else {
                    String searchChr = charSequence.toString().toLowerCase();
                    List<Specialty> result = new ArrayList<>();
                    for (Specialty specialty : filteredList) {
                        if (specialty.getName().toLowerCase().contains(searchChr)) {
                            result.add(specialty);
                        }
                    }
                    filterResults.count = result.size();
                    filterResults.values = result;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                specialtiesList = (List<Specialty>) filterResults.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    public interface SelectedItem {
        void selectedItem(Specialty specialty);
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
                    selectedItem.selectedItem(specialtiesList.get(getAdapterPosition()));
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
        Specialty specialty = specialtiesList.get(position);
        holder.title.setText(specialty.getName());
        // holder.specialty.setText(specialty.getSpecialty());
        //   holder.year.setText(specialty.getYear());
    }

    @Override
    public int getItemCount() {
        return specialtiesList.size();
    }
}