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
import com.example.kouvee_mobile.Model.Hewan_Model;
import com.example.kouvee_mobile.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Hewan extends RecyclerView.Adapter<Adapter_Hewan.HewanViewHolder> implements Filterable {
    List<Hewan_Model> hewans, hewansFilter;
    private Context context;
    private Adapter_Hewan.RecyclerViewHewanClickListener mListener;
    Search_Filter_Hewan filter;

    public Adapter_Hewan(List<Hewan_Model> hewans, Adapter_Hewan.RecyclerViewHewanClickListener listener) {
        this.hewans = hewans;
        this.hewansFilter = hewans;
        //this.context = context;
        this.mListener = listener;
    }

    @Override
    public Adapter_Hewan.HewanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_hewan,parent,false);
        return new Adapter_Hewan.HewanViewHolder(view, mListener);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(final Adapter_Hewan.HewanViewHolder holder, int position) {
        Hewan_Model sup = hewans.get(position);
        holder.pNamaHewan.setText(sup.getNama_hewan());
        holder.pIdJenis.setText(sup.getId_jenis());
        holder.pIdCustomer.setText(sup.getId_customer());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.skipMemoryCache(true);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        requestOptions.placeholder(R.drawable.add);
        requestOptions.error(R.drawable.add);

    }

    @Override
    public int getItemCount() {
        return hewans.size();
    }

    public Filter getFilter() {
        if (filter==null) {
            filter=new Search_Filter_Hewan((ArrayList<Hewan_Model>) hewansFilter,this);

        }
        return filter;
    }


    public class HewanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView pNamaHewan,pIdJenis, pIdCustomer;
        private Adapter_Hewan.RecyclerViewHewanClickListener mListener;
        private RelativeLayout mRowContainer;

        public HewanViewHolder(View itemView, Adapter_Hewan.RecyclerViewHewanClickListener listener) {
            super(itemView);
            pNamaHewan = itemView.findViewById(R.id.NamaHewan);
            pIdJenis = itemView.findViewById(R.id.IdJenisJoinHewan);
            pIdCustomer = itemView.findViewById(R.id.IdCustomerJoinHewan);
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

    public interface RecyclerViewHewanClickListener {
        void onRowClick(View view, int position);
    }

}
