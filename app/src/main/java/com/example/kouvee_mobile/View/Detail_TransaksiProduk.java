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
import android.os.Environment;
import android.preference.PreferenceManager;
import android.print.PrintAttributes;
import android.text.TextUtils;
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
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.kouvee_mobile.Controller.API_client;
import com.example.kouvee_mobile.Controller.Customer_Interface;
import com.example.kouvee_mobile.Controller.Produk_Interface;
import com.example.kouvee_mobile.Controller.TransaksiProduk_Interface;
import com.example.kouvee_mobile.Model.Customer_Model;
import com.example.kouvee_mobile.Model.Produk_Model;
import com.example.kouvee_mobile.Model.TransaksiProduk_Model;
import com.example.kouvee_mobile.R;
import com.uttampanchasara.pdfgenerator.CreatePdf;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detail_TransaksiProduk extends AppCompatActivity {

    TransaksiProduk_Interface apiInterface;

    private EditText pIdTransaksi, pIdProduk, pIdCustomer, pTanggalTransaksi, pTotalTransaksi, pTglDibuat,
            pTglDiubah, pUserLog;
    private String id_customer, kode_transaksi, tanggal_transaksi, total_transaksi, tgl_dibuat, tgl_diubah, user_log;
    private int id;

    private Button tambahProdukBtn;

    public String sp_NamaPegawai="";
    public String sp_IdTransaksi="";

    private Spinner spinnerCustomer;

    private List<String> listSpinnerCustomer;

    private Menu action;

    private final static String TAG = "Detail_Transaksi";
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    SharedPreferences sp;
    public static final int mode = Activity.MODE_PRIVATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__transaksi_produk);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        if(sp!=null)
        {
            sp_NamaPegawai = sp.getString("sp_nama_pegawai", "");
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        pIdTransaksi = findViewById(R.id.KodeTransPrdk);
        pIdCustomer = findViewById(R.id.NamaCustomerJoinTransPrdk);
        pTanggalTransaksi = findViewById(R.id.TanggalTransPrdk);
        pTotalTransaksi = findViewById(R.id.TotalTransPrdk);

        pTglDibuat = findViewById(R.id.tanggal_tambah_trans_prdk);
        pTglDiubah = findViewById(R.id.tanggal_ubah_trans_prdk);
        pUserLog = findViewById(R.id.user_trans_prdk);

        tambahProdukBtn = findViewById(R.id.btnTambahProdukTransPrdk);

        listSpinnerCustomer = new ArrayList<>();
        spinnerCustomer = findViewById(R.id.spinnerCustomerTransPrdk);

        Intent intent = getIntent();
        id = intent.getIntExtra("id_transaksi_produk", 0);
        id_customer = intent.getStringExtra("id_customer");
        kode_transaksi = intent.getStringExtra("kode_transaksi_produk");
        tanggal_transaksi = intent.getStringExtra("tanggal_transaksi_produk");
        total_transaksi = intent.getStringExtra("total_transaksi_produk");
        tgl_dibuat = intent.getStringExtra("tanggal_tambah_transaksi_log");
        tgl_diubah = intent.getStringExtra("tanggal_ubah_transaksi_log");
        user_log = intent.getStringExtra("user_transaksi_log");

        sp_IdTransaksi = String.valueOf(id);
        savePreferenceIdTransaksi(sp_IdTransaksi);

        setDataFromIntentExtra();

        loadSpinnerCustomer();

        spinnerCustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                compareSpinnerCustomer();
            }
            public void onNothingSelected(AdapterView<?> parent)
            {}
        });

        tambahProdukBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tambahProduk = new Intent(Detail_TransaksiProduk.this,
                        Detail_ProdukTransaksiProduk.class);
                startActivity(tambahProduk);
            }
        });

    }

    private void setDataFromIntentExtra() {
        if (id != 0) {
            readMode();

            FrameLayout layout = (FrameLayout)findViewById(R.id.placeHolderFragmentTransPrdk);
            layout.setVisibility(View.VISIBLE);

            pIdTransaksi.setText(kode_transaksi);
            pIdCustomer.setText(id_customer);
            pTanggalTransaksi.setText(tanggal_transaksi);
            pTotalTransaksi.setText(total_transaksi);

            pTglDibuat.setText(tgl_dibuat);
            pTglDiubah.setText(tgl_diubah);
            pUserLog.setText(user_log);

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.skipMemoryCache(true);
            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
            requestOptions.placeholder(R.drawable.add);
            requestOptions.error(R.drawable.add);

            spinnerCustomer.setVisibility(View.GONE);

        } else {
            getSupportActionBar().setTitle("Tambah Transaksi");

            FrameLayout layout = (FrameLayout)findViewById(R.id.placeHolderFragmentTransPrdk);
            layout.setVisibility(View.GONE);

            pIdTransaksi.setVisibility(View.GONE);
            pIdCustomer.setVisibility(View.GONE);
            pTotalTransaksi.setVisibility(View.GONE);
            pTglDibuat.setVisibility(View.GONE);
            pTglDiubah.setVisibility(View.GONE);
            pUserLog.setVisibility(View.GONE);

            tambahProdukBtn.setVisibility(View.GONE);
            pTotalTransaksi.setText("0");
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.transaksi_produk_detail_menu, menu);
        action = menu;
        action.findItem(R.id.menu_save).setVisible(false);

        if (id == 0) {
            editMode();
            pTanggalTransaksi = (EditText) findViewById(R.id.TanggalTransPrdk);
            pTanggalTransaksi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dialog = new DatePickerDialog(Detail_TransaksiProduk.this,
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
                    pTanggalTransaksi.setText(date);
                }
            };

            action.findItem(R.id.menu_edit).setVisible(false);
            action.findItem(R.id.menu_delete).setVisible(false);
            action.findItem(R.id.menu_save).setVisible(true);
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:

                this.finish();
                return true;

            case R.id.menu_edit:
                editMode();
                setUpdateSpinnerCustomer();

                pIdCustomer.setVisibility(View.GONE);
                spinnerCustomer.setVisibility(View.VISIBLE);

                pTanggalTransaksi = (EditText) findViewById(R.id.TanggalTransPrdk); //date
                pTanggalTransaksi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar cal = Calendar.getInstance();
                        int year = cal.get(Calendar.YEAR);
                        int month = cal.get(Calendar.MONTH);
                        int day = cal.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog dialog = new DatePickerDialog(Detail_TransaksiProduk.this,
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
                        pTanggalTransaksi.setText(date);
                    }
                };

                action.findItem(R.id.menu_edit).setVisible(false);
                action.findItem(R.id.menu_delete).setVisible(false);
                action.findItem(R.id.menu_save).setVisible(true);

                return true;

            case R.id.menu_save:
                if (id == 0) {

                    if (TextUtils.isEmpty(pTanggalTransaksi.getText().toString()) ||
                            spinnerCustomer.getSelectedItemPosition()==0) {
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
                        //habis insert, keluar ke recyclerview
                        postData("insert");
                        action.findItem(R.id.menu_edit).setVisible(true);
                        action.findItem(R.id.menu_save).setVisible(false);
                        action.findItem(R.id.menu_delete).setVisible(true);

                        readMode();
                    }
                } else {
                    updateData("update", id);
                    //habis update, keluar ke recyclerview
                    action.findItem(R.id.menu_edit).setVisible(true);
                    action.findItem(R.id.menu_save).setVisible(false);
                    action.findItem(R.id.menu_delete).setVisible(true);

                    readMode();
                }
                return true;

            case R.id.menu_delete:
                AlertDialog.Builder dialog = new AlertDialog.Builder(Detail_TransaksiProduk.this);
                dialog.setMessage("Menghapus Transaksi?");

                dialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deleteData("delete", id);
                    }
                });
                dialog.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
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

        String id_customer = pIdCustomer.getText().toString().trim();
        String tanggal_transaksi = pTanggalTransaksi.getText().toString().trim();
        String total_transaksi = pTotalTransaksi.getText().toString().trim();

        apiInterface = API_client.getApiClient().create(TransaksiProduk_Interface.class);
        Call<TransaksiProduk_Model> call =
                apiInterface.createTransaksiProduk(
                        key,
                        id_customer,
                        tanggal_transaksi,
                        "0",
                        sp_NamaPegawai);

        call.enqueue(new Callback<TransaksiProduk_Model>() {
            public void onResponse(Call<TransaksiProduk_Model> call, Response<TransaksiProduk_Model> response) {
                progressDialog.dismiss();

                Log.i(Detail_TransaksiProduk.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")) {
                    Toast.makeText(Detail_TransaksiProduk.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(Detail_TransaksiProduk.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            public void onFailure(Call<TransaksiProduk_Model> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Detail_TransaksiProduk.this, t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateData(final String key, final int id) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating...");
        progressDialog.show();

        readMode();

        String tanggal_transaksi = pTanggalTransaksi.getText().toString().trim();
        String total_transaksi = pTotalTransaksi.getText().toString().trim();
        String id_customer= pIdCustomer.getText().toString().trim();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String tgl_ubah_transaksi = simpleDateFormat.format(new Date());

        apiInterface = API_client.getApiClient().create(TransaksiProduk_Interface.class);

        Call<TransaksiProduk_Model> call =
                apiInterface.editTransaksiProduk(
                        key,
                        String.valueOf(id),
                        id_customer,
                        tanggal_transaksi,
                        total_transaksi,
                        tgl_ubah_transaksi,
                        sp_NamaPegawai);

        call.enqueue(new Callback<TransaksiProduk_Model>() {
            public void onResponse(Call<TransaksiProduk_Model> call, Response<TransaksiProduk_Model> response) {
                progressDialog.dismiss();

                Log.i(Detail_TransaksiProduk.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")) {
                    Toast.makeText(Detail_TransaksiProduk.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Detail_TransaksiProduk.this, message, Toast.LENGTH_SHORT).show();
                }

                Intent back = new Intent(Detail_TransaksiProduk.this, Activity_TransaksiProduk.class);
                startActivity(back);
            }

            public void onFailure(Call<TransaksiProduk_Model> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Detail_TransaksiProduk.this, t.getMessage().toString(),
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

        Call<TransaksiProduk_Model> call = apiInterface.hapusTransaksiProduk(
                key,
                String.valueOf(id),
                sp_NamaPegawai);

        call.enqueue(new Callback<TransaksiProduk_Model>() {
            @Override
            public void onResponse(Call<TransaksiProduk_Model> call, Response<TransaksiProduk_Model> response) {
                progressDialog.dismiss();

                Log.i(Detail_TransaksiProduk.class.getSimpleName(), response.toString());
                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")) {
                    Toast.makeText(Detail_TransaksiProduk.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(Detail_TransaksiProduk.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TransaksiProduk_Model> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Detail_TransaksiProduk.this, t.getMessage().toString(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void loadSpinnerCustomer()
    {
        Customer_Interface apiCustomer = API_client.getApiClient().create(Customer_Interface.class);
        Call<List<Customer_Model>> listCall = apiCustomer.getCustomer();

        listCall.enqueue(new Callback<List<Customer_Model>>() {
            @Override
            public void onResponse(Call<List<Customer_Model>> call, Response<List<Customer_Model>> response) {
                List<Customer_Model> customerModels = response.body();
                for(int i=0; i < customerModels.size(); i++ ){
                    String name = customerModels.get(i).getNama_customer();
                    listSpinnerCustomer.add(name);
                }
                listSpinnerCustomer.add(0,"- PILIH CUSTOMER -");
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Detail_TransaksiProduk.this,
                        android.R.layout.simple_spinner_item, listSpinnerCustomer);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCustomer.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Customer_Model>> call, Throwable t) {
                Toast.makeText(Detail_TransaksiProduk.this, "Cek " + t.getMessage().toString(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void compareSpinnerCustomer()
    {
        Customer_Interface apiCustomer = API_client.getApiClient().create(Customer_Interface.class);
        Call<List<Customer_Model>> listCall = apiCustomer.getCustomer();

        listCall.enqueue(new Callback<List<Customer_Model>>() {
            @Override
            public void onResponse(Call<List<Customer_Model>> call, Response<List<Customer_Model>> response) {
                List<Customer_Model> customerModels = response.body();

                for(int i=0; i < customerModels.size(); i++ ) {
                    String nama = customerModels.get(i).getNama_customer();
                    String temp = spinnerCustomer.getSelectedItem().toString();

                    if(temp.equals(nama))
                    {
                        pIdCustomer.setText(String.valueOf(customerModels.get(i).getIdCustomer()));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Customer_Model>> call, Throwable t) {
                Toast.makeText(Detail_TransaksiProduk.this, t.getMessage().toString(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void editMode() {
        pIdTransaksi.setFocusableInTouchMode(false);
        pIdCustomer.setFocusableInTouchMode(true);
        pTotalTransaksi.setFocusableInTouchMode(false);
        pTanggalTransaksi.setFocusableInTouchMode(false);
        pTglDibuat.setFocusableInTouchMode(false);
        pTglDiubah.setFocusableInTouchMode(false);
        pUserLog.setFocusableInTouchMode(false);
    }

    private void readMode() {
        pIdTransaksi.setFocusableInTouchMode(false);
        pIdCustomer.setFocusableInTouchMode(false);
        pTotalTransaksi.setFocusableInTouchMode(false);
        pTanggalTransaksi.setFocusableInTouchMode(false);
        pTglDibuat.setFocusableInTouchMode(false);
        pTglDiubah.setFocusableInTouchMode(false);
        pUserLog.setFocusableInTouchMode(false);

        alertDisable(pIdCustomer);
        alertDisable(pTanggalTransaksi);
    }

    private void alertDisable(EditText editText) {
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Detail_TransaksiProduk.this,
                        "Klik icon Edit terlebih dahulu untuk mengubah data!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpdateSpinnerCustomer()
    {
        Customer_Interface apiCustomer = API_client.getApiClient().create(Customer_Interface.class);
        Call<List<Customer_Model>> listCall = apiCustomer.getCustomer();

        listCall.enqueue(new Callback<List<Customer_Model>>() {
            @Override
            public void onResponse(Call<List<Customer_Model>> call, Response<List<Customer_Model>> response) {
                List<Customer_Model> customerModels = response.body();

                String editText = pIdCustomer.getText().toString();
                for(int i=0; i < customerModels.size(); i++ ) {
                    String nama = customerModels.get(i).getNama_customer();

                    if(editText.equals(nama))
                    {
                        spinnerCustomer.setSelection(i+1);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Customer_Model>> call, Throwable t) {
                Toast.makeText(Detail_TransaksiProduk.this, "Cek " + t.getMessage().toString(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void savePreferenceIdTransaksi(String string)
    {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("sp_id_transaksi_produk", string);
        editor.apply();
    }

    protected void onResume() {
        super.onResume();

        //
        Fragment_TransaksiProduk fragment = new Fragment_TransaksiProduk();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.placeHolderFragmentTransPrdk, fragment);
        fragmentTransaction.commit();
        //
    }
}
