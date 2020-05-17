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
import com.example.kouvee_mobile.Model.TransaksiLayanan_Model;
import com.example.kouvee_mobile.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_TransaksiLayanan extends RecyclerView.Adapter<Adapter_TransaksiLayanan.TransaksiLayananViewHolder>
        implements Filterable {
    List<TransaksiLayanan_Model> transaksi, transaksiFilter;
    private Context context;
    private Adapter_TransaksiLayanan.RecyclerViewTransaksiLayananClickListener mListener;
    Search_Filter_TransaksiLayanan filter;

    public Adapter_TransaksiLayanan(List<TransaksiLayanan_Model> transaksi,
                                   Adapter_TransaksiLayanan.RecyclerViewTransaksiLayananClickListener listener) {
        this.transaksi = transaksi;
        this.transaksiFilter = transaksi;
        //this.context = context;
        this.mListener = listener;
    }

    @Override
    public Adapter_TransaksiLayanan.TransaksiLayananViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_transaksi_layanan,
                parent, false);
        return new Adapter_TransaksiLayanan.TransaksiLayananViewHolder(view, mListener);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(final Adapter_TransaksiLayanan.TransaksiLayananViewHolder holder, int position) {
        TransaksiLayanan_Model sup = transaksi.get(position);

        holder.pKodeTransaksi.setText(sup.getKode_transaksi_layanan());
        holder.pIdHewan.setText(sup.getId_hewan());
        holder.pIdLayanan.setText(sup.getId_layanan());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.skipMemoryCache(true);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        requestOptions.placeholder(R.drawable.add);
        requestOptions.error(R.drawable.add);

    }

    @Override
    public int getItemCount() {
        return transaksi.size();
    }

    public Filter getFilter() {
        if (filter==null) {
            filter=new Search_Filter_TransaksiLayanan((ArrayList<TransaksiLayanan_Model>) transaksiFilter,this);

        }
        return filter;
    }


    public class TransaksiLayananViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView pKodeTransaksi, pIdHewan, pIdLayanan;
        private Adapter_TransaksiLayanan.RecyclerViewTransaksiLayananClickListener mListener;
        private RelativeLayout mRowContainer;

        public TransaksiLayananViewHolder(View itemView,
                                         Adapter_TransaksiLayanan.RecyclerViewTransaksiLayananClickListener listener) {
            super(itemView);
            pKodeTransaksi = itemView.findViewById(R.id.KodeTransaksiLayanan);
            pIdHewan = itemView.findViewById(R.id.IdHewanJoinTransLyn);
            pIdLayanan = itemView.findViewById(R.id.IdLayananJoinTransLyn);

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

    public interface RecyclerViewTransaksiLayananClickListener {
        void onRowClick(View view, int position);
    }
}
