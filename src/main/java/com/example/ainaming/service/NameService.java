package com.example.ainaming.service;

import com.example.ainaming.dto.NameRequest;
import com.example.ainaming.dto.NameResponse;

/**
 * 取名 Service 接口
 */
public interface NameService {

    /**
     * 根据用户输入信息，调用 DeepSeek AI 生成推荐名字
     *
     * @param request 用户提交的取名请求参数
     * @return 包含多个推荐名字的结果
     */
    NameResponse generateNames(NameRequest request);
}
