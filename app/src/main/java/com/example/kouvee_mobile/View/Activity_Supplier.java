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
import com.example.kouvee_mobile.Controller.Supplier_Interface;
import com.example.kouvee_mobile.Model.Supplier_Model;
import com.example.kouvee_mobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Supplier extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private Adapter_Supplier supplieradapter;
    private List<Supplier_Model> suppliersList;
    Adapter_Supplier.RecyclerViewSupplierClickListener listener;
    Supplier_Interface apiInterface;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__supplier);

        apiInterface = API_client.getApiClient().create(Supplier_Interface.class);

        progressBar = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        listener = new Adapter_Supplier.RecyclerViewSupplierClickListener(){
            @Override
            public void onRowClick(View view, int position) {
                Intent intent = new Intent(Activity_Supplier.this, Detail_Supplier.class);
                intent.putExtra("id", suppliersList.get(position).getId_supplier());
                intent.putExtra("nama_supplier", suppliersList.get(position).getNama_supplier());
                intent.putExtra("alamat_supplier", suppliersList.get(position).getAlamat_supplier());
                intent.putExtra("telepon_supplier", suppliersList.get(position).getTelepon_supplier());
                startActivity(intent);
            }
        };


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_Supplier.this, Detail_Supplier.class));
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.supplier_main_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName())
        );

        searchView.setQueryHint("Mencari Supplier");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                supplieradapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                supplieradapter.getFilter().filter(newText);
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

    public void getSupplier(){
        Call<List<Supplier_Model>> call = apiInterface.getSupplier();
        call.enqueue(new Callback<List<Supplier_Model>>() {
            @Override
            public void onResponse(Call<List<Supplier_Model>> call, Response<List<Supplier_Model>> response) {
                progressBar.setVisibility(View.GONE);
                suppliersList = response.body();
                Log.i(Activity_Supplier.class.getSimpleName(), response.body().toString());
                supplieradapter = new Adapter_Supplier(suppliersList,  listener);
                recyclerView.setAdapter(supplieradapter);
                supplieradapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Supplier_Model>> call, Throwable t) {
                Toast.makeText(Activity_Supplier. this, "Rp" + t.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void onResume() {
        super.onResume();
        getSupplier();
    }
}
