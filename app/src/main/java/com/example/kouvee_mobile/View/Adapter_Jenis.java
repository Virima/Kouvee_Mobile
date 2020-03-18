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
import com.example.kouvee_mobile.Model.Jenis_Model;
import com.example.kouvee_mobile.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Jenis extends RecyclerView.Adapter<Adapter_Jenis.JenisViewHolder> implements Filterable {
    List<Jenis_Model> jenis, jenisFilter;
    private Context context;
    private Adapter_Jenis.RecyclerViewJenisClickListener mListener;
    Search_Filter_Jenis filter;

    @Override
    public Adapter_Jenis.JenisViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_jenis_hewan,parent,false);
        return new Adapter_Jenis.JenisViewHolder(view, mListener);
    }

    public Adapter_Jenis(List<Jenis_Model> jenis, Adapter_Jenis.RecyclerViewJenisClickListener listener) {
        this.jenis = jenis;
        this.jenisFilter = jenis;
        //this.context = context;
        this.mListener = listener;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(final Adapter_Jenis.JenisViewHolder holder, int position) {

        Jenis_Model sup = jenis.get(position);

        //String id_string = sup.getId_layanan()

        holder.pNamaJenis.setText(sup.getNama_jenis());
        //holder.pIdLayanan.setText(sup.getId_layanan());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.skipMemoryCache(true);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        requestOptions.placeholder(R.drawable.add);
        requestOptions.error(R.drawable.add);

    }

    @Override
    public int getItemCount() {
        return jenis.size();
    }

    public Filter getFilter() {
        if (filter==null) {
            filter=new Search_Filter_Jenis((ArrayList<Jenis_Model>) jenisFilter,this);

        }
        return filter;
    }

    public class JenisViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView pIdJenis, pNamaJenis;
        private Adapter_Jenis.RecyclerViewJenisClickListener mListener;
        private RelativeLayout mRowContainer;

        public JenisViewHolder(View itemView, Adapter_Jenis.RecyclerViewJenisClickListener listener) {
            super(itemView);
            //pIdLayanan = itemView.findViewById(R.id.IdLayanan);
            pNamaJenis = itemView.findViewById(R.id.NamaJenis);
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

    public interface RecyclerViewJenisClickListener {
        void onRowClick(View view, int position);
    }
}
