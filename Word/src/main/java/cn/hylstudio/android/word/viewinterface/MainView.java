package cn.hylstudio.android.word.viewinterface;

import android.widget.TextView;

import cn.hylstudio.android.word.model.Word;

/**
 * Created by HYL on 2016/9/11.
 */
public interface MainView {
    /**
     * 更新单词细节
     * @param w 要更新的单词
     */
    void updateDetailFragment(Word w);

    /**
     * 展示新增单词窗口
     * @return 新单词
     */
    void showAddWordDialog();

    /**
     * 更新单词列表
     */
    void refreshWordListFragment();
    void refreshWordListFragment(String wordContent);
}
