package com.example.kouvee_mobile.View;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.kouvee_mobile.Controller.API_client;
import com.example.kouvee_mobile.Controller.Customer_Interface;
import com.example.kouvee_mobile.Model.Customer_Model;
import com.example.kouvee_mobile.R;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detail_LihatCustomer extends AppCompatActivity {
    private EditText pNamaCustomer, pAlamatCustomer, pTeleponCostumer, pTglLahirCustomer, pTglDibuat, pTglDiubah, pUserLog;
    private String nama_customer, alamat_customer, telepon_customer, tgl_lahir_customer, tgl_dibuat, tgl_diubah, user_log;
    private int id;

    private Menu action;

    public String sp_NamaPegawai="";

    private final static String TAG = "Detail_LihatCustomer";
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private Customer_Interface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__lihat_customer);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        pNamaCustomer = findViewById(R.id.NamaCustomer);
        pAlamatCustomer = findViewById(R.id.AlamatCustomer);
        pTeleponCostumer = findViewById(R.id.TeleponCustomer);
        pTglLahirCustomer = findViewById(R.id.TglLahirCustomer);
        pTglDibuat = findViewById(R.id.tanggal_tambah_customer_log);
        pTglDiubah = findViewById(R.id.tanggal_ubah_customer_log);
        pUserLog = findViewById(R.id.user_customer_log);

        Intent intent = getIntent();
        id = intent.getIntExtra("id_customer", 0);
        nama_customer = intent.getStringExtra("nama_customer");
        alamat_customer = intent.getStringExtra("alamat_customer");
        telepon_customer = intent.getStringExtra("telepon_customer");
        tgl_lahir_customer = intent.getStringExtra("tgl_lahir_customer");
        tgl_dibuat = intent.getStringExtra("tanggal_tambah_customer_log");
        tgl_diubah = intent.getStringExtra("tanggal_ubah_customer_log");
        user_log = intent.getStringExtra("user_customer_log");

        setDataFromIntentExtra();
    }

    private void setDataFromIntentExtra() {
        if (id != 0) {
            readMode();
            getSupportActionBar().setTitle(nama_customer.toString());     //Header

            pNamaCustomer.setText(nama_customer);
            pAlamatCustomer.setText(alamat_customer);
            pTeleponCostumer.setText(telepon_customer);
            pTglLahirCustomer.setText(tgl_lahir_customer);
            pTglDibuat.setText(tgl_dibuat);
            pTglDiubah.setText(tgl_diubah);
            pUserLog.setText(user_log);

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.skipMemoryCache(true);
            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
            requestOptions.placeholder(R.drawable.add);
            requestOptions.error(R.drawable.add);

        }else {
            getSupportActionBar().setTitle("Tambah Customer");
            pTglDibuat.setVisibility(View.GONE);
            pTglDiubah.setVisibility(View.GONE);
            pUserLog.setVisibility(View.GONE);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.customer_detail_menu, menu);
        action = menu;
        action.findItem(R.id.menu_save).setVisible(false);

        if (id == 0){
            editMode();

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

            mDateSetListener= new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    month = month+1;
                    String date = year + "/" + month + "/" + day;
                    pTglLahirCustomer.setText(date);
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

                mDateSetListener= new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month+1;
                        String date = year + "/" + month + "/" + day;
                        pTglLahirCustomer.setText(date);
                    }
                };

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(pNamaCustomer, InputMethodManager.SHOW_IMPLICIT);

                action.findItem(R.id.menu_edit).setVisible(false);
                action.findItem(R.id.menu_delete).setVisible(false);
                action.findItem(R.id.menu_save).setVisible(true);

                return true;

            case R.id.menu_save:
                if (id == 0) {
                    if (TextUtils.isEmpty(pNamaCustomer.getText().toString()) ||
                            TextUtils.isEmpty(pAlamatCustomer.getText().toString()) ||
                            TextUtils.isEmpty(pTeleponCostumer.getText().toString()) ||
                            TextUtils.isEmpty(pTglLahirCustomer.getText().toString()) )   {
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
                AlertDialog.Builder dialog = new AlertDialog.Builder(Detail_LihatCustomer.this);
                dialog.setMessage("Menghapus Customer?");

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

        String nama_customer = pNamaCustomer.getText().toString().trim();
        String alamat_customer = pAlamatCustomer.getText().toString().trim();
        String telepon_customer = pTeleponCostumer.getText().toString().trim();
        String tgl_lahir_customer = pTglLahirCustomer.getText().toString().trim();

        apiInterface = API_client.getApiClient().create(Customer_Interface.class);

        Call<Customer_Model> call =
                apiInterface.createCustomer(key, nama_customer, alamat_customer, telepon_customer,
                        tgl_lahir_customer, sp_NamaPegawai);

        call.enqueue(new Callback<Customer_Model>() {
            public void onResponse(Call<Customer_Model> call, Response<Customer_Model> response){
                progressDialog.dismiss();

                Log.i(Detail_LihatCustomer.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")){
                    finish();
                } else {
                    Toast.makeText(Detail_LihatCustomer.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            public void onFailure (Call<Customer_Model> call, Throwable t)
            {
                progressDialog.dismiss();
                Toast.makeText(Detail_LihatCustomer.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateData(final String key, final int id) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating...");
        progressDialog.show();

        readMode();

        String nama_customer = pNamaCustomer.getText().toString().trim();
        String alamat_customer = pAlamatCustomer.getText().toString().trim();
        String telepon_customer = pTeleponCostumer.getText().toString().trim();
        String tgl_lahir_customer = pTglLahirCustomer.getText().toString().trim();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String tgl_ubah_customer_log = simpleDateFormat.format(new Date());
        System.out.println(tgl_ubah_customer_log);

        apiInterface = API_client.getApiClient().create(Customer_Interface.class);

        Call<Customer_Model> call =
                apiInterface.editCustomer(key, String.valueOf(id), nama_customer, alamat_customer,
                        telepon_customer, tgl_lahir_customer, tgl_ubah_customer_log, sp_NamaPegawai);


        call.enqueue(new Callback<Customer_Model>() {
            public void onResponse(Call<Customer_Model> call, Response<Customer_Model> response){
                progressDialog.dismiss();

                Log.i(Detail_LihatCustomer.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")) {
                    Toast.makeText(Detail_LihatCustomer.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Detail_LihatCustomer.this, message, Toast.LENGTH_SHORT).show();
                }

                Intent back = new Intent(Detail_LihatCustomer.this, Activity_LihatCustomer.class);
                startActivity(back);
            }

            public void onFailure(Call<Customer_Model> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Detail_LihatCustomer.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void deleteData(String key, int id) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Menghapus...");
        progressDialog.show();
        readMode();

        apiInterface = API_client.getApiClient().create(Customer_Interface.class);

        /*
        Statement stmt = con.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery("SELECT tanggal_hapus_customer_log FROM customer");
        */

        Call<Customer_Model> call = apiInterface.hapusCustomer(key, String.valueOf(id), sp_NamaPegawai);

        call.enqueue(new Callback<Customer_Model>() {
            @Override
            public void onResponse(Call<Customer_Model> call, Response<Customer_Model> response) {
                progressDialog.dismiss();

                Log.i(Detail_LihatCustomer.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")){
                    Toast.makeText(Detail_LihatCustomer.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(Detail_LihatCustomer.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Customer_Model> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Detail_LihatCustomer.this, "Cek "+t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void editMode() {
        pNamaCustomer.setFocusableInTouchMode(true);
        pAlamatCustomer.setFocusableInTouchMode(true);
        pTeleponCostumer.setFocusableInTouchMode(true);     //enable
        pTglLahirCustomer.setFocusableInTouchMode(false);   //disable
        pTglDibuat.setFocusableInTouchMode(false);
        pTglDiubah.setFocusableInTouchMode(false);
        pUserLog.setFocusableInTouchMode(false);
    }

    private void readMode() {
        pNamaCustomer.setFocusableInTouchMode(false);       //disable
        pAlamatCustomer.setFocusableInTouchMode(false);
        pTeleponCostumer.setFocusableInTouchMode(false);
        pTglLahirCustomer.setFocusableInTouchMode(false);
        pTglDibuat.setFocusableInTouchMode(false);
        pTglDiubah.setFocusableInTouchMode(false);
        pUserLog.setFocusableInTouchMode(false);

        alertDisable(pNamaCustomer);
        alertDisable(pAlamatCustomer);
        alertDisable(pTeleponCostumer);
        alertDisable(pTglLahirCustomer);
    }

    private void alertDisable(EditText editText) {
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Detail_LihatCustomer.this, "Klik icon Edit terlebih dahulu untuk mengubah data!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
