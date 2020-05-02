package com.example.kouvee_mobile.View;

import android.widget.Filter;

import com.example.kouvee_mobile.Model.Pengadaan_Model;

import java.util.ArrayList;

public class Search_Filter_Pengadaan extends Filter {
    Adapter_Pengadaan adapter;
    ArrayList<Pengadaan_Model> filterList;

    public Search_Filter_Pengadaan(ArrayList<Pengadaan_Model> filterList, Adapter_Pengadaan adapter)
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
            ArrayList<Pengadaan_Model> filteredPengadaan = new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getKode_pengadaan().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredPengadaan.add(filterList.get(i));
                }
            }
            results.count=filteredPengadaan.size();
            results.values=filteredPengadaan;
        }else
        {
            results.count=filterList.size();
            results.values=filterList;
        }
        return results;
    }

    protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
        adapter.pengadaan = (ArrayList<Pengadaan_Model>) results.values;
        //REFRESH
        adapter.notifyDataSetChanged();
    }
}
