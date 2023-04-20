package com.pushnotification.product_barcode_scan;

import static android.content.Context.WINDOW_SERVICE;
import static androidx.core.content.ContextCompat.getExternalFilesDirs;
import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Product_Table#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Product_Table extends Fragment {
    DBHelper helper;
    Bitmap bitmap;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private JSONObject object;

    public Product_Table() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Product_Table.
     */
    // TODO: Rename and change types and number of parameters
    public static Product_Table newInstance(String param1, String param2) {
        Product_Table fragment = new Product_Table();
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
        View view = inflater.inflate(R.layout.fragment_product__table, container, false);
        AutoCompleteTextView spname = view.findViewById(R.id.spinner6);
        Spinner category = view.findViewById(R.id.spinner1);
        Spinner spnsubcategory = view.findViewById(R.id.spinner2);
        Spinner spnbrand = view.findViewById(R.id.spinner3);
        Spinner spnmodel = view.findViewById(R.id.spinner4);
        TextView count = view.findViewById(R.id.count);
        Button generateqr = view.findViewById(R.id.generateQR);
        EditText MRP = view.findViewById(R.id.MRP);
        EditText stockIn = view.findViewById(R.id.stockin);
        helper = new DBHelper(getContext());

        LinearLayout liner = view.findViewById(R.id.linearproduct);
        liner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        List<String> list = new ArrayList<>();
        list.add("Select Category");
        for (category list1 : helper.getcategory()) {
            list.add(list1.getText());
        }
        category.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list));


        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    List<subcategory> list1 = helper.getsubcategoryforCategory(category.getSelectedItem().toString());
                    Log.e("TAG", "onItemSelected: " + list1.size());

                    List<String> subcategory = new ArrayList<>();
                    subcategory.add("Select SubCategory");
                    for (subcategory sub : list1) {
                        subcategory.add(sub.getText());
                    }
                    spnsubcategory.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, subcategory));

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spnsubcategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    List<brand> mlist = helper.getbrandforsubcategory(spnsubcategory.getSelectedItem().toString());
                    List<String> brand = new ArrayList<>();
                    brand.add("Select Model");
                    for (brand br : mlist) {
                        brand.add(br.getText());
                    }
                    spnbrand.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, brand));


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnbrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    List<model> models = helper.getmodelforbrand(spnbrand.getSelectedItem().toString());
                    List<String> list1 = new ArrayList<>();
                    list1.add("Select model");
                    for (model br : models) {
                        list1.add(br.getText());
                    }
                    spnmodel.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list1));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 2) {
                    List<namepro> product = helper.searchname(s.toString(),spnmodel.getSelectedItem().toString());
                    List<String> namelist = new ArrayList<>();
                    for (namepro name: product) {
                    namelist.add(name.getText());
                    }
                    spname.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, namelist));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        generateqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                object = new JSONObject();
                try {
//                    object.put("name",name.getSelectedItem().toString());
                    object.put("category", category.getSelectedItem().toString());
                    object.put("sucategory", spnsubcategory.getSelectedItem().toString());
                    object.put("brand", spnbrand.getSelectedItem().toString());
                    object.put("name", spname.getText().toString());
                    object.put("model", spnmodel.getSelectedItem().toString());
                    object.put("MRP_price", MRP.getText().toString());
                    object.put("Stock_price", stockIn.getText().toString());

                    Bitmap bitmap1 = textToImageEncode(object.toString());  //called bitmap method and pass JSON object
                    Dialog dialog = new Dialog(getContext());
                    ImageView imageView = new ImageView(getContext());
                    imageView.setImageBitmap(bitmap1);
                    dialog.setContentView(imageView);
                    dialog.show();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                } catch (WriterException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        return view;


    }

    //.create bitmap method. bitmap using image create
    private Bitmap textToImageEncode(String value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    500, 500, null);
        } catch (IllegalArgumentException e) {
            return null;
        }

        int bitMatrixWidth = bitMatrix.getWidth();
        int bitMatrixHeight = bitMatrix.getHeight();
        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offSet = y * bitMatrixWidth;
            for (int x = 0; x < bitMatrixWidth; x++) {
                pixels[offSet + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.black) : getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);
        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
       /* File folder = new File(Environment.getExternalStorageDirectory()+"/photos/");
        if (!folder.exists()){
            folder.mkdir();
        }
        String path  = Environment.getExternalStorageDirectory()+"/photos/qr.jpg";
        File file =new File(path);
        try {
            OutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
            outputStream.flush

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        */
        return bitmap;
    }
}