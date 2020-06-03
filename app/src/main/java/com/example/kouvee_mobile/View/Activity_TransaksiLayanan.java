package com.example.kouvee_mobile.View;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kouvee_mobile.Controller.API_client;
import com.example.kouvee_mobile.Controller.TransaksiLayanan_Interface;
import com.example.kouvee_mobile.Model.TransaksiLayanan_Model;
import com.example.kouvee_mobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_TransaksiLayanan extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private Adapter_TransaksiLayanan transaksiadapter;
    private List<TransaksiLayanan_Model> transaksiList;
    Adapter_TransaksiLayanan.RecyclerViewTransaksiLayananClickListener listener;
    TransaksiLayanan_Interface apiInterface;
    ProgressBar progressBar;

    private Menu action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__transaksi_layanan);

        apiInterface = API_client.getApiClient().create(TransaksiLayanan_Interface.class);

        progressBar = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //parsing
        listener = new Adapter_TransaksiLayanan.RecyclerViewTransaksiLayananClickListener(){
            @Override
            public void onRowClick(View view, int position) {
                Intent intent = new Intent(Activity_TransaksiLayanan.this, Detail_TransaksiLayanan.class);
                intent.putExtra("id_transaksi_layanan", transaksiList.get(position).getId_transaksi_layanan());
                intent.putExtra("id_hewan", transaksiList.get(position).getId_hewan());
                intent.putExtra("id_customer", transaksiList.get(position).getId_customer());
                intent.putExtra("telepon_customer", transaksiList.get(position).getTelepon_customer());
                intent.putExtra("id_ukuran", transaksiList.get(position).getId_ukuran());
                intent.putExtra("id_jenis", transaksiList.get(position).getId_jenis());
                intent.putExtra("kode_transaksi_layanan", transaksiList.get(position).getKode_transaksi_layanan());
                intent.putExtra("tanggal_transaksi_layanan", transaksiList.get(position).getTanggal_transaksi_layanan());
                intent.putExtra("total_transaksi_layanan", transaksiList.get(position).getTotal_transaksi_layanan());
                intent.putExtra("status_transaksi_layanan", transaksiList.get(position).getStatus_transaksi_layanan());
                intent.putExtra("tanggal_tambah_transaksi_log", transaksiList.get(position).getTanggalTambah());
                intent.putExtra("tanggal_ubah_transaksi_log", transaksiList.get(position).getTanggalUbah());
                intent.putExtra("user_transaksi_add", transaksiList.get(position).getUser_transaksi_add());
                intent.putExtra("user_transaksi_edit", transaksiList.get(position).getUser_transaksi_edit());
                startActivity(intent);
            }
        };

        FloatingActionButton fab = findViewById(R.id.tambah_transaksi_layanan);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_TransaksiLayanan.this, Detail_TransaksiLayanan.class));
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.transaksi_layanan_main_menu, menu);
        action = menu;

        action.findItem(R.id.action_all).setVisible(false);
        action.findItem(R.id.action_filter).setVisible(true);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        MenuItem filterMenuItem = menu.findItem(R.id.action_filter);
        MenuItem ShowAllMenuItem = menu.findItem(R.id.action_all);

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName())
        );

        searchView.setQueryHint("Cari Transaksi");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                transaksiadapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                transaksiadapter.getFilter().filter(newText);
                return false;
            }
        });
        searchMenuItem.getIcon().setVisible(false, false);
        filterMenuItem.getIcon().setVisible(false, false);
        ShowAllMenuItem.getIcon().setVisible(false, false);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.action_filter)
        {
            action.findItem(R.id.action_all).setVisible(true);
            action.findItem(R.id.action_filter).setVisible(false);

            Toast.makeText(Activity_TransaksiLayanan.this, "Menampilkan Filter Transaksi Belum Selesai",
                    Toast.LENGTH_SHORT).show();


            getTransaksiLayananBelumSelesai();
        }
        else if (id == R.id.action_all)
        {
            action.findItem(R.id.action_all).setVisible(false);
            action.findItem(R.id.action_filter).setVisible(true);

            Toast.makeText(Activity_TransaksiLayanan.this, "Menampilkan Semua Transaksi Layanan",
                    Toast.LENGTH_SHORT).show();

            getTransaksiLayanan();
        }
        return super.onOptionsItemSelected(item);
    }

    public void getTransaksiLayanan() {
        Call<List<TransaksiLayanan_Model>> call = apiInterface.getTransaksiLayanan();
        call.enqueue(new Callback<List<TransaksiLayanan_Model>>() {
            @Override
            public void onResponse(Call<List<TransaksiLayanan_Model>> call, Response<List<TransaksiLayanan_Model>> response) {
                progressBar.setVisibility(View.GONE);
                transaksiList = response.body();
                Log.i(Activity_TransaksiLayanan.class.getSimpleName(), response.body().toString());
                transaksiadapter = new Adapter_TransaksiLayanan(transaksiList,  listener);
                recyclerView.setAdapter(transaksiadapter);
                transaksiadapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<TransaksiLayanan_Model>> call, Throwable t) {
                Toast.makeText(Activity_TransaksiLayanan.this, "Rp " + t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getTransaksiLayananBelumSelesai() {
        Call<List<TransaksiLayanan_Model>> call = apiInterface.getTransaksiLayananBelumSelesai();
        call.enqueue(new Callback<List<TransaksiLayanan_Model>>() {
            @Override
            public void onResponse(Call<List<TransaksiLayanan_Model>> call, Response<List<TransaksiLayanan_Model>> response) {
                progressBar.setVisibility(View.GONE);
                transaksiList = response.body();
                Log.i(Activity_TransaksiLayanan.class.getSimpleName(), response.body().toString());
                transaksiadapter = new Adapter_TransaksiLayanan(transaksiList,  listener);
                recyclerView.setAdapter(transaksiadapter);
                transaksiadapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<TransaksiLayanan_Model>> call, Throwable t) {
                Toast.makeText(Activity_TransaksiLayanan.this, "Rp " + t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void onResume() {
        super.onResume();
        getTransaksiLayanan();
    }
}
