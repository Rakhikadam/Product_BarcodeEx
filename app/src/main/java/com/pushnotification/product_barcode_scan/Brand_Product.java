package com.pushnotification.product_barcode_scan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Brand_Product#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Brand_Product extends Fragment {
    DBHelper helper;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Brand_Product() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Brand_Product.
     */
    // TODO: Rename and change types and number of parameters
    public static Brand_Product newInstance(String param1, String param2) {
        Brand_Product fragment = new Brand_Product();
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
        View view= inflater.inflate(R.layout.fragment_brand__product, container, false);
        Spinner spinner = view.findViewById(R.id.subcategory1);
        EditText brand = view.findViewById(R.id.brand);
        Button save = view.findViewById(R.id.saveB);
        helper = new DBHelper(getContext());

        List<String>subcategorylist = new ArrayList<>();
        for (subcategory list : helper.getsubcategory()){
            subcategorylist.add(list.getText());

        }
        spinner.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,subcategorylist));


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Brand = brand.getText().toString();
                if (Brand.isEmpty()){
                    Toast.makeText(getContext(), "Empty brans is not valid", Toast.LENGTH_SHORT).show();
              return;
                }

                if (Brand.contains("!@#$%^&*")){
                    Toast.makeText(getContext(), "It is not allowed", Toast.LENGTH_SHORT).show();
                return;
                }
                helper.addbrand(Brand,spinner.getSelectedItem().toString());
                brand.setText("");
            }
        });

        return view;
    }
}