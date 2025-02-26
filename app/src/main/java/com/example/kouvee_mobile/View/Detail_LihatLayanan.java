package com.example.kouvee_mobile.View;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.kouvee_mobile.Controller.API_client;

import com.example.kouvee_mobile.Controller.Layanan_Interface;
import com.example.kouvee_mobile.Model.Layanan_Model;
import com.example.kouvee_mobile.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detail_LihatLayanan extends AppCompatActivity {
    private EditText pNamaLayanan, pHargaLayanan, pTglDibuat, pTglDiubah, pUserLog;
    private TextView pIdLayanan;
    private String id_layanan, nama_layanan, harga_layanan, tanggal_dibuat, tanggal_diubah, user_log;
    private int id;

    private Menu action;

    public String sp_NamaPegawai="";

    private final static String TAG = "Detail_LihatLayanan";
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private Layanan_Interface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__lihat_layanan);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //pIdLayanan = findViewById(R.id.IdLayananTxt);
        pNamaLayanan = findViewById(R.id.NamaLayanan);
        pHargaLayanan = findViewById(R.id.HargaLayanan);
        pTglDibuat = findViewById(R.id.tanggal_tambah_layanan_log);
        pTglDiubah = findViewById(R.id.tanggal_ubah_layanan_log);
        pUserLog = findViewById(R.id.user_layanan_log);

        Intent intent = getIntent();
        id = intent.getIntExtra("id_layanan", 0);
        id_layanan = intent.getStringExtra("id_layanan");
        nama_layanan = intent.getStringExtra("nama_layanan");
        harga_layanan = intent.getStringExtra("harga_layanan");
        tanggal_dibuat = intent.getStringExtra("tanggal_tambah_layanan_log");
        tanggal_diubah = intent.getStringExtra("tanggal_ubah_layanan_log");
        user_log = intent.getStringExtra("user_layanan_log");

        setDataFromIntentExtra();
    }

    private void setDataFromIntentExtra() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        if(sp!=null)
        {
            sp_NamaPegawai = sp.getString("sp_nama_pegawai", "");
        }

        if (id != 0) {
            readMode();
            getSupportActionBar().setTitle(nama_layanan.toString());     //Header

            //pIdLayanan.setText(id_layanan);
            pNamaLayanan.setText(nama_layanan);
            pHargaLayanan.setText(harga_layanan);
            pTglDibuat.setText(tanggal_dibuat);
            pTglDiubah.setText(tanggal_diubah);
            pUserLog.setText(user_log);

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.skipMemoryCache(true);
            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
            requestOptions.placeholder(R.drawable.add);
            requestOptions.error(R.drawable.add);

        }else {
            getSupportActionBar().setTitle("Tambah Layanan");
            pTglDibuat.setVisibility(View.GONE);
            pTglDiubah.setVisibility(View.GONE);
            pUserLog.setVisibility(View.GONE);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.layanan_detail_menu, menu);
        action = menu;
        action.findItem(R.id.menu_save).setVisible(false);

        if (id == 0){

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
                //Edit

                editMode();

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(pNamaLayanan, InputMethodManager.SHOW_IMPLICIT);

                action.findItem(R.id.menu_edit).setVisible(false);
                action.findItem(R.id.menu_delete).setVisible(false);
                action.findItem(R.id.menu_save).setVisible(true);

                return true;

            case R.id.menu_save:

                if (id == 0 || id==1) {
                    if (TextUtils.isEmpty(pNamaLayanan.getText().toString()) ||
                        TextUtils.isEmpty(pHargaLayanan.getText().toString()) )   {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                        alertDialog.setMessage("Isilah semua field yang tersedia!");
                        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    }
                    else {
                        postData("insert");
                        action.findItem(R.id.menu_edit).setVisible(true);
                        action.findItem(R.id.menu_save).setVisible(false);
                        action.findItem(R.id.menu_delete).setVisible(true);

                        readMode();
                    }
                }
                else {
                    updateData("update", id);
                    action.findItem(R.id.menu_edit).setVisible(true);
                    action.findItem(R.id.menu_save).setVisible(false);
                    action.findItem(R.id.menu_delete).setVisible(true);

                    readMode();
                }
                return true;

            case R.id.menu_delete:
                AlertDialog.Builder dialog = new AlertDialog.Builder(Detail_LihatLayanan.this);
                dialog.setMessage("Menghapus Layanaan?");

                dialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deleteData ("delete", id);
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

    private void postData(final String key){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Menyimpan...");
        progressDialog.show();

        readMode();

        String nama_layanan = pNamaLayanan.getText().toString().trim();
        String harga_layanan = pHargaLayanan.getText().toString().trim();

        apiInterface = API_client.getApiClient().create(Layanan_Interface.class);

        Call<Layanan_Model> call =
                apiInterface.createLayanan(key, nama_layanan, harga_layanan, sp_NamaPegawai);

        call.enqueue(new Callback<Layanan_Model>() {
            public void onResponse(Call<Layanan_Model> call, Response<Layanan_Model> response){
                progressDialog.dismiss();

                Log.i(Detail_LihatLayanan.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")){
                    finish();
                } else {
                    Toast.makeText(Detail_LihatLayanan.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            public void onFailure (Call<Layanan_Model> call, Throwable t)
            {
                progressDialog.dismiss();
                Toast.makeText(Detail_LihatLayanan.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateData(final String key, final int id) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating...");
        progressDialog.show();

        readMode();

        String nama_layanan = pNamaLayanan.getText().toString().trim();
        String harga_layanan = pHargaLayanan.getText().toString().trim();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String tgl_ubah_layanan_log = simpleDateFormat.format(new Date());

        apiInterface = API_client.getApiClient().create(Layanan_Interface.class);

        Call<Layanan_Model> call =
                apiInterface.editLayanan(key, String.valueOf(id), nama_layanan, harga_layanan,
                        tgl_ubah_layanan_log, sp_NamaPegawai);

        call.enqueue(new Callback<Layanan_Model>() {
            public void onResponse(Call<Layanan_Model> call, Response<Layanan_Model> response){
                progressDialog.dismiss();

                Log.i(Detail_LihatLayanan.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")) {
                    Toast.makeText(Detail_LihatLayanan.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Detail_LihatLayanan.this, message, Toast.LENGTH_SHORT).show();
                }

                Intent back = new Intent(Detail_LihatLayanan.this, Activity_LihatLayanan.class);
                startActivity(back);
            }

            public void onFailure(Call<Layanan_Model> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Detail_LihatLayanan.this, t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void deleteData(String key, int id) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Menghapus...");
        progressDialog.show();
        readMode();

        apiInterface = API_client.getApiClient().create(Layanan_Interface.class);

        Call<Layanan_Model> call = apiInterface.hapusLayanan(key, String.valueOf(id), sp_NamaPegawai);

        call.enqueue(new Callback<Layanan_Model>() {
            @Override
            public void onResponse(Call<Layanan_Model> call, Response<Layanan_Model> response) {
                progressDialog.dismiss();

                Log.i(Detail_LihatLayanan.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")){
                    Toast.makeText(Detail_LihatLayanan.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(Detail_LihatLayanan.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Layanan_Model> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Detail_LihatLayanan.this, "Cek "+t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });


    }

    private void editMode() {
        pNamaLayanan.setFocusableInTouchMode(true);
        pHargaLayanan.setFocusableInTouchMode(true);
        pTglDibuat.setFocusableInTouchMode(false);
        pTglDiubah.setFocusableInTouchMode(false);
        pUserLog.setFocusableInTouchMode(false);
    }

    private void readMode() {
        pNamaLayanan.setFocusableInTouchMode(false);  //disable
        pHargaLayanan.setFocusableInTouchMode(false);
        pTglDibuat.setFocusableInTouchMode(false);
        pTglDiubah.setFocusableInTouchMode(false);
        pUserLog.setFocusableInTouchMode(false);

        alertDisable(pNamaLayanan);
        alertDisable(pHargaLayanan);
    }

    private void alertDisable(EditText editText) {
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Detail_LihatLayanan.this, "Klik icon Edit terlebih dahulu untuk mengubah data!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
