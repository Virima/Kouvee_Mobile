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
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.kouvee_mobile.Controller.API_client;
import com.example.kouvee_mobile.Controller.DataHewan_Interface;
import com.example.kouvee_mobile.Controller.TransaksiLayanan_Interface;
import com.example.kouvee_mobile.Model.Customer_Model;
import com.example.kouvee_mobile.Model.Hewan_Model;
import com.example.kouvee_mobile.Model.TransaksiLayanan_Model;
import com.example.kouvee_mobile.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detail_TransaksiLayanan extends AppCompatActivity {
    TransaksiLayanan_Interface apiInterface;

    private EditText pIdTransaksi, pIdHewan, pTanggalTransaksi, pTotalTransaksi, pTglDibuat, pStatusTransaksi,
            pTglDiubah, pIdCustomer;
    private String id_hewan, kode_transaksi, tanggal_transaksi, total_transaksi, status_transaksi,
            tgl_dibuat, tgl_diubah;
    private int id;

    private Button tambahLayananBtn;

    public String sp_NamaPegawai="";
    public String sp_IdTransaksi="";

    private Spinner spinnerHewan;

    private List<String> listSpinnerHewan;

    private Menu action;

    private final static String TAG = "Detail_Transaksi";
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    SharedPreferences sp;
    public static final int mode = Activity.MODE_PRIVATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__transaksi_layanan);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        if(sp!=null)
        {
            sp_NamaPegawai = sp.getString("sp_nama_pegawai", "");
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        pIdTransaksi = findViewById(R.id.KodeTransLyn);
        pIdHewan = findViewById(R.id.NamaHewanJoinTransLyn);
        pIdCustomer = findViewById(R.id.NamaCustomerJoinTransLyn);
        pTanggalTransaksi = findViewById(R.id.TanggalTransPrdk);
        pTotalTransaksi = findViewById(R.id.TotalTransPrdk);
        //pStatusTransaksi = findViewById(R.id.)

        pTglDibuat = findViewById(R.id.tanggal_tambah_trans_prdk);
        pTglDiubah = findViewById(R.id.tanggal_ubah_trans_prdk);
        //pUserLog = findViewById(R.id.user_trans_prdk);

        tambahLayananBtn = findViewById(R.id.btnTambahLayananTransLyn);

        listSpinnerHewan = new ArrayList<>();
        spinnerHewan = findViewById(R.id.spinnerHewanTransLyn);

        Intent intent = getIntent();
        id = intent.getIntExtra("id_transaksi_layanan", 0);
        id_hewan = intent.getStringExtra("id_hewan");
        kode_transaksi = intent.getStringExtra("kode_transaksi_layanan");
        tanggal_transaksi = intent.getStringExtra("tanggal_transaksi_layanan");
        total_transaksi = intent.getStringExtra("total_transaksi_layanan");
        status_transaksi = intent.getStringExtra("status_transaksi_layanan");
        tgl_dibuat = intent.getStringExtra("tanggal_tambah_transaksi_log");
        tgl_diubah = intent.getStringExtra("tanggal_ubah_transaksi_log");
        //user_log = intent.getStringExtra("user_transaksi_log");

        sp_IdTransaksi = String.valueOf(id);
        savePreferenceIdTransaksi(sp_IdTransaksi);

        setDataFromIntentExtra();

        loadSpinnerHewan();

        spinnerHewan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                compareSpinnerHewan();
            }
            public void onNothingSelected(AdapterView<?> parent)
            {}
        });

        tambahLayananBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tambahLayanan = new Intent(Detail_TransaksiLayanan.this,
                        Detail_Layanan_TransaksiLayanan.class);
                startActivity(tambahLayanan);
            }
        });

    }

    private void setDataFromIntentExtra() {
        if (id != 0) {
            readMode();

            FrameLayout layout = (FrameLayout)findViewById(R.id.placeHolderFragmentTransLyn);
            layout.setVisibility(View.VISIBLE);

            pIdTransaksi.setText(kode_transaksi);
            pIdHewan.setText(id_hewan);
            pTanggalTransaksi.setText(tanggal_transaksi);
            pTotalTransaksi.setText(total_transaksi);

            pTglDibuat.setText(tgl_dibuat);
            pTglDiubah.setText(tgl_diubah);
            //pUserLog.setText(user_log);

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.skipMemoryCache(true);
            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
            requestOptions.placeholder(R.drawable.add);
            requestOptions.error(R.drawable.add);

            spinnerHewan.setVisibility(View.GONE);

        } else {
            getSupportActionBar().setTitle("Tambah Transaksi");

            FrameLayout layout = (FrameLayout)findViewById(R.id.placeHolderFragmentTransLyn);
            layout.setVisibility(View.GONE);

            pIdTransaksi.setVisibility(View.GONE);
            pIdHewan.setVisibility(View.GONE);
            pIdCustomer.setVisibility(View.GONE);
            pTotalTransaksi.setVisibility(View.GONE);
            pTglDibuat.setVisibility(View.GONE);
            pTglDiubah.setVisibility(View.GONE);
            //pUserLog.setVisibility(View.GONE);

            tambahLayananBtn.setVisibility(View.GONE);
            pTotalTransaksi.setText("0");
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.transaksi_layanan_detail_menu, menu);
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

                    DatePickerDialog dialog = new DatePickerDialog(Detail_TransaksiLayanan.this,
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
                setUpdateSpinnerHewan();

                pIdHewan.setVisibility(View.GONE);
                spinnerHewan.setVisibility(View.VISIBLE);

                pTanggalTransaksi = (EditText) findViewById(R.id.TanggalTransPrdk); //date
                pTanggalTransaksi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar cal = Calendar.getInstance();
                        int year = cal.get(Calendar.YEAR);
                        int month = cal.get(Calendar.MONTH);
                        int day = cal.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog dialog = new DatePickerDialog(Detail_TransaksiLayanan.this,
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
                            spinnerHewan.getSelectedItemPosition()==0) {
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
                AlertDialog.Builder dialog = new AlertDialog.Builder(Detail_TransaksiLayanan.this);
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

        String id_hewan = pIdHewan.getText().toString().trim();
        String tanggal_transaksi = pTanggalTransaksi.getText().toString().trim();
        String total_transaksi = pTotalTransaksi.getText().toString().trim();

        apiInterface = API_client.getApiClient().create(TransaksiLayanan_Interface.class);
        Call<TransaksiLayanan_Model> call =
                apiInterface.createTransaksiLayanan(
                        key,
                        id_hewan,
                        tanggal_transaksi,
                        "0",
                        "Menunggu Pembayaran",
                        sp_NamaPegawai);

        call.enqueue(new Callback<TransaksiLayanan_Model>() {
            public void onResponse(Call<TransaksiLayanan_Model> call, Response<TransaksiLayanan_Model> response) {
                progressDialog.dismiss();

                Log.i(Detail_TransaksiLayanan.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")) {
                    Toast.makeText(Detail_TransaksiLayanan.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(Detail_TransaksiLayanan.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            public void onFailure(Call<TransaksiLayanan_Model> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Detail_TransaksiLayanan.this, t.getMessage().toString(),
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
        String id_hewan = pIdHewan.getText().toString().trim();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String tgl_ubah_transaksi = simpleDateFormat.format(new Date());

        apiInterface = API_client.getApiClient().create(TransaksiLayanan_Interface.class);

        Call<TransaksiLayanan_Model> call =
                apiInterface.editTransaksiLayanan(
                        key,
                        String.valueOf(id),
                        id_hewan,
                        tanggal_transaksi,
                        total_transaksi,
                        tgl_ubah_transaksi,
                        sp_NamaPegawai);

        call.enqueue(new Callback<TransaksiLayanan_Model>() {
            public void onResponse(Call<TransaksiLayanan_Model> call, Response<TransaksiLayanan_Model> response) {
                progressDialog.dismiss();

                Log.i(Detail_TransaksiLayanan.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")) {
                    Toast.makeText(Detail_TransaksiLayanan.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Detail_TransaksiLayanan.this, message, Toast.LENGTH_SHORT).show();
                }

                Intent back = new Intent(Detail_TransaksiLayanan.this, Activity_TransaksiLayanan.class);
                startActivity(back);
            }

            public void onFailure(Call<TransaksiLayanan_Model> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Detail_TransaksiLayanan.this, t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteData(String key, int id) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Menghapus...");
        progressDialog.show();
        readMode();

        apiInterface = API_client.getApiClient().create(TransaksiLayanan_Interface.class);

        Call<TransaksiLayanan_Model> call = apiInterface.hapusTransaksiLayanan(
                key,
                String.valueOf(id),
                sp_NamaPegawai);

        call.enqueue(new Callback<TransaksiLayanan_Model>() {
            @Override
            public void onResponse(Call<TransaksiLayanan_Model> call, Response<TransaksiLayanan_Model> response) {
                progressDialog.dismiss();

                Log.i(Detail_TransaksiProduk.class.getSimpleName(), response.toString());
                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")) {
                    Toast.makeText(Detail_TransaksiLayanan.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(Detail_TransaksiLayanan.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TransaksiLayanan_Model> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Detail_TransaksiLayanan.this, t.getMessage().toString(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void loadSpinnerHewan()
    {
        DataHewan_Interface apiHewan = API_client.getApiClient().create(DataHewan_Interface.class);
        Call<List<Hewan_Model>> listCall = apiHewan.getHewan();

        listCall.enqueue(new Callback<List<Hewan_Model>>() {
            @Override
            public void onResponse(Call<List<Hewan_Model>> call, Response<List<Hewan_Model>> response) {
                List<Hewan_Model> hewanModels = response.body();
                for(int i=0; i < hewanModels.size(); i++ ){
                    String name = hewanModels.get(i).getNama_hewan();
                    listSpinnerHewan.add(name);
                }
                listSpinnerHewan.add(0,"- PILIH HEWAN -");
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Detail_TransaksiLayanan.this,
                        android.R.layout.simple_spinner_item, listSpinnerHewan);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerHewan.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Hewan_Model>> call, Throwable t) {
                Toast.makeText(Detail_TransaksiLayanan.this, "Cek " + t.getMessage().toString(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void compareSpinnerHewan()
    {
        DataHewan_Interface apiHewan = API_client.getApiClient().create(DataHewan_Interface.class);
        Call<List<Hewan_Model>> listCall = apiHewan.getHewan();

        listCall.enqueue(new Callback<List<Hewan_Model>>() {
            @Override
            public void onResponse(Call<List<Hewan_Model>> call, Response<List<Hewan_Model>> response) {
                List<Hewan_Model> hewanModels = response.body();

                for(int i=0; i < hewanModels.size(); i++ ) {
                    String nama = hewanModels.get(i).getNama_hewan();
                    String temp = spinnerHewan.getSelectedItem().toString();

                    if(temp.equals(nama))
                    {
                        pIdHewan.setText(String.valueOf(hewanModels.get(i).getIdHewan()));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Hewan_Model>> call, Throwable t) {
                Toast.makeText(Detail_TransaksiLayanan.this, t.getMessage().toString(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void editMode() {
        pIdTransaksi.setFocusableInTouchMode(false);
        pIdCustomer.setFocusableInTouchMode(true);
        pIdHewan.setFocusableInTouchMode(false);
        pTotalTransaksi.setFocusableInTouchMode(false);
        pTanggalTransaksi.setFocusableInTouchMode(false);
        pTglDibuat.setFocusableInTouchMode(false);
        pTglDiubah.setFocusableInTouchMode(false);
        //pUserLog.setFocusableInTouchMode(false);
    }

    private void readMode() {
        pIdTransaksi.setFocusableInTouchMode(false);
        pIdCustomer.setFocusableInTouchMode(false);
        pIdHewan.setFocusableInTouchMode(false);
        pTotalTransaksi.setFocusableInTouchMode(false);
        pTanggalTransaksi.setFocusableInTouchMode(false);
        pTglDibuat.setFocusableInTouchMode(false);
        pTglDiubah.setFocusableInTouchMode(false);
        //pUserLog.setFocusableInTouchMode(false);

        alertDisable(pIdHewan);
        alertDisable(pTanggalTransaksi);
    }

    private void alertDisable(EditText editText) {
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Detail_TransaksiLayanan.this,
                        "Klik icon Edit terlebih dahulu untuk mengubah data!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpdateSpinnerHewan()
    {
        DataHewan_Interface apiHewan = API_client.getApiClient().create(DataHewan_Interface.class);
        Call<List<Hewan_Model>> listCall = apiHewan.getHewan();

        listCall.enqueue(new Callback<List<Hewan_Model>>() {
            @Override
            public void onResponse(Call<List<Hewan_Model>> call, Response<List<Hewan_Model>> response) {
                List<Hewan_Model> hewanModels = response.body();

                String editText = pIdHewan.getText().toString();
                for(int i=0; i < hewanModels.size(); i++ ) {
                    String nama = hewanModels.get(i).getNama_hewan();

                    if(editText.equals(nama))
                    {
                        spinnerHewan.setSelection(i+1);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Hewan_Model>> call, Throwable t) {
                Toast.makeText(Detail_TransaksiLayanan.this, "Cek " + t.getMessage().toString(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void savePreferenceIdTransaksi(String string)
    {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("sp_id_transaksi_layanan", string);
        editor.apply();
    }

    protected void onResume() {
        super.onResume();

        //
        Fragment_TransaksiLayanan fragment = new Fragment_TransaksiLayanan();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.placeHolderFragmentTransLyn, fragment);
        fragmentTransaction.commit();
        //
    }
}
