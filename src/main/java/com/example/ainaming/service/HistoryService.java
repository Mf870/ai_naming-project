package com.example.ainaming.service;

import com.example.ainaming.dto.HistoryItem;

import java.util.List;

/**
 * 历史记录 Service 接口
 */
public interface HistoryService {

    /**
     * 查询全部取名历史记录，按创建时间倒序排列（最新的在最前面）
     */
    List<HistoryItem> listHistory();
}
