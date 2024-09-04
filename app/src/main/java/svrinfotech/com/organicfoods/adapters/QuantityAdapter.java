package svrinfotech.com.organicfoods.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import svrinfotech.com.organicfoods.R;

public class QuantityAdapter extends RecyclerView.Adapter<QuantityAdapter.QuantityHolder>{

    private ArrayList<Integer> nos;

    public QuantityAdapter(ArrayList<Integer> nos) {
        this.nos = nos;
    }

    public class QuantityHolder extends RecyclerView.ViewHolder {
        TextView qtyTextView;
        public QuantityHolder(View itemView) {
            super(itemView);
            qtyTextView=itemView.findViewById(R.id.quantity_row);
        }
    }

    @NonNull
    @Override
    public QuantityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.quantity_row,parent,false);
        return new QuantityHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuantityHolder holder, int position) {
        Integer no=nos.get(position);
        holder.qtyTextView.setText(String.valueOf(no));
    }

    @Override
    public int getItemCount() {
        Log.e("Size Of List : ",nos.size()+"");
        return nos.size();
    }
}
