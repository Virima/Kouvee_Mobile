package com.example.kouvee_mobile.View;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kouvee_mobile.Controller.API_client;
import com.example.kouvee_mobile.Controller.Pengadaan_Interface;
import com.example.kouvee_mobile.Controller.Produk_Interface;
import com.example.kouvee_mobile.Model.Pengadaan_Model;
import com.example.kouvee_mobile.Model.Produk_Model;
import com.example.kouvee_mobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Pengadaan extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    public String sp_IdPegawai="";

    private Adapter_Pengadaan pengadaanadapter;
    private List<Pengadaan_Model> pengadaanList;
    Adapter_Pengadaan.RecyclerViewPengadaanClickListener listener;
    Pengadaan_Interface apiInterface;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__pengadaan);

        apiInterface = API_client.getApiClient().create(Pengadaan_Interface.class);

        progressBar = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //parsing
        listener = new Adapter_Pengadaan.RecyclerViewPengadaanClickListener(){
            @Override
            public void onRowClick(View view, int position) {
                Intent intent = new Intent(Activity_Pengadaan.this, Detail_Pengadaan.class);
                //intent.putExtra("id_detail_pengadaan", pengadaanList.get(position).getId());
                intent.putExtra("id_pengadaan", pengadaanList.get(position).getId_pengadaan());
                //intent.putExtra("id_produk", pengadaanList.get(position).getId_produk());
                intent.putExtra("id_supplier", pengadaanList.get(position).getId_supplier());
                intent.putExtra("telepon_supplier", pengadaanList.get(position).getTelepon_supplier());
                intent.putExtra("alamat_supplier", pengadaanList.get(position).getAlamat_supplier());
                intent.putExtra("kode_pengadaan", pengadaanList.get(position).getKode_pengadaan());
                intent.putExtra("tanggal_pengadaan", pengadaanList.get(position).getTanggal_pengadaan());
                //intent.putExtra("jumlah_pengadaan", pengadaanList.get(position).getJumlah_pengadaan());
                //intent.putExtra("subtotal_pengadaan", pengadaanList.get(position).getSubtotal_pengadaan());
                intent.putExtra("status_pengadaan", pengadaanList.get(position).getStatus_pengadaan());
                intent.putExtra("total_pengadaan", pengadaanList.get(position).getTotal_pengadaan());
                intent.putExtra("tanggal_tambah_pengadaan_log", pengadaanList.get(position).getTanggalTambah());
                intent.putExtra("tanggal_ubah_pengadaan_log", pengadaanList.get(position).getTanggalUbah());
                intent.putExtra("user_pengadaan_log", pengadaanList.get(position).getUser_pengadaan_log());
                startActivity(intent);
            }
        };

        FloatingActionButton fab = findViewById(R.id.tambah_pengadaan);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_Pengadaan.this, Detail_Pengadaan.class));
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.pengadaan_main_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName())
        );

        searchView.setQueryHint("Cari Pengadaan");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                pengadaanadapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                pengadaanadapter.getFilter().filter(newText);
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

    public void getPengadaan(){
        Call<List<Pengadaan_Model>> call = apiInterface.getPengadaan();
        call.enqueue(new Callback<List<Pengadaan_Model>>() {
            @Override
            public void onResponse(Call<List<Pengadaan_Model>> call, Response<List<Pengadaan_Model>> response) {
                progressBar.setVisibility(View.GONE);
                pengadaanList = response.body();
                Log.i(Activity_Produk.class.getSimpleName(), response.body().toString());
                pengadaanadapter = new Adapter_Pengadaan(pengadaanList,  listener);
                recyclerView.setAdapter(pengadaanadapter);
                pengadaanadapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Pengadaan_Model>> call, Throwable t) {
                Toast.makeText(Activity_Pengadaan.this, "Rp " + t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void onResume() {
        super.onResume();
        getPengadaan();
    }

}
