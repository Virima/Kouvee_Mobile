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

public class Adapter_Detail_TransaksiLayanan extends
        RecyclerView.Adapter<Adapter_Detail_TransaksiLayanan.DetailTransaksiLayananViewHolder> implements Filterable {

    List<TransaksiLayanan_Model> transaksi, transaksiFilter;
    private Context context;
    private Adapter_Detail_TransaksiLayanan.RecyclerViewDetailTransaksiClickListener mListener;
    Search_Filter_Detail_TransaksiLayanan filter;

    public Adapter_Detail_TransaksiLayanan(List<TransaksiLayanan_Model> transaksi,
                                          Adapter_Detail_TransaksiLayanan.RecyclerViewDetailTransaksiClickListener listener) {
        this.transaksi = transaksi;
        this.transaksiFilter = transaksi;
        //this.context = context;
        this.mListener = listener;
    }

    @Override
    public Adapter_Detail_TransaksiLayanan.DetailTransaksiLayananViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_layanan_transaksi_layanan,parent,false);
        return new Adapter_Detail_TransaksiLayanan.DetailTransaksiLayananViewHolder(view, mListener);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(final Adapter_Detail_TransaksiLayanan.DetailTransaksiLayananViewHolder holder, int position) {
        TransaksiLayanan_Model sup = transaksi.get(position);

        holder.pNamaLayanan.setText(sup.getId_layanan());
        holder.pJumlahLayanan.setText(sup.getJumlah_transaksi_layanan());
        holder.pSubtotalLayanan.setText(sup.getSubtotal_transaksi_layanan());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.skipMemoryCache(true);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        requestOptions.placeholder(R.drawable.add);
        requestOptions.error(R.drawable.add);
    }

    @Override
    public int getItemCount() {
        return transaksi.size();
        //return transaksi == null ? 0 : transaksi.size();
    }

    public Filter getFilter() {
        if (filter==null) {
            filter=new Search_Filter_Detail_TransaksiLayanan((ArrayList<TransaksiLayanan_Model>)
                    transaksiFilter,this);
        }
        return filter;
    }

    public class DetailTransaksiLayananViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView pNamaLayanan, pJumlahLayanan, pSubtotalLayanan;
        private Adapter_Detail_TransaksiLayanan.RecyclerViewDetailTransaksiClickListener mListener;
        private RelativeLayout mRowContainer;

        public DetailTransaksiLayananViewHolder(View itemView, Adapter_Detail_TransaksiLayanan.
                RecyclerViewDetailTransaksiClickListener listener) {
            super(itemView);
            pNamaLayanan = itemView.findViewById(R.id.NamaLayananTransLyn);
            pJumlahLayanan = itemView.findViewById(R.id.JumlahLayananTransLyn);
            pSubtotalLayanan = itemView.findViewById(R.id.SubtotalLayananTransLyn);
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

    public interface RecyclerViewDetailTransaksiClickListener {
        void onRowClick(View view, int position);
    }
}
