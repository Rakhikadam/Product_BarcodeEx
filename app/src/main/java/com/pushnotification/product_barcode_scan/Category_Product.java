package com.pushnotification.product_barcode_scan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Category_Product#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Category_Product extends Fragment {
    DBHelper helper;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Category_Product() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Category_Product.
     */
    // TODO: Rename and change types and number of parameters
    public static Category_Product newInstance(String param1, String param2) {
        Category_Product fragment = new Category_Product();
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
        View view= inflater.inflate(R.layout.fragment_category__product, container, false);
        EditText categery = view.findViewById(R.id.category);
        Button save = view.findViewById(R.id.saveC);
        LinearLayout liner = view.findViewById(R.id.linercatrgory);
         helper = new DBHelper(getContext());

         liner.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

             }
         });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Category = categery.getText().toString();

                if (Category.isEmpty()){
                    Toast.makeText(getContext(), "Empty category is not valid", Toast.LENGTH_SHORT).show();
               return;
                }
                if (Category.contains("!@#$%^&*")){
                    Toast.makeText(getContext(), "It is not allowed", Toast.LENGTH_SHORT).show();
               return;
                }

                helper.addcategory(Category);
                categery.setText("");
            }
        });
        return view;
    }
}