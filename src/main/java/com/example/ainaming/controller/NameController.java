package com.example.ainaming.controller;

import com.example.ainaming.dto.ApiResult;
import com.example.ainaming.dto.NameRequest;
import com.example.ainaming.dto.NameResponse;
import com.example.ainaming.service.NameService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AI 智能取名 Controller
 * 提供 POST /api/name 接口，接收前端提交的取名信息，
 * 调用 Service 层生成推荐名字，并以统一格式返回
 */
@RestController
@RequestMapping("/api")
public class NameController {

    private final NameService nameService;

    public NameController(NameService nameService) {
        this.nameService = nameService;
    }

    /**
     * AI 智能取名接口
     *
     * @param request 取名请求参数（姓氏、性别、生日、出生时辰、期望寓意、其他要求）
     * @return 统一返回结果，data 中包含推荐名字列表
     */
    @PostMapping("/name")
    public ApiResult<NameResponse> generateName(@Valid @RequestBody NameRequest request) {
        NameResponse response = nameService.generateNames(request);
        return ApiResult.success(response);
    }
}
