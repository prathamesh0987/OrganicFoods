package svrinfotech.com.organicfoods.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import svrinfotech.com.organicfoods.Product;
import svrinfotech.com.organicfoods.R;
import svrinfotech.com.organicfoods.pojo.OrderedProducts;

public class OrderAdatpter extends RecyclerView.Adapter<OrderAdatpter.OrderHolder>{

    private ArrayList<OrderedProducts> orderedProducts;
    private Context context;
    static int i;

    public OrderAdatpter(Context context,ArrayList<OrderedProducts> orderedProducts) {
        this.context=context;
        this.orderedProducts=orderedProducts;
    }

    public class OrderHolder extends RecyclerView.ViewHolder {

        ImageView orderedImage;
        TextView orderName,orderPrice,orderQuantity;
        Button delete;
        private Button plus,minus;
        public OrderHolder(View itemView) {
            super(itemView);
            orderedImage=itemView.findViewById(R.id.orderedImage);
            orderName=itemView.findViewById(R.id.orderedName);
            orderPrice=itemView.findViewById(R.id.orderedPrice);
            orderQuantity=itemView.findViewById(R.id.orderedQuantity);
            plus=itemView.findViewById(R.id.increase);
            minus=itemView.findViewById(R.id.decrease);
            delete=itemView.findViewById(R.id.deleteOrder);
        }
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_row,parent,false);
        return new OrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderHolder holder, final int position) {
        final OrderedProducts products=orderedProducts.get(position);
        Picasso.with(context).load(products.getImage()).networkPolicy(NetworkPolicy.OFFLINE).into(holder.orderedImage, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                Picasso.with(context).load(products.getImage()).into(holder.orderedImage);
            }
        });

        holder.orderName.setText(products.getName());
        holder.orderName.setTypeface(holder.orderName.getTypeface(), Typeface.BOLD);
        holder.orderPrice.setText(products.getFinalCost());
        holder.orderPrice.setTypeface(holder.orderPrice.getTypeface(), Typeface.BOLD);
        holder.orderQuantity.setText(products.getQuantity());
        holder.orderQuantity.setTypeface(holder.orderQuantity.getTypeface(), Typeface.BOLD);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderedProducts.deleteAll(OrderedProducts.class,"name=?",products.getName());
                List<OrderedProducts> productsList=OrderedProducts.listAll(OrderedProducts.class);
                orderedProducts=new ArrayList<>(productsList);
                notifyDataSetChanged();
                if(orderedProducts.size()==0) {
                    context.startActivity(new Intent(context, Product.class));
                }
            }
        });

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=Integer.parseInt(holder.orderQuantity.getText().toString());
                i+=1;
                holder.orderQuantity.setText(String.valueOf(i));
            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=Integer.parseInt(holder.orderQuantity.getText().toString());
                if(i<=1) {
                    Toast.makeText(context, "Not Allow To Decrease", Toast.LENGTH_LONG).show();
                } else {
                    i-=1;
                }
                holder.orderQuantity.setText(String.valueOf(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderedProducts.size();
    }

}
