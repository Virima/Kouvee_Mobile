package com.example.kouvee_mobile.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.kouvee_mobile.Controller.API_client;
import com.example.kouvee_mobile.Controller.Pengadaan_Interface;
import com.example.kouvee_mobile.Controller.Produk_Interface;
import com.example.kouvee_mobile.Controller.Supplier_Interface;
import com.example.kouvee_mobile.Model.Pengadaan_Model;
import com.example.kouvee_mobile.Model.Produk_Model;
import com.example.kouvee_mobile.Model.Supplier_Model;
import com.example.kouvee_mobile.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detail_ProdukPengadaan extends AppCompatActivity {
    private EditText pIdPengadaan, pIdProduk, pJumlahPengadaan, pSubtotalPengadaan, pKode,pTglDibuat, pTglDiubah, pUserLog;
    private String id_pengadaan, id_produk, kode_pengadaan, jumlah_pengadaan, subtotal_pengadaan, tgl_dibuat, tgl_diubah, user_log;
    private int id;

    private Button tambahProdukPengadaanBtn;

    public String sp_IdPengadaan = "";
    private int jumlah, harga;

    private Spinner spinnerProduk;
    private List<String> listSpinnerProduk;

    private Menu action;

    private Pengadaan_Interface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_produk_pengadaan);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //pIdPengadaan = findViewById(R.id.KodePengadaan);
        pIdProduk = findViewById(R.id.NamaProdukJoinPengadaan);
        pSubtotalPengadaan = findViewById(R.id.SubtotalPengadaan);
        pJumlahPengadaan = findViewById(R.id.JumlahPengadaan);
        pJumlahPengadaan.setInputType(InputType.TYPE_CLASS_NUMBER);
        //pTglDibuat = findViewById(R.id.tanggal_tambah_pengadaan_log);
        //pTglDiubah = findViewById(R.id.tanggal_ubah_pengadaan_log);
        //pUserLog = findViewById(R.id.user_pengadaan_log);

        listSpinnerProduk = new ArrayList<>();

        spinnerProduk = findViewById(R.id.spinnerProdukPengadaan);

        Intent intent = getIntent();
        id = intent.getIntExtra("id_detail_pengadaan", 0);
        id_pengadaan = intent.getStringExtra("id_pengadaan");
        id_produk = intent.getStringExtra("id_produk");
        kode_pengadaan = intent.getStringExtra("kode_pengadaan");
        jumlah_pengadaan = intent.getStringExtra("jumlah_pengadaan");
        subtotal_pengadaan = intent.getStringExtra("subtotal_pengadaan");
        //tgl_dibuat = intent.getStringExtra("tanggal_tambah_pengadaan_log");
        //tgl_diubah = intent.getStringExtra("tanggal_ubah_pengadaan_log");
        //user_log = intent.getStringExtra("user_pengadaan_log");
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

            //pIdPengadaan.setText(kode_pengadaan);
            pIdProduk.setText(id_produk);
            pJumlahPengadaan.setText(jumlah_pengadaan);
            pSubtotalPengadaan.setText(subtotal_pengadaan);
            //pTglDibuat.setText(tgl_dibuat);
            //pTglDiubah.setText(tgl_diubah);
            //pUserLog.setText(user_log);

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.skipMemoryCache(true);
            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
            requestOptions.placeholder(R.drawable.add);
            requestOptions.error(R.drawable.add);

            spinnerProduk.setVisibility(View.GONE);

        } else {
            getSupportActionBar().setTitle("Tambah Produk");

            //pIdPengadaan.setVisibility(View.GONE);
            setUpdateSubtotal(pJumlahPengadaan);
            pIdProduk.setVisibility(View.GONE);

            //pTglDibuat.setVisibility(View.GONE);
            //pTglDiubah.setVisibility(View.GONE);
            //pUserLog.setVisibility(View.GONE);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.pengadaan_detail_menu, menu);
        action = menu;
        action.findItem(R.id.menu_save).setVisible(false);

        if (id == 0) {
            editMode();

            action.findItem(R.id.menu_edit).setVisible(false);
            action.findItem(R.id.menu_delete).setVisible(false);
            action.findItem(R.id.menu_save).setVisible(true);
            action.findItem(R.id.menu_print).setVisible(false);
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        if(sp!=null)
        {
            sp_IdPengadaan = sp.getString("sp_id_pengadaan", "");
        }

        switch (item.getItemId()) {
            case android.R.id.home:

                this.finish();
                return true;

            case R.id.menu_edit:
                //Edit
                editMode();

                setUpdateSpinnerProduk();

                setUpdateSubtotal(pJumlahPengadaan);

                pIdProduk.setVisibility(View.GONE);
                spinnerProduk.setVisibility(View.VISIBLE);

                //InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                //imm.showSoftInput(pNamaHewan, InputMethodManager.SHOW_IMPLICIT);

                action.findItem(R.id.menu_edit).setVisible(false);
                action.findItem(R.id.menu_delete).setVisible(false);
                action.findItem(R.id.menu_save).setVisible(true);
                action.findItem(R.id.menu_print).setVisible(false);

                return true;

            case R.id.menu_save:
                if (id == 0) {
                    //loadPreference();
                    //setForm();

                    if (TextUtils.isEmpty(pJumlahPengadaan.getText().toString()) ||
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
                        action.findItem(R.id.menu_print).setVisible(false);

                        readMode();
                    }
                } else {
                    updateData("update", id);
                    action.findItem(R.id.menu_edit).setVisible(true);
                    action.findItem(R.id.menu_save).setVisible(false);
                    action.findItem(R.id.menu_delete).setVisible(true);
                    action.findItem(R.id.menu_print).setVisible(false);

                    readMode();
                }
                return true;

            case R.id.menu_delete:
                AlertDialog.Builder dialog = new AlertDialog.Builder(Detail_ProdukPengadaan.this);
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
        String jumlah_pengadaan = pJumlahPengadaan.getText().toString().trim();
        String subtotal_pengadaan = pSubtotalPengadaan.getText().toString().trim();

        apiInterface = API_client.getApiClient().create(Pengadaan_Interface.class);
        Call<Pengadaan_Model> call =
                apiInterface.createProdukPengadaan(
                        key,
                        sp_IdPengadaan,
                        id_produk,
                        jumlah_pengadaan,
                        subtotal_pengadaan);

        call.enqueue(new Callback<Pengadaan_Model>() {
            public void onResponse(Call<Pengadaan_Model> call, Response<Pengadaan_Model> response) {
                progressDialog.dismiss();

                Log.i(Detail_Pengadaan.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")) {
                    Toast.makeText(Detail_ProdukPengadaan.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(Detail_ProdukPengadaan.this, message, Toast.LENGTH_SHORT).show();
                }
                Intent back = new Intent(Detail_ProdukPengadaan.this, Activity_Pengadaan.class);
                startActivity(back);
            }

            public void onFailure(Call<Pengadaan_Model> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Detail_ProdukPengadaan.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void updateData(final String key, final int id) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating...");
        progressDialog.show();

        readMode();

        String id_produk = pIdProduk.getText().toString().trim();
        String jumlah_pengadaan = pJumlahPengadaan.getText().toString().trim();
        String subtotal_pengadaan = pSubtotalPengadaan.getText().toString().trim();

        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //String tgl_ubah_customer_log = simpleDateFormat.format(new Date());

        apiInterface = API_client.getApiClient().create(Pengadaan_Interface.class);

        Call<Pengadaan_Model> call =
                apiInterface.editProdukPengadaan(
                        key,
                        String.valueOf(id),     //id_detail
                        jumlah_pengadaan,
                        subtotal_pengadaan);

        call.enqueue(new Callback<Pengadaan_Model>() {
            public void onResponse(Call<Pengadaan_Model> call, Response<Pengadaan_Model> response) {
                progressDialog.dismiss();

                Log.i(Detail_Pengadaan.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")) {
                    Toast.makeText(Detail_ProdukPengadaan.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Detail_ProdukPengadaan.this, message, Toast.LENGTH_SHORT).show();
                }

                Intent back = new Intent(Detail_ProdukPengadaan.this, Activity_Pengadaan.class);
                startActivity(back);
            }

            public void onFailure(Call<Pengadaan_Model> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Detail_ProdukPengadaan.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void deleteData(String key, int id) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Menghapus...");
        progressDialog.show();
        readMode();

        apiInterface = API_client.getApiClient().create(Pengadaan_Interface.class);

        Call<Pengadaan_Model> call = apiInterface.hapusProdukPengadaan(
                key,
                String.valueOf(id)
        );

        call.enqueue(new Callback<Pengadaan_Model>() {
            @Override
            public void onResponse(Call<Pengadaan_Model> call, Response<Pengadaan_Model> response) {
                progressDialog.dismiss();

                Log.i(Detail_Pengadaan.class.getSimpleName(), response.toString());
                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")) {
                    Toast.makeText(Detail_ProdukPengadaan.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(Detail_ProdukPengadaan.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Pengadaan_Model> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Detail_ProdukPengadaan.this, "Cek " + t.getMessage().toString(), Toast.LENGTH_LONG).show();
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
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Detail_ProdukPengadaan.this,
                        android.R.layout.simple_spinner_item, listSpinnerProduk);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerProduk.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Produk_Model>> call, Throwable t) {
                Toast.makeText(Detail_ProdukPengadaan.this, "Cek " + t.getMessage().toString(),
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
                Toast.makeText(Detail_ProdukPengadaan.this, "Cek " + t.getMessage().toString(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }


    private void editMode() {
        //pIdPengadaan.setFocusableInTouchMode(false);
        pIdProduk.setFocusableInTouchMode(true);
        pJumlahPengadaan.setFocusableInTouchMode(true);
        pSubtotalPengadaan.setFocusableInTouchMode(false);
        //pTglDibuat.setFocusableInTouchMode(false);
        //pTglDiubah.setFocusableInTouchMode(false);
        //pUserLog.setFocusableInTouchMode(false);

        alertIsiNamaProdukDulu(pJumlahPengadaan);
    }

    private void readMode() {
        //pIdPengadaan.setFocusableInTouchMode(false);
        pIdProduk.setFocusableInTouchMode(false);
        pJumlahPengadaan.setFocusableInTouchMode(false);
        pSubtotalPengadaan.setFocusableInTouchMode(false);
        //pTglDibuat.setFocusableInTouchMode(false);
        //pTglDiubah.setFocusableInTouchMode(false);
        //pUserLog.setFocusableInTouchMode(false);

        alertDisable(pIdProduk);
        alertDisable(pJumlahPengadaan);
        alertDisable(pSubtotalPengadaan);
    }

    private void alertDisable(EditText editText) {
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Detail_ProdukPengadaan.this,
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
                    Toast.makeText(Detail_ProdukPengadaan.this,
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
                Toast.makeText(Detail_ProdukPengadaan.this, "Cek " + t.getMessage().toString(),
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
                                    jumlah = Integer.valueOf(pJumlahPengadaan.getText().toString());
                                    int subtotal = harga * jumlah;
                                    pSubtotalPengadaan.setText(String.valueOf(subtotal));
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Produk_Model>> call, Throwable t) {
                            Toast.makeText(Detail_ProdukPengadaan.this, "Cek " + t.getMessage().toString(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                } else {
                    pSubtotalPengadaan.setText("0");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
}
