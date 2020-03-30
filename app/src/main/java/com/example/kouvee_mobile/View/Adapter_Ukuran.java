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
import com.example.kouvee_mobile.Model.Ukuran_Model;
import com.example.kouvee_mobile.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Ukuran extends RecyclerView.Adapter<Adapter_Ukuran.UkuranViewHolder> implements Filterable {
    List<Ukuran_Model> ukuran, ukuranFilter;
    private Context context;
    private Adapter_Ukuran.RecyclerViewUkuranClickListener mListener;
    Search_Filter_Ukuran filter;

    @Override
    public Adapter_Ukuran.UkuranViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_ukuran_hewan,parent,false);
        return new Adapter_Ukuran.UkuranViewHolder(view, mListener);
    }

    public Adapter_Ukuran(List<Ukuran_Model> ukuran, Adapter_Ukuran.RecyclerViewUkuranClickListener listener) {
        this.ukuran = ukuran;
        this.ukuranFilter = ukuran;
        //this.context = context;
        this.mListener = listener;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(final Adapter_Ukuran.UkuranViewHolder holder, int position) {

        Ukuran_Model sup = ukuran.get(position);

        holder.pNamaUkuran.setText(sup.getNama_ukuran());
        holder.pIdUkuran.setText("ID Ukuran : "+sup.getId_ukuran());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.skipMemoryCache(true);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        requestOptions.placeholder(R.drawable.add);
        requestOptions.error(R.drawable.add);

    }

    @Override
    public int getItemCount() {
        return ukuran.size();
    }

    public Filter getFilter() {
        if (filter==null) {
            filter=new Search_Filter_Ukuran((ArrayList<Ukuran_Model>) ukuranFilter,this);

        }
        return filter;
    }

    public class UkuranViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView pIdUkuran, pNamaUkuran;
        private Adapter_Ukuran.RecyclerViewUkuranClickListener mListener;
        private RelativeLayout mRowContainer;

        public UkuranViewHolder(View itemView, Adapter_Ukuran.RecyclerViewUkuranClickListener listener) {
            super(itemView);
            pIdUkuran = itemView.findViewById(R.id.IdUkuran);
            pNamaUkuran = itemView.findViewById(R.id.NamaUkuran);
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

    public interface RecyclerViewUkuranClickListener {
        void onRowClick(View view, int position);
    }
}
