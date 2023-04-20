package com.pushnotification.product_barcode_scan;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link productlist_recyclerview#newInstance} factory method to
 * create an instance of this fragment.
 */
public class productlist_recyclerview extends Fragment {
    DBHelper helper ;
    ProductlistAdpter adpter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public productlist_recyclerview() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment productlist_recyclerview.
     */
    // TODO: Rename and change types and number of parameters
    public static productlist_recyclerview newInstance(String param1, String param2) {
        productlist_recyclerview fragment = new productlist_recyclerview();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_productlist_recyclerview, container, false);
        RecyclerView recycler = view.findViewById(R.id.recyclelist);
        LinearLayout linear = view.findViewById(R.id.productlist);
        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        helper = new DBHelper(getContext());
        List<Product> list = helper.getproduct();
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        adpter = new ProductlistAdpter(list);
        recycler.setAdapter(adpter);
        return view;
    }
    class ProductlistAdpter extends RecyclerView.Adapter<ProductlistAdpter.CustomViewHolder>{
        List<Product> list;
        public ProductlistAdpter(List<Product> list) {
            this.list=list;
        }

        @NonNull
        @Override
        public ProductlistAdpter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
           View view = LayoutInflater.from(getContext()).inflate(R.layout.producttable,parent,false);
           CustomViewHolder holder = new CustomViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ProductlistAdpter.CustomViewHolder holder, int position) {
           holder.text1.setText(list.get(position).getCategory());
           holder.text2.setText(list.get(position).getSubcategory());
           holder.text3.setText(list.get(position).getBrand());
           holder.text4.setText(list.get(position).getModel());
           holder.text5.setText(list.get(position).getName());
           holder.text6.setText(list.get(position).getCount());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder{
            TextView text1,text2,text3,text4,text5,text6;
            public CustomViewHolder(@NonNull View itemView) {
                super(itemView);
                text1 = itemView.findViewById(R.id.Tcategory);
                text2 = itemView.findViewById(R.id.Tsubcategory);
                text3 = itemView.findViewById(R.id.Tbrand);
                text4 = itemView.findViewById(R.id.Tmodel);
                text5 = itemView.findViewById(R.id.Tproduct);
                text6 = itemView.findViewById(R.id.Tcount);

            }
        }
    }
}