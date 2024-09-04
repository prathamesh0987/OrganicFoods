package svrinfotech.com.organicfoods;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import svrinfotech.com.organicfoods.adapters.ProductSilderAdapter;
import svrinfotech.com.organicfoods.pojo.OrderedProducts;
import svrinfotech.com.organicfoods.pojo.UploadProduct;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowProduct extends Fragment implements View.OnClickListener {

    ViewPager viewPager;
    TabLayout tabLayout;
    List<String> backgroud;
    UploadProduct uploadProduct;
    Context context;
    FragmentActivity activity;
    TextView title,description,discCost,actualCost,discount;
    Button addCart,buyNow;
    LinearLayout quantity;
    CheckBox cod;
    //SelectQuantity selectQuantity;
    TextView qty;
    String kgs;
    float price;
    Button plus,minus;
    static int i=1;
    public ShowProduct() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_show_product, container, false);
        uploadProduct=(UploadProduct) getArguments().getSerializable("uploadProduct");
        init(view);
        setQuantity(i);
        return view;
    }

    private void init(View view) {
        activity=getActivity();
        context=view.getContext();
        viewPager=view.findViewById(R.id.productViewPager);
        tabLayout=view.findViewById(R.id.productTabLayout);
        backgroud=new ArrayList<>();
        backgroud.add(uploadProduct.getFirstImage());
        backgroud.add(uploadProduct.getSecondImage());
        backgroud.add(uploadProduct.getThirdImage());
        title=view.findViewById(R.id.showName);
        description=view.findViewById(R.id.showDescription);
        discCost=view.findViewById(R.id.finalCost);
        actualCost=view.findViewById(R.id.actualCost);
        discount=view.findViewById(R.id.discount);
        addCart=view.findViewById(R.id.addToCart);
        quantity=view.findViewById(R.id.quantity);
        buyNow=view.findViewById(R.id.buyNow);
        qty=view.findViewById(R.id.user_requirement);
        cod=view.findViewById(R.id.cashOnDelivery);
        plus=view.findViewById(R.id.plus);
        minus=view.findViewById(R.id.minus);
    }

    @Override
    public void onStart() {
        super.onStart();
        addCart.setOnClickListener(this);
        quantity.setOnClickListener(this);
        buyNow.setOnClickListener(this);
        plus.setOnClickListener(this);
        minus.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        viewPager.setAdapter(new ProductSilderAdapter(context,backgroud));
        tabLayout.setupWithViewPager(viewPager,true);
        Timer timer=new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(),4000,6000);
        title.setText(uploadProduct.getTitle());
        description.setText(uploadProduct.getDescription());
        int productsActualPrice=Integer.valueOf(uploadProduct.getPrice());
        float discountOnProduct=Integer.valueOf(uploadProduct.getDiscount());
        float percentValue=(discountOnProduct/100)*productsActualPrice;
        price=productsActualPrice-percentValue;
        discCost.setText(String.valueOf(price));
        discCost.setTypeface(discCost.getTypeface(), Typeface.BOLD);
        discCost.setPaintFlags(discCost.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        actualCost.setText(String.valueOf(Float.valueOf(productsActualPrice)));
        discount.setText(uploadProduct.getDiscount()+"% OFF");
        actualCost.setTypeface(actualCost.getTypeface(), Typeface.BOLD_ITALIC);
        actualCost.setPaintFlags(actualCost.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        /*ArrayAdapter aa = new ArrayAdapter(context,android.R.layout.simple_spinner_item,kgsList);
        qty.setAdapter(aa);
        qty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kgs=kgsList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addToCart:
                addToCart();
                break;
            case R.id.buyNow:
                if(cod.isChecked()) {
                    Toast.makeText(context,"You use Cash On Delivery Mode to buy this Product",Toast.LENGTH_LONG).show();
                } else {
                    kgs=qty.getText().toString();
                    OrderedProducts orderedProducts=new OrderedProducts();
                    orderedProducts.setQuantity(String.valueOf(kgs));
                    orderedProducts.setName(uploadProduct.getTitle());
                    orderedProducts.setFinalCost(String.valueOf(price));
                    orderedProducts.setImage(uploadProduct.getFirstImage());
                    Intent intent=new Intent(context,Checksum.class);
                    intent.putExtra("orderedProduct",orderedProducts);
                    startActivity(intent);
                }
                break;
            case R.id.plus:
                i+=1;
                setQuantity(i);
                break;
            case R.id.minus:
                if(i<=1) {
                    Toast.makeText(context, "Not Allow To Decrease", Toast.LENGTH_LONG).show();
                } else {
                    i-=1;
                }
                setQuantity(i);
                break;
        }
    }

    private class SliderTimer extends TimerTask {

        @Override
        public void run() {
            if(activity!=null) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(viewPager.getCurrentItem()<(backgroud.size()-1)) {
                            viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                        } else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        }
    }

    private void addToCart() {
        OrderedProducts orderedProducts=new OrderedProducts();
        orderedProducts.setQuantity(kgs);
        orderedProducts.setName(uploadProduct.getTitle());
        orderedProducts.setFinalCost(String.valueOf(price));
        orderedProducts.setImage(uploadProduct.getFirstImage());
        OrderedProducts.save(orderedProducts);
    }

    public  void setQuantity(int i) {
        qty.setText(String.valueOf(i));
    }
}