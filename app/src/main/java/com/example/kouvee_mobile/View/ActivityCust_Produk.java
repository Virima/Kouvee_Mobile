package com.example.kouvee_mobile.View;

import android.app.PendingIntent;
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
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.kouvee_mobile.Controller.API_client;
import com.example.kouvee_mobile.Controller.Produk_Interface;
import com.example.kouvee_mobile.Model.Produk_Model;
import com.example.kouvee_mobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityCust_Produk extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private AdapterCust_Produk produkadapter;
    private List<Produk_Model> produkList;
    AdapterCust_Produk.RecyclerViewProdukCustomerClickListener listener;
    Produk_Interface apiInterface;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitycust__produk);

        apiInterface = API_client.getApiClient().create(Produk_Interface.class);

        progressBar = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //parsing
        listener = new AdapterCust_Produk.RecyclerViewProdukCustomerClickListener(){
            @Override
            public void onRowClick(View view, int position) {
                Intent intent = new Intent(ActivityCust_Produk.this, ActivityCust_Produk.class);
                intent.putExtra("id_produk", produkList.get(position).getId_produk());
                intent.putExtra("nama_produk", produkList.get(position).getNama_produk());
                intent.putExtra("satuan_produk", produkList.get(position).getSatuan_produk());
                intent.putExtra("stok_produk", produkList.get(position).getStok_produk());
                intent.putExtra("stok_min_produk", produkList.get(position).getStok_min_produk());
                intent.putExtra("harga_produk", produkList.get(position).getHarga_produk());
                intent.putExtra("image_path", produkList.get(position).getImage_path());
                intent.putExtra("tanggal_tambah_produk_log", produkList.get(position).getTanggalTambah());
                intent.putExtra("tanggal_ubah_produk_log", produkList.get(position).getTanggalUbah());
                intent.putExtra("user_produk_log", produkList.get(position).getUser_produk_log());
                //startActivity(intent);
            }
        };
    }


    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.produk_main_menu, menu);

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
                produkadapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                produkadapter.getFilter().filter(newText);
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

    public void getProduk(){
        Call<List<Produk_Model>> call = apiInterface.getProduk();
        call.enqueue(new Callback<List<Produk_Model>>() {
            @Override
            public void onResponse(Call<List<Produk_Model>> call, Response<List<Produk_Model>> response) {
                progressBar.setVisibility(View.GONE);
                produkList = response.body();
                Log.i(ActivityCust_Produk.class.getSimpleName(), response.body().toString());
                produkadapter = new AdapterCust_Produk(produkList,  listener);
                recyclerView.setAdapter(produkadapter);
                produkadapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Produk_Model>> call, Throwable t) {
                Toast.makeText(ActivityCust_Produk.this, "Rp " + t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void onResume() {
        super.onResume();
        getProduk();
    }
}
