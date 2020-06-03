package com.example.kouvee_mobile.View;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.kouvee_mobile.Controller.API_client;
import com.example.kouvee_mobile.Controller.Produk_Interface;
import com.example.kouvee_mobile.Model.Produk_Model;
import com.example.kouvee_mobile.R;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detail_Produk extends AppCompatActivity {
    private EditText pNamaProduk, pSatuanProduk, pStokProduk, pStokMin, pHargaProduk,
            pTglDibuat, pTglDiubah, pUserLog;
    private String nama_produk, satuan_produk, stok_produk, stok_min, harga_produk, image_path,
            tanggal_dibuat, tanggal_diubah, user_log;
    private int id;

    public String sp_NamaPegawai="";

    Button CaptureImageFromCamera;
    MenuItem saveImage;
    ImageView ImageViewHolder;
    ProgressDialog progressDialog ;
    Intent intentCam;
    public  static final int RequestPermissionCode  = 1 ;
    Bitmap bitmap;
    boolean check = true;
    String ImagePathFieldOnServer = "image_path";
    String ImageUploadPathOnServer ="http://192.168.1.2:8181/api_android/insertproduk.php";
    String ImageUpdatePathOnServer ="http://192.168.1.2:8181/api_android/editproduk.php";

    private Menu action;
    private final static String TAG = "Detail_Produk";
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Produk_Interface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__produk);

        ImageViewHolder = findViewById(R.id.imageViewProduk);
        CaptureImageFromCamera = findViewById(R.id.BtnCameraProduk);
        saveImage = findViewById(R.id.menu_save);

        EnableRuntimePermissionToAccessCamera();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        pNamaProduk = findViewById(R.id.NamaProduk);
        pSatuanProduk = findViewById(R.id.SatuanProduk);
        pStokProduk = findViewById(R.id.StokProduk);
        pStokMin = findViewById(R.id.StokMinProduk);
        pHargaProduk = findViewById(R.id.HargaProduk);
        //pImagePath = findViewById(R.id.PemilikHewanJoinHewan);
        pTglDibuat = findViewById(R.id.tanggal_tambah_produk_log);
        pTglDiubah = findViewById(R.id.tanggal_ubah_produk_log);
        pUserLog = findViewById(R.id.user_produk_log);

        pStokProduk.setInputType(InputType.TYPE_CLASS_NUMBER);
        pStokMin.setInputType(InputType.TYPE_CLASS_NUMBER);
        pHargaProduk.setInputType(InputType.TYPE_CLASS_NUMBER);

        CaptureImageFromCamera.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 7);
                    intentCam = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intentCam, 7);
                }
                else
                {
                    intentCam = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intentCam, 7);
                }
            }
        });

        Intent intent = getIntent();
        id = intent.getIntExtra("id_produk", 0);
        nama_produk = intent.getStringExtra("nama_produk");
        satuan_produk = intent.getStringExtra("satuan_produk");
        stok_produk = intent.getStringExtra("stok_produk");
        stok_min = intent.getStringExtra("stok_min_produk");
        harga_produk = intent.getStringExtra("harga_produk");
        image_path = intent.getStringExtra("image_path");
        tanggal_dibuat = intent.getStringExtra("tanggal_tambah_produk_log");
        tanggal_diubah = intent.getStringExtra("tanggal_ubah_produk_log");
        user_log = intent.getStringExtra("user_produk_log");
        setDataFromIntentExtra();
    }

    private void setDataFromIntentExtra() {
        if (id != 0) {
            readMode();
            getSupportActionBar().setTitle(nama_produk.toString());     //Header

            CaptureImageFromCamera.setVisibility(View.GONE);

            pNamaProduk.setText(nama_produk);
            pSatuanProduk.setText(satuan_produk);
            pStokProduk.setText(stok_produk);
            pStokMin.setText(stok_min);
            pHargaProduk.setText(harga_produk);
            Picasso.get().load(image_path).
                    resize(1000, 1000).centerCrop().into(ImageViewHolder);
            pTglDibuat.setText(tanggal_dibuat);
            pTglDiubah.setText(tanggal_diubah);
            pUserLog.setText(user_log);

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.skipMemoryCache(true);
            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
            requestOptions.placeholder(R.drawable.add);
            requestOptions.error(R.drawable.add);

        } else {
            getSupportActionBar().setTitle("Tambah Produk");
            pTglDibuat.setVisibility(View.GONE);
            pTglDiubah.setVisibility(View.GONE);
            pUserLog.setVisibility(View.GONE);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.produk_detail_menu, menu);
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
            sp_NamaPegawai = sp.getString("sp_nama_pegawai", "");
        }

        switch (item.getItemId()) {
            case android.R.id.home:

                this.finish();

                return true;

            case R.id.menu_edit:
                //Edit
                editMode();
                CaptureImageFromCamera.setVisibility(View.VISIBLE);
                action.findItem(R.id.menu_edit).setVisible(false);
                action.findItem(R.id.menu_delete).setVisible(false);
                action.findItem(R.id.menu_save).setVisible(true);

                return true;

            case R.id.menu_save:
                if (id == 0) {
                    CaptureImageFromCamera.setVisibility(View.VISIBLE);

                    if (TextUtils.isEmpty(pNamaProduk.getText().toString()) ||
                            TextUtils.isEmpty(pSatuanProduk.getText().toString()) ||
                            TextUtils.isEmpty(pStokProduk.getText().toString()) ||
                            TextUtils.isEmpty(pStokMin.getText().toString()) ||
                            TextUtils.isEmpty(pHargaProduk.getText().toString())) {
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
                        //sehabis insert muncul icon edit dan delete
                        //postData("insert");
                        nama_produk = pNamaProduk.getText().toString().trim();
                        satuan_produk = pSatuanProduk.getText().toString().trim();
                        stok_produk = pStokProduk.getText().toString().trim();
                        stok_min = pStokMin.getText().toString().trim();
                        harga_produk = pHargaProduk.getText().toString().trim();

                        ImageUploadToServerFunctionInsert();

                        action.findItem(R.id.menu_edit).setVisible(true);
                        action.findItem(R.id.menu_save).setVisible(false);
                        action.findItem(R.id.menu_delete).setVisible(true);

                        readMode();
                    }

                } else {
                    nama_produk = pNamaProduk.getText().toString().trim();
                    satuan_produk = pSatuanProduk.getText().toString().trim();
                    stok_produk = pStokProduk.getText().toString().trim();
                    stok_min = pStokMin.getText().toString().trim();
                    harga_produk = pHargaProduk.getText().toString().trim();

                    ImageUploadToServerFunctionUpdate(id);
                    //updateData("update", id);
                    action.findItem(R.id.menu_edit).setVisible(true);
                    action.findItem(R.id.menu_save).setVisible(false);
                    action.findItem(R.id.menu_delete).setVisible(true);

                    readMode();
                }
                return true;

            case R.id.menu_delete:
                AlertDialog.Builder dialog = new AlertDialog.Builder(Detail_Produk.this);
                dialog.setMessage("Menghapus Produk?");

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

    /*
    private void postData(final String key) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Menyimpan...");
        progressDialog.show();

        readMode();

        String nama_produk = pNamaProduk.getText().toString().trim();
        String satuan_produk = pSatuanProduk.getText().toString().trim();
        final String stok_produk = pStokProduk.getText().toString().trim();
        final String stok_min = pStokMin.getText().toString().trim();
        String harga_produk = pHargaProduk.getText().toString().trim();
        //ImageUploadToServerFunction();

        apiInterface = API_client.getApiClient().create(Produk_Interface.class);

        Call<Produk_Model> call =
                apiInterface.createProduk(key, nama_produk, satuan_produk, stok_produk, stok_min, harga_produk,
                        ImageViewHolder.toString(), sp_NamaPegawai);

        call.enqueue(new Callback<Produk_Model>() {
            public void onResponse(Call<Produk_Model> call, Response<Produk_Model> response) {
                progressDialog.dismiss();

                Log.i(Detail_Produk.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")) {
                    finish();
                } else {
                    Toast.makeText(Detail_Produk.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            public void onFailure(Call<Produk_Model> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Detail_Produk.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    */

    /*
    private void updateData(final String key, final int id) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating...");
        progressDialog.show();

        readMode();

        String nama_produk = pNamaProduk.getText().toString().trim();
        String satuan_produk = pSatuanProduk.getText().toString().trim();
        final String stok_produk = pStokProduk.getText().toString().trim();
        final String stok_min = pStokMin.getText().toString().trim();
        String harga_produk = pHargaProduk.getText().toString().trim();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String tgl_ubah_produk_log = simpleDateFormat.format(new Date());

        apiInterface = API_client.getApiClient().create(Produk_Interface.class);

        Call<Produk_Model> call =
                apiInterface.editProduk(key, String.valueOf(id), nama_produk, satuan_produk, stok_produk,
                        stok_min, harga_produk, tgl_ubah_produk_log, sp_NamaPegawai);


        call.enqueue(new Callback<Produk_Model>() {
            public void onResponse(Call<Produk_Model> call, Response<Produk_Model> response) {
                progressDialog.dismiss();

                Log.i(Detail_Produk.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")) {
                    Toast.makeText(Detail_Produk.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Detail_Produk.this, message, Toast.LENGTH_SHORT).show();
                }

                Intent back = new Intent(Detail_Produk.this, Activity_Produk.class);
                startActivity(back);
            }

            public void onFailure(Call<Produk_Model> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Detail_Produk.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    } */

    private void deleteData(String key, int id) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Menghapus...");
        progressDialog.show();
        readMode();

        apiInterface = API_client.getApiClient().create(Produk_Interface.class);

        Call<Produk_Model> call = apiInterface.hapusProduk(key, String.valueOf(id), sp_NamaPegawai);

        call.enqueue(new Callback<Produk_Model>() {
            @Override
            public void onResponse(Call<Produk_Model> call, Response<Produk_Model> response) {
                progressDialog.dismiss();

                Log.i(Detail_Produk.class.getSimpleName(), response.toString());
                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")) {
                    Toast.makeText(Detail_Produk.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(Detail_Produk.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Produk_Model> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Detail_Produk.this, "Cek " + t.getMessage().toString(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void editMode() {
        pNamaProduk.setFocusableInTouchMode(true);
        pSatuanProduk.setFocusableInTouchMode(true);
        pStokProduk.setFocusableInTouchMode(true);
        pStokMin.setFocusableInTouchMode(true);     //enable
        pHargaProduk.setFocusableInTouchMode(true);
        pTglDibuat.setFocusableInTouchMode(false);
        pTglDiubah.setFocusableInTouchMode(false);
        pUserLog.setFocusableInTouchMode(false);
    }

    private void readMode() {
        pNamaProduk.setFocusableInTouchMode(false);
        pSatuanProduk.setFocusableInTouchMode(false);
        pStokProduk.setFocusableInTouchMode(false);
        pStokMin.setFocusableInTouchMode(false);
        pHargaProduk.setFocusableInTouchMode(false);
        pTglDibuat.setFocusableInTouchMode(false);
        pTglDiubah.setFocusableInTouchMode(false);
        pUserLog.setFocusableInTouchMode(false);

        alertDisable(pNamaProduk);
        alertDisable(pSatuanProduk);
        alertDisable(pStokProduk);
        alertDisable(pStokMin);
        alertDisable(pSatuanProduk);
        alertDisable(pHargaProduk);
    }

    private void alertDisable(EditText editText) {
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Detail_Produk.this,
                        "Klik icon Edit terlebih dahulu untuk mengubah data!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    // SEPUTAR CAMERA
    // Start activity for result method to Set captured image on image view after click.
    protected void onActivityResult(int requestCode, int resultCode, Intent intentCam) {

        super.onActivityResult(requestCode, resultCode, intentCam);
        Uri uri = intentCam.getData();

        if(resultCode != RESULT_CANCELED){

            if (requestCode == 7) {
                bitmap = (Bitmap) intentCam.getExtras().get("data");
                ImageViewHolder.setImageBitmap(bitmap);
            }
        }
    }

    // Requesting runtime permission to access camera.
    public void EnableRuntimePermissionToAccessCamera(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(Detail_Produk.this,
                Manifest.permission.CAMERA))
        {
            // Printing toast message after enabling runtime permission.
            Toast.makeText(Detail_Produk.this,"CAMERA permission allows us to Access CAMERA app",
                    Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(Detail_Produk.this,new String[]{Manifest.permission.CAMERA},
                    RequestPermissionCode);
        }
    }

    // Upload captured image online on server function.
    public void ImageUploadToServerFunctionInsert() {
        ByteArrayOutputStream bao;
        bao = new ByteArrayOutputStream();

        // Converting bitmap image to jpeg format, so by default image will upload in jpeg format.
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bao);

        byte[] byteArrayVar = bao.toByteArray();

        final String ConvertImage = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);

        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // Showing progress dialog at image upload time.
                progressDialog = ProgressDialog.show(Detail_Produk.this,
                        "Image is Uploading","Please Wait",false,false);
            }

            @Override
            protected void onPostExecute(String string1) {
                super.onPostExecute(string1);

                // Dismiss the progress dialog after done uploading.
                progressDialog.dismiss();

                // Printing uploading success message coming from server on android app.
                Toast.makeText(Detail_Produk.this,string1 + "Success~",Toast.LENGTH_LONG).show();

                // Setting image as transparent after done uploading.
                ImageViewHolder.setImageResource(android.R.color.transparent);
            }

            @Override
            protected String doInBackground(Void... params) {
                ImageProcessClass imageProcessClass = new ImageProcessClass();
                HashMap<String,String> HashMapParams = new HashMap<String,String>();

                HashMapParams.put("nama_produk", nama_produk);
                HashMapParams.put("satuan_produk", satuan_produk);
                HashMapParams.put("stok_produk", stok_produk);
                HashMapParams.put("stok_min_produk", stok_min);
                HashMapParams.put("harga_produk", harga_produk);
                HashMapParams.put("user_produk_log", sp_NamaPegawai);

                HashMapParams.put(ImagePathFieldOnServer, ConvertImage);
                String FinalData = imageProcessClass.ImageHttpRequest(ImageUploadPathOnServer, HashMapParams);
                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();
        AsyncTaskUploadClassOBJ.execute();

        Intent back = new Intent(Detail_Produk.this, Activity_Produk.class);
        startActivity(back);
    }

    public void ImageUploadToServerFunctionUpdate(final int id) {
        ByteArrayOutputStream bao;
        bao = new ByteArrayOutputStream();

        // Converting bitmap image to jpeg format, so by default image will upload in jpeg format.
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bao);

        byte[] byteArrayVar = bao.toByteArray();

        final String ConvertImage = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);

        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // Showing progress dialog at image upload time.
                progressDialog = ProgressDialog.show(Detail_Produk.this,
                        "Image is Uploading","Please Wait",false,false);
            }

            @Override
            protected void onPostExecute(String string1) {
                super.onPostExecute(string1);

                // Dismiss the progress dialog after done uploading.
                progressDialog.dismiss();

                // Printing uploading success message coming from server on android app.
                Toast.makeText(Detail_Produk.this,string1 + "Success~",Toast.LENGTH_LONG).show();

                // Setting image as transparent after done uploading.
                ImageViewHolder.setImageResource(android.R.color.transparent);
            }

            @Override
            protected String doInBackground(Void... params) {
                ImageProcessClass imageProcessClass = new ImageProcessClass();
                HashMap<String,String> HashMapParams = new HashMap<String,String>();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String tgl_ubah_produk_log = simpleDateFormat.format(new Date());

                HashMapParams.put("id_produk", String.valueOf(id));
                HashMapParams.put("nama_produk", nama_produk);
                HashMapParams.put("satuan_produk", satuan_produk);
                HashMapParams.put("stok_produk", stok_produk);
                HashMapParams.put("stok_min_produk", stok_min);
                HashMapParams.put("harga_produk", harga_produk);
                HashMapParams.put("user_produk_log", sp_NamaPegawai);
                HashMapParams.put("tanggal_ubah_produk_log", tgl_ubah_produk_log);

                HashMapParams.put(ImagePathFieldOnServer, ConvertImage);
                String FinalData = imageProcessClass.ImageHttpRequest(ImageUpdatePathOnServer, HashMapParams);
                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();
        AsyncTaskUploadClassOBJ.execute();

        Intent back = new Intent(Detail_Produk.this, Activity_Produk.class);
        startActivity(back);
    }

    public class ImageProcessClass {
        public String ImageHttpRequest(String requestURL,HashMap<String, String> PData) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                URL url;
                HttpURLConnection httpURLConnectionObject ;
                OutputStream OutPutStream;
                BufferedWriter bufferedWriterObject ;
                BufferedReader bufferedReaderObject ;
                int RC ;

                url = new URL(requestURL);
                httpURLConnectionObject = (HttpURLConnection) url.openConnection();
                httpURLConnectionObject.setReadTimeout(19000);
                httpURLConnectionObject.setConnectTimeout(19000);
                httpURLConnectionObject.setRequestMethod("POST");
                httpURLConnectionObject.setDoInput(true);
                httpURLConnectionObject.setDoOutput(true);
                OutPutStream = httpURLConnectionObject.getOutputStream();
                bufferedWriterObject = new BufferedWriter(
                        new OutputStreamWriter(OutPutStream, "UTF-8"));

                bufferedWriterObject.write(bufferedWriterDataFN(PData));
                bufferedWriterObject.flush();
                bufferedWriterObject.close();
                OutPutStream.close();

                RC = httpURLConnectionObject.getResponseCode();
                if (RC == HttpsURLConnection.HTTP_OK) {
                    bufferedReaderObject = new BufferedReader
                            (new InputStreamReader(httpURLConnectionObject.getInputStream()));
                    stringBuilder = new StringBuilder();

                    String RC2;
                    while ((RC2 = bufferedReaderObject.readLine()) != null){
                        stringBuilder.append(RC2);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {
            StringBuilder stringBuilderObject;
            stringBuilderObject = new StringBuilder();
            for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {
                if (check)
                    check = false;
                else
                    stringBuilderObject.append("&");

                stringBuilderObject.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));
                stringBuilderObject.append("=");
                stringBuilderObject.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
            }
            return stringBuilderObject.toString();
        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {
        switch (RC) {
            case RequestPermissionCode:
                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(Detail_Produk.this,
                            "Permission Akses Kamera Diaktifkan.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Detail_Produk.this,
                            "Permission Akses Kamera Ditolak.",
                            Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

}
