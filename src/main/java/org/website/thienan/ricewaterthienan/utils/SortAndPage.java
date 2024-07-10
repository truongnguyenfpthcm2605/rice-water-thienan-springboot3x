package org.website.thienan.ricewaterthienan.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class SortAndPage{
    public static final Integer MAX_PAGE = 9;
    public static Sort getSort(String keySort) {
        return Sort.by(Sort.Direction.DESC, keySort);
    }
    public static Sort getSortUp(String keySort) {
        return Sort.by(Sort.Direction.ASC, keySort);
    }
    public static Pageable getPage(Integer number,Integer pageSize,Sort sort){
        return PageRequest.of(number, pageSize,sort);
    }
}
