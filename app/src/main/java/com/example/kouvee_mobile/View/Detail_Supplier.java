package com.example.kouvee_mobile.View;

import android.app.AlertDialog;
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
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.kouvee_mobile.Controller.API_client;
import com.example.kouvee_mobile.Controller.Supplier_Interface;
import com.example.kouvee_mobile.Model.Supplier_Model;
import com.example.kouvee_mobile.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detail_Supplier extends AppCompatActivity {
    private EditText pNamaSupplier, pAlamatSupplier, pTeleponSupplier, pTglDibuat, pTglDiubah;;
    private String nama_supplier, alamat_supplier, telepon_supplier, tanggal_dibuat, tanggal_diubah;
    private int id;

    private Menu action;

    private Supplier_Interface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__supplier);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        pNamaSupplier = findViewById(R.id.NamaSupplier);
        pAlamatSupplier = findViewById(R.id.AlamatSupplier);
        pTeleponSupplier = findViewById(R.id.TeleponSupplier);
        pTglDibuat = findViewById(R.id.tanggal_tambah_supplier_log);
        pTglDiubah = findViewById(R.id.tanggal_ubah_supplier_log);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        nama_supplier = intent.getStringExtra("nama_supplier");
        alamat_supplier = intent.getStringExtra("alamat_supplier");
        telepon_supplier = intent.getStringExtra("telepon_supplier");
        tanggal_dibuat = intent.getStringExtra("tanggal_tambah_supplier_log");
        tanggal_diubah = intent.getStringExtra("tanggal_ubah_supplier_log");

        setDataFromIntentExtra();
    }

    private void setDataFromIntentExtra() {
        if (id != 0) {
            readMode();
            getSupportActionBar().setTitle(nama_supplier.toString());

            pNamaSupplier.setText(nama_supplier);
            pAlamatSupplier.setText(alamat_supplier);
            pTeleponSupplier.setText(telepon_supplier);
            pTglDibuat.setText(tanggal_dibuat);
            pTglDiubah.setText(tanggal_diubah);

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.skipMemoryCache(true);
            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
            requestOptions.placeholder(R.drawable.add);
            requestOptions.error(R.drawable.add);

        }else {
            getSupportActionBar().setTitle("Tambah Supplier");
            pTglDibuat.setVisibility(View.GONE);
            pTglDiubah.setVisibility(View.GONE);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.supplier_detail_menu, menu);
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
                imm.showSoftInput(pNamaSupplier, InputMethodManager.SHOW_IMPLICIT);

                action.findItem(R.id.menu_edit).setVisible(false);
                action.findItem(R.id.menu_delete).setVisible(false);
                action.findItem(R.id.menu_save).setVisible(true);

                return true;

            case R.id.menu_save:

                if (id == 0) {
                    if (TextUtils.isEmpty(pNamaSupplier.getText().toString()) ||
                            TextUtils.isEmpty(pAlamatSupplier.getText().toString()) ||
                            TextUtils.isEmpty(pTeleponSupplier.getText().toString()) ) {
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
                AlertDialog.Builder dialog = new AlertDialog.Builder(Detail_Supplier.this);
                dialog.setMessage("Menghapus Supplier?");

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

        String nama_supplier = pNamaSupplier.getText().toString().trim();
        String alamat_supplier = pAlamatSupplier.getText().toString().trim();
        String telepon_supplier = pTeleponSupplier.getText().toString().trim();

        apiInterface = API_client.getApiClient().create(Supplier_Interface.class);

        Call<Supplier_Model> call = apiInterface.createSupplier(key, nama_supplier, alamat_supplier, telepon_supplier);

        call.enqueue(new Callback<Supplier_Model>() {
            public void onResponse(Call<Supplier_Model> call, Response<Supplier_Model> response){
                progressDialog.dismiss();

                Log.i(Detail_Supplier.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")){
                    finish();
                } else {
                    Toast.makeText(Detail_Supplier.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            public void onFailure (Call<Supplier_Model> call, Throwable t)
            {
                progressDialog.dismiss();
                Toast.makeText(Detail_Supplier.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateData(final String key, final int id) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating...");
        progressDialog.show();

        readMode();

        String nama_supplier = pNamaSupplier.getText().toString().trim();
        String alamat_supplier = pAlamatSupplier.getText().toString().trim();
        String telepon_supplier = pTeleponSupplier.getText().toString().trim();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String tgl_ubah_supplier_log = simpleDateFormat.format(new Date());

        apiInterface = API_client.getApiClient().create(Supplier_Interface.class);

        Call<Supplier_Model> call = apiInterface.editSupplier(key, String.valueOf(id), nama_supplier, alamat_supplier,
                telepon_supplier, tgl_ubah_supplier_log);


        call.enqueue(new Callback<Supplier_Model>() {
            public void onResponse(Call<Supplier_Model> call, Response<Supplier_Model> response){
                progressDialog.dismiss();

                Log.i(Detail_Supplier.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")) {
                    Toast.makeText(Detail_Supplier.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Detail_Supplier.this, message, Toast.LENGTH_SHORT).show();
                }

                Intent back = new Intent(Detail_Supplier.this, Activity_Supplier.class);
                startActivity(back);
            }

            public void onFailure(Call<Supplier_Model> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Detail_Supplier.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void deleteData(String key, int id_supplier) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Menghapus...");
        progressDialog.show();

        readMode();

        apiInterface = API_client.getApiClient().create(Supplier_Interface.class);

        Call<Supplier_Model> call = apiInterface.hapusSupplier(key, String.valueOf(id_supplier)); //String.valueOf(id_supplier)

        call.enqueue(new Callback<Supplier_Model>() {
            @Override
            public void onResponse(Call<Supplier_Model> call, Response<Supplier_Model> response) {
                progressDialog.dismiss();

                Log.i(Detail_Supplier.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")){
                    Toast.makeText(Detail_Supplier.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(Detail_Supplier.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Supplier_Model> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Detail_Supplier.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void editMode() {
        pNamaSupplier.setFocusableInTouchMode(true);
        pAlamatSupplier.setFocusableInTouchMode(true);
        pTeleponSupplier.setFocusableInTouchMode(true);
        pTglDibuat.setFocusableInTouchMode(false);
        pTglDiubah.setFocusableInTouchMode(false);
    }

    private void readMode() {
        pNamaSupplier.setFocusableInTouchMode(false);
        pAlamatSupplier.setFocusableInTouchMode(false);
        pTeleponSupplier.setFocusableInTouchMode(false);
        pTglDibuat.setFocusableInTouchMode(false);
        pTglDiubah.setFocusableInTouchMode(false);
    }
}
