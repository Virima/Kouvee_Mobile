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
import com.example.kouvee_mobile.Model.Produk_Model;
import com.example.kouvee_mobile.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_NotifStokProduk extends RecyclerView.Adapter<Adapter_NotifStokProduk.ProdukViewHolder> implements Filterable {
    List<Produk_Model> produk, produkFilter;
    private Context context;
    private Adapter_NotifStokProduk.RecyclerViewProdukClickListener mListener;
    Search_Filter_NotifStokProduk filter;

    public Adapter_NotifStokProduk(List<Produk_Model> produk, Adapter_NotifStokProduk.RecyclerViewProdukClickListener listener) {
        this.produk = produk;
        this.produkFilter = produk;
        //this.context = context;
        this.mListener = listener;
    }

    @Override
    public Adapter_NotifStokProduk.ProdukViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_notif_stok_produk,parent,false);
        return new Adapter_NotifStokProduk.ProdukViewHolder(view, mListener); ///////////////////////////
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(final Adapter_NotifStokProduk.ProdukViewHolder holder, int position) {
        Produk_Model sup = produk.get(position);
        holder.pNamaProduk.setText(sup.getNama_produk());
        holder.pStokProduk.setText(sup.getStok_produk());
        holder.pStokMinProduk.setText(sup.getStok_min_produk());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.skipMemoryCache(true);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        requestOptions.placeholder(R.drawable.add);
        requestOptions.error(R.drawable.add);

    }

    @Override
    public int getItemCount() {
        return produk.size();
    }

    public Filter getFilter() {
        if (filter==null) {
            filter=new Search_Filter_NotifStokProduk((ArrayList<Produk_Model>) produkFilter,this);

        }
        return filter;
    }


    public class ProdukViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView pNamaProduk, pStokProduk, pStokMinProduk;
        private Adapter_NotifStokProduk.RecyclerViewProdukClickListener mListener;
        private RelativeLayout mRowContainer;

        public ProdukViewHolder(View itemView, Adapter_NotifStokProduk.RecyclerViewProdukClickListener listener) {
            super(itemView);
            pNamaProduk = itemView.findViewById(R.id.NotifNamaProduk);
            pStokProduk = itemView.findViewById(R.id.NotifStokProduk);
            pStokMinProduk = itemView.findViewById(R.id.NotifStokMinProduk);
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

    public interface RecyclerViewProdukClickListener {
        void onRowClick(View view, int position);
    }
}
