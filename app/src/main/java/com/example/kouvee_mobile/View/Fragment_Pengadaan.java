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
import com.example.kouvee_mobile.Controller.Pengadaan_Interface;
import com.example.kouvee_mobile.Model.Pengadaan_Model;
import com.example.kouvee_mobile.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Pengadaan extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private Adapter_Detail_Pengadaan pengadaanadapter;
    private List<Pengadaan_Model> pengadaanList;
    Adapter_Detail_Pengadaan.RecyclerViewDetailPengadaanClickListener listener;
    Pengadaan_Interface apiInterface;
    ProgressBar progressBar;

    //public String sp_NamaPegawai="";
    public String sp_IdPengadaan="";

    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pengadaan, container, false);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        final TextView recyclerKosong = (TextView) view.findViewById(R.id.recyclerKosong);

        //Adapter_Detail_Pengadaan listAdapter = new Adapter_Detail_Pengadaan();
        //recyclerView.setAdapter(listAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        apiInterface = API_client.getApiClient().create(Pengadaan_Interface.class);

        //progressBar = findViewById(R.id.progress);
        //recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if(sp!=null)
        {
            sp_IdPengadaan = sp.getString("sp_id_pengadaan", "");
        }

        //parsing
        listener = new Adapter_Detail_Pengadaan.RecyclerViewDetailPengadaanClickListener(){
            @Override
            public void onRowClick(View view, int position) {
                Intent intent = new Intent(getActivity(), Detail_ProdukPengadaan.class);
                intent.putExtra("id_detail_pengadaan", pengadaanList.get(position).getId_detail_pengadaan());
                intent.putExtra("id_produk", pengadaanList.get(position).getId_produk());
                intent.putExtra("jumlah_pengadaan", pengadaanList.get(position).getJumlah_pengadaan());
                intent.putExtra("subtotal_pengadaan", pengadaanList.get(position).getSubtotal_pengadaan());
                startActivity(intent);
            }
        };

        Call<List<Pengadaan_Model>> call = apiInterface.getProdukPengadaan(sp_IdPengadaan);
        call.enqueue(new Callback<List<Pengadaan_Model>>() {
            @Override
            public void onResponse(Call<List<Pengadaan_Model>> call, Response<List<Pengadaan_Model>> response) {

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);

                //progressBar.setVisibility(View.GONE);
                pengadaanList = response.body();
                Log.i(Detail_Pengadaan.class.getSimpleName(), response.body().toString());
                pengadaanadapter = new Adapter_Detail_Pengadaan(pengadaanList,  listener);

                if(pengadaanadapter.getItemCount() == 0)
                {
                    recyclerKosong.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(pengadaanadapter);
                    pengadaanadapter.notifyDataSetChanged();
                }
                else
                {
                    recyclerKosong.setVisibility(View.GONE);
                    recyclerView.setAdapter(pengadaanadapter);
                    pengadaanadapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Pengadaan_Model>> call, Throwable t) {
                Toast.makeText(getActivity(), "Rp " + t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    /*
    public void getProdukPengadaan(){
        Call<List<Pengadaan_Model>> call = apiInterface.getProdukPengadaan();
        call.enqueue(new Callback<List<Pengadaan_Model>>() {
            @Override
            public void onResponse(Call<List<Pengadaan_Model>> call, Response<List<Pengadaan_Model>> response) {

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);

                //progressBar.setVisibility(View.GONE);
                pengadaanList = response.body();
                Log.i(Activity_ProdukPengadaan.class.getSimpleName(), response.body().toString());
                pengadaanadapter = new Adapter_Detail_Pengadaan(pengadaanList,  listener);
                recyclerView.setAdapter(pengadaanadapter);
                pengadaanadapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Pengadaan_Model>> call, Throwable t) {
                Toast.makeText(getActivity(), "Rp " + t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
     */

}
