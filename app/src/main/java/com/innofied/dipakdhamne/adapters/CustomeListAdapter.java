package com.innofied.dipakdhamne.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.innofied.dipakdhamne.R;
import com.innofied.dipakdhamne.databinding.LayoutLilstItemBinding;
import com.innofied.dipakdhamne.model.DataArray;

import java.util.ArrayList;
import java.util.List;

public class CustomeListAdapter extends RecyclerView.Adapter<CustomeListAdapter.ListItemViewHolder> {

    private Context context;
    private List<DataArray> list;

    public CustomeListAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    @NonNull
    @Override
    public ListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        LayoutLilstItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.layout_lilst_item, parent, false);


        return new ListItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemViewHolder listItemViewHolder, int position) {
        final DataArray item = list.get(position);
        listItemViewHolder.bind(item);

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class ListItemViewHolder extends RecyclerView.ViewHolder {

        public LayoutLilstItemBinding bindingList;

        public ListItemViewHolder(@NonNull LayoutLilstItemBinding itemView) {
            super(itemView.getRoot());
            this.bindingList = itemView;
        }

        public void bind(DataArray item) {
            bindingList.setDataItem(item);
            bindingList.setTextName(item.getFirstName() + " " + item.getLastName());
            bindingList.executePendingBindings();
        }
    }

    public void add(DataArray data) {
        list.add(data);
        notifyItemInserted(list.size() - 1);
    }

    public void addAll(List<DataArray> data) {
        for (DataArray result : data) {
            add(result);
        }
    }

    public void clearRecord() {
        list.clear();
    }
}
