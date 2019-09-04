/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package sti.com.livefeedback.ui.productdetails;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import sti.com.livefeedback.R;
import sti.com.livefeedback.data.model.api.storedetails.TabbedDetail;
import sti.com.livefeedback.ui.feed.blogs.BlogFragment;
import sti.com.livefeedback.ui.feed.opensource.OpenSourceFragment;
import sti.com.livefeedback.ui.main.map.MapFragment;

import java.util.List;



public class TabbedPagerAdapter extends PagerAdapter {


    private Context mContext;
    List<TabbedDetail> tabbedDetails;
    public TabbedPagerAdapter(Context context, List<TabbedDetail> tabbedDetails) {
        mContext = context;
        this.tabbedDetails=tabbedDetails;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_tabbled_html_value, collection, false);
        String value=tabbedDetails.get(position).getDesc();
        TextView tv=layout.findViewById(R.id.review_option);
        tv.setText(Html.fromHtml(value));
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return tabbedDetails.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabbedDetails.get(position).getLabel();
    }
}
