package com.example.discoverfreedom.fragments.list;

import com.example.discoverfreedom.fragments.ViewContract;

public interface ListViewContract<I, N> extends ViewContract<I> {
    void showListFooter(boolean show);

    void handleNextItems(N result);
}
