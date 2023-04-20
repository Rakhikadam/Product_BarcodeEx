package com.pushnotification.product_barcode_scan;

import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Retailer_DashBoard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Retailer_DashBoard extends Fragment {
    private OrderAdpter adpter;
    List<cart> list;
    private RecyclerView recycler;
    DBHelper helper;


    //It is object create stockbutton
    ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if (result.getContents() == null) {
                    Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    //  String content = "[{\"category\":\"machine\",\"subcategory\":\"fan\",\"name\":\"100\"},{\"category\":\"machine\",\"subcategory\":\"fan\",\"name\":\"100\"},{\"category\":\"machine\",\"subcategory\":\"fan\",\"name\":\"100\"}]";
                    String content = result.getContents();   //gettin JSON data
                    processJSON(content);  //pass method
//                    Log.e("TAG", ": " + result.getContents());
//                    Toast.makeText(getContext(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                }
            });

    //It is object create stockbutton
    ActivityResultLauncher<ScanOptions> barcodeLauncher1 = registerForActivityResult(new ScanContract(),
            result -> {
                if (result.getContents() == null) {
                    Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    //  String content = "[{\"category\":\"machine\",\"subcategory\":\"fan\",\"name\":\"100\"},{\"category\":\"machine\",\"subcategory\":\"fan\",\"name\":\"100\"},{\"category\":\"machine\",\"subcategory\":\"fan\",\"name\":\"100\"}]";
                    String data = result.getContents();   //gettin JSON data
                    billingJSON(data);  //pass method
                    Log.e("TAG", ": " + result.getContents());
                    Toast.makeText(getContext(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                }
                list = helper.getcartItem();
                adpter = new OrderAdpter(list);
                recycler.setAdapter(adpter);

            });

    //create method
    private void processJSON(String content) {
        if (content.startsWith("[")) {
            try {
                JSONArray array = new JSONArray(content);
                for (int i = 0; i < array.length(); i++) {
                    String name = array.getJSONObject(i).getString("name");
                    String category = array.getJSONObject(i).getString("category");
                    String subcategory = array.getJSONObject(i).getString("sucategory");
                    String brand = array.getJSONObject(i).getString("brand");
                    String model = array.getJSONObject(i).getString("model");
                    if (helper.getproductByname(name, category, subcategory, brand, model).size() < 1) {
                        helper.addProduct(name, category, subcategory, brand, model);
                    } else {
                        helper.updateproduct(name, category, subcategory, brand, model);
                    }
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


        } else if (content.startsWith("{")) {
            JSONObject object = null;
            try {
                object = new JSONObject(content);
//                for (int i=0; i<object.length(); i++){   // for not using in JSON object becz it is single object not arraylist
                String name = object.optString("name", "NA");
                String category = object.getString("category");
                String subcategory = object.getString("sucategory");
                String brand = object.getString("brand");
                String model = object.getString("model");
                // String count = object.getString("count");
                if (helper.getproductByname(name, category, subcategory, brand, model).size() == 0) {
                    helper.addProduct(name, category, subcategory, brand, model);
                } else if (helper.getproductByname(name, category, subcategory, brand, model).size() == 1) {
                    helper.updateproduct(name, category, subcategory, brand, model);
                }else {
                    Toast.makeText(getContext(), "Wrong in addition.", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void billingJSON(String data) {
        if (data.startsWith("[")) {
            try {
                JSONArray array = new JSONArray(data);
                for (int i = 0; i < array.length(); i++) {
                    String name = array.getJSONObject(i).getString("name");
                    String category = array.getJSONObject(i).getString("category");
                    String subcategory = array.getJSONObject(i).getString("sucategory");
                    String brand = array.getJSONObject(i).getString("brand");
                    String model = array.getJSONObject(i).getString("model");
                    String count = array.getJSONObject(i).getString("count");
                    if (Integer.parseInt(helper.getproductByname(name, category, subcategory, brand, model).get(0).getCount()) < 1) {
                        Toast.makeText(getContext(), "InValid Barcode", Toast.LENGTH_SHORT).show();
                    } else {
//                        if (helper.getcartItembyName(name).size()==0){
//                            helper.addcartItem();
//                        }
//                        helper.updateproductcount(name, category, subcategory, brand, model);
                    }

                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        } else if (data.startsWith("{")) {
            try {
                JSONObject object = new JSONObject(data);
                String name = object.optString("name", "NA");
                String category = object.optString("category");
                String subcategory = object.optString("sucategory");
                String brand = object.optString("brand");
                String model = object.getString("model");
                //    String count = object.getString("count");
                if (helper.getproductByname(name, category, subcategory, brand, model).size() == 0) {
                    Toast.makeText(getContext(), "Not in stack.", Toast.LENGTH_SHORT).show();
                } else if (helper.getproductByname(name, category, subcategory, brand, model).size() == 1) {
                    if (helper.getcartItembyName(name).size() == 1) {
                        if (Integer.parseInt(helper.getproductByname(name, category, subcategory, brand, model).get(0).getCount()) > Integer.parseInt(helper.getcartItembyName(name).get(0).getCount())) {
                            helper.updateCart(name, model);
                        } else {
                            Toast.makeText(getContext(), "Not in stack.", Toast.LENGTH_SHORT).show();
                        }
                    } else if (helper.getcartItembyName(name).size() == 0) {
                        helper.addcartItem(category, subcategory, brand, model, "1", name, "");
                    } else {
                        Toast.makeText(getContext(), "Something went wrong in Cart.", Toast.LENGTH_SHORT).show();
                    }
                    //helper.updateproductcount(name,category,subcategory,brand,model);
                } else {
                    Toast.makeText(getContext(), "Something went wrong in Product.", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Retailer_DashBoard() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Retailer_DashBoard.
     */
    // TODO: Rename and change types and number of parameters
    public static Retailer_DashBoard newInstance(String param1, String param2) {
        Retailer_DashBoard fragment = new Retailer_DashBoard();
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
            mParam2 = getArguments().getString("data");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_retailer__dash_board, container, false);
        Button stockbutton = view.findViewById(R.id.stockbutton);
        Button billingbutton = view.findViewById(R.id.billing);
        RelativeLayout realtive = view.findViewById(R.id.relative);
        Button payment = view.findViewById(R.id.payment);
        Button productlist = view.findViewById(R.id.productlist);
        recycler = view.findViewById(R.id.recyecler);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        helper = new DBHelper(getContext());


        list = helper.getcartItem();
        adpter = new OrderAdpter(list);
        recycler.setAdapter(adpter);


        stockbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ScanOptions options = new ScanOptions();  //create empty constructor
                options.setDesiredBarcodeFormats(ScanOptions.QR_CODE);  //type of qrcode
                options.setPrompt("Scan a barcode");
                options.setCameraId(0);  // Use a specific camera of the device. 0 means backcamera and 1 means frondcamera
                options.setBeepEnabled(false);      //sound
                options.setBarcodeImageEnabled(true); //save image in device fileexplore
                barcodeLauncher.launch(options);  // get result


            }
        });

        billingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScanOptions options = new ScanOptions();  //create empty constructor
                options.setDesiredBarcodeFormats(ScanOptions.QR_CODE);  //type of qrcode
                options.setPrompt("Scan a barcode");
                options.setCameraId(0);  // Use a specific camera of the device. 0 means backcamera and 1 means frondcamera
                options.setBeepEnabled(false);      //sound
                options.setBarcodeImageEnabled(true); //save image in device fileexplore
                barcodeLauncher1.launch(options);  // get result


            }
        });


        realtive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long order_id = System.currentTimeMillis();
                for (cart item : list) {
                    helper.updateproductcount(item.getProductname(), item.getCatrgory(), item.getSubcategory(), item.getBrand(), item.getModel(), item.getCount());
                }
                helper.UpdateCart(String.valueOf(order_id));
            }
        });


        productlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.framereatiler,new productlist_recyclerview());
                transaction.commit();

            }
        });
        return view;
    }

    class OrderAdpter extends RecyclerView.Adapter<OrderAdpter.CustomViewHolder> {
        List<cart> list;

        public OrderAdpter(List<cart> list) {
            this.list = list;

        }

        @NonNull
        @Override
        public OrderAdpter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.producttable, parent, false);
            CustomViewHolder holder = new CustomViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull OrderAdpter.CustomViewHolder holder, int position) {
            holder.text1.setText(list.get(position).getCatrgory());
            holder.text2.setText(list.get(position).getSubcategory());
            holder.text3.setText(list.get(position).getBrand());
            holder.text4.setText(list.get(position).getModel());
            holder.text5.setText(list.get(position).getProductname());
            holder.text6.setText(list.get(position).getCount());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {
            TextView text1, text2, text3, text4, text5, text6;

            public CustomViewHolder(@NonNull View itemView) {
                super(itemView);
                text1 = itemView.findViewById(R.id.Tcategory);
                text2 = itemView.findViewById(R.id.Tsubcategory);
                text3 = itemView.findViewById(R.id.Tbrand);
                text4 = itemView.findViewById(R.id.Tmodel);
                text5 = itemView.findViewById(R.id.Tproduct);
                text6 = itemView.findViewById(R.id.Tstatus);

            }
        }
    }
}