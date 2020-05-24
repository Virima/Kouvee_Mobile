package com.example.kouvee_mobile.View;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.kouvee_mobile.Controller.API_client;
import com.example.kouvee_mobile.Controller.Layanan_Interface;
import com.example.kouvee_mobile.Controller.TransaksiLayanan_Interface;
import com.example.kouvee_mobile.Model.Layanan_Model;
import com.example.kouvee_mobile.Model.TransaksiLayanan_Model;
import com.example.kouvee_mobile.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detail_Layanan_TransaksiLayanan extends AppCompatActivity {
    private EditText pIdLayanan, pJumlahTransaksi, pSubtotalTransaksi;
    private String id_transaksi ,id_layanan, jumlah_transaksi, subtotal_transaksi;
    private int id;

    public String sp_IdTransaksi = "";
    private int jumlah, harga;

    private Spinner spinnerLayanan;
    private List<String> listSpinnerLayanan;

    private Menu action;

    private TransaksiLayanan_Interface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_layanan_transaksi);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        pIdLayanan = findViewById(R.id.NamaLayananJoinTransaksi);
        pSubtotalTransaksi = findViewById(R.id.SubtotalLayananTransaksi);
        pJumlahTransaksi = findViewById(R.id.JumlahLayananTransaksi);
        pJumlahTransaksi.setInputType(InputType.TYPE_CLASS_NUMBER);

        listSpinnerLayanan = new ArrayList<>();

        spinnerLayanan = findViewById(R.id.spinnerLayananTransaksi);

        Intent intent = getIntent();
        id = intent.getIntExtra("id_detail_transaksi", 0);
        id_transaksi = intent.getStringExtra("id_transaksi");
        id_layanan = intent.getStringExtra("id_layanan");
        jumlah_transaksi = intent.getStringExtra("jumlah_transaksi_layanan");
        subtotal_transaksi = intent.getStringExtra("subtotal_transaksi_layanan");

        setDataFromIntentExtra();

        loadSpinnerLayanan();

        spinnerLayanan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                compareSpinnerLayanan();
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {}
        });

    }

    private void setDataFromIntentExtra() {
        if (id != 0) {
            readMode();

            pIdLayanan.setText(id_layanan);
            pJumlahTransaksi.setText(jumlah_transaksi);
            pSubtotalTransaksi.setText(subtotal_transaksi);

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.skipMemoryCache(true);
            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
            requestOptions.placeholder(R.drawable.add);
            requestOptions.error(R.drawable.add);

            spinnerLayanan.setVisibility(View.GONE);

        } else {
            getSupportActionBar().setTitle("Tambah Layanan");

            setUpdateSubtotal(pJumlahTransaksi);
            pIdLayanan.setVisibility(View.GONE);

        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.transaksi_layanan_detail_menu, menu);
        action = menu;
        action.findItem(R.id.menu_save).setVisible(false);

        if (id == 0) {
            editMode();

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
            sp_IdTransaksi = sp.getString("sp_id_transaksi_layanan", "");
        }

        switch (item.getItemId()) {
            case android.R.id.home:

                this.finish();
                return true;

            case R.id.menu_edit:
                //Edit
                editMode();

                setUpdateSpinnerLayanan();

                setUpdateSubtotal(pJumlahTransaksi);

                pIdLayanan.setVisibility(View.GONE);
                spinnerLayanan.setVisibility(View.VISIBLE);

                action.findItem(R.id.menu_edit).setVisible(false);
                action.findItem(R.id.menu_delete).setVisible(false);
                action.findItem(R.id.menu_save).setVisible(true);

                return true;

            case R.id.menu_save:
                if (id == 0) {
                    //loadPreference();
                    //setForm();

                    if (TextUtils.isEmpty(pJumlahTransaksi.getText().toString()) ||
                            spinnerLayanan.getSelectedItemPosition()==0 ) {
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
                AlertDialog.Builder dialog = new AlertDialog.Builder(Detail_Layanan_TransaksiLayanan.this);
                dialog.setMessage("Menghapus Layanan?");

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

        String id_layanan = pIdLayanan.getText().toString().trim();
        String jumlah_transaksi = pJumlahTransaksi.getText().toString().trim();
        String subtotal_transaksi = pSubtotalTransaksi.getText().toString().trim();

        apiInterface = API_client.getApiClient().create(TransaksiLayanan_Interface.class);
        Call<TransaksiLayanan_Model> call =
                apiInterface.createLayananTransaksiLayanan(
                        key,
                        sp_IdTransaksi,
                        id_layanan,
                        jumlah_transaksi,
                        subtotal_transaksi);

        call.enqueue(new Callback<TransaksiLayanan_Model>() {
            public void onResponse(Call<TransaksiLayanan_Model> call, Response<TransaksiLayanan_Model> response) {
                progressDialog.dismiss();

                Log.i(Detail_TransaksiLayanan.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")) {
                    Toast.makeText(Detail_Layanan_TransaksiLayanan.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(Detail_Layanan_TransaksiLayanan.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            public void onFailure(Call<TransaksiLayanan_Model> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Detail_Layanan_TransaksiLayanan.this, t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void updateData(final String key, final int id) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating...");
        progressDialog.show();

        readMode();

        String id_layanan = pIdLayanan.getText().toString().trim();
        String jumlah_transaksi = pJumlahTransaksi.getText().toString().trim();
        String subtotal_edit = pSubtotalTransaksi.getText().toString().trim();

        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //String tgl_ubah_customer_log = simpleDateFormat.format(new Date());

        apiInterface = API_client.getApiClient().create(TransaksiLayanan_Interface.class);

        Call<TransaksiLayanan_Model> call =
                apiInterface.editLayananTransaksiLayanan(
                        key,
                        sp_IdTransaksi,
                        String.valueOf(id),     //id_detail
                        jumlah_transaksi,
                        subtotal_edit,
                        subtotal_transaksi);

        call.enqueue(new Callback<TransaksiLayanan_Model>() {
            public void onResponse(Call<TransaksiLayanan_Model> call, Response<TransaksiLayanan_Model> response) {
                progressDialog.dismiss();

                Log.i(Detail_TransaksiLayanan.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")) {
                    Toast.makeText(Detail_Layanan_TransaksiLayanan.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(Detail_Layanan_TransaksiLayanan.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            public void onFailure(Call<TransaksiLayanan_Model> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Detail_Layanan_TransaksiLayanan.this, t.getMessage().toString(),
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
        String subtotal_transkasi = pSubtotalTransaksi.getText().toString().trim();

        Call<TransaksiLayanan_Model> call = apiInterface.hapusLayananTransaksiLayanan(
                key,
                sp_IdTransaksi,
                String.valueOf(id),
                subtotal_transkasi
        );

        call.enqueue(new Callback<TransaksiLayanan_Model>() {
            @Override
            public void onResponse(Call<TransaksiLayanan_Model> call, Response<TransaksiLayanan_Model> response) {
                progressDialog.dismiss();

                Log.i(Detail_TransaksiLayanan.class.getSimpleName(), response.toString());
                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")) {
                    Toast.makeText(Detail_Layanan_TransaksiLayanan.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(Detail_Layanan_TransaksiLayanan.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TransaksiLayanan_Model> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Detail_Layanan_TransaksiLayanan.this, "Cek " +
                        t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }


    public void loadSpinnerLayanan()
    {
        Layanan_Interface apiLayanan = API_client.getApiClient().create(Layanan_Interface.class);
        Call<List<Layanan_Model>> listCall = apiLayanan.getLayanan();

        listCall.enqueue(new Callback<List<Layanan_Model>>() {
            @Override
            public void onResponse(Call<List<Layanan_Model>> call, Response<List<Layanan_Model>> response) {
                List<Layanan_Model> layananModels = response.body();
                for(int i=0; i < layananModels.size(); i++ ){
                    String name = layananModels.get(i).getNama_layanan();
                    listSpinnerLayanan.add(name);
                }
                listSpinnerLayanan.add(0,"- PILIH LAYANAN -");
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Detail_Layanan_TransaksiLayanan.this,
                        android.R.layout.simple_spinner_item, listSpinnerLayanan);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerLayanan.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Layanan_Model>> call, Throwable t) {
                Toast.makeText(Detail_Layanan_TransaksiLayanan.this, "Cek " + t.getMessage().toString(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void compareSpinnerLayanan()
    {
        Layanan_Interface apiLayanan = API_client.getApiClient().create(Layanan_Interface.class);
        Call<List<Layanan_Model>> listCall = apiLayanan.getLayanan();

        listCall.enqueue(new Callback<List<Layanan_Model>>() {
            @Override
            public void onResponse(Call<List<Layanan_Model>> call, Response<List<Layanan_Model>> response) {
                List<Layanan_Model> layananModels = response.body();
                for(int i=0; i < layananModels.size(); i++ ) {
                    String nama = layananModels.get(i).getNama_layanan();
                    String temp = spinnerLayanan.getSelectedItem().toString();

                    String list_id = String.valueOf(layananModels.get(i).getId_layanan());
                    //tempIdJenis.add(list_id);

                    if(temp.equals(nama))
                    {
                        pIdLayanan.setText(String.valueOf(layananModels.get(i).getId_layanan()));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Layanan_Model>> call, Throwable t) {
                Toast.makeText(Detail_Layanan_TransaksiLayanan.this, "Cek " + t.getMessage().toString(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }


    private void editMode() {
        pIdLayanan.setFocusableInTouchMode(true);
        pJumlahTransaksi.setFocusableInTouchMode(true);
        pSubtotalTransaksi.setFocusableInTouchMode(false);

        alertIsiNamaLayananDulu(pJumlahTransaksi);
    }

    private void readMode() {
        pIdLayanan.setFocusableInTouchMode(false);
        pJumlahTransaksi.setFocusableInTouchMode(false);
        pSubtotalTransaksi.setFocusableInTouchMode(false);

        alertDisable(pIdLayanan);
        alertDisable(pJumlahTransaksi);
        alertDisable(pSubtotalTransaksi);
    }

    private void alertDisable(EditText editText) {
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Detail_Layanan_TransaksiLayanan.this,
                        "Klik icon Edit terlebih dahulu untuk mengubah data!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void alertIsiNamaLayananDulu(final EditText editText) {
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spinnerLayanan.getSelectedItemPosition()==0)
                {
                    editText.setFocusableInTouchMode(false);
                    Toast.makeText(Detail_Layanan_TransaksiLayanan.this,
                            "Isi Nama Layanan terlebih dahulu untuk menentukan Jumlah dan Subtotal!",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    editText.setFocusableInTouchMode(true);
                }

            }
        });
    }

    private void setUpdateSpinnerLayanan()
    {
        Layanan_Interface apiLayanan = API_client.getApiClient().create(Layanan_Interface.class);
        Call<List<Layanan_Model>> listCall = apiLayanan.getLayanan();

        listCall.enqueue(new Callback<List<Layanan_Model>>() {
            @Override
            public void onResponse(Call<List<Layanan_Model>> call, Response<List<Layanan_Model>> response) {
                List<Layanan_Model> layananModels = response.body();

                String editText = pIdLayanan.getText().toString();

                for(int i=0; i < layananModels.size(); i++ ) {
                    String nama = layananModels.get(i).getNama_layanan();

                    if(editText.equals(nama))
                    {
                        spinnerLayanan.setSelection(i+1);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Layanan_Model>> call, Throwable t) {
                Toast.makeText(Detail_Layanan_TransaksiLayanan.this, "Cek " + t.getMessage().toString(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }


    private void setUpdateSubtotal(EditText et1)
    {
        et1.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0) {

                    Layanan_Interface apiLayanan = API_client.getApiClient().create(Layanan_Interface.class);
                    Call<List<Layanan_Model>> listCall = apiLayanan.getLayanan();

                    listCall.enqueue(new Callback<List<Layanan_Model>>() {
                        @Override
                        public void onResponse(Call<List<Layanan_Model>> call, Response<List<Layanan_Model>> response) {
                            List<Layanan_Model> layananModels = response.body();

                            String editText = spinnerLayanan.getSelectedItem().toString();
                            System.out.println("TES SPINNER" + editText);

                            for(int i=0; i < layananModels.size(); i++ ) {
                                String nama = layananModels.get(i).getNama_layanan();

                                if(editText.equals(nama))
                                {
                                    harga = Integer.valueOf(layananModels.get(i).getHarga_layanan());
                                    jumlah = Integer.valueOf(pJumlahTransaksi.getText().toString());
                                    int subtotal = harga * jumlah;
                                    pSubtotalTransaksi.setText(String.valueOf(subtotal));
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Layanan_Model>> call, Throwable t) {
                            Toast.makeText(Detail_Layanan_TransaksiLayanan.this, "Cek " +
                                    t.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                    });

                } else {
                    pSubtotalTransaksi.setText("0");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
}
