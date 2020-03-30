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
import com.example.kouvee_mobile.Model.Supplier_Model;
import com.example.kouvee_mobile.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Supplier extends RecyclerView.Adapter<Adapter_Supplier.SupplierViewHolder> implements Filterable {

    List<Supplier_Model> suppliers, suppliersFilter;
    private Context context;
    private RecyclerViewSupplierClickListener mListener;
    Search_Filter_Supplier filter;

    public Adapter_Supplier(List<Supplier_Model> suppliers, RecyclerViewSupplierClickListener listener) {
        this.suppliers = suppliers;
        this.suppliersFilter = suppliers;
        //this.context = context;
        this.mListener = listener;
    }

    @Override
    public SupplierViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_supplier,parent,false);
        return new SupplierViewHolder(view, mListener);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(final SupplierViewHolder holder, int position) {
        Supplier_Model sup = suppliers.get(position);
        holder.pNamaSupplier.setText(sup.getNama_supplier());
        holder.pAlamatSupplier.setText(sup.getAlamat_supplier());
        holder.pTeleponSupplier.setText("Telp: "+sup.getTelepon_supplier());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.skipMemoryCache(true);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        requestOptions.placeholder(R.drawable.add);
        requestOptions.error(R.drawable.add);

    }

    @Override
    public int getItemCount() {
        return suppliers.size();
    }

    public Filter getFilter() {
        if (filter==null) {
            filter=new Search_Filter_Supplier((ArrayList<Supplier_Model>) suppliersFilter,this);

        }
        return filter;
    }


    public class SupplierViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView pNamaSupplier,pAlamatSupplier, pTeleponSupplier;
        private RecyclerViewSupplierClickListener mListener;
        private RelativeLayout mRowContainer;

        public SupplierViewHolder(View itemView, RecyclerViewSupplierClickListener listener) {
            super(itemView);
            pNamaSupplier = itemView.findViewById(R.id.NamaSupplier);
            pAlamatSupplier = itemView.findViewById(R.id.AlamatSupplier);
            pTeleponSupplier = itemView.findViewById(R.id.TeleponSupplier);
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

    public interface RecyclerViewSupplierClickListener {
        void onRowClick(View view, int position);
    }
}
