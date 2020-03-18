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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detail_JenisHewan extends AppCompatActivity {
    private EditText pNamaJenis;
    private TextView pIdJenis;
    private String id_jenis, nama_jenis;
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

        Intent intent = getIntent();
        id = intent.getIntExtra("id_jenis", 0);
        id_jenis = intent.getStringExtra("id_jenis");
        nama_jenis = intent.getStringExtra("nama_jenis");

        setDataFromIntentExtra();
    }

    private void setDataFromIntentExtra() {
        if (id != 0) {
            readMode();
            getSupportActionBar().setTitle(nama_jenis.toString());     //Header

            //pIdJenis.setText(id_jenis);
            pNamaJenis.setText(nama_jenis);

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.skipMemoryCache(true);
            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
            requestOptions.placeholder(R.drawable.add);
            requestOptions.error(R.drawable.add);

        }else {
            getSupportActionBar().setTitle("Tambah Jenis");
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

        apiInterface = API_client.getApiClient().create(Jenis_Interface.class);

        Call<Jenis_Model> call =
                apiInterface.editJenis(key, String.valueOf(id), nama_jenis);


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
    }

    private void readMode() {
        pNamaJenis.setFocusableInTouchMode(false);  //disable
    }
}
