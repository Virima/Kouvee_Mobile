package com.example.kouvee_mobile.View;

import android.widget.Filter;

import com.example.kouvee_mobile.Model.Jenis_Model;

import java.util.ArrayList;

public class Search_Filter_Jenis extends Filter {
    Adapter_Jenis adapter;
    ArrayList<Jenis_Model> filterList;

    public Search_Filter_Jenis(ArrayList<Jenis_Model> filterList, Adapter_Jenis adapter)
    {
        this.adapter=adapter;
        this.filterList=filterList;
    }


    protected Filter.FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<Jenis_Model> filteredJenis = new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getNama_jenis().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredJenis.add(filterList.get(i));
                }
            }
            results.count=filteredJenis.size();
            results.values=filteredJenis;
        }else
        {
            results.count=filterList.size();
            results.values=filterList;
        }
        return results;
    }

    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.jenis = (ArrayList<Jenis_Model>) results.values;
        //REFRESH
        adapter.notifyDataSetChanged();
    }
}
