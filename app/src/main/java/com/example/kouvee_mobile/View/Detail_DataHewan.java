package com.example.kouvee_mobile.View;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.kouvee_mobile.Controller.API_client;
import com.example.kouvee_mobile.Controller.DataHewan_Interface;
import com.example.kouvee_mobile.Model.Hewan_Model;
import com.example.kouvee_mobile.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detail_DataHewan extends AppCompatActivity {
    private EditText pNamaHewan, pTglLahirHewan, pIdJenis, pIdUkuran, pId_Customer, pTglDibuat, pTglDiubah;
    private String nama_hewan, tgl_lahir_hewan, id_jenis, id_ukuran, id_customer, tanggal_dibuat, tanggal_diubah;
    private int id;

    private Menu action;

    private final static String TAG = "Detail_DataHewan";
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private DataHewan_Interface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__data_hewan);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        pNamaHewan = findViewById(R.id.NamaHewan);
        pTglLahirHewan = findViewById(R.id.TglLahirHewan);
        pIdJenis = findViewById(R.id.JenisHewanJoinHewan);
        pIdUkuran = findViewById(R.id.UkuranHewanJoinHewan);
        pId_Customer = findViewById(R.id.PemilikHewanJoinHewan);
        pTglDibuat = findViewById(R.id.tanggal_tambah_hewan_log);
        pTglDiubah = findViewById(R.id.tanggal_ubah_hewan_log);

        /*
        pTglLahirCustomer = (EditText) findViewById(R.id.TglLahirCustomer); //date
        pTglLahirCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Detail_LihatCustomer.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener, year, month, day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        */

        Intent intent = getIntent();
        id = intent.getIntExtra("id_hewan", 0);
        nama_hewan = intent.getStringExtra("nama_hewan");
        tgl_lahir_hewan = intent.getStringExtra("tgl_lahir_hewan");
        id_jenis = intent.getStringExtra("id_jenis");
        id_ukuran = intent.getStringExtra("id_ukuran");
        id_customer = intent.getStringExtra("id_customer");
        tanggal_dibuat = intent.getStringExtra("tanggal_tambah_hewan_log");
        tanggal_diubah = intent.getStringExtra("tanggal_ubah_hewan_log");
        setDataFromIntentExtra();

    }

    private void setDataFromIntentExtra() {
        if (id != 0) {
            readMode();
            getSupportActionBar().setTitle(nama_hewan.toString());     //Header

            pNamaHewan.setText(nama_hewan);
            pTglLahirHewan.setText(tgl_lahir_hewan);
            pIdJenis.setText(id_jenis);
            pIdUkuran.setText(id_ukuran);
            pId_Customer.setText(id_customer);
            pTglDibuat.setText(tanggal_dibuat);
            pTglDiubah.setText(tanggal_diubah);

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.skipMemoryCache(true);
            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
            requestOptions.placeholder(R.drawable.add);
            requestOptions.error(R.drawable.add);

        } else {
            getSupportActionBar().setTitle("Tambah Hewan");
            pTglDibuat.setVisibility(View.GONE);
            pTglDiubah.setVisibility(View.GONE);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.hewan_detail_menu, menu);
        action = menu;
        action.findItem(R.id.menu_save).setVisible(false);

        if (id == 0) {
            editMode();

            pTglLahirHewan = (EditText) findViewById(R.id.TglLahirHewan); //date
            pTglLahirHewan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dialog = new DatePickerDialog(Detail_DataHewan.this,
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
                    pTglLahirHewan.setText(date);
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
                //Edit
                editMode();

                pTglLahirHewan = (EditText) findViewById(R.id.TglLahirHewan); //date
                pTglLahirHewan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar cal = Calendar.getInstance();
                        int year = cal.get(Calendar.YEAR);
                        int month = cal.get(Calendar.MONTH);
                        int day = cal.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog dialog = new DatePickerDialog(Detail_DataHewan.this,
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
                        pTglLahirHewan.setText(date);
                    }
                };

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(pNamaHewan, InputMethodManager.SHOW_IMPLICIT);

                action.findItem(R.id.menu_edit).setVisible(false);
                action.findItem(R.id.menu_delete).setVisible(false);
                action.findItem(R.id.menu_save).setVisible(true);

                return true;

            case R.id.menu_save:
                if (id == 0) {
                    if (TextUtils.isEmpty(pNamaHewan.getText().toString()) ||
                            TextUtils.isEmpty(pTglLahirHewan.getText().toString()) ||
                            TextUtils.isEmpty(pIdJenis.getText().toString()) ||
                            TextUtils.isEmpty(pIdUkuran.getText().toString()) ||
                            TextUtils.isEmpty(pId_Customer.getText().toString())) {
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
                AlertDialog.Builder dialog = new AlertDialog.Builder(Detail_DataHewan.this);
                dialog.setMessage("Menghapus Hewan?");

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

        String nama_hewan = pNamaHewan.getText().toString().trim();
        String tgl_lahir_hewan = pTglLahirHewan.getText().toString().trim();
        String id_jenis = pIdJenis.getText().toString().trim();
        String id_ukuran = pIdUkuran.getText().toString().trim();
        String id_customer = pId_Customer.getText().toString().trim();

        apiInterface = API_client.getApiClient().create(DataHewan_Interface.class);

        Call<Hewan_Model> call =
                apiInterface.createHewan(key, nama_hewan, tgl_lahir_hewan, id_jenis, id_ukuran, id_customer);

        call.enqueue(new Callback<Hewan_Model>() {
            public void onResponse(Call<Hewan_Model> call, Response<Hewan_Model> response) {
                progressDialog.dismiss();

                Log.i(Detail_DataHewan.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")) {
                    finish();
                } else {
                    Toast.makeText(Detail_DataHewan.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            public void onFailure(Call<Hewan_Model> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Detail_DataHewan.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateData(final String key, final int id) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating...");
        progressDialog.show();

        readMode();

        String nama_hewan = pNamaHewan.getText().toString().trim();
        String tgl_lahir_hewan = pTglLahirHewan.getText().toString().trim();
        String id_jenis = pIdJenis.getText().toString().trim();
        String id_ukuran = pIdUkuran.getText().toString().trim();
        String id_customer = pId_Customer.getText().toString().trim();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String tgl_ubah_hewan_log = simpleDateFormat.format(new Date());

        apiInterface = API_client.getApiClient().create(DataHewan_Interface.class);

        Call<Hewan_Model> call =
                apiInterface.editHewan(key, String.valueOf(id), nama_hewan, tgl_lahir_hewan, id_jenis,
                        id_ukuran, id_customer, tgl_ubah_hewan_log);


        call.enqueue(new Callback<Hewan_Model>() {
            public void onResponse(Call<Hewan_Model> call, Response<Hewan_Model> response) {
                progressDialog.dismiss();

                Log.i(Detail_DataHewan.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")) {
                    Toast.makeText(Detail_DataHewan.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Detail_DataHewan.this, message, Toast.LENGTH_SHORT).show();
                }

                Intent back = new Intent(Detail_DataHewan.this, Activity_DataHewan.class);
                startActivity(back);
            }

            public void onFailure(Call<Hewan_Model> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Detail_DataHewan.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void deleteData(String key, int id) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Menghapus...");
        progressDialog.show();
        readMode();

        apiInterface = API_client.getApiClient().create(DataHewan_Interface.class);

        Call<Hewan_Model> call = apiInterface.hapusHewan(key, String.valueOf(id));

        call.enqueue(new Callback<Hewan_Model>() {
            @Override
            public void onResponse(Call<Hewan_Model> call, Response<Hewan_Model> response) {
                progressDialog.dismiss();

                Log.i(Detail_DataHewan.class.getSimpleName(), response.toString());
                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")) {
                    Toast.makeText(Detail_DataHewan.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(Detail_DataHewan.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Hewan_Model> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Detail_DataHewan.this, "Cek " + t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void editMode() {
        pNamaHewan.setFocusableInTouchMode(true);
        pTglLahirHewan.setFocusableInTouchMode(false); //disable
        pIdJenis.setFocusableInTouchMode(true);
        pIdUkuran.setFocusableInTouchMode(true);     //enable
        pId_Customer.setFocusableInTouchMode(true);
        pTglDibuat.setFocusableInTouchMode(false);
        pTglDiubah.setFocusableInTouchMode(false);
    }

    private void readMode() {
        pNamaHewan.setFocusableInTouchMode(false);
        pTglLahirHewan.setFocusableInTouchMode(false);
        pIdJenis.setFocusableInTouchMode(false);
        pIdUkuran.setFocusableInTouchMode(false);
        pId_Customer.setFocusableInTouchMode(false);
        pTglDibuat.setFocusableInTouchMode(false);
        pTglDiubah.setFocusableInTouchMode(false);

        alertDisable(pNamaHewan);
        alertDisable(pTglLahirHewan);
        alertDisable(pIdJenis);
        alertDisable(pIdUkuran);
        alertDisable(pId_Customer);
    }

    private void alertDisable(EditText editText) {
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Detail_DataHewan.this, "Klik icon Edit terlebih dahulu untuk mengubah data!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}

