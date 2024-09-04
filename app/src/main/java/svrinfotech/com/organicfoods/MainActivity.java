package svrinfotech.com.organicfoods;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import svrinfotech.com.organicfoods.adapters.SliderAdapter;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout indicator;

    List<Integer> backgroud;
    List<Integer> names;

    private  void init() {
        viewPager=findViewById(R.id.viewPager);
        indicator=findViewById(R.id.tabLayout);

        backgroud=new ArrayList<>();
        backgroud.add(R.drawable.first);
        backgroud.add(R.drawable.second);
        backgroud.add(R.drawable.third);

        names=new ArrayList<>();
        names.add(R.string.app_name);
        names.add(R.string.organicFarming);
        names.add(R.string.pomegranate);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        viewPager.setAdapter(new SliderAdapter(this,backgroud,names));
        indicator.setupWithViewPager(viewPager,true);
        Timer timer=new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(),4000,6000);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        final Snackbar snackbar=Snackbar.make(findViewById(android.R.id.content),"",Snackbar.LENGTH_INDEFINITE);
        final Intent intent=new Intent(this,Login.class);
        snackbar.setAction("Go To Login >", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
                finish();
                if(snackbar.isShown()) {
                    snackbar.dismiss();
                }
            }
        });
        snackbar.setActionTextColor(getResources().getColor(android.R.color.white));
        snackbar.show();
    }

    private class SliderTimer extends TimerTask {

        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(viewPager.getCurrentItem()<(names.size()-1)) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }

    /*void hideFrontView() {
            viewPager.setVisibility(View.GONE);
            indicator.setVisibility(View.GONE);
    }*/
}
