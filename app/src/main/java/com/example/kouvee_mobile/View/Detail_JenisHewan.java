package com.example.kouvee_mobile.View;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.kouvee_mobile.Controller.Jenis_Interface;
import com.example.kouvee_mobile.Model.Jenis_Model;
import com.example.kouvee_mobile.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detail_JenisHewan extends AppCompatActivity {
    private EditText pNamaJenis, pTglDibuat, pTglDiubah;
    private TextView pIdJenis;
    private String id_jenis, nama_jenis, tanggal_dibuat, tanggal_diubah;
    private int id;

    private Menu action;

    private final static String TAG = "Detail_JenisHewan";
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private Jenis_Interface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__jenis_hewan);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //pIdJenis = findViewById(R.id.IdJenisTxt);
        pNamaJenis = findViewById(R.id.NamaJenis);
        pTglDibuat = findViewById(R.id.tanggal_tambah_jenis_log);
        pTglDiubah = findViewById(R.id.tanggal_ubah_jenis_log);

        Intent intent = getIntent();
        id = intent.getIntExtra("id_jenis", 0);
        id_jenis = intent.getStringExtra("id_jenis");
        nama_jenis = intent.getStringExtra("nama_jenis");
        tanggal_dibuat = intent.getStringExtra("tanggal_tambah_jenis_log");
        tanggal_diubah = intent.getStringExtra("tanggal_ubah_jenis_log");

        setDataFromIntentExtra();
    }

    private void setDataFromIntentExtra() {
        if (id != 0) {
            readMode();
            getSupportActionBar().setTitle(nama_jenis.toString());     //Header

            //pIdJenis.setText(id_jenis);
            pNamaJenis.setText(nama_jenis);
            pTglDibuat.setText(tanggal_dibuat);
            pTglDiubah.setText(tanggal_diubah);

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.skipMemoryCache(true);
            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
            requestOptions.placeholder(R.drawable.add);
            requestOptions.error(R.drawable.add);

        }else {
            getSupportActionBar().setTitle("Tambah Jenis");
            pTglDibuat.setVisibility(View.GONE);
            pTglDiubah.setVisibility(View.GONE);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.jenis_detail_menu, menu);
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
                imm.showSoftInput(pNamaJenis, InputMethodManager.SHOW_IMPLICIT);

                action.findItem(R.id.menu_edit).setVisible(false);
                action.findItem(R.id.menu_delete).setVisible(false);
                action.findItem(R.id.menu_save).setVisible(true);

                return true;

            case R.id.menu_save:

                if (id == 0) {
                    if (TextUtils.isEmpty(pNamaJenis.getText().toString()) )   {
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
                AlertDialog.Builder dialog = new AlertDialog.Builder(Detail_JenisHewan.this);
                dialog.setMessage("Menghapus Jenis?");

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

        String nama_jenis = pNamaJenis.getText().toString().trim();

        apiInterface = API_client.getApiClient().create(Jenis_Interface.class);

        Call<Jenis_Model> call =
                apiInterface.createJenis(key, nama_jenis);

        call.enqueue(new Callback<Jenis_Model>() {
            public void onResponse(Call<Jenis_Model> call, Response<Jenis_Model> response){
                progressDialog.dismiss();

                Log.i(Detail_JenisHewan.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")){
                    finish();
                } else {
                    Toast.makeText(Detail_JenisHewan.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            public void onFailure (Call<Jenis_Model> call, Throwable t)
            {
                progressDialog.dismiss();
                Toast.makeText(Detail_JenisHewan.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateData(final String key, final int id) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating...");
        progressDialog.show();

        readMode();

        String nama_jenis= pNamaJenis.getText().toString().trim();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String tgl_ubah_jenis_log = simpleDateFormat.format(new Date());

        apiInterface = API_client.getApiClient().create(Jenis_Interface.class);

        Call<Jenis_Model> call =
                apiInterface.editJenis(key, String.valueOf(id), nama_jenis, tgl_ubah_jenis_log);


        call.enqueue(new Callback<Jenis_Model>() {
            public void onResponse(Call<Jenis_Model> call, Response<Jenis_Model> response){
                progressDialog.dismiss();

                Log.i(Detail_JenisHewan.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")) {
                    Toast.makeText(Detail_JenisHewan.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Detail_JenisHewan.this, message, Toast.LENGTH_SHORT).show();
                }

                Intent back = new Intent(Detail_JenisHewan.this, Activity_JenisHewan.class);
                startActivity(back);
            }

            public void onFailure(Call<Jenis_Model> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Detail_JenisHewan.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void deleteData(String key, int id) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Menghapus...");
        progressDialog.show();
        readMode();

        apiInterface = API_client.getApiClient().create(Jenis_Interface.class);

        Call<Jenis_Model> call = apiInterface.hapusJenis(key, String.valueOf(id));

        call.enqueue(new Callback<Jenis_Model>() {
            @Override
            public void onResponse(Call<Jenis_Model> call, Response<Jenis_Model> response) {
                progressDialog.dismiss();

                Log.i(Detail_JenisHewan.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")){
                    Toast.makeText(Detail_JenisHewan.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(Detail_JenisHewan.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Jenis_Model> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Detail_JenisHewan.this, "Cek "+t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });


    }

    private void editMode() {
        pNamaJenis.setFocusableInTouchMode(true);
        pTglDibuat.setFocusableInTouchMode(false);
        pTglDiubah.setFocusableInTouchMode(false);
    }

    private void readMode() {
        pNamaJenis.setFocusableInTouchMode(false);  //disable
        pTglDibuat.setFocusableInTouchMode(false);
        pTglDiubah.setFocusableInTouchMode(false);

        alertDisable(pNamaJenis);
    }

    private void alertDisable(EditText editText) {
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Detail_JenisHewan.this, "Klik icon Edit terlebih dahulu untuk mengubah data!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
