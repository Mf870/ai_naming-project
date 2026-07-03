package com.example.ainaming.controller;

import com.example.ainaming.dto.ApiResult;
import com.example.ainaming.dto.HistoryItem;
import com.example.ainaming.service.HistoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 历史记录 Controller
 * 提供 GET /api/history 接口，查看用户过往取名历史
 */
@RestController
@RequestMapping("/api")
public class HistoryController {

    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping("/history")
    public ApiResult<List<HistoryItem>> getHistory() {
        return ApiResult.success(historyService.listHistory());
    }
}
