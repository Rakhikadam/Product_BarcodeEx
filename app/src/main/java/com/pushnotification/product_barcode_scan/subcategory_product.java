package com.pushnotification.product_barcode_scan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link subcategory_product#newInstance} factory method to
 * create an instance of this fragment.
 */
public class subcategory_product extends Fragment {
    DBHelper helper;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public subcategory_product() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment subcategory_product.
     */
    // TODO: Rename and change types and number of parameters
    public static subcategory_product newInstance(String param1, String param2) {
        subcategory_product fragment = new subcategory_product();
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
        View view = inflater.inflate(R.layout.fragment_subcategory_product, container, false);
        EditText subcategory = view.findViewById(R.id.subcategory);
        Spinner spinner = view.findViewById(R.id.category1);
        Button save = view.findViewById(R.id.savesub);
        helper = new DBHelper(getContext());

        List<String> categorylist = new ArrayList<>();
        for (category mycat:helper.getcategory()){
            categorylist.add(mycat.getText());
        }

       /* for (int i=0;i<helper.getcategory().size();i++){
            category mycat=  helper.getcategory().get(i);

        }*/
        spinner.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, categorylist));


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Subcategory = subcategory.getText().toString();
                if (Subcategory.isEmpty()){
                    Toast.makeText(getContext(), "Empty subcategory is not valid", Toast.LENGTH_SHORT).show();
                return;
                }

                if (Subcategory.contains("!@#$%^&*")){
                    Toast.makeText(getContext(), "It is not allowed", Toast.LENGTH_SHORT).show();
              return;
                }
                helper.addsubcategory(Subcategory, spinner.getSelectedItem().toString());
                subcategory.setText("");
            }
        });

        return view;
    }
}


