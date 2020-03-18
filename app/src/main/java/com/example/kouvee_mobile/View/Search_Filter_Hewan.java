package com.example.kouvee_mobile.View;

import android.widget.Filter;

import com.example.kouvee_mobile.Model.Hewan_Model;

import java.util.ArrayList;

public class Search_Filter_Hewan extends Filter {
    Adapter_Hewan adapter;
    ArrayList<Hewan_Model> filterList;

    public Search_Filter_Hewan(ArrayList<Hewan_Model> filterList, Adapter_Hewan adapter)
    {
        this.adapter=adapter;
        this.filterList=filterList;
    }


    protected Filter.FilterResults performFiltering(CharSequence constraint) {
        Filter.FilterResults results=new Filter.FilterResults();
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<Hewan_Model> filteredHewan = new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getNama_hewan().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredHewan.add(filterList.get(i));
                }
            }
            results.count=filteredHewan.size();
            results.values=filteredHewan;
        }else
        {
            results.count=filterList.size();
            results.values=filterList;
        }
        return results;
    }

    protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
        adapter.hewans = (ArrayList<Hewan_Model>) results.values;
        //REFRESH
        adapter.notifyDataSetChanged();
    }
}
