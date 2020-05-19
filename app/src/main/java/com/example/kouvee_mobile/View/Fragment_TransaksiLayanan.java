package com.example.kouvee_mobile.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.example.kouvee_mobile.Controller.TransaksiLayanan_Interface;
import com.example.kouvee_mobile.Model.TransaksiLayanan_Model;
import com.example.kouvee_mobile.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_TransaksiLayanan extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private Adapter_Detail_TransaksiLayanan transaksiadapter;
    private List<TransaksiLayanan_Model> transaksiList;
    Adapter_Detail_TransaksiLayanan.RecyclerViewDetailTransaksiClickListener listener;
    TransaksiLayanan_Interface apiInterface;
    ProgressBar progressBar;

    //public String sp_NamaPegawai="";
    public String sp_IdTransaksi="";

    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_transaksi_layanan, container, false);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        final TextView recyclerKosong = (TextView) view.findViewById(R.id.recyclerKosong);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        apiInterface = API_client.getApiClient().create(TransaksiLayanan_Interface.class);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if(sp!=null)
        {
            sp_IdTransaksi = sp.getString("sp_id_transaksi_layanan", "");
        }

        //parsing
        listener = new Adapter_Detail_TransaksiLayanan.RecyclerViewDetailTransaksiClickListener() {
            @Override
            public void onRowClick(View view, int position) {
                Intent intent = new Intent(getActivity(), Detail_Layanan_TransaksiLayanan.class);
                intent.putExtra("id_detail_transaksi", transaksiList.get(position).getId_detail_transaksi());
                intent.putExtra("id_layanan", transaksiList.get(position).getId_layanan());
                intent.putExtra("jumlah_transaksi_layanan", transaksiList.get(position).getJumlah_transaksi_layanan());
                intent.putExtra("subtotal_transaksi_layanan", transaksiList.get(position).getSubtotal_transaksi_layanan());
                startActivity(intent);
            }
        };

        Call<List<TransaksiLayanan_Model>> call = apiInterface.getLayananTransaksiLayanan(sp_IdTransaksi);
        call.enqueue(new Callback<List<TransaksiLayanan_Model>>() {
            @Override
            public void onResponse(Call<List<TransaksiLayanan_Model>> call, Response<List<TransaksiLayanan_Model>> response) {

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);

                transaksiList = response.body();
                //Log.i(Detail_TransaksiLayanan.class.getSimpleName(), response.body().toString());
                transaksiadapter = new Adapter_Detail_TransaksiLayanan(transaksiList,  listener);

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
            public void onFailure(Call<List<TransaksiLayanan_Model>> call, Throwable t) {
                Toast.makeText(getActivity(), "Rp " + t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
