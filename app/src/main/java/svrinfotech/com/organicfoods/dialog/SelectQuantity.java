package svrinfotech.com.organicfoods.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import java.util.ArrayList;

import svrinfotech.com.organicfoods.R;
import svrinfotech.com.organicfoods.adapters.QuantityAdapter;
import svrinfotech.com.organicfoods.listener.RecyclerTouchListener;

public class SelectQuantity extends Dialog implements View.OnClickListener {
    private ArrayList<Integer> nos;
    private RecyclerView recyclerView;
    ImageButton closeDialog;
    Context context;
    QuantityAdapter quantityAdapter;
    public int no;
    public SelectQuantity(@NonNull Context context) {
        super(context);
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.quantity_dialog);
        init();
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(quantityAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(context,
                        recyclerView,
                new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        no=nos.get(position);
                        dismiss();
                    }

                    @Override
                    public void onHold(View view, int position) {

                    }
                }));
        closeDialog.setOnClickListener(this);
    }

    private void init() {
        recyclerView=findViewById(R.id.kgs);
        nos=new ArrayList<>();
        quantityAdapter=new QuantityAdapter(nos);
        for(int i=1;i<11;i++) {
            nos.add(i);
            quantityAdapter.notifyDataSetChanged();
        }
        closeDialog=findViewById(R.id.closeDialog);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.closeDialog:
                dismiss();
                break;
        }
    }
}
