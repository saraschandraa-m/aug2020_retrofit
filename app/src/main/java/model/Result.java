package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Result {

    public String status;
    public int totalResults;
    public ArrayList<Article> articles;

    public static Result parseResultResponse(JSONObject jsonObject){
        Result item = new Result();
        item.status = jsonObject.optString("status");
        item.totalResults = jsonObject.optInt("totalResults");

        item.articles = new ArrayList<>();

        JSONArray articleArray = jsonObject.optJSONArray("articles");

        if(articleArray.length() > 0) {
            for (int i=0 ;i <articleArray.length(); i++){
                Article articleItem = Article.parseArticleResponse(articleArray.optJSONObject(i));
                item.articles.add(articleItem);
            }
        }

        return item;
    }
}
