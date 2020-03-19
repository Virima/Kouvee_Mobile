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
import com.example.kouvee_mobile.Controller.Layanan_Interface;

import com.example.kouvee_mobile.Model.Layanan_Model;
import com.example.kouvee_mobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_LihatLayanan extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private Adapter_Layanan layananadapter;
    private List<Layanan_Model> layananList;
    Adapter_Layanan.RecyclerViewLayananClickListener listener;
    Layanan_Interface apiInterface;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__lihat_layanan);

        apiInterface = API_client.getApiClient().create(Layanan_Interface.class);

        progressBar = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //parsing
        listener = new Adapter_Layanan.RecyclerViewLayananClickListener(){
            @Override
            public void onRowClick(View view, int position) {
                Intent intent = new Intent(Activity_LihatLayanan.this, Detail_LihatLayanan.class);
                intent.putExtra("id_layanan", layananList.get(position).getId_layanan());
                intent.putExtra("nama_layanan", layananList.get(position).getNama_layanan());
                intent.putExtra("tanggal_tambah_layanan_log", layananList.get(position).getTanggalTambah());
                intent.putExtra("tanggal_ubah_layanan_log", layananList.get(position).getTanggalUbah());

                startActivity(intent);
            }
        };

        FloatingActionButton fab = findViewById(R.id.tambah_layanan);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_LihatLayanan.this, Detail_LihatLayanan.class));
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.layanan_main_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName())
        );

        searchView.setQueryHint("Cari Layanan");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                layananadapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                layananadapter.getFilter().filter(newText);
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

    public void getLayanan(){
        Call<List<Layanan_Model>> call = apiInterface.getLayanan();
        call.enqueue(new Callback<List<Layanan_Model>>() {
            @Override
            public void onResponse(Call<List<Layanan_Model>> call, Response<List<Layanan_Model>> response) {
                progressBar.setVisibility(View.GONE);
                layananList = response.body();
                Log.i(Activity_LihatLayanan.class.getSimpleName(), response.body().toString());
                layananadapter = new Adapter_Layanan(layananList,  listener);
                recyclerView.setAdapter(layananadapter);
                layananadapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Layanan_Model>> call, Throwable t) {
                Toast.makeText(Activity_LihatLayanan.this, "Rp" + t.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void onResume() {
        super.onResume();
        getLayanan();
    }
}
