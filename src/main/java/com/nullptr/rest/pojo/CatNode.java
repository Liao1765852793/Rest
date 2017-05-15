package com.nullptr.rest.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Nullptr on 2017/5/15.
 * 分类节点Item
 */
public class CatNode {
    @JsonProperty(value = "u")
    private String url;
    @JsonProperty(value = "n")
    private String name;
    @JsonProperty(value = "i")
    private List items;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List getItems() {
        return items;
    }

    public void setItems(List items) {
        this.items = items;
    }


}
