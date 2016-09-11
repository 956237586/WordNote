package cn.hylstudio.android.word.presenter;

import android.content.Context;

import cn.hylstudio.android.word.dao.WordDao;
import cn.hylstudio.android.word.daoimpl.WordDaoImpl;
import cn.hylstudio.android.word.model.Word;
import cn.hylstudio.android.word.viewinterface.MainView;

/**
 * Created by HYL on 2016/9/11.
 */
public class WordPresenter {
    private MainView view;
    private WordDao db;
    public WordPresenter(MainView view) {
        this.view = view;
        db = new WordDaoImpl((Context) view);
    }
    public void addWord(Word word){
        db.addWord(word);
        //单词已经插入到数据库，更新显示列表
        view.refreshWordListFragment();
    }

    public Word getWordByContent(String wordContent){
        return db.getWordByContent(wordContent);
    }
}
