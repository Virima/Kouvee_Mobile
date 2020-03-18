package com.example.kouvee_mobile.View;

import android.widget.Filter;

import com.example.kouvee_mobile.Model.Layanan_Model;

import java.util.ArrayList;

public class Search_Filter_Layanan extends Filter {
    Adapter_Layanan adapter;
    ArrayList<Layanan_Model> filterList;

    public Search_Filter_Layanan(ArrayList<Layanan_Model> filterList, Adapter_Layanan adapter)
    {
        this.adapter=adapter;
        this.filterList=filterList;
    }


    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<Layanan_Model> filteredLayanan=new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getNama_layanan().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredLayanan.add(filterList.get(i));
                }
            }
            results.count=filteredLayanan.size();
            results.values=filteredLayanan;
        }else
        {
            results.count=filterList.size();
            results.values=filterList;
        }
        return results;
    }

    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.layanan = (ArrayList<Layanan_Model>) results.values;
        //REFRESH
        adapter.notifyDataSetChanged();
    }
}
