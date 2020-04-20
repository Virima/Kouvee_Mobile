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
import com.example.kouvee_mobile.Controller.Jenis_Interface;
import com.example.kouvee_mobile.Model.Jenis_Model;
import com.example.kouvee_mobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_JenisHewan extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private Adapter_Jenis jenisadapter;
    private List<Jenis_Model> jenisList;
    Adapter_Jenis.RecyclerViewJenisClickListener listener;
    Jenis_Interface apiInterface;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__jenis_hewan);

        apiInterface = API_client.getApiClient().create(Jenis_Interface.class);

        progressBar = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //parsing
        listener = new Adapter_Jenis.RecyclerViewJenisClickListener(){
            @Override
            public void onRowClick(View view, int position) {
                Intent intent = new Intent(Activity_JenisHewan.this, Detail_JenisHewan.class);
                intent.putExtra("id_jenis", jenisList.get(position).getId_jenis());
                intent.putExtra("nama_jenis", jenisList.get(position).getNama_jenis());
                intent.putExtra("tanggal_tambah_jenis_log", jenisList.get(position).getTanggalTambah());
                intent.putExtra("tanggal_ubah_jenis_log", jenisList.get(position).getTanggalUbah());
                intent.putExtra("user_jenis_log", jenisList.get(position).getUser_jenis_log());

                startActivity(intent);
            }
        };

        FloatingActionButton fab = findViewById(R.id.tambah_jenis);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_JenisHewan.this, Detail_JenisHewan.class));
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.jenis_main_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName())
        );

        searchView.setQueryHint("Cari Jenis");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                jenisadapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                jenisadapter.getFilter().filter(newText);
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

    public void getJenis(){
        Call<List<Jenis_Model>> call = apiInterface.getJenis();
        call.enqueue(new Callback<List<Jenis_Model>>() {
            @Override
            public void onResponse(Call<List<Jenis_Model>> call, Response<List<Jenis_Model>> response) {
                progressBar.setVisibility(View.GONE);
                jenisList = response.body();
                Log.i(Activity_JenisHewan.class.getSimpleName(), response.body().toString());
                jenisadapter = new Adapter_Jenis(jenisList,  listener);
                recyclerView.setAdapter(jenisadapter);
                jenisadapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Jenis_Model>> call, Throwable t) {
                Toast.makeText(Activity_JenisHewan.this, "Rp" + t.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void onResume() {
        super.onResume();
        getJenis();
    }
}
