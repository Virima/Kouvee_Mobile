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
import com.example.kouvee_mobile.Controller.DataHewan_Interface;
import com.example.kouvee_mobile.Model.Hewan_Model;
import com.example.kouvee_mobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_DataHewan extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private Adapter_Hewan hewanadapter;
    private List<Hewan_Model> hewansList;
    Adapter_Hewan.RecyclerViewHewanClickListener listener;
    DataHewan_Interface apiInterface;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__data_hewan);

        apiInterface = API_client.getApiClient().create(DataHewan_Interface.class);

        progressBar = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //parsing
        listener = new Adapter_Hewan.RecyclerViewHewanClickListener(){
            @Override
            public void onRowClick(View view, int position) {
                Intent intent = new Intent(Activity_DataHewan.this, Detail_DataHewan.class);
                intent.putExtra("id_hewan", hewansList.get(position).getIdHewan());
                intent.putExtra("nama_hewan", hewansList.get(position).getNama_hewan());
                intent.putExtra("tgl_lahir_hewan", hewansList.get(position).getTgl_lahir_hewan());
                intent.putExtra("id_jenis", hewansList.get(position).getId_jenis());
                intent.putExtra("id_ukuran", hewansList.get(position).getId_ukuran());
                intent.putExtra("id_customer", hewansList.get(position).getId_customer());
                startActivity(intent);
            }
        };

        FloatingActionButton fab = findViewById(R.id.tambah_hewan);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_DataHewan.this, Detail_DataHewan.class));
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.hewan_main_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName())
        );

        searchView.setQueryHint("Cari Hewan");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                hewanadapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                hewanadapter.getFilter().filter(newText);
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

    public void getHewan(){
        Call<List<Hewan_Model>> call = apiInterface.getHewan();
        call.enqueue(new Callback<List<Hewan_Model>>() {
            @Override
            public void onResponse(Call<List<Hewan_Model>> call, Response<List<Hewan_Model>> response) {
                progressBar.setVisibility(View.GONE);
                hewansList = response.body();
                Log.i(Activity_DataHewan.class.getSimpleName(), response.body().toString());
                hewanadapter = new Adapter_Hewan(hewansList,  listener);
                recyclerView.setAdapter(hewanadapter);
                hewanadapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Hewan_Model>> call, Throwable t) {
                Toast.makeText(Activity_DataHewan.this, "Rp" + t.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void onResume() {
        super.onResume();
        getHewan();
    }
}
