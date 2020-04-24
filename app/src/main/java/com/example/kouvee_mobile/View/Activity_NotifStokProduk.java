package com.example.kouvee_mobile.View;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kouvee_mobile.Controller.API_client;
import com.example.kouvee_mobile.Controller.Produk_Interface;
import com.example.kouvee_mobile.Model.Produk_Model;
import com.example.kouvee_mobile.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_NotifStokProduk extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private Adapter_NotifStokProduk produkadapter;
    private List<Produk_Model> produkList;
    Adapter_NotifStokProduk.RecyclerViewProdukClickListener listener;
    Produk_Interface apiInterface;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__notif_stok_produk);

        apiInterface = API_client.getApiClient().create(Produk_Interface.class);

        progressBar = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //parsing
        listener = new Adapter_NotifStokProduk.RecyclerViewProdukClickListener(){
            @Override
            public void onRowClick(View view, int position) {
                alertStok(position);
            }
        };
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.notif_stok_produk_menu, menu);

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

    public void getProdukHampirHabis(){
        Call<List<Produk_Model>> call = apiInterface.getProdukHampirHabis();
        call.enqueue(new Callback<List<Produk_Model>>() {
            @Override
            public void onResponse(Call<List<Produk_Model>> call, Response<List<Produk_Model>> response) {
                progressBar.setVisibility(View.GONE);
                produkList = response.body();
                Log.i(Activity_NotifStokProduk.class.getSimpleName(), response.body().toString());
                produkadapter = new Adapter_NotifStokProduk(produkList,  listener);
                recyclerView.setAdapter(produkadapter);
                produkadapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Produk_Model>> call, Throwable t) {
                Toast.makeText(Activity_NotifStokProduk.this, "Rp" + t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void alertStok(int position)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        String namaprdk = produkList.get(position).getNama_produk();
        namaprdk.toUpperCase();

        alert.setTitle(produkList.get(position).getNama_produk());
        alert.setMessage("Tambah Pengadaan baru untuk Produk "+ namaprdk + "?");

        /*
        // Set an EditText view to get user input
        final EditText inputTambahStok = new EditText(this);
        inputTambahStok.setHint("Jumlah Tambah Stok");
        inputTambahStok.setInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setView(inputTambahStok);

        final int id = produkList.get(position).getId_produk();  //di final biar bs kepake di onclick
        final String stokSebelum = produkList.get(position).getStok_produk();
         */

        alert.setPositiveButton("Oke", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                //String stokTambah= inputTambahStok.getText().toString().trim();
                //updateStok(id, stokSebelum, stokTambah);

                Intent i = new Intent(Activity_NotifStokProduk.this, Detail_Pengadaan.class);
                startActivity(i);
            }
        });

        alert.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
    }

    /*
    private void updateStok(final int id, String stokSebelum, String stokTambah) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating...");
        progressDialog.show();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String tgl_ubah_produk_log = simpleDateFormat.format(new Date());
        String stokFinal = String.valueOf(Integer.valueOf(stokSebelum) + Integer.valueOf(stokTambah));

        apiInterface = API_client.getApiClient().create(Produk_Interface.class);

        Call<Produk_Model> call =
                apiInterface.updateStokProduk(String.valueOf(id),
                        stokFinal,
                        tgl_ubah_produk_log);


        call.enqueue(new Callback<Produk_Model>() {
            public void onResponse(Call<Produk_Model> call, Response<Produk_Model> response) {
                progressDialog.dismiss();

                Log.i(Activity_NotifStokProduk.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")) {
                    Toast.makeText(Activity_NotifStokProduk.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Activity_NotifStokProduk.this, message, Toast.LENGTH_SHORT).show();
                }

                Intent back = new Intent(Activity_NotifStokProduk.this, Activity_Produk.class);
                startActivity(back);
            }

            public void onFailure(Call<Produk_Model> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Activity_NotifStokProduk.this, t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

    } */

    protected void onResume() {
        super.onResume();
        getProdukHampirHabis();
    }

}
