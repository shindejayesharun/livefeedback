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

package sti.com.livefeedback.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.DiskCache;
import sti.com.livefeedback.data.model.api.BlogResponse;
import sti.com.livefeedback.data.model.api.StoreResponse;
import sti.com.livefeedback.data.model.others.QuestionCardData;
import sti.com.livefeedback.ui.feed.blogs.BlogAdapter;
import sti.com.livefeedback.ui.feed.opensource.OpenSourceAdapter;
import sti.com.livefeedback.ui.feed.opensource.OpenSourceItemViewModel;
import sti.com.livefeedback.ui.main.MainViewModel;
import sti.com.livefeedback.ui.main.QuestionCard;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import sti.com.livefeedback.ui.main.stores.StoreAdapter;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static sti.com.livefeedback.data.remote.ApiEndPoint.BASE_URL;

/**
 * Created by amitshekhar on 11/07/17.
 */

public final class BindingUtils {

    private BindingUtils() {
        // This class is not publicly instantiable
    }

    @BindingAdapter({"adapter"})
    public static void addBlogItems(RecyclerView recyclerView, List<BlogResponse.Blog> blogs) {
        BlogAdapter adapter = (BlogAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(blogs);
        }
    }

   /* @BindingAdapter({"adapter"})
    public static void addStoreItems(RecyclerView recyclerView, List<StoreResponse.Blog> blogs) {
        StoreAdapter adapter = (StoreAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(blogs);
        }
    }*/



    @BindingAdapter({"adapter"})
    public static void addOpenSourceItems(RecyclerView recyclerView, List<OpenSourceItemViewModel> openSourceItems) {
        OpenSourceAdapter adapter = (OpenSourceAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(openSourceItems);
        }
    }

    @BindingAdapter({"adapter", "action"})
    public static void addQuestionItems(SwipePlaceHolderView mCardsContainerView, List<QuestionCardData> mQuestionList, int mAction) {
        if (mAction == MainViewModel.ACTION_ADD_ALL) {
            if (mQuestionList != null) {
                mCardsContainerView.removeAllViews();
                for (QuestionCardData question : mQuestionList) {
                    if (question != null && question.options != null && question.options.size() == 3) {
                        mCardsContainerView.addView(new QuestionCard(question));
                    }
                }
                ViewAnimationUtils.scaleAnimateView(mCardsContainerView);
            }
        }
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        url=BASE_URL+"photos/"+url;
        Context context = imageView.getContext();

        Glide.with(context).load(url)
                //.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);


        TextView tv=new TextView(context);
        new GetSizeTask(tv).execute(new File(context.getCacheDir(), DiskCache.Factory.DEFAULT_DISK_CACHE_DIR));
    }


    static class GetSizeTask extends AsyncTask<File, Long, Long> {
        private final TextView resultView;
        public GetSizeTask(TextView resultView) { this.resultView = resultView; }
        @Override protected void onPreExecute() { resultView.setText("Calculating..."); }
        @Override protected void onProgressUpdate(Long... values) { /* onPostExecute(values[values.length - 1]); */ }
        @Override protected Long doInBackground(File... dirs) {
            try {
                long totalSize = 0;
                for (File dir : dirs) {
                    publishProgress(totalSize);
                    totalSize += calculateSize(dir);
                }
                return totalSize;
            } catch (RuntimeException ex) {
                final String message = String.format("Cannot get size of %s: %s", Arrays.toString(dirs), ex);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override public void run() {
                        resultView.setText("error");
                        Toast.makeText(resultView.getContext(), message, Toast.LENGTH_LONG).show();
                    }
                });
            }
            return 0l;
        }

        @Override protected void onPostExecute(Long size) {
            String sizeText = android.text.format.Formatter.formatFileSize(resultView.getContext(), size);
            resultView.setText(sizeText);
            Log.d("Cache size", String.valueOf(sizeText));
            //229496=224 kb
            if(size>2229496){
               /* Glide glide = Glide.get(resultView.getContext().getApplicationContext());
                glide.clearMemory();
                glide.clearDiskCache();*/
            }

        }
        private static long calculateSize(File dir) {
            if (dir == null) return 0;
            if (!dir.isDirectory()) return dir.length();
            long result = 0;
            File[] children = dir.listFiles();
            if (children != null)
                for (File child : children)
                    result += calculateSize(child);
            return result;
        }
    }
}
