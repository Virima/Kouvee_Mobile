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
import com.example.kouvee_mobile.Model.TransaksiProduk_Model;
import com.example.kouvee_mobile.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Detail_TransaksiProduk extends
        RecyclerView.Adapter<Adapter_Detail_TransaksiProduk.DetailTransaksiProdukViewHolder> implements Filterable {

    List<TransaksiProduk_Model> transaksi, transaksiFilter;
    private Context context;
    private Adapter_Detail_TransaksiProduk.RecyclerViewDetailTransaksiClickListener mListener;
    Search_Filter_Detail_TransaksiProduk filter;

    public Adapter_Detail_TransaksiProduk(List<TransaksiProduk_Model> transaksi,
                                    Adapter_Detail_TransaksiProduk.RecyclerViewDetailTransaksiClickListener listener) {
        this.transaksi = transaksi;
        this.transaksiFilter = transaksi;
        //this.context = context;
        this.mListener = listener;
    }

    @Override
    public Adapter_Detail_TransaksiProduk.DetailTransaksiProdukViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_produk_transaksi_produk,parent,false);
        return new Adapter_Detail_TransaksiProduk.DetailTransaksiProdukViewHolder(view, mListener);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(final Adapter_Detail_TransaksiProduk.DetailTransaksiProdukViewHolder holder, int position) {
        TransaksiProduk_Model sup = transaksi.get(position);

        holder.pNamaProduk.setText(sup.getId_produk());
        holder.pJumlahProduk.setText(sup.getJumlah_transaksi_produk());
        holder.pSubtotalProduk.setText(sup.getSubtotal_transaksi_produk());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.skipMemoryCache(true);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        requestOptions.placeholder(R.drawable.add);
        requestOptions.error(R.drawable.add);
    }

    @Override
    public int getItemCount() {
        //return transaksi.size();
        return transaksi == null ? 0 : transaksi.size();
    }

    public Filter getFilter() {
        if (filter==null) {
            filter=new Search_Filter_Detail_TransaksiProduk((ArrayList<TransaksiProduk_Model>)
                    transaksiFilter,this);
        }
        return filter;
    }

    public class DetailTransaksiProdukViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView pNamaProduk, pJumlahProduk, pSubtotalProduk;
        private Adapter_Detail_TransaksiProduk.RecyclerViewDetailTransaksiClickListener mListener;
        private RelativeLayout mRowContainer;

        public DetailTransaksiProdukViewHolder(View itemView, Adapter_Detail_TransaksiProduk.
                RecyclerViewDetailTransaksiClickListener listener) {
            super(itemView);
            pNamaProduk = itemView.findViewById(R.id.NamaProdukTransaksiProduk);
            pJumlahProduk = itemView.findViewById(R.id.JumlahProdukTransaksi);
            pSubtotalProduk = itemView.findViewById(R.id.SubtotalProdukTransaksi);
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
