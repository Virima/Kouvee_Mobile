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
import com.example.kouvee_mobile.Model.Layanan_Model;
import com.example.kouvee_mobile.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Layanan extends RecyclerView.Adapter<Adapter_Layanan.LayananViewHolder> implements Filterable {
    List<Layanan_Model> layanan, layananFilter;
    private Context context;
    private Adapter_Layanan.RecyclerViewLayananClickListener mListener;
    Search_Filter_Layanan filter;

    @Override
    public Adapter_Layanan.LayananViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_layanan,parent,false);
        return new Adapter_Layanan.LayananViewHolder(view, mListener);
    }

    public Adapter_Layanan(List<Layanan_Model> layanan, Adapter_Layanan.RecyclerViewLayananClickListener listener) {
        this.layanan = layanan;
        this.layananFilter = layanan;
        //this.context = context;
        this.mListener = listener;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(final Adapter_Layanan.LayananViewHolder holder, int position) {

        Layanan_Model sup = layanan.get(position);

        //String id_string = sup.getId_layanan()

        holder.pNamaLayanan.setText(sup.getNama_layanan());
        //holder.pIdLayanan.setText(sup.getId_layanan());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.skipMemoryCache(true);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        requestOptions.placeholder(R.drawable.add);
        requestOptions.error(R.drawable.add);

    }

    @Override
    public int getItemCount() {
        return layanan.size();
    }

    public Filter getFilter() {
        if (filter==null) {
            filter=new Search_Filter_Layanan((ArrayList<Layanan_Model>) layananFilter,this);

        }
        return filter;
    }

    public class LayananViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView pIdLayanan, pNamaLayanan;
        private Adapter_Layanan.RecyclerViewLayananClickListener mListener;
        private RelativeLayout mRowContainer;

        public LayananViewHolder(View itemView, Adapter_Layanan.RecyclerViewLayananClickListener listener) {
            super(itemView);
            //pIdLayanan = itemView.findViewById(R.id.IdLayanan);
            pNamaLayanan = itemView.findViewById(R.id.NamaLayanan);
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

    public interface RecyclerViewLayananClickListener {
        void onRowClick(View view, int position);
    }
}
