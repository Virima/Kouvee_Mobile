package com.example.kouvee_mobile.View;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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

public class Detail_Pengadaan extends AppCompatActivity {
    private EditText pIdPengadaan, pIdProduk, pIdSupplier, pTanggalPengadaan, pJumlahPengadaan, pSubtotalPengadaan,
            pStatusPengadaan, pTglDibuat, pTglDiubah, pUserLog;
    private String id_pengadaan, id_produk, id_supplier, kode_pengadaan, tanggal_pengadaan, jumlah_pengadaan,
            subtotal_pengadaan, status_pengadaan, tgl_dibuat, tgl_diubah, user_log;
    private int id;

    private int jumlah, harga;

    public String sp_NamaPegawai="";

    private Spinner spinnerProduk;
    private Spinner spinnerSupplier;
    private Spinner spinnerStatusPengadaan;
    private List<String> listSpinnerProduk;
    private List<String> listSpinnerSupplier;
    private List<String> listSpinnerStatusPengadaan;
    private Menu action;

    private final static String TAG = "Detail_Pengadaan";
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private Pengadaan_Interface apiInterface;

    SharedPreferences sp;
    public static final int mode = Activity.MODE_PRIVATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pengadaan);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        pIdPengadaan = findViewById(R.id.KodePengadaan);
        pIdProduk = findViewById(R.id.NamaProdukJoinPengadaan);
        pIdSupplier = findViewById(R.id.NamaSupplierJoinPengadaan);
        pTanggalPengadaan = findViewById(R.id.TanggalPengadaan);
        pJumlahPengadaan = findViewById(R.id.JumlahPengadaan);
        pSubtotalPengadaan = findViewById(R.id.SubtotalPengadaan);
        pStatusPengadaan = findViewById(R.id.StatusPengadaan);
        pJumlahPengadaan.setInputType(InputType.TYPE_CLASS_NUMBER);
        pTglDibuat = findViewById(R.id.tanggal_tambah_pengadaan_log);
        pTglDiubah = findViewById(R.id.tanggal_ubah_pengadaan_log);
        pUserLog = findViewById(R.id.user_pengadaan_log);

        listSpinnerProduk = new ArrayList<>();
        listSpinnerSupplier = new ArrayList<>();
        listSpinnerStatusPengadaan = new ArrayList<>();

        spinnerProduk = findViewById(R.id.spinnerProdukPengadaan);
        spinnerSupplier = findViewById(R.id.spinnerSupplierPengadaan);
        spinnerStatusPengadaan = findViewById(R.id.spinnerStatusPengadaan);

        Intent intent = getIntent();
        id = intent.getIntExtra("id_detail_pengadaan", 0);
        id_pengadaan = intent.getStringExtra("id_pengadaan");
        id_produk = intent.getStringExtra("id_produk");
        id_supplier = intent.getStringExtra("id_supplier");
        kode_pengadaan = intent.getStringExtra("kode_pengadaan");
        tanggal_pengadaan = intent.getStringExtra("tanggal_pengadaan");
        jumlah_pengadaan = intent.getStringExtra("jumlah_pengadaan");
        subtotal_pengadaan = intent.getStringExtra("subtotal_pengadaan");
        status_pengadaan = intent.getStringExtra("status_pengadaan");
        tgl_dibuat = intent.getStringExtra("tanggal_tambah_pengadaan_log");
        tgl_diubah = intent.getStringExtra("tanggal_ubah_pengadaan_log");
        user_log = intent.getStringExtra("user_pengadaan_log");
        setDataFromIntentExtra();

        loadSpinnerProduk();
        loadSpinnerSupplier();
        loadSpinnerStatus();

        spinnerProduk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                compareSpinnerProduk();
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {}
        });

        spinnerSupplier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                compareSpinnerSupplier();
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {}
        });

        spinnerStatusPengadaan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                compareSpinnerStatusPengadaan();
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {}
        });
    }

    private void setDataFromIntentExtra() {
        if (id != 0) {
            readMode();

            pIdPengadaan.setText(kode_pengadaan);
            pIdProduk.setText(id_produk);
            pIdSupplier.setText(id_supplier);
            pTanggalPengadaan.setText(tanggal_pengadaan);
            pJumlahPengadaan.setText(jumlah_pengadaan);
            pSubtotalPengadaan.setText(subtotal_pengadaan);
            pStatusPengadaan.setText(status_pengadaan);
            pTglDibuat.setText(tgl_dibuat);
            pTglDiubah.setText(tgl_diubah);
            pUserLog.setText(user_log);

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.skipMemoryCache(true);
            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
            requestOptions.placeholder(R.drawable.add);
            requestOptions.error(R.drawable.add);

            spinnerProduk.setVisibility(View.GONE);
            spinnerSupplier.setVisibility(View.GONE);
            spinnerStatusPengadaan.setVisibility(View.GONE);

        } else {
            getSupportActionBar().setTitle("Tambah Pengadaan");

            pIdPengadaan.setVisibility(View.GONE);
            setUpdateSubtotal(pJumlahPengadaan);
            pIdProduk.setVisibility(View.GONE);
            pIdSupplier.setVisibility(View.GONE);
            pStatusPengadaan.setVisibility(View.GONE);

            pTglDibuat.setVisibility(View.GONE);
            pTglDiubah.setVisibility(View.GONE);
            pUserLog.setVisibility(View.GONE);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.pengadaan_detail_menu, menu);
        action = menu;
        action.findItem(R.id.menu_save).setVisible(false);

        if (id == 0) {
            editMode();

            pTanggalPengadaan = (EditText) findViewById(R.id.TanggalPengadaan); //date
            pTanggalPengadaan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dialog = new DatePickerDialog(Detail_Pengadaan.this,
                            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                            mDateSetListener, year, month, day);

                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }
            });

            mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    month = month + 1;
                    String date = year + "/" + month + "/" + day;
                    pTanggalPengadaan.setText(date);
                }
            };

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
            sp_NamaPegawai = sp.getString("sp_nama_pegawai", "");
        }

        switch (item.getItemId()) {
            case android.R.id.home:

                this.finish();
                return true;

            case R.id.menu_edit:
                //Edit
                editMode();

                setUpdateSpinnerProduk();
                setUpdateSpinnerSupplier();
                setUpdateSpinnerStatusPengadaan();

                setUpdateSubtotal(pJumlahPengadaan);

                pIdProduk.setVisibility(View.GONE);
                pIdSupplier.setVisibility(View.GONE);
                pStatusPengadaan.setVisibility(View.GONE);

                spinnerProduk.setVisibility(View.VISIBLE);
                spinnerSupplier.setVisibility(View.VISIBLE);
                spinnerStatusPengadaan.setVisibility(View.VISIBLE);

                pTanggalPengadaan = (EditText) findViewById(R.id.TanggalPengadaan); //date
                pTanggalPengadaan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar cal = Calendar.getInstance();
                        int year = cal.get(Calendar.YEAR);
                        int month = cal.get(Calendar.MONTH);
                        int day = cal.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog dialog = new DatePickerDialog(Detail_Pengadaan.this,
                                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                                mDateSetListener, year, month, day);

                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();
                    }
                });

                mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = year + "/" + month + "/" + day;
                        pTanggalPengadaan.setText(date);
                    }
                };

                //InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                //imm.showSoftInput(pNamaHewan, InputMethodManager.SHOW_IMPLICIT);

                action.findItem(R.id.menu_edit).setVisible(false);
                action.findItem(R.id.menu_delete).setVisible(false);
                action.findItem(R.id.menu_save).setVisible(true);

                return true;

            case R.id.menu_save:
                if (id == 0) {
                    //loadPreference();
                    //setForm();

                    if (TextUtils.isEmpty(pTanggalPengadaan.getText().toString()) ||
                            TextUtils.isEmpty(pJumlahPengadaan.getText().toString()) ||
                            spinnerProduk.getSelectedItemPosition()==0 ||
                            spinnerSupplier.getSelectedItemPosition()==0 ||
                            spinnerStatusPengadaan.getSelectedItemPosition()==0) {
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
                AlertDialog.Builder dialog = new AlertDialog.Builder(Detail_Pengadaan.this);
                dialog.setMessage("Menghapus Pengadaan?");

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
        /*
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        month = month + 1;
        String date = year + "-" + month + "-" + day;
        if(day<10)
        {
            date = year + "-" + month + "-" + "0" + day;
        }

        Integer temp = Integer.valueOf(id_pengadaan);
        if(temp < 10)
        {
            id_pengadaan = "00" + id_pengadaan;
        }
        else if(temp >= 10 && temp <100)
        {
            id_pengadaan = "0" + id_pengadaan;
        }

        String kode_pengadaan = "PO" + "-" + date + "-" + id_pengadaan;
         */
        String id_produk = pIdProduk.getText().toString().trim();
        String id_supplier = pIdSupplier.getText().toString().trim();
        String tanggal_pengadaan = pTanggalPengadaan.getText().toString().trim();
        String jumlah_pengadaan = pJumlahPengadaan.getText().toString().trim();
        String subtotal_pengadaan = pSubtotalPengadaan.getText().toString().trim();
        String status_pengadaan = pStatusPengadaan.getText().toString().trim();

        apiInterface = API_client.getApiClient().create(Pengadaan_Interface.class);
        Call<Pengadaan_Model> call =
                apiInterface.createPengadaan(
                        key,
                        id_produk,
                        id_supplier,
                        tanggal_pengadaan,
                        jumlah_pengadaan,
                        subtotal_pengadaan,
                        status_pengadaan,
                        sp_NamaPegawai);

        call.enqueue(new Callback<Pengadaan_Model>() {
            public void onResponse(Call<Pengadaan_Model> call, Response<Pengadaan_Model> response) {
                progressDialog.dismiss();

                Log.i(Detail_Pengadaan.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")) {
                    Toast.makeText(Detail_Pengadaan.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(Detail_Pengadaan.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            public void onFailure(Call<Pengadaan_Model> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Detail_Pengadaan.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateData(final String key, final int id) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating...");
        progressDialog.show();

        readMode();

        String id_produk = pIdProduk.getText().toString().trim();
        String id_supplier = pIdSupplier.getText().toString().trim();
        String tanggal_pengadaan = pTanggalPengadaan.getText().toString().trim();
        String jumlah_pengadaan = pJumlahPengadaan.getText().toString().trim();
        String subtotal_pengadaan = pSubtotalPengadaan.getText().toString().trim();
        String status_pengadaan = pStatusPengadaan.getText().toString().trim();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String tgl_ubah_customer_log = simpleDateFormat.format(new Date());

        apiInterface = API_client.getApiClient().create(Pengadaan_Interface.class);

        Call<Pengadaan_Model> call =
                apiInterface.editPengadaan(
                        key,
                        id_pengadaan,
                        String.valueOf(id),     //id_detail
                        id_produk,
                        id_supplier,
                        kode_pengadaan,
                        tanggal_pengadaan,
                        jumlah_pengadaan,
                        subtotal_pengadaan,
                        status_pengadaan,
                        tgl_ubah_customer_log,
                        sp_NamaPegawai);

        call.enqueue(new Callback<Pengadaan_Model>() {
            public void onResponse(Call<Pengadaan_Model> call, Response<Pengadaan_Model> response) {
                progressDialog.dismiss();

                Log.i(Detail_Pengadaan.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")) {
                    Toast.makeText(Detail_Pengadaan.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Detail_Pengadaan.this, message, Toast.LENGTH_SHORT).show();
                }

                Intent back = new Intent(Detail_Pengadaan.this, Activity_Pengadaan.class);
                startActivity(back);
            }

            public void onFailure(Call<Pengadaan_Model> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Detail_Pengadaan.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void deleteData(String key, int id) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Menghapus...");
        progressDialog.show();
        readMode();

        apiInterface = API_client.getApiClient().create(Pengadaan_Interface.class);

        Call<Pengadaan_Model> call = apiInterface.hapusPengadaan(key, id_pengadaan, String.valueOf(id), sp_NamaPegawai);

        call.enqueue(new Callback<Pengadaan_Model>() {
            @Override
            public void onResponse(Call<Pengadaan_Model> call, Response<Pengadaan_Model> response) {
                progressDialog.dismiss();

                Log.i(Detail_Pengadaan.class.getSimpleName(), response.toString());
                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")) {
                    Toast.makeText(Detail_Pengadaan.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(Detail_Pengadaan.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Pengadaan_Model> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Detail_Pengadaan.this, "Cek " + t.getMessage().toString(), Toast.LENGTH_LONG).show();
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
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Detail_Pengadaan.this,
                        android.R.layout.simple_spinner_item, listSpinnerProduk);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerProduk.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Produk_Model>> call, Throwable t) {
                Toast.makeText(Detail_Pengadaan.this, "Cek " + t.getMessage().toString(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void loadSpinnerSupplier()
    {
        Supplier_Interface apiSupplier = API_client.getApiClient().create(Supplier_Interface.class);
        Call<List<Supplier_Model>> listCall = apiSupplier.getSupplier();

        listCall.enqueue(new Callback<List<Supplier_Model>>() {
            @Override
            public void onResponse(Call<List<Supplier_Model>> call, Response<List<Supplier_Model>> response) {
                List<Supplier_Model> supplierModels = response.body();
                for(int i=0; i < supplierModels.size(); i++ ){
                    String name = supplierModels.get(i).getNama_supplier();
                    listSpinnerSupplier.add(name);
                }
                listSpinnerSupplier.add(0,"- PILIH SUPPLIER -");
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Detail_Pengadaan.this,
                        android.R.layout.simple_spinner_item, listSpinnerSupplier);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSupplier.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Supplier_Model>> call, Throwable t) {
                Toast.makeText(Detail_Pengadaan.this, "Cek " + t.getMessage().toString(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void loadSpinnerStatus()
    {
        listSpinnerStatusPengadaan.add(0,"- PILIH STATUS PENGADAAN -");
        listSpinnerStatusPengadaan.add(1,"Belum Selesai");
        listSpinnerStatusPengadaan.add(2,"Selesai");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Detail_Pengadaan.this,
                android.R.layout.simple_spinner_item, listSpinnerStatusPengadaan);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatusPengadaan.setAdapter(adapter);
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
                Toast.makeText(Detail_Pengadaan.this, "Cek " + t.getMessage().toString(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void compareSpinnerSupplier()
    {
        Supplier_Interface apiSupplier = API_client.getApiClient().create(Supplier_Interface.class);
        Call<List<Supplier_Model>> listCall = apiSupplier.getSupplier();

        listCall.enqueue(new Callback<List<Supplier_Model>>() {
            @Override
            public void onResponse(Call<List<Supplier_Model>> call, Response<List<Supplier_Model>> response) {
                List<Supplier_Model> supplierModels = response.body();

                for(int i=0; i < supplierModels.size(); i++ ) {
                    String nama = supplierModels.get(i).getNama_supplier();
                    String temp = spinnerSupplier.getSelectedItem().toString();

                    if(temp.equals(nama))
                    {
                        pIdSupplier.setText(String.valueOf(supplierModels.get(i).getId_supplier()));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Supplier_Model>> call, Throwable t) {
                Toast.makeText(Detail_Pengadaan.this, "Cek " + t.getMessage().toString(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void compareSpinnerStatusPengadaan()
    {
        String temp = spinnerStatusPengadaan.getSelectedItem().toString();

        if(temp.equals("Selesai"))
        {
            pStatusPengadaan.setText("Selesai");
        }
        else
        {
            pStatusPengadaan.setText("Belum Selesai");
        }
    }

    private void editMode() {
        pIdPengadaan.setFocusableInTouchMode(false);
        pIdProduk.setFocusableInTouchMode(true);
        pIdSupplier.setFocusableInTouchMode(true);
        pStatusPengadaan.setFocusableInTouchMode(true);
        pTanggalPengadaan.setFocusableInTouchMode(false);
        pJumlahPengadaan.setFocusableInTouchMode(true);
        pSubtotalPengadaan.setFocusableInTouchMode(false);
        pTglDibuat.setFocusableInTouchMode(false);
        pTglDiubah.setFocusableInTouchMode(false);
        pUserLog.setFocusableInTouchMode(false);

        alertIsiNamaProdukDulu(pJumlahPengadaan);
    }

    private void readMode() {
        pIdPengadaan.setFocusableInTouchMode(false);
        pIdSupplier.setFocusableInTouchMode(false);
        pIdProduk.setFocusableInTouchMode(false);
        pIdSupplier.setFocusableInTouchMode(false);
        pStatusPengadaan.setFocusableInTouchMode(false);
        pTanggalPengadaan.setFocusableInTouchMode(false);
        pJumlahPengadaan.setFocusableInTouchMode(false);
        pSubtotalPengadaan.setFocusableInTouchMode(false);
        pTglDibuat.setFocusableInTouchMode(false);
        pTglDiubah.setFocusableInTouchMode(false);
        pUserLog.setFocusableInTouchMode(false);

        alertDisable(pIdProduk);
        alertDisable(pIdSupplier);
        alertDisable(pStatusPengadaan);
        alertDisable(pTanggalPengadaan);
        alertDisable(pJumlahPengadaan);
        alertDisable(pSubtotalPengadaan);
    }

    private void alertDisable(EditText editText) {
        if(spinnerProduk.getSelectedItemPosition()==0)
        {
            editText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(Detail_Pengadaan.this,
                            "Klik icon Edit terlebih dahulu untuk mengubah data!",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{}
    }

    private void alertIsiNamaProdukDulu(final EditText editText) {
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spinnerProduk.getSelectedItemPosition()==0)
                {
                    editText.setFocusableInTouchMode(false);
                    Toast.makeText(Detail_Pengadaan.this,
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
                Toast.makeText(Detail_Pengadaan.this, "Cek " + t.getMessage().toString(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setUpdateSpinnerSupplier()
    {
        Supplier_Interface apiSupplier = API_client.getApiClient().create(Supplier_Interface.class);
        Call<List<Supplier_Model>> listCall = apiSupplier.getSupplier();

        listCall.enqueue(new Callback<List<Supplier_Model>>() {
            @Override
            public void onResponse(Call<List<Supplier_Model>> call, Response<List<Supplier_Model>> response) {
                List<Supplier_Model> supplierModels = response.body();

                String editText = pIdSupplier.getText().toString();
                for(int i=0; i < supplierModels.size(); i++ ) {
                    String nama = supplierModels.get(i).getNama_supplier();

                    if(editText.equals(nama))
                    {
                        spinnerSupplier.setSelection(i+1);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Supplier_Model>> call, Throwable t) {
                Toast.makeText(Detail_Pengadaan.this, "Cek " + t.getMessage().toString(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setUpdateSpinnerStatusPengadaan()
    {
        String editText = pStatusPengadaan.getText().toString();
        for(int i=0; i < spinnerStatusPengadaan.getCount(); i++) {

            String nama = spinnerStatusPengadaan.getSelectedItem().toString();

            if(editText.equals(nama))
            {
                spinnerStatusPengadaan.setSelection(i+1);
            }
        }
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
                            Toast.makeText(Detail_Pengadaan.this, "Cek " + t.getMessage().toString(),
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
