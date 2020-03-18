package com.example.kouvee_mobile.View;

import android.widget.Filter;

import com.example.kouvee_mobile.Model.Customer_Model;

import java.util.ArrayList;

public class Search_Filter_Customer extends Filter {
    Adapter_Customer adapter;
    ArrayList<Customer_Model> filterList;

    public Search_Filter_Customer(ArrayList<Customer_Model> filterList, Adapter_Customer adapter)
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
            ArrayList<Customer_Model> filteredCustomer=new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getNama_customer().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredCustomer.add(filterList.get(i));
                }
            }
            results.count=filteredCustomer.size();
            results.values=filteredCustomer;
        }else
        {
            results.count=filterList.size();
            results.values=filterList;
        }
        return results;
    }

    protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
        adapter.customers= (ArrayList<Customer_Model>) results.values;
        //REFRESH
        adapter.notifyDataSetChanged();
    }
}
