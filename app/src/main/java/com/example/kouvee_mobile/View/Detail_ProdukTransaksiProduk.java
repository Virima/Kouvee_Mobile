package com.example.kouvee_mobile.View;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.kouvee_mobile.Controller.API_client;
import com.example.kouvee_mobile.Controller.Produk_Interface;
import com.example.kouvee_mobile.Controller.TransaksiProduk_Interface;
import com.example.kouvee_mobile.Model.Produk_Model;
import com.example.kouvee_mobile.Model.TransaksiProduk_Model;
import com.example.kouvee_mobile.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detail_ProdukTransaksiProduk extends AppCompatActivity {
    private EditText pIdProduk, pJumlahTransaksi, pSubtotalTransaksi;
    private String id_transaksi ,id_produk, jumlah_transaksi, subtotal_transaksi;
    private int id;

    public String sp_IdTransaksi = "";
    private int jumlah, harga;

    private Spinner spinnerProduk;
    private List<String> listSpinnerProduk;

    private Menu action;

    private TransaksiProduk_Interface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_produk_transaksi);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        pIdProduk = findViewById(R.id.NamaProdukJoinTransaksi);
        pSubtotalTransaksi = findViewById(R.id.SubtotalTransPrdk);
        pJumlahTransaksi = findViewById(R.id.JumlahTransPrdk);
        pJumlahTransaksi.setInputType(InputType.TYPE_CLASS_NUMBER);

        listSpinnerProduk = new ArrayList<>();

        spinnerProduk = findViewById(R.id.spinnerProdukTransaksi);

        Intent intent = getIntent();
        id = intent.getIntExtra("id_detail_transaksi", 0);
        id_transaksi = intent.getStringExtra("id_transaksi");
        id_produk = intent.getStringExtra("id_produk");
        jumlah_transaksi = intent.getStringExtra("jumlah_transaksi_produk");
        subtotal_transaksi = intent.getStringExtra("subtotal_transaksi_produk");

        setDataFromIntentExtra();

        loadSpinnerProduk();

        spinnerProduk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                compareSpinnerProduk();
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {}
        });

    }

    private void setDataFromIntentExtra() {
        if (id != 0) {
            readMode();

            pIdProduk.setText(id_produk);
            pJumlahTransaksi.setText(jumlah_transaksi);
            pSubtotalTransaksi.setText(subtotal_transaksi);

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.skipMemoryCache(true);
            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
            requestOptions.placeholder(R.drawable.add);
            requestOptions.error(R.drawable.add);

            spinnerProduk.setVisibility(View.GONE);

        } else {
            getSupportActionBar().setTitle("Tambah Produk");

            setUpdateSubtotal(pJumlahTransaksi);
            pIdProduk.setVisibility(View.GONE);

        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.transaksi_produk_detail_menu, menu);
        action = menu;
        action.findItem(R.id.menu_save).setVisible(false);

        if (id == 0) {
            editMode();

            action.findItem(R.id.menu_edit).setVisible(false);
            action.findItem(R.id.menu_delete).setVisible(false);
            action.findItem(R.id.menu_save).setVisible(true);
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        if(sp!=null)
        {
            sp_IdTransaksi = sp.getString("sp_id_transaksi_produk", "");
        }

        switch (item.getItemId()) {
            case android.R.id.home:

                this.finish();
                return true;

            case R.id.menu_edit:
                //Edit
                editMode();

                setUpdateSpinnerProduk();

                setUpdateSubtotal(pJumlahTransaksi);

                pIdProduk.setVisibility(View.GONE);
                spinnerProduk.setVisibility(View.VISIBLE);

                action.findItem(R.id.menu_edit).setVisible(false);
                action.findItem(R.id.menu_delete).setVisible(false);
                action.findItem(R.id.menu_save).setVisible(true);

                return true;

            case R.id.menu_save:
                if (id == 0) {
                    //loadPreference();
                    //setForm();

                    if (TextUtils.isEmpty(pJumlahTransaksi.getText().toString()) ||
                            spinnerProduk.getSelectedItemPosition()==0 ) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                        alertDialog.setMessage("Isilah semua field yang tersedia!");
                        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    } else {
                        postData("insert");
                        action.findItem(R.id.menu_edit).setVisible(true);
                        action.findItem(R.id.menu_save).setVisible(false);
                        action.findItem(R.id.menu_delete).setVisible(true);

                        readMode();
                    }
                } else {
                    updateData("update", id);
                    action.findItem(R.id.menu_edit).setVisible(true);
                    action.findItem(R.id.menu_save).setVisible(false);
                    action.findItem(R.id.menu_delete).setVisible(true);

                    readMode();
                }
                return true;

            case R.id.menu_delete:
                AlertDialog.Builder dialog = new AlertDialog.Builder(Detail_ProdukTransaksiProduk.this);
                dialog.setMessage("Menghapus Produk?");

                dialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deleteData("delete", id);
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void postData(final String key) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Menyimpan...");
        progressDialog.show();

        readMode();

        String id_produk = pIdProduk.getText().toString().trim();
        String jumlah_transaksi = pJumlahTransaksi.getText().toString().trim();
        String subtotal_transaksi = pSubtotalTransaksi.getText().toString().trim();

        apiInterface = API_client.getApiClient().create(TransaksiProduk_Interface.class);
        Call<TransaksiProduk_Model> call =
                apiInterface.createProdukTransaksiProduk(
                        key,
                        sp_IdTransaksi,
                        id_produk,
                        jumlah_transaksi,
                        subtotal_transaksi);

        call.enqueue(new Callback<TransaksiProduk_Model>() {
            public void onResponse(Call<TransaksiProduk_Model> call, Response<TransaksiProduk_Model> response) {
                progressDialog.dismiss();

                Log.i(Detail_TransaksiProduk.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")) {
                    Toast.makeText(Detail_ProdukTransaksiProduk.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(Detail_ProdukTransaksiProduk.this, message, Toast.LENGTH_SHORT).show();
                }
                Intent back = new Intent(Detail_ProdukTransaksiProduk.this,
                        Activity_TransaksiProduk.class);
                startActivity(back);
            }

            public void onFailure(Call<TransaksiProduk_Model> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Detail_ProdukTransaksiProduk.this, t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void updateData(final String key, final int id) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating...");
        progressDialog.show();

        readMode();

        String id_produk = pIdProduk.getText().toString().trim();
        String jumlah_transaksi = pJumlahTransaksi.getText().toString().trim();
        String subtotal_edit = pSubtotalTransaksi.getText().toString().trim();

        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //String tgl_ubah_customer_log = simpleDateFormat.format(new Date());

        apiInterface = API_client.getApiClient().create(TransaksiProduk_Interface.class);

        Call<TransaksiProduk_Model> call =
                apiInterface.editProdukTransaksiProduk(
                        key,
                        sp_IdTransaksi,
                        String.valueOf(id),     //id_detail
                        jumlah_transaksi,
                        subtotal_edit,
                        subtotal_transaksi);

        call.enqueue(new Callback<TransaksiProduk_Model>() {
            public void onResponse(Call<TransaksiProduk_Model> call, Response<TransaksiProduk_Model> response) {
                progressDialog.dismiss();

                Log.i(Detail_TransaksiProduk.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")) {
                    Toast.makeText(Detail_ProdukTransaksiProduk.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Detail_ProdukTransaksiProduk.this, message, Toast.LENGTH_SHORT).show();
                }

                Intent back = new Intent(Detail_ProdukTransaksiProduk.this,
                        Activity_TransaksiProduk.class);
                startActivity(back);
            }

            public void onFailure(Call<TransaksiProduk_Model> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Detail_ProdukTransaksiProduk.this, t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void deleteData(String key, int id) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Menghapus...");
        progressDialog.show();
        readMode();

        apiInterface = API_client.getApiClient().create(TransaksiProduk_Interface.class);
        String subtotal_transkasi = pSubtotalTransaksi.getText().toString().trim();

        Call<TransaksiProduk_Model> call = apiInterface.hapusProdukTransaksiProduk(
                key,
                sp_IdTransaksi,
                String.valueOf(id),
                subtotal_transkasi
        );

        call.enqueue(new Callback<TransaksiProduk_Model>() {
            @Override
            public void onResponse(Call<TransaksiProduk_Model> call, Response<TransaksiProduk_Model> response) {
                progressDialog.dismiss();

                Log.i(Detail_TransaksiProduk.class.getSimpleName(), response.toString());
                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")) {
                    Toast.makeText(Detail_ProdukTransaksiProduk.this, message, Toast.LENGTH_SHORT).show();
                    Intent back = new Intent(Detail_ProdukTransaksiProduk.this,
                            Activity_TransaksiProduk.class);
                    startActivity(back);
                    finish();
                } else {
                    Toast.makeText(Detail_ProdukTransaksiProduk.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TransaksiProduk_Model> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Detail_ProdukTransaksiProduk.this, "Cek " +
                        t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }


    public void loadSpinnerProduk()
    {
        Produk_Interface apiProduk = API_client.getApiClient().create(Produk_Interface.class);
        Call<List<Produk_Model>> listCall = apiProduk.getProduk();

        listCall.enqueue(new Callback<List<Produk_Model>>() {
            @Override
            public void onResponse(Call<List<Produk_Model>> call, Response<List<Produk_Model>> response) {
                List<Produk_Model> produkModels = response.body();
                for(int i=0; i < produkModels.size(); i++ ){
                    String name = produkModels.get(i).getNama_produk();
                    listSpinnerProduk.add(name);
                }
                listSpinnerProduk.add(0,"- PILIH PRODUK -");
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Detail_ProdukTransaksiProduk.this,
                        android.R.layout.simple_spinner_item, listSpinnerProduk);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerProduk.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Produk_Model>> call, Throwable t) {
                Toast.makeText(Detail_ProdukTransaksiProduk.this, "Cek " + t.getMessage().toString(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void compareSpinnerProduk()
    {
        Produk_Interface apiProduk = API_client.getApiClient().create(Produk_Interface.class);
        Call<List<Produk_Model>> listCall = apiProduk.getProduk();

        listCall.enqueue(new Callback<List<Produk_Model>>() {
            @Override
            public void onResponse(Call<List<Produk_Model>> call, Response<List<Produk_Model>> response) {
                List<Produk_Model> produkModels = response.body();
                for(int i=0; i < produkModels.size(); i++ ) {
                    String nama = produkModels.get(i).getNama_produk();
                    String temp = spinnerProduk.getSelectedItem().toString();

                    String list_id = String.valueOf(produkModels.get(i).getId_produk());
                    //tempIdJenis.add(list_id);

                    if(temp.equals(nama))
                    {
                        pIdProduk.setText(String.valueOf(produkModels.get(i).getId_produk()));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Produk_Model>> call, Throwable t) {
                Toast.makeText(Detail_ProdukTransaksiProduk.this, "Cek " + t.getMessage().toString(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }


    private void editMode() {
        pIdProduk.setFocusableInTouchMode(true);
        pJumlahTransaksi.setFocusableInTouchMode(true);
        pSubtotalTransaksi.setFocusableInTouchMode(false);

        alertIsiNamaProdukDulu(pJumlahTransaksi);
    }

    private void readMode() {
        pIdProduk.setFocusableInTouchMode(false);
        pJumlahTransaksi.setFocusableInTouchMode(false);
        pSubtotalTransaksi.setFocusableInTouchMode(false);

        alertDisable(pIdProduk);
        alertDisable(pJumlahTransaksi);
        alertDisable(pSubtotalTransaksi);
    }

    private void alertDisable(EditText editText) {
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Detail_ProdukTransaksiProduk.this,
                        "Klik icon Edit terlebih dahulu untuk mengubah data!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void alertIsiNamaProdukDulu(final EditText editText) {
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spinnerProduk.getSelectedItemPosition()==0)
                {
                    editText.setFocusableInTouchMode(false);
                    Toast.makeText(Detail_ProdukTransaksiProduk.this,
                            "Isi Nama Produk terlebih dahulu untuk menentukan Jumlah dan Subtotal!",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    editText.setFocusableInTouchMode(true);
                }

            }
        });
    }

    private void setUpdateSpinnerProduk()
    {
        Produk_Interface apiProduk = API_client.getApiClient().create(Produk_Interface.class);
        Call<List<Produk_Model>> listCall = apiProduk.getProduk();

        listCall.enqueue(new Callback<List<Produk_Model>>() {
            @Override
            public void onResponse(Call<List<Produk_Model>> call, Response<List<Produk_Model>> response) {
                List<Produk_Model> produkModels = response.body();

                String editText = pIdProduk.getText().toString();

                for(int i=0; i < produkModels.size(); i++ ) {
                    String nama = produkModels.get(i).getNama_produk();

                    if(editText.equals(nama))
                    {
                        spinnerProduk.setSelection(i+1);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Produk_Model>> call, Throwable t) {
                Toast.makeText(Detail_ProdukTransaksiProduk.this, "Cek " + t.getMessage().toString(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }


    private void setUpdateSubtotal(EditText et1)
    {
        et1.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0) {

                    Produk_Interface apiProduk = API_client.getApiClient().create(Produk_Interface.class);
                    Call<List<Produk_Model>> listCall = apiProduk.getProduk();

                    listCall.enqueue(new Callback<List<Produk_Model>>() {
                        @Override
                        public void onResponse(Call<List<Produk_Model>> call, Response<List<Produk_Model>> response) {
                            List<Produk_Model> produkModels = response.body();

                            String editText = spinnerProduk.getSelectedItem().toString();
                            System.out.println("TES SPINNER" + editText);

                            for(int i=0; i < produkModels.size(); i++ ) {
                                String nama = produkModels.get(i).getNama_produk();

                                if(editText.equals(nama))
                                {
                                    harga = Integer.valueOf(produkModels.get(i).getHarga_produk());
                                    jumlah = Integer.valueOf(pJumlahTransaksi.getText().toString());
                                    int subtotal = harga * jumlah;
                                    pSubtotalTransaksi.setText(String.valueOf(subtotal));
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Produk_Model>> call, Throwable t) {
                            Toast.makeText(Detail_ProdukTransaksiProduk.this, "Cek " +
                                            t.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                    });

                } else {
                    pSubtotalTransaksi.setText("0");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
}
