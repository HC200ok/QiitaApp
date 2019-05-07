package com.example.firstapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firstapplication.constants.QiitaConstants;
import com.example.firstapplication.helper.UserHelper;
import com.example.firstapplication.util.OkHttp3Utils;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PostActivity extends Activity {
    public static final String POST_BODY = "postBody";
    public static final String POST_CREATEDAT = "postCreatedAt";
    public static final String POST_TITLE = "postTitle";
    public static final String POST_TAGS = "postTags";
    public static final String POST_ID = "postId";
    public static final String POST_AUTH_ID = "postAuthId";

    String postBody;
    TextView tvPostBody;
    ImageView imLike;
    ImageView imStock;

    String postId;
    String postAuthId;

    Boolean liked;
    Boolean stocked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        postId = getIntent().getStringExtra(POST_ID);
        postAuthId = getIntent().getStringExtra(POST_AUTH_ID);

        NavBar.init(PostActivity.this, true, "", "");

        initView();
        initOperations();
    }

    private void initView() {
        imLike = findViewById(R.id.post_like);
        imStock = findViewById(R.id.post_stock);
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

        TextView tvPostAuthId = findViewById(R.id.post_author_id);
        tvPostAuthId.setText(postAuthId);

        String postTitle = getIntent().getStringExtra(POST_TITLE);
        TextView tvPostTitle = findViewById(R.id.post_title);
        tvPostTitle.setText(postTitle);

        String postTags = getIntent().getStringExtra(POST_TAGS);
        TextView tvPostTags = findViewById(R.id.post_tags);
        tvPostTags.setText(postTags);

        postBody = getIntent().getStringExtra(POST_BODY);
        tvPostBody = findViewById(R.id.post_body);
        URLImageParser imageGetter = new URLImageParser(tvPostBody);
        tvPostBody.setMovementMethod(ScrollingMovementMethod.getInstance());
        tvPostBody.setText(Html.fromHtml(postBody, imageGetter, null));

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

    private void initOperations() {
        if (UserHelper.getInstance().isLogin()) {
            initLike();
            initStock();
        }
        bindClickEvents();
    }

    private void bindClickEvents() {
        imLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (liked) {
                    changeLikeStatus(false, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }
                        @Override
                        public void onResponse(Call call, final Response response) throws IOException {
                            final String res = response.body().string();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (response.code() == 204) {
                                        unlike();
                                    } else {
                                        Toast.makeText(PostActivity.this, res, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                } else {
                    changeLikeStatus(true, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }
                        @Override
                        public void onResponse(Call call, final Response response) throws IOException {
                            final String res = response.body().string();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (response.code() == 204) {
                                        like();
                                    } else {
                                        Toast.makeText(PostActivity.this, res, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                }
            }
        });

        imStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stocked) {
                    changeStockStatus(false, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                        }
                        @Override
                        public void onResponse(Call call, final Response response) throws IOException {
                            final String res = response.body().string();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (response.code() == 204) {
                                        unStock();
                                    } else {
                                        Toast.makeText(PostActivity.this, res, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                    });
                } else {
                    changeStockStatus(true, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                        }
                        @Override
                        public void onResponse(Call call, final Response response) throws IOException {
                            final String res = response.body().string();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (response.code() == 204) {
                                        stock();
                                    } else {
                                        Toast.makeText(PostActivity.this, res, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }

                    });
                }
            }
        });
    }

    private void changeLikeStatus(Boolean like, Callback callback) {
        String url = QiitaConstants.HOST + "/items/" + postId + "/like";
        if (like) {
            OkHttp3Utils.doPut(url, callback);
        } else {
            OkHttp3Utils.doDelete(url, callback);
        }
    }

    private void changeStockStatus(Boolean stock, Callback callback) {
        String url = QiitaConstants.HOST + "/items/" + postId + "/stock";
        if (stock) {
            OkHttp3Utils.doPut(url, callback);
        } else {
            OkHttp3Utils.doDelete(url, callback);
        }
    }

    private void initLike() {
        String url = QiitaConstants.HOST + "/items/" + postId + "/like";
        OkHttp3Utils.doGet(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //Gson解析
                Gson gson = new Gson();
                final int code = response.code();
//               204/404
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch(code) {
                                case 204:
                                    like();
                                    break;
                                case 404:
                                    unlike();
                                    break;
                                    default:
                                    unlike();
                                    break;
                            }
                        }
                    });
                }
        });
    }

    private void initStock() {
        String url = QiitaConstants.HOST + "/items/" + postId + "/stock";
        OkHttp3Utils.doGet(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //Gson解析
                Gson gson = new Gson();
                final int code = response.code();
//               204/404
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch(code) {
                            case 204:
                                stock();
                                break;
                            case 404:
                                unStock();
                                break;
                            default:
                                unStock();
                                break;
                        }
                    }
                });
            }
        });
    }

    private void like() {
        liked = true;
        imLike.setImageResource(R.mipmap.icon_like_qiita);
    }

    private void unlike() {
        liked = false;
        imLike.setImageResource(R.mipmap.icon_like);
    }

    private void stock() {
        stocked = true;
        imStock.setImageResource(R.mipmap.icon_stock_qiita);
    }

    private void unStock() {
        stocked = false;
        imStock.setImageResource(R.mipmap.icon_stock);
    }
}
