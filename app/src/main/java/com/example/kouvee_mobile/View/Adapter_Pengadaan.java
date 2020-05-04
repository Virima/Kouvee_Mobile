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
import com.example.kouvee_mobile.Model.Produk_Model;
import com.example.kouvee_mobile.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Pengadaan extends RecyclerView.Adapter<Adapter_Pengadaan.PengadaanViewHolder> implements Filterable {
    List<Pengadaan_Model> pengadaan, pengadaanFilter;
    private Context context;
    private Adapter_Pengadaan.RecyclerViewPengadaanClickListener mListener;
    Search_Filter_Pengadaan filter;

    public Adapter_Pengadaan(List<Pengadaan_Model> pengadaan,
                             Adapter_Pengadaan.RecyclerViewPengadaanClickListener listener) {
        this.pengadaan = pengadaan;
        this.pengadaanFilter = pengadaan;
        //this.context = context;
        this.mListener = listener;
    }

    @Override
    public Adapter_Pengadaan.PengadaanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_pengadaan,parent,false);
        return new Adapter_Pengadaan.PengadaanViewHolder(view, mListener);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(final Adapter_Pengadaan.PengadaanViewHolder holder, int position) {
        Pengadaan_Model sup = pengadaan.get(position);

        holder.pKodePengadaan.setText(sup.getKode_pengadaan());
        holder.pIdSupplier.setText(sup.getId_supplier());
        holder.pStatusPengadaan.setText(sup.getStatus_pengadaan());

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
            filter=new Search_Filter_Pengadaan((ArrayList<Pengadaan_Model>) pengadaanFilter,this);

        }
        return filter;
    }


    public class PengadaanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView pKodePengadaan, pIdSupplier, pStatusPengadaan;
        private Adapter_Pengadaan.RecyclerViewPengadaanClickListener mListener;
        private RelativeLayout mRowContainer;

        public PengadaanViewHolder(View itemView, Adapter_Pengadaan.RecyclerViewPengadaanClickListener listener) {
            super(itemView);
            pKodePengadaan = itemView.findViewById(R.id.KodePengadaan);
            pIdSupplier = itemView.findViewById(R.id.IdSupplierPengadaan);
            pStatusPengadaan = itemView.findViewById(R.id.StatusPengadaanTxt);
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

    public interface RecyclerViewPengadaanClickListener {
        void onRowClick(View view, int position);
    }
}
