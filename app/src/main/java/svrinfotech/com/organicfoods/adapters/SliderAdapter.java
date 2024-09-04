package svrinfotech.com.organicfoods.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import svrinfotech.com.organicfoods.R;

public class SliderAdapter extends PagerAdapter {
    Context context;
    private List<Integer> background;
    private List<Integer> names;
    public SliderAdapter(Context context, List<Integer> background, List<Integer> names) {
        this.context=context;
        this.background=background;
        this.names=names;
    }

    @Override
    public int getCount() {
        return background.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.item_slider,null);
        TextView textView=view.findViewById(R.id.itemName);
        LinearLayout linearLayout=view.findViewById(R.id.item_slider);
        textView.setText(names.get(position));
        linearLayout.setBackgroundResource(background.get(position));

        ViewPager viewPager=(ViewPager) container;
        viewPager.addView(view,0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager viewPager=(ViewPager) container;
        View view=(View) object;
        viewPager.removeView(view);
    }
}
