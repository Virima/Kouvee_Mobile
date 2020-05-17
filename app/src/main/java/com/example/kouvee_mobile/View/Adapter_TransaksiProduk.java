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


public class Adapter_TransaksiProduk extends RecyclerView.Adapter<Adapter_TransaksiProduk.TransaksiProdukViewHolder>
        implements Filterable {

    List<TransaksiProduk_Model> transaksi, transaksiFilter;
    private Context context;
    private Adapter_TransaksiProduk.RecyclerViewTransaksiProdukClickListener mListener;
    Search_Filter_TransaksiProduk filter;

    public Adapter_TransaksiProduk(List<TransaksiProduk_Model> transaksi,
                             Adapter_TransaksiProduk.RecyclerViewTransaksiProdukClickListener listener) {
        this.transaksi = transaksi;
        this.transaksiFilter = transaksi;
        //this.context = context;
        this.mListener = listener;
    }

    @Override
    public Adapter_TransaksiProduk.TransaksiProdukViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_transaksi_produk,
                parent, false);
        return new TransaksiProdukViewHolder(view, mListener);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(final Adapter_TransaksiProduk.TransaksiProdukViewHolder holder, int position) {
        TransaksiProduk_Model sup = transaksi.get(position);

        holder.pKodeTransaksi.setText(sup.getKode_transaksi_produk());
        holder.pIdCustomer.setText(sup.getId_customer());
        holder.pTotalTransaksi.setText(sup.getTotal_transaksi_produk());

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
            filter=new Search_Filter_TransaksiProduk((ArrayList<TransaksiProduk_Model>) transaksiFilter,this);

        }
        return filter;
    }


    public class TransaksiProdukViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView pKodeTransaksi, pIdCustomer, pTotalTransaksi;
        private Adapter_TransaksiProduk.RecyclerViewTransaksiProdukClickListener mListener;
        private RelativeLayout mRowContainer;

        public TransaksiProdukViewHolder(View itemView,
                                         Adapter_TransaksiProduk.RecyclerViewTransaksiProdukClickListener listener) {
            super(itemView);
            pKodeTransaksi = itemView.findViewById(R.id.KodeTransaksiProduk);
            pIdCustomer = itemView.findViewById(R.id.IdCustomerJoinTransPrdk);
            pTotalTransaksi = itemView.findViewById(R.id.TotalTransaksiPrdk);
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

    public interface RecyclerViewTransaksiProdukClickListener {
        void onRowClick(View view, int position);
    }
}
