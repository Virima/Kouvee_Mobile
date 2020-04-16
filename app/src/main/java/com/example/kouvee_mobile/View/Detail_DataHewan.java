package com.example.kouvee_mobile.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import com.example.kouvee_mobile.Controller.Customer_Interface;
import com.example.kouvee_mobile.Controller.DataHewan_Interface;
import com.example.kouvee_mobile.Controller.Jenis_Interface;
import com.example.kouvee_mobile.Controller.Ukuran_Interface;
import com.example.kouvee_mobile.Model.Customer_Model;
import com.example.kouvee_mobile.Model.Hewan_Model;
import com.example.kouvee_mobile.Model.Jenis_Model;
import com.example.kouvee_mobile.Model.Ukuran_Model;
import com.example.kouvee_mobile.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detail_DataHewan extends AppCompatActivity {
    private EditText pNamaHewan, pTglLahirHewan, pIdJenis, pIdUkuran, pId_Customer, pTglDibuat, pTglDiubah;
    private String nama_hewan, tgl_lahir_hewan, id_jenis, id_ukuran, id_customer, tanggal_dibuat, tanggal_diubah;
    private int id;

    private Spinner spinnerJenisHewan;
    private Spinner spinnerUkuranHewan;
    private Spinner spinnerCustomerHewan;
    private List<String> listSpinnerJenis;
    private List<String> listSpinnerUkuran;
    private List<String> listSpinnerCustomer;
    private Menu action;

    private final static String TAG = "Detail_DataHewan";
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private DataHewan_Interface apiInterface;

    SharedPreferences sp;
    public static final int mode = Activity.MODE_PRIVATE;
    private String sp_nama="";
    private String sp_tanggalLahir="";
    private String sp_jenis="";
    private String sp_ukuran="";
    private String sp_customer="";
    private Button spSimpanBtn, spResetBtn;


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
        //spResetBtn = findViewById(R.id.spResetHewanBtn);
        //spSimpanBtn = findViewById(R.id.spSimpanHewanBtn);
        listSpinnerJenis = new ArrayList<>();
        listSpinnerUkuran = new ArrayList<>();
        listSpinnerCustomer = new ArrayList<>();

        spinnerJenisHewan = findViewById(R.id.spinnerJenis);
        spinnerUkuranHewan = findViewById(R.id.spinnerUkuran);
        spinnerCustomerHewan = findViewById(R.id.spinnerCustomer);

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

        loadSpinnerJenis();
        loadSpinnerUkuran();
        loadSpinnerCustomer();

        spinnerJenisHewan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                compareSpinnerJenis();
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {}
        });

        spinnerUkuranHewan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                compareSpinnerUkuran();
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {}
        });

        spinnerCustomerHewan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                compareSpinnerCustomer();
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {}
        });
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

            spinnerJenisHewan.setVisibility(View.GONE);
            spinnerUkuranHewan.setVisibility(View.GONE);
            spinnerCustomerHewan.setVisibility(View.GONE);
            //spResetBtn.setVisibility(View.GONE);
            //spSimpanBtn.setVisibility(View.GONE);

        } else {
            getSupportActionBar().setTitle("Tambah Hewan");

            //loadPreference();
            //setForm();
            pIdJenis.setVisibility(View.GONE);
            pIdUkuran.setVisibility(View.GONE);
            pId_Customer.setVisibility(View.GONE);

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
                //spResetBtn.setVisibility(View.VISIBLE);
                //spSimpanBtn.setVisibility(View.VISIBLE);

                setUpdateSpinnerJenis();
                setUpdateSpinnerUkuran();
                setUpdateSpinnerCustomer();
                pIdJenis.setVisibility(View.GONE);
                pIdUkuran.setVisibility(View.GONE);
                pId_Customer.setVisibility(View.GONE);

                spinnerJenisHewan.setVisibility(View.VISIBLE);
                spinnerUkuranHewan.setVisibility(View.VISIBLE);
                spinnerCustomerHewan.setVisibility(View.VISIBLE);

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
                    //loadPreference();
                    //setForm();

                    if (TextUtils.isEmpty(pNamaHewan.getText().toString()) ||
                            TextUtils.isEmpty(pTglLahirHewan.getText().toString()) ||
                            /*TextUtils.isEmpty(pIdJenis.getText().toString()) || */
                            /*TextUtils.isEmpty(pIdUkuran.getText().toString()) || */
                            /*TextUtils.isEmpty(pId_Customer.getText().toString()) || */
                            spinnerJenisHewan.getSelectedItemPosition()==0 ||
                            spinnerUkuranHewan.getSelectedItemPosition()==0 ||
                            spinnerCustomerHewan.getSelectedItemPosition()==0) {
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
                apiInterface.createHewan(
                        key,
                        nama_hewan,
                        tgl_lahir_hewan,
                        /*spinnerJenisHewan.getSelectedItem().toString()*/ id_jenis,
                        id_ukuran,
                        id_customer);

        call.enqueue(new Callback<Hewan_Model>() {
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
        //String id_jenis = spinnerJenisHewan.getSelectedItem().toString();

        apiInterface = API_client.getApiClient().create(DataHewan_Interface.class);

        Call<Hewan_Model> call =
                apiInterface.editHewan(key,
                        String.valueOf(id),
                        nama_hewan,
                        tgl_lahir_hewan,
                        id_jenis,
                        id_ukuran,
                        id_customer,
                        tgl_ubah_hewan_log);

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

    public void loadSpinnerJenis()
    {
        Jenis_Interface apiJenis = API_client.getApiClient().create(Jenis_Interface.class);
        Call<List<Jenis_Model>> listCall = apiJenis.getJenis();

        listCall.enqueue(new Callback<List<Jenis_Model>>() {
            @Override
            public void onResponse(Call<List<Jenis_Model>> call, Response<List<Jenis_Model>> response) {
                List<Jenis_Model> jenisModels = response.body();
                for(int i=0; i < jenisModels.size(); i++ ){
                    String name = jenisModels.get(i).getNama_jenis();
                    listSpinnerJenis.add(name);
                }
                listSpinnerJenis.add(0,"- PILIH JENIS HEWAN -");
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Detail_DataHewan.this,
                        android.R.layout.simple_spinner_item, listSpinnerJenis);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerJenisHewan.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Jenis_Model>> call, Throwable t) {
                Toast.makeText(Detail_DataHewan.this, "Cek " + t.getMessage().toString(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void loadSpinnerUkuran()
    {
        Ukuran_Interface apiUkuran = API_client.getApiClient().create(Ukuran_Interface.class);
        Call<List<Ukuran_Model>> listCall = apiUkuran.getUkuran();

        listCall.enqueue(new Callback<List<Ukuran_Model>>() {
            @Override
            public void onResponse(Call<List<Ukuran_Model>> call, Response<List<Ukuran_Model>> response) {
                List<Ukuran_Model> ukuranModels = response.body();
                for(int i=0; i < ukuranModels.size(); i++ ){
                    String name = ukuranModels.get(i).getNama_ukuran();
                    listSpinnerUkuran.add(name);
                }
                listSpinnerUkuran.add(0,"- PILIH UKURAN HEWAN -");
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Detail_DataHewan.this,
                        android.R.layout.simple_spinner_item, listSpinnerUkuran);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerUkuranHewan.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Ukuran_Model>> call, Throwable t) {
                Toast.makeText(Detail_DataHewan.this, "Cek " + t.getMessage().toString(),
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
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Detail_DataHewan.this,
                        android.R.layout.simple_spinner_item, listSpinnerCustomer);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCustomerHewan.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Customer_Model>> call, Throwable t) {
                Toast.makeText(Detail_DataHewan.this, "Cek " + t.getMessage().toString(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void compareSpinnerJenis()
    {
        Jenis_Interface apiJenis = API_client.getApiClient().create(Jenis_Interface.class);
        Call<List<Jenis_Model>> listCall = apiJenis.getJenis();

        listCall.enqueue(new Callback<List<Jenis_Model>>() {
            @Override
            public void onResponse(Call<List<Jenis_Model>> call, Response<List<Jenis_Model>> response) {
                List<Jenis_Model> jenisModels = response.body();
                for(int i=0; i < jenisModels.size(); i++ ) {
                    String nama = jenisModels.get(i).getNama_jenis();   //get setiap nama jenis
                    String temp = spinnerJenisHewan.getSelectedItem().toString();   //get selected nama jenis

                    String list_id = String.valueOf(jenisModels.get(i).getId_jenis());  //tampungan id jenis
                    //tempIdJenis.add(list_id);   //ngisi tampungan id jenis

                    if(temp.equals(nama))
                    {
                        /*
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Detail_DataHewan.this,
                                android.R.layout.simple_spinner_item, tempIdJenis);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerJenisHewan.setAdapter(adapter);

                        spinnerJenisHewan.setSelection(i);
                        System.out.println("WOII "+ spinnerJenisHewan.getSelectedItem());
                        */

                        pIdJenis.setText(String.valueOf(jenisModels.get(i).getId_jenis()));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Jenis_Model>> call, Throwable t) {
                Toast.makeText(Detail_DataHewan.this, "Cek " + t.getMessage().toString(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void compareSpinnerUkuran()
    {
        Ukuran_Interface apiUkuran = API_client.getApiClient().create(Ukuran_Interface.class);
        Call<List<Ukuran_Model>> listCall = apiUkuran.getUkuran();

        listCall.enqueue(new Callback<List<Ukuran_Model>>() {
            @Override
            public void onResponse(Call<List<Ukuran_Model>> call, Response<List<Ukuran_Model>> response) {
                List<Ukuran_Model> ukuranModels = response.body();

                for(int i=0; i < ukuranModels.size(); i++ ) {
                    String nama = ukuranModels.get(i).getNama_ukuran();
                    String temp = spinnerUkuranHewan.getSelectedItem().toString();

                    if(temp.equals(nama))
                    {
                        pIdUkuran.setText(String.valueOf(ukuranModels.get(i).getId_ukuran()));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Ukuran_Model>> call, Throwable t) {
                Toast.makeText(Detail_DataHewan.this, "Cek " + t.getMessage().toString(),
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
                    String temp = spinnerCustomerHewan.getSelectedItem().toString();

                    if(temp.equals(nama))
                    {
                        pId_Customer.setText(String.valueOf(customerModels.get(i).getIdCustomer()));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Customer_Model>> call, Throwable t) {
                Toast.makeText(Detail_DataHewan.this, "Cek " + t.getMessage().toString(),
                        Toast.LENGTH_LONG).show();
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
        //spSimpanBtn.setFocusableInTouchMode(false);
        //spResetBtn.setFocusableInTouchMode(false);

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
                Toast.makeText(Detail_DataHewan.this,
                        "Klik icon Edit terlebih dahulu untuk mengubah data!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpdateSpinnerJenis()
    {
        Jenis_Interface apiJenis = API_client.getApiClient().create(Jenis_Interface.class);
        Call<List<Jenis_Model>> listCall = apiJenis.getJenis();

        listCall.enqueue(new Callback<List<Jenis_Model>>() {
            @Override
            public void onResponse(Call<List<Jenis_Model>> call, Response<List<Jenis_Model>> response) {
                List<Jenis_Model> jenisModels = response.body();

                String editText = pIdJenis.getText().toString();
                System.out.println("EDIT TEXT NI " + editText);
                for(int i=0; i < jenisModels.size(); i++ ) {
                    String nama = jenisModels.get(i).getNama_jenis();   //get setiap nama jenis
                    //String temp = spinnerJenisHewan.getSelectedItem().toString();   //get selected nama jenis

                    if(editText.equals(nama))
                    {
                        spinnerJenisHewan.setSelection(i+1);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Jenis_Model>> call, Throwable t) {
                Toast.makeText(Detail_DataHewan.this, "Cek " + t.getMessage().toString(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setUpdateSpinnerUkuran()
    {
        Ukuran_Interface apiUkuran = API_client.getApiClient().create(Ukuran_Interface.class);
        Call<List<Ukuran_Model>> listCall = apiUkuran.getUkuran();

        listCall.enqueue(new Callback<List<Ukuran_Model>>() {
            @Override
            public void onResponse(Call<List<Ukuran_Model>> call, Response<List<Ukuran_Model>> response) {
                List<Ukuran_Model> ukuranModels = response.body();

                String editText = pIdUkuran.getText().toString();
                for(int i=0; i < ukuranModels.size(); i++ ) {
                    String nama = ukuranModels.get(i).getNama_ukuran();

                    if(editText.equals(nama))
                    {
                        spinnerUkuranHewan.setSelection(i+1);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Ukuran_Model>> call, Throwable t) {
                Toast.makeText(Detail_DataHewan.this, "Cek " + t.getMessage().toString(),
                        Toast.LENGTH_LONG).show();
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

                String editText = pId_Customer.getText().toString();
                for(int i=0; i < customerModels.size(); i++ ) {
                    String nama = customerModels.get(i).getNama_customer();

                    if(editText.equals(nama))
                    {
                        spinnerCustomerHewan.setSelection(i+1);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Customer_Model>> call, Throwable t) {
                Toast.makeText(Detail_DataHewan.this, "Cek " + t.getMessage().toString(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    //-  Fungsi simpan dan load teks sementara [SharedPreferences]  -//
    /*
    private void setForm()
    {
        pNamaHewan = findViewById(R.id.NamaHewan);
        pTglLahirHewan = findViewById(R.id.TglLahirHewan);
        pIdJenis = findViewById(R.id.JenisHewanJoinHewan);
        pIdUkuran = findViewById(R.id.UkuranHewanJoinHewan);
        pId_Customer = findViewById(R.id.PemilikHewanJoinHewan);

        pNamaHewan.setText(sp_nama);
        pTglLahirHewan.setText(sp_tanggalLahir);
        pIdJenis.setText(sp_jenis);
        pIdUkuran.setText(sp_ukuran);
        pId_Customer.setText(sp_customer);
    }

    private void loadPreference()
    {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        if(sp!=null)
        {
            sp_nama = sp.getString("sp_nama", "");
            sp_tanggalLahir = sp.getString("sp_tanggalLahir", "");
            sp_jenis = sp.getString("sp_jenis", "");
            sp_ukuran = sp.getString("sp_ukuran", "");
            sp_customer = sp.getString("sp_customer", "");
        }
    }

    private void savePreference()
    {
        pNamaHewan = findViewById(R.id.NamaHewan);
        pTglLahirHewan = findViewById(R.id.TglLahirHewan);
        pIdJenis = findViewById(R.id.JenisHewanJoinHewan);
        pIdUkuran = findViewById(R.id.UkuranHewanJoinHewan);
        pId_Customer = findViewById(R.id.PemilikHewanJoinHewan);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("sp_nama", pNamaHewan.getText().toString());
        editor.putString("sp_tanggalLahir", pTglLahirHewan.getText().toString());
        editor.putString("sp_jenis", pIdJenis.getText().toString());
        editor.putString("sp_ukuran", pIdUkuran.getText().toString());
        editor.putString("sp_customer", pId_Customer.getText().toString());
        editor.apply();

        Toast.makeText(Detail_DataHewan.this, "Data disimpan sementara",
                Toast.LENGTH_SHORT).show();
    }

    public void spButtonSimpan(View v)
    {
        savePreference();
    }

    public void spButtonReset(View v)
    {
        pNamaHewan = findViewById(R.id.NamaHewan);
        pTglLahirHewan = findViewById(R.id.TglLahirHewan);
        pIdJenis = findViewById(R.id.JenisHewanJoinHewan);
        pIdUkuran = findViewById(R.id.UkuranHewanJoinHewan);
        pId_Customer = findViewById(R.id.PemilikHewanJoinHewan);

        pNamaHewan.setText("");
        pTglLahirHewan.setText("");
        pIdJenis.setText("");
        pIdUkuran.setText("");
        pId_Customer.setText("");
    }
     */

}

