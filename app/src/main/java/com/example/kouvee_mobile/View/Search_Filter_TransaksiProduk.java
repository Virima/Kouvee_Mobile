package com.example.kouvee_mobile.View;

import android.widget.Filter;

import com.example.kouvee_mobile.Model.TransaksiProduk_Model;

import java.util.ArrayList;

public class Search_Filter_TransaksiProduk extends Filter {
    Adapter_TransaksiProduk adapter;
    ArrayList<TransaksiProduk_Model> filterList;

    public Search_Filter_TransaksiProduk(ArrayList<TransaksiProduk_Model> filterList, Adapter_TransaksiProduk adapter)
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
            ArrayList<TransaksiProduk_Model> filteredTransaksi = new ArrayList<>();

            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getKode_transaksi_produk().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredTransaksi.add(filterList.get(i));
                }
            }
            results.count=filteredTransaksi.size();
            results.values=filteredTransaksi;
        }else
        {
            results.count=filterList.size();
            results.values=filterList;
        }
        return results;
    }

    protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
        adapter.transaksi = (ArrayList<TransaksiProduk_Model>) results.values;
        //REFRESH
        adapter.notifyDataSetChanged();
    }
}
