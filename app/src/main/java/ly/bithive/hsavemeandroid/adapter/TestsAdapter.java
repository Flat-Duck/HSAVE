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
import ly.bithive.hsavemeandroid.model.Test;

public class TestsAdapter extends RecyclerView.Adapter<TestsAdapter.MyViewHolder> implements Filterable {

    private List<Test> testsList;
    private List<Test> filteredList;
    public SelectedItem selectedItem;

    public TestsAdapter(List<Test> mTestsList, SelectedItem mSelectedItem) {
        this.testsList = mTestsList;
        this.filteredList = mTestsList;
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
                    List<Test> result = new ArrayList<>();
                    for(Test test: filteredList){
                        if (test.getName().toLowerCase().contains(searchChr)){
                            result.add(test);
                        }
                    }
                    filterResults.count = result.size();
                    filterResults.values = result;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                testsList = (List<Test>) filterResults.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
    public interface SelectedItem{
        void selectedItem(Test test);
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
                    selectedItem.selectedItem(testsList.get(getAdapterPosition()));
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
        Test test = testsList.get(position);
        holder.title.setText(test.getName());
     //   holder.specialty.setText(test.getSpecialty());
        //   holder.year.setText(test.getYear());
    }

    @Override
    public int getItemCount() {
        return testsList.size();
    }
}