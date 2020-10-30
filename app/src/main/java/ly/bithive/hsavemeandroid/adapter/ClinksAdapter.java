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
import ly.bithive.hsavemeandroid.model.Clink;
import ly.bithive.hsavemeandroid.model.Doctor;


public class ClinksAdapter extends RecyclerView.Adapter<ClinksAdapter.MyViewHolder> implements Filterable {

    private List<Clink> clinksList;
    private List<Clink> filteredList;
    public SelectedItem selectedItem;
    public ClinksAdapter(List<Clink> mClinksList,SelectedItem mSelectedItem ) {

        this.clinksList = mClinksList;
        this.filteredList = mClinksList;
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
                    List<Clink> result = new ArrayList<>();
                    for (Clink clink : filteredList) {
                        if (clink.getName().toLowerCase().contains(searchChr)) {
                            result.add(clink);
                        }
                    }
                    filterResults.count = result.size();
                    filterResults.values = result;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                clinksList = (List<Clink>) filterResults.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    public interface SelectedItem{
        void selectedItem(Clink clink);
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
                    selectedItem.selectedItem(clinksList.get(getAdapterPosition()));
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
        Clink clink = clinksList.get(position);
        holder.title.setText(clink.getName());
        holder.specialty.setText(clink.getAddress());

        //   holder.year.setText(clink.getYear());
    }

    @Override
    public int getItemCount() {
        return clinksList.size();
    }
}