package svrinfotech.com.organicfoods;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import svrinfotech.com.organicfoods.firebase.Firebase;
import svrinfotech.com.organicfoods.holder.ProductHolder;
import svrinfotech.com.organicfoods.listener.RecyclerTouchListener;
import svrinfotech.com.organicfoods.pojo.UploadProduct;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateProduct extends Fragment {

    RecyclerView updateRecyclerView;
    DatabaseReference productReference;
    ArrayList<UploadProduct> uploadProduct;
    Context context;

    public UpdateProduct() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_update_product, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        updateRecyclerView=view.findViewById(R.id.updateRecyclerView);
        context=view.getContext();
        productReference= Firebase.getProductReference();
        uploadProduct=new ArrayList<>();
    }

    @Override
    public void onStart() {
        super.onStart();
        updateRecyclerView.setHasFixedSize(true);
        updateRecyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public void onResume() {
        super.onResume();

        FirebaseRecyclerOptions<UploadProduct> firebaseRecyclerOptions=new FirebaseRecyclerOptions.
                Builder<UploadProduct>()
                .setQuery(productReference,UploadProduct.class)
                .build();

        FirebaseRecyclerAdapter firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<UploadProduct, ProductHolder>(firebaseRecyclerOptions) {


            @NonNull
            @Override
            public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.product_row, parent, false);
                return new ProductHolder(itemView);
            }

            @Override
            protected void onBindViewHolder(@NonNull ProductHolder holder, int position, @NonNull UploadProduct model) {
                holder.setProductTitle(model.getTitle());
                holder.setProductImage(model.getFirstImage(),context);
                int productsActualPrice=Integer.valueOf(model.getPrice());
                float discountOnProduct=Integer.valueOf(model.getDiscount());
                float percentValue=(discountOnProduct/100)*productsActualPrice;
                float price=productsActualPrice-percentValue;
                holder.setActualPrice(String.valueOf(Float.valueOf(model.getPrice())));
                holder.setProductPrice(String.valueOf(price));
                holder.setProductDiscount(model.getDiscount());
                uploadProduct.add(model);
            }
        };

        updateRecyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();

        updateRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(context,
                updateRecyclerView,
                new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        if(uploadProduct!=null) {
                            Fragment fragment=new UpdateDetails();
                            Bundle bundle=new Bundle();
                            bundle.putSerializable("product",uploadProduct.get(position));
                            fragment.setArguments(bundle);
                            getFragmentManager().beginTransaction().replace(R.id.replaceFragment,fragment).commitAllowingStateLoss();
                        }
                    }

                    @Override
                    public void onHold(View view, int position) {

                    }
                }));
    }
}