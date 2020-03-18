package com.example.kouvee_mobile.View;

import android.widget.Filter;

import com.example.kouvee_mobile.Model.Supplier_Model;

import java.util.ArrayList;

public class Search_Filter_Supplier extends Filter {
    Adapter_Supplier adapter;
    ArrayList<Supplier_Model> filterList;

    public Search_Filter_Supplier(ArrayList<Supplier_Model> filterList, Adapter_Supplier adapter)
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
            ArrayList<Supplier_Model> filteredSupplier=new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getNama_supplier().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredSupplier.add(filterList.get(i));
                }
            }
            results.count=filteredSupplier.size();
            results.values=filteredSupplier;
        }else
        {
            results.count=filterList.size();
            results.values=filterList;
        }
        return results;
    }

    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.suppliers= (ArrayList<Supplier_Model>) results.values;
        //REFRESH
        adapter.notifyDataSetChanged();
    }
}
