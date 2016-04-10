package com.example.stone.test.ImageLoader;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.Environment;

import com.example.stone.test.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.File;

/**
 * Created by 润 on 2016/4/9.
 */
public class loadImage extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initConfiguration();
    }

    /**
     * 初始化ImageLoaderConfiguration，通常不需要设置这么多属性
     * @description：
     * @author ldm
     * @date 2016-1-12 下午4:42:31
     */
    private void initConfiguration() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).memoryCacheExtraOptions(480, 800) // 设置内存缓存文件的最大长宽
                .diskCacheExtraOptions(480, 800, null) // 设置本地缓存的详细信息(缓存的最大长宽)，最好不要设置这个
                .threadPoolSize(3) // 设置线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2) // 设置当前线程的优先级
                .tasksProcessingOrder(QueueProcessingType.FIFO) // 设置缓存策略
                .denyCacheImageMultipleSizesInMemory()// 设置缓存显示不同大小的同一张图片
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024)) // 设置通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024) // 设置内存缓存的最大值
                .memoryCacheSizePercentage(13) // 设置内存缓存最大大小占当前应用可用内存的百分比，默认是当前应用可用内存的1/8
                .discCache(new UnlimitedDiskCache(new File(Environment.getExternalStorageDirectory() + "/vLine")))// Environment.getExternalStorageDirectory()获取SD卡路径，设置缓存文件目录
                .diskCacheSize(50 * 1024 * 1024) // 设置 sd卡(本地)缓存的最大值50M
                .diskCacheFileCount(100) // 设置可以缓存的文件数量
                        // .diskCacheFileNameGenerator(new Md5FileNameGenerator()) 设置保存的URL用MD5加密
                .imageDownloader(new BaseImageDownloader(this)) // 设置默认最大连接时间
                .imageDecoder(new BaseImageDecoder(false)) // default
                .defaultDisplayImageOptions(getDisplayOptions()) // 设置自定义的DisplayImageOptions
                .writeDebugLogs() // 设置打印log
                .build(); // 开始构建
        ImageLoader.getInstance().init(config);
    }

    /**
     * 自定义DisplayImageOptions
     * @description：
     * @date 2016-1-12 下午4:46:47
     */
    private DisplayImageOptions getDisplayOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_launcher) // 设置图片在下载中显示的图片
                .showImageForEmptyUri(R.drawable.ic_launcher)// 设置图片Uri为空或错误时显示的图片
                .showImageOnFail(R.drawable.ic_launcher) // 设置图片加载失败时候显示的图片
                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
                .considerExifParams(true) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)// 设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型
                .delayBeforeLoading(0)// 设置的下载前的延迟时间
                .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
                .displayer(new RoundedBitmapDisplayer(20))// 设置为圆角，弧度为多少，不推荐用！
                .displayer(new FadeInBitmapDisplayer(100))// 设置加载好后渐入的动画时间，可能会出现闪动
                .build();// 构建完成
        return options;
    }
}
