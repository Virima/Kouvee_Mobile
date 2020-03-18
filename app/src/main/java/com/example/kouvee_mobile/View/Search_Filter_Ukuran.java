package com.example.kouvee_mobile.View;

import android.widget.Filter;

import com.example.kouvee_mobile.Model.Ukuran_Model;

import java.util.ArrayList;

public class Search_Filter_Ukuran extends Filter {
    Adapter_Ukuran adapter;
    ArrayList<Ukuran_Model> filterList;

    public Search_Filter_Ukuran(ArrayList<Ukuran_Model> filterList, Adapter_Ukuran adapter)
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
            ArrayList<Ukuran_Model> filteredUkuran = new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getNama_ukuran().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredUkuran.add(filterList.get(i));
                }
            }
            results.count=filteredUkuran.size();
            results.values=filteredUkuran;
        }else
        {
            results.count=filterList.size();
            results.values=filterList;
        }
        return results;
    }

    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.ukuran = (ArrayList<Ukuran_Model>) results.values;
        //REFRESH
        adapter.notifyDataSetChanged();
    }
}
