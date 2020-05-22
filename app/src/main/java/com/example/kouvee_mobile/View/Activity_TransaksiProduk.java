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
import com.example.kouvee_mobile.Controller.TransaksiProduk_Interface;
import com.example.kouvee_mobile.Model.TransaksiProduk_Model;
import com.example.kouvee_mobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_TransaksiProduk extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;


    private Adapter_TransaksiProduk transaksiadapter;
    private List<TransaksiProduk_Model> transaksiList;
    Adapter_TransaksiProduk.RecyclerViewTransaksiProdukClickListener listener;
    TransaksiProduk_Interface apiInterface;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__transaksi_produk);

        apiInterface = API_client.getApiClient().create(TransaksiProduk_Interface.class);

        progressBar = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //parsing
        listener = new Adapter_TransaksiProduk.RecyclerViewTransaksiProdukClickListener(){
            @Override
            public void onRowClick(View view, int position) {
                Intent intent = new Intent(Activity_TransaksiProduk.this, Detail_TransaksiProduk.class);
                intent.putExtra("id_transaksi_produk", transaksiList.get(position).getId_transaksi_produk());
                intent.putExtra("id_customer", transaksiList.get(position).getId_customer());
                intent.putExtra("kode_transaksi_produk", transaksiList.get(position).getKode_transaksi_produk());
                intent.putExtra("tanggal_transaksi_produk", transaksiList.get(position).getTanggal_transaksi_produk());
                intent.putExtra("total_transaksi_produk", transaksiList.get(position).getTotal_transaksi_produk());
                intent.putExtra("tanggal_tambah_transaksi_log", transaksiList.get(position).getTanggal_transaksi_produk());
                intent.putExtra("tanggal_ubah_transaksi_log", transaksiList.get(position).getTanggalUbah());
                intent.putExtra("user_transaksi_add", transaksiList.get(position).getUser_transaksi_add());
                intent.putExtra("user_transaksi_edit", transaksiList.get(position).getUser_transaksi_edit());
                startActivity(intent);
            }
        };

        FloatingActionButton fab = findViewById(R.id.tambah_transaksi_produk);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_TransaksiProduk.this, Detail_TransaksiProduk.class));
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.transaksi_produk_main_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);

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
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getTransaksiProduk() {
        Call<List<TransaksiProduk_Model>> call = apiInterface.getTransaksiProduk();
        call.enqueue(new Callback<List<TransaksiProduk_Model>>() {
            @Override
            public void onResponse(Call<List<TransaksiProduk_Model>> call, Response<List<TransaksiProduk_Model>> response) {
                progressBar.setVisibility(View.GONE);
                transaksiList = response.body();
                Log.i(Activity_TransaksiProduk.class.getSimpleName(), response.body().toString());
                transaksiadapter = new Adapter_TransaksiProduk(transaksiList,  listener);
                recyclerView.setAdapter(transaksiadapter);
                transaksiadapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<TransaksiProduk_Model>> call, Throwable t) {
                Toast.makeText(Activity_TransaksiProduk.this, "Rp " + t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void onResume() {
        super.onResume();
        getTransaksiProduk();
    }
}
