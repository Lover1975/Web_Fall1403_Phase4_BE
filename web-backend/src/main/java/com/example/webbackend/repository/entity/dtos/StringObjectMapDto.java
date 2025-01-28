package com.example.webbackend.repository.entity.dtos;

import java.util.HashMap;
import java.util.Map;

/**
 * یک DTO ساده که حاوی یک Map از String -> Object است.
 * با این DTO می‌توانید در متدهایی مانند signin یا signup، چندین فیلد دلخواه برگردانید.
 */
public class StringObjectMapDto extends Dto {

    // فیلد اصلی که داده‌ها را نگه می‌دارد
    private Map<String, Object> data = new HashMap<>();

    // سازنده پیش‌فرض
    public StringObjectMapDto() {
    }

    /**
     * متد کمکی برای قرار دادن داده در Map
     */
    public void put(String key, Object value) {
        data.put(key, value);
    }

    // Getter و Setter برای فیلد data
    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
