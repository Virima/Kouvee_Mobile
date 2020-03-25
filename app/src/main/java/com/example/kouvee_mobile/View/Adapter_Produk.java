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

public class Adapter_Produk extends RecyclerView.Adapter<Adapter_Produk.ProdukViewHolder> implements Filterable {
    List<Produk_Model> produk, produkFilter;
    private Context context;
    private Adapter_Produk.RecyclerViewProdukClickListener mListener;
    Search_Filter_Produk filter;

    public Adapter_Produk(List<Produk_Model> produk, Adapter_Produk.RecyclerViewProdukClickListener listener) {
        this.produk = produk;
        this.produkFilter = produk;
        //this.context = context;
        this.mListener = listener;
    }

    @Override
    public Adapter_Produk.ProdukViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_produk,parent,false);
        return new Adapter_Produk.ProdukViewHolder(view, mListener); ///////////////////////////
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(final Adapter_Produk.ProdukViewHolder holder, int position) {
        Produk_Model sup = produk.get(position);
        holder.pNamaProduk.setText(sup.getNama_produk());
        holder.pStokProduk.setText(sup.getStok_produk());
        holder.pHargaProduk.setText("Rp." + sup.getHarga_produk());

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
            filter=new Search_Filter_Produk((ArrayList<Produk_Model>) produkFilter,this);

        }
        return filter;
    }


    public class ProdukViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView pNamaProduk, pStokProduk, pHargaProduk;
        private Adapter_Produk.RecyclerViewProdukClickListener mListener;
        private RelativeLayout mRowContainer;

        public ProdukViewHolder(View itemView, Adapter_Produk.RecyclerViewProdukClickListener listener) {
            super(itemView);
            pNamaProduk = itemView.findViewById(R.id.NamaProduk);
            pStokProduk = itemView.findViewById(R.id.StokProduk);
            pHargaProduk = itemView.findViewById(R.id.HargaProduk);
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
