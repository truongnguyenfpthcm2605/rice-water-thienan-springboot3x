package org.website.thienan.ricewaterthienan.services;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RedisService {
    void set(String key, String value);
    void setTimeToLive(String key,long timeToLive);
    void hashSet(String key, String field, Object value);
    boolean hashExists(String key, String field);
    Object get(String key);
    Object hashGet(String key, String field);
    List<Object> hashGetByFiedlPrefix(String key, String fieldPrefix);
    Set<String> getFieldPrefix(String key);
    void delete(String key);
    void delete(String key, String field);
    void deleteByField(String key, List<String> fields);
}
