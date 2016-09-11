package cn.hylstudio.android.word.presenter;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.hylstudio.android.word.dao.WordDao;
import cn.hylstudio.android.word.daoimpl.WordDaoImpl;
import cn.hylstudio.android.word.model.Word;
import cn.hylstudio.android.word.viewinterface.MainView;
import cz.msebera.android.httpclient.Header;

/**
 * Created by HYL on 2016/9/11.
 */
public class WordPresenter {
    private MainView view;
    private WordDao db;
    private Word word;

    public WordPresenter(MainView view) {
        this.view = view;
        db = new WordDaoImpl((Context) view);
    }

    public void addWord(Word word) {
        this.word = word;
        //TODO:询问是否使用网络解释
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://fanyi.youdao.com/openapi.do?keyfrom=haobaoshui&key=1650542691&type=data&doctype=json&version=1.1&q=";
        url += word.getWord();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                String json = new String(response);
                parseJson(json);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
            }

            @Override
            public void onRetry(int retryNo) {
            }
        });

    }

    private boolean parseJson(String json) {
        try {
            JSONObject result = new JSONObject(json);
            if (0 != (int) result.get("errorCode")) return false;

            JSONArray translations = (JSONArray) result.get("translation");
            String translation = "";
            for (int i = 0; i < translations.length(); i++) {
                translation += translations.get(i);
            }

            Log.d("net meaning", "parseJson: meaning" + translation);
            word.setMeaning(translation);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        db.addWord(word);
        //单词已经插入到数据库，更新显示列表
        view.refreshWordListFragment();
        return true;
    }

    public Word getWordByContent(String wordContent) {
        return db.getWordByContent(wordContent);
    }
}
