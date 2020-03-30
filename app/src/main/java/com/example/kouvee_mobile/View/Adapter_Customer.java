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
import com.example.kouvee_mobile.Model.Customer_Model;
import com.example.kouvee_mobile.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Customer extends RecyclerView.Adapter<Adapter_Customer.CustomerViewHolder> implements Filterable {
    List<Customer_Model> customers, customersFilter;
    private Context context;
    private Adapter_Customer.RecyclerViewCustomerClickListener mListener;
    Search_Filter_Customer filter;

    public Adapter_Customer(List<Customer_Model> customers, Adapter_Customer.RecyclerViewCustomerClickListener listener) {
        this.customers = customers;
        this.customersFilter = customers;
        //this.context = context;
        this.mListener = listener;
    }

    @Override
    public Adapter_Customer.CustomerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_customer,parent,false);
        return new Adapter_Customer.CustomerViewHolder(view, mListener);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(final Adapter_Customer.CustomerViewHolder holder, int position) {
        Customer_Model sup = customers.get(position);
        holder.pNamaCustomer.setText(sup.getNama_customer());
        holder.pIdCustomer.setText("ID Customer : "+sup.getIdCustomer());
        holder.pTeleponCustomer.setText("Telp: "+sup.getTelepon_customer());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.skipMemoryCache(true);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        requestOptions.placeholder(R.drawable.add);
        requestOptions.error(R.drawable.add);

    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    public Filter getFilter() {
        if (filter==null) {
            filter=new Search_Filter_Customer((ArrayList<Customer_Model>) customersFilter,this);

        }
        return filter;
    }


    public class CustomerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView pNamaCustomer, pTeleponCustomer, pIdCustomer;
        private Adapter_Customer.RecyclerViewCustomerClickListener mListener;
        private RelativeLayout mRowContainer;

        public CustomerViewHolder(View itemView, Adapter_Customer.RecyclerViewCustomerClickListener listener) {
            super(itemView);
            pNamaCustomer = itemView.findViewById(R.id.NamaCustomer);
            pIdCustomer = itemView.findViewById(R.id.IdCustomer);
            pTeleponCustomer = itemView.findViewById(R.id.TeleponCustomer);
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

    public interface RecyclerViewCustomerClickListener {
        void onRowClick(View view, int position);
    }

}
