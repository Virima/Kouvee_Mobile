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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kouvee_mobile.Controller.API_client;
import com.example.kouvee_mobile.Controller.Pengadaan_Interface;
import com.example.kouvee_mobile.Model.Pengadaan_Model;
import com.example.kouvee_mobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_ProdukPengadaan extends AppCompatActivity {
    /*
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private Adapter_Detail_Pengadaan pengadaanadapter;
    private List<Pengadaan_Model> pengadaanList;
    Adapter_Detail_Pengadaan.RecyclerViewDetailPengadaanClickListener listener;
    Pengadaan_Interface apiInterface;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pengadaan);

        apiInterface = API_client.getApiClient().create(Pengadaan_Interface.class);

        progressBar = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //parsing
        listener = new Adapter_Detail_Pengadaan.RecyclerViewDetailPengadaanClickListener(){
            @Override
            public void onRowClick(View view, int position) {
                Intent intent = new Intent(Activity_ProdukPengadaan.this, Detail_ProdukPengadaan.class);
                intent.putExtra("id_detail_pengadaan", pengadaanList.get(position).getId_detail_pengadaan());
                intent.putExtra("id_produk", pengadaanList.get(position).getId_produk());
                intent.putExtra("jumlah_pengadaan", pengadaanList.get(position).getJumlah_pengadaan());
                intent.putExtra("subtotal_pengadaan", pengadaanList.get(position).getSubtotal_pengadaan());
                startActivity(intent);
            }
        };

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

        searchView.setQueryHint("Cari Produk");

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

    public void getProdukPengadaan(){
        Call<List<Pengadaan_Model>> call = apiInterface.getProdukPengadaan();
        call.enqueue(new Callback<List<Pengadaan_Model>>() {
            @Override
            public void onResponse(Call<List<Pengadaan_Model>> call, Response<List<Pengadaan_Model>> response) {
                System.out.println("SHIDDDDDDDDDDDDDDDDDDDDD");
                progressBar.setVisibility(View.GONE);
                pengadaanList = response.body();
                Log.i(Activity_ProdukPengadaan.class.getSimpleName(), response.body().toString());
                pengadaanadapter = new Adapter_Detail_Pengadaan(pengadaanList,  listener);
                recyclerView.setAdapter(pengadaanadapter);
                pengadaanadapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Pengadaan_Model>> call, Throwable t) {
                Toast.makeText(Activity_ProdukPengadaan.this, "Rp " + t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void onResume() {
        super.onResume();
        getProdukPengadaan();
    }

     */
}
