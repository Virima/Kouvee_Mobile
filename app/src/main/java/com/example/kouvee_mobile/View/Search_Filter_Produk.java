package com.example.kouvee_mobile.View;

import android.widget.Filter;


import com.example.kouvee_mobile.Model.Produk_Model;

import java.util.ArrayList;

public class Search_Filter_Produk extends Filter{
    Adapter_Produk adapter;
    ArrayList<Produk_Model> filterList;

    public Search_Filter_Produk(ArrayList<Produk_Model> filterList, Adapter_Produk adapter)
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
            ArrayList<Produk_Model> filteredProduk = new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getNama_produk().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredProduk.add(filterList.get(i));
                }
            }
            results.count=filteredProduk.size();
            results.values=filteredProduk;
        }else
        {
            results.count=filterList.size();
            results.values=filterList;
        }
        return results;
    }

    protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
        adapter.produk = (ArrayList<Produk_Model>) results.values;
        //REFRESH
        adapter.notifyDataSetChanged();
    }
}
