package com.example.kouvee_mobile.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.kouvee_mobile.Model.Pengadaan_Model;
import com.example.kouvee_mobile.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Detail_Pengadaan extends RecyclerView.Adapter<Adapter_Detail_Pengadaan.DetailPengadaanViewHolder> implements Filterable {
    List<Pengadaan_Model> pengadaan, pengadaanFilter;
    private Context context;
    private Adapter_Detail_Pengadaan.RecyclerViewDetailPengadaanClickListener mListener;
    Search_Filter_Detail_Pengadaan filter;

    public Adapter_Detail_Pengadaan(List<Pengadaan_Model> pengadaan,
                             Adapter_Detail_Pengadaan.RecyclerViewDetailPengadaanClickListener listener) {
        this.pengadaan = pengadaan;
        this.pengadaanFilter = pengadaan;
        //this.context = context;
        this.mListener = listener;
    }

    @Override
    public Adapter_Detail_Pengadaan.DetailPengadaanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_produk_pengadaan,parent,false);
        return new Adapter_Detail_Pengadaan.DetailPengadaanViewHolder(view, mListener);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(final Adapter_Detail_Pengadaan.DetailPengadaanViewHolder holder, int position) {
        Pengadaan_Model sup = pengadaan.get(position);

        holder.pNamaProduk.setText(sup.getId_produk());
        holder.pJumlahProduk.setText(sup.getJumlah_pengadaan());
        holder.pSubtotalProduk.setText(sup.getSubtotal_pengadaan());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.skipMemoryCache(true);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        requestOptions.placeholder(R.drawable.add);
        requestOptions.error(R.drawable.add);
    }

    @Override
    public int getItemCount() {
        return pengadaan.size();
    }

    public Filter getFilter() {
        if (filter==null) {
            filter=new Search_Filter_Detail_Pengadaan((ArrayList<Pengadaan_Model>) pengadaanFilter,this);
        }
        return filter;
    }

    public class DetailPengadaanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView pNamaProduk, pJumlahProduk, pSubtotalProduk;
        private Adapter_Detail_Pengadaan.RecyclerViewDetailPengadaanClickListener mListener;
        private RelativeLayout mRowContainer;

        public DetailPengadaanViewHolder(View itemView, Adapter_Detail_Pengadaan.RecyclerViewDetailPengadaanClickListener listener) {
            super(itemView);
            pNamaProduk = itemView.findViewById(R.id.NamaProdukPengadaan);
            pJumlahProduk = itemView.findViewById(R.id.JumlahProdukPengadaan);
            pSubtotalProduk = itemView.findViewById(R.id.SubtotalProdukPengadaan);
            mRowContainer = itemView.findViewById(R.id.row_container);
            mListener = listener;
            mRowContainer.setOnClickListener(this);
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.row_container:
                    mListener.onRowClick(mRowContainer, getAdapterPosition());
                    break;
                default:
                    break;
            }
        }
    }

    public interface RecyclerViewDetailPengadaanClickListener {
        void onRowClick(View view, int position);
    }
}
