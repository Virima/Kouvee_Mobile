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

import com.example.kouvee_mobile.Controller.Layanan_Interface;
import com.example.kouvee_mobile.Model.Layanan_Model;
import com.example.kouvee_mobile.R;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detail_LihatLayanan extends AppCompatActivity {
    private EditText pNamaLayanan;
    private TextView pIdLayanan;
    private String id_layanan, nama_layanan;
    private int id;

    private Menu action;

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

        Intent intent = getIntent();
        id = intent.getIntExtra("id_layanan", 0);
        id_layanan = intent.getStringExtra("id_layanan");
        nama_layanan = intent.getStringExtra("nama_layanan");

        setDataFromIntentExtra();
    }

    private void setDataFromIntentExtra() {
        if (id != 0) {
            readMode();
            getSupportActionBar().setTitle(nama_layanan.toString());     //Header

            //pIdLayanan.setText(id_layanan);
            pNamaLayanan.setText(nama_layanan);

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.skipMemoryCache(true);
            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
            requestOptions.placeholder(R.drawable.add);
            requestOptions.error(R.drawable.add);

        }else {
            getSupportActionBar().setTitle("Tambah Layanan");
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

                if (id == 0) {
                    if (TextUtils.isEmpty(pNamaLayanan.getText().toString()) )   {
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

        apiInterface = API_client.getApiClient().create(Layanan_Interface.class);

        Call<Layanan_Model> call =
                apiInterface.createLayanan(key, nama_layanan);

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

        apiInterface = API_client.getApiClient().create(Layanan_Interface.class);

        Call<Layanan_Model> call =
                apiInterface.editLayanan(key, String.valueOf(id), nama_layanan);


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
                Toast.makeText(Detail_LihatLayanan.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void deleteData(String key, int id) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Menghapus...");
        progressDialog.show();
        readMode();

        apiInterface = API_client.getApiClient().create(Layanan_Interface.class);

        Call<Layanan_Model> call = apiInterface.hapusLayanan(key, String.valueOf(id));

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
    }

    private void readMode() {
        pNamaLayanan.setFocusableInTouchMode(false);  //disable
    }
}
