package com.example.firstapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PostActivity extends Activity {
    public static final String POST_BODY = "postBody";
    public static final String POST_CREATEDAT = "postCreatedAt";
    public static final String POST_TITLE = "postTitle";
    public static final String POST_TAGS = "postTags";

    String post_body;
    TextView tvPostBody;

    public static PostActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        instance = this;
        NavBar.init(instance, true, "", "");
        initView();
    }

    private void initView() {
        // ImageLoader需要实例化
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));

        String postCreatedAt = getIntent().getStringExtra(POST_CREATEDAT);

        SimpleDateFormat origin_format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat new_format = new SimpleDateFormat("yyyy/MM/dd HH:mm");

        try {
            postCreatedAt = new_format.format(origin_format1.parse(postCreatedAt));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        TextView tvPostCreatedAt = findViewById(R.id.post_createdAt);
        tvPostCreatedAt.setText(postCreatedAt);

        String postTitle = getIntent().getStringExtra(POST_TITLE);
        TextView tvPostTitle = findViewById(R.id.post_title);
        tvPostTitle.setText(postTitle);

        String postTags = getIntent().getStringExtra(POST_TAGS);
        TextView tvPostTags = findViewById(R.id.post_tags);
        tvPostTags.setText(postTags);

        post_body = getIntent().getStringExtra(POST_BODY);
        tvPostBody = findViewById(R.id.post_body);
        URLImageParser imageGetter = new URLImageParser(tvPostBody);
        tvPostBody.setMovementMethod(ScrollingMovementMethod.getInstance());
        tvPostBody.setText(Html.fromHtml(post_body, imageGetter, null));
    }

    private class URLImageParser implements Html.ImageGetter {
        TextView mTextView;
        public URLImageParser(TextView textView) {
            this.mTextView = textView;
        }
        @Override
        public Drawable getDrawable(String source) {
            final URLDrawable urlDrawable = new URLDrawable();
            ImageLoader.getInstance().loadImage(source,
                    new SimpleImageLoadingListener() {
                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            urlDrawable.bitmap = loadedImage;
                            urlDrawable.setBounds(0, 0, loadedImage.getWidth(), loadedImage.getHeight());
                            mTextView.invalidate();
                            mTextView.setText(mTextView.getText());
                        }
                    });
            return urlDrawable;
        }
    }


    private class URLDrawable extends BitmapDrawable {
        protected Bitmap bitmap;
        @Override
        public void draw(Canvas canvas) {
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, 0, 0, getPaint());
            }
        }
    }
}
