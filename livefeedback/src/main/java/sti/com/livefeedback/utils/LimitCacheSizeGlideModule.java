package sti.com.livefeedback.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.module.GlideModule;

import java.util.Locale;

public class LimitCacheSizeGlideModule implements GlideModule {
    // Modern device should have 8GB (=7.45GiB) or more!
    private static final int SMALL_INTERNAL_STORAGE_THRESHOLD_GIB = 6;
    private static final int DISK_CACHE_SIZE_FOR_SMALL_INTERNAL_STORAGE_MIB = 50;

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //if (MyApplication.from(context).isTest()) return; // NOTE: StatFs will crash on robolectric.

        double totalGiB = getTotalBytesOfInternalStorage() / 1024.0 / 1024.0 / 1024.0;
        Log.i("Internal", String.valueOf(totalGiB));
        if (totalGiB < SMALL_INTERNAL_STORAGE_THRESHOLD_GIB) {
            Log.i("Limiting" , DISK_CACHE_SIZE_FOR_SMALL_INTERNAL_STORAGE_MIB + "MiB");
            builder.setDiskCache(new InternalCacheDiskCacheFactory(context, DISK_CACHE_SIZE_FOR_SMALL_INTERNAL_STORAGE_MIB));
        }
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
    }

    private long getTotalBytesOfInternalStorage() {
        // http://stackoverflow.com/a/4595449/1474113
        StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return getTotalBytesOfInternalStorageWithStatFs(stat);
        } else {
            return getTotalBytesOfInternalStorageWithStatFsPreJBMR2(stat);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private long getTotalBytesOfInternalStorageWithStatFs(StatFs stat) {
        return stat.getTotalBytes();
    }

    @SuppressWarnings("deprecation")
    private long getTotalBytesOfInternalStorageWithStatFsPreJBMR2(StatFs stat) {
        return (long) stat.getBlockSize() * stat.getBlockCount();
    }
}
