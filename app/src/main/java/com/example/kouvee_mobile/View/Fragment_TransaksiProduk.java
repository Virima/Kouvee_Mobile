package com.example.kouvee_mobile.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kouvee_mobile.Controller.API_client;
import com.example.kouvee_mobile.Controller.TransaksiProduk_Interface;
import com.example.kouvee_mobile.Model.TransaksiProduk_Model;
import com.example.kouvee_mobile.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_TransaksiProduk extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private Adapter_Detail_TransaksiProduk transaksiadapter;
    private List<TransaksiProduk_Model> transaksiList;
    Adapter_Detail_TransaksiProduk.RecyclerViewDetailTransaksiClickListener listener;
    TransaksiProduk_Interface apiInterface;
    ProgressBar progressBar;

    //public String sp_NamaPegawai="";
    public String sp_IdTransaksi="";

    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_transaksi_produk, container, false);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        final TextView recyclerKosong = (TextView) view.findViewById(R.id.recyclerKosong);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        apiInterface = API_client.getApiClient().create(TransaksiProduk_Interface.class);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if(sp!=null)
        {
            sp_IdTransaksi = sp.getString("sp_id_transaksi_produk", "");
        }

        //parsing
        listener = new Adapter_Detail_TransaksiProduk.RecyclerViewDetailTransaksiClickListener() {
            @Override
            public void onRowClick(View view, int position) {
                Intent intent = new Intent(getActivity(), Detail_ProdukTransaksiProduk.class);
                intent.putExtra("id_detail_transaksi", transaksiList.get(position).getId_detail_transaksi());
                intent.putExtra("id_produk", transaksiList.get(position).getId_produk());
                intent.putExtra("jumlah_transaksi_produk", transaksiList.get(position).getJumlah_transaksi_produk());
                intent.putExtra("subtotal_transaksi_produk", transaksiList.get(position).getSubtotal_transaksi_produk());
                startActivity(intent);
            }
        };

        Call<List<TransaksiProduk_Model>> call = apiInterface.getProdukTransaksiProduk(sp_IdTransaksi);
        call.enqueue(new Callback<List<TransaksiProduk_Model>>() {
            @Override
            public void onResponse(Call<List<TransaksiProduk_Model>> call, Response<List<TransaksiProduk_Model>> response) {

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);

                transaksiList = response.body();
                //Log.i(Detail_TransaksiProduk.class.getSimpleName(), response.body().toString());
                transaksiadapter = new Adapter_Detail_TransaksiProduk(transaksiList,  listener);

                if(transaksiadapter.getItemCount() == 0)
                {
                    recyclerKosong.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(transaksiadapter);
                    transaksiadapter.notifyDataSetChanged();
                }
                else
                {
                    recyclerKosong.setVisibility(View.GONE);
                    recyclerView.setAdapter(transaksiadapter);
                    transaksiadapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<TransaksiProduk_Model>> call, Throwable t) {
                Toast.makeText(getActivity(), "Rp " + t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
