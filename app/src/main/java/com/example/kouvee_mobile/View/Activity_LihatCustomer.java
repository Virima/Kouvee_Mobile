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
import com.example.kouvee_mobile.Controller.Customer_Interface;
import com.example.kouvee_mobile.Model.Customer_Model;
import com.example.kouvee_mobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_LihatCustomer extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private Adapter_Customer customeradapter;
    private List<Customer_Model> customersList;
    Adapter_Customer.RecyclerViewCustomerClickListener listener;
    Customer_Interface apiInterface;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__lihat_customer);

        apiInterface = API_client.getApiClient().create(Customer_Interface.class);

        progressBar = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //parsing
        listener = new Adapter_Customer.RecyclerViewCustomerClickListener(){
            @Override
            public void onRowClick(View view, int position) {
                Intent intent = new Intent(Activity_LihatCustomer.this, Detail_LihatCustomer.class);
                intent.putExtra("id_customer", customersList.get(position).getIdCustomer());
                intent.putExtra("nama_customer", customersList.get(position).getNama_customer());
                intent.putExtra("alamat_customer", customersList.get(position).getAlamat_customer());
                intent.putExtra("telepon_customer", customersList.get(position).getTelepon_customer());
                intent.putExtra("tgl_lahir_customer", customersList.get(position).getTgl_Lahir_customer());
                intent.putExtra("tanggal_tambah_customer_log", customersList.get(position).getTanggalTambah());
                intent.putExtra("tanggal_ubah_customer_log", customersList.get(position).getTanggalUbah());
                startActivity(intent);
            }
        };

        FloatingActionButton fab = findViewById(R.id.tambah_customer);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_LihatCustomer.this, Detail_LihatCustomer.class));
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.customer_main_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName())
        );

        searchView.setQueryHint("Cari Customer");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                customeradapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                customeradapter.getFilter().filter(newText);
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

    public void getCustomer(){
        Call<List<Customer_Model>> call = apiInterface.getCustomer();
        call.enqueue(new Callback<List<Customer_Model>>() {
            @Override
            public void onResponse(Call<List<Customer_Model>> call, Response<List<Customer_Model>> response) {
                progressBar.setVisibility(View.GONE);
                customersList = response.body();
                Log.i(Activity_LihatCustomer.class.getSimpleName(), response.body().toString());
                customeradapter = new Adapter_Customer(customersList,  listener);
                recyclerView.setAdapter(customeradapter);
                customeradapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Customer_Model>> call, Throwable t) {
                Toast.makeText(Activity_LihatCustomer. this, "Rp" + t.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void onResume() {
        super.onResume();
        getCustomer();
    }
}
