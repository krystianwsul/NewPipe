package com.example.discoverfreedom.database.history.dao;

import com.example.discoverfreedom.database.BasicDAO;

public interface HistoryDAO<T> extends BasicDAO<T> {
    T getLatestEntry();
}
