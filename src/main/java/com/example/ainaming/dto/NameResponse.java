package com.example.ainaming.dto;

import java.util.List;

/**
 * 取名结果 DTO
 * 对应 DeepSeek 返回内容解析后的 JSON 结构：{ "names": [ ... ] }
 */
public class NameResponse {

    /** 推荐名字列表 */
    private List<NameItem> names;

    public NameResponse() {
    }

    public NameResponse(List<NameItem> names) {
        this.names = names;
    }

    public List<NameItem> getNames() {
        return names;
    }

    public void setNames(List<NameItem> names) {
        this.names = names;
    }
}
