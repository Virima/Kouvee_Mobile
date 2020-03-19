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

import com.example.kouvee_mobile.Controller.Ukuran_Interface;
import com.example.kouvee_mobile.Model.Ukuran_Model;
import com.example.kouvee_mobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_UkuranHewan extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private Adapter_Ukuran ukuranadapter;
    private List<Ukuran_Model> ukuranList;
    Adapter_Ukuran.RecyclerViewUkuranClickListener listener;
    Ukuran_Interface apiInterface;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__ukuran_hewan);

        apiInterface = API_client.getApiClient().create(Ukuran_Interface.class);

        progressBar = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //parsing
        listener = new Adapter_Ukuran.RecyclerViewUkuranClickListener(){
            @Override
            public void onRowClick(View view, int position) {
                Intent intent = new Intent(Activity_UkuranHewan.this, Detail_UkuranHewan.class);
                intent.putExtra("id_ukuran", ukuranList.get(position).getId_ukuran());
                intent.putExtra("nama_ukuran", ukuranList.get(position).getNama_ukuran());
                intent.putExtra("tanggal_tambah_ukuran_log", ukuranList.get(position).getTanggalTambah());
                intent.putExtra("tanggal_ubah_ukuran_log", ukuranList.get(position).getTanggalUbah());

                startActivity(intent);
            }
        };

        FloatingActionButton fab = findViewById(R.id.tambah_ukuran);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_UkuranHewan.this, Detail_UkuranHewan.class));
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.ukuran_main_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName())
        );

        searchView.setQueryHint("Cari Ukuran");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                ukuranadapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ukuranadapter.getFilter().filter(newText);
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

    public void getUkuran(){
        Call<List<Ukuran_Model>> call = apiInterface.getUkuran();
        call.enqueue(new Callback<List<Ukuran_Model>>() {
            @Override
            public void onResponse(Call<List<Ukuran_Model>> call, Response<List<Ukuran_Model>> response) {
                progressBar.setVisibility(View.GONE);
                ukuranList = response.body();
                Log.i(Activity_UkuranHewan.class.getSimpleName(), response.body().toString());
                ukuranadapter = new Adapter_Ukuran(ukuranList,  listener);
                recyclerView.setAdapter(ukuranadapter);
                ukuranadapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Ukuran_Model>> call, Throwable t) {
                Toast.makeText(Activity_UkuranHewan.this, "Rp" + t.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void onResume() {
        super.onResume();
        getUkuran();
    }
}
