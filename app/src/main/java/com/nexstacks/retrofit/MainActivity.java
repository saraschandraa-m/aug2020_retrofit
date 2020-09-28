package com.nexstacks.retrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import model.Article;
import model.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NewsCategoryAdapter.NewsCategoryClickListener {

    private ImageView mIvSingleImage;
    private RecyclerView mRcNews;
    private String categoryName = "business";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView mRcNewsCategory = findViewById(R.id.rc_news_categories);
        mRcNewsCategory.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.HORIZONTAL, false));

        mRcNews = findViewById(R.id.rc_news);
        mRcNews.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false));

        NewsCategoryAdapter newsCategoryAdapter = new NewsCategoryAdapter(MainActivity.this);
        newsCategoryAdapter.setListener(this);
        mRcNewsCategory.setAdapter(newsCategoryAdapter);

        Button mBtnGetResult = findViewById(R.id.btn_get_result);
        mIvSingleImage = findViewById(R.id.iv_single_image);

        mBtnGetResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNews();
            }
        });

        getSourcesNews();
    }

    private void getNews() {

        ProgressDialog pg = new ProgressDialog(MainActivity.this);
        pg.setTitle("Getting your news");
        pg.show();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        HashMap<String, Object> queries = new HashMap<>();
        queries.put("apiKey", "4c82d7e8131841f484c6cf169bb83ae4");
        queries.put("sources", "google-news");

//        Call<String> getNews = apiInterface.getAllNews(queries);
//
//        getNews.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                pg.hide();
//
//                String responseValue = response.body();
//
//                try {
//                    JSONObject responseObject = new JSONObject(responseValue);
//                    Result resultValue = Result.parseResultResponse(responseObject);
//
//                    ArrayList<Article> articles = resultValue.articles;
//
//                    Glide.with(MainActivity.this).load(articles.get(5).url).into(mIvSingleImage);
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                pg.hide();
//                Toast.makeText(MainActivity.this, "Failed ", Toast.LENGTH_LONG).show();
//            }
//        });
    }

    private void getSourcesNews() {
        HashMap<String, Object> queries = new HashMap<>();
        queries.put("apiKey", "4c82d7e8131841f484c6cf169bb83ae4");
        queries.put("category", categoryName);

        ProgressDialog dialog = new ProgressDialog(MainActivity.this);
        dialog.setTitle("Getting News");
        dialog.show();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

//        Call<String> getSourceNews = apiInterface.getAllSources(queries);
//        getSourceNews.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                dialog.hide();
//                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_LONG).show();
//
////                String responseValue = response.body();
////
////                try {
////                    JSONObject responseObject = new JSONObject(responseValue);
////                    Result resultValue = Result.parseResultResponse(responseObject);
////
////                    ArrayList<Article> articles = resultValue.articles;
////
////                    NewsAdapter newsAdapter = new NewsAdapter(MainActivity.this, articles);
////                    mRcNews.setAdapter(newsAdapter);
////
////
////                }catch (JSONException e){
////                    e.printStackTrace();
////                }
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                dialog.hide();
//                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_LONG).show();
//            }
//        });

        Call<Result> getTopHeadlines = apiInterface.getAllSource(queries);
        getTopHeadlines.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                dialog.hide();
                Result result = response.body();

                ArrayList<Article> articles = result.articles;
                NewsAdapter newsAdapter = new NewsAdapter(MainActivity.this, articles);
                mRcNews.setAdapter(newsAdapter);
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                dialog.hide();
            }
        });

    }

    @Override
    public void onNewsCategoryClicked(String categoryName) {
        this.categoryName = categoryName;
        getSourcesNews();
    }
}