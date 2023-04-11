package com.weng.searchset.Strategy;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface SearchTypeStrategy {

    /**
     * 搜索类型
     */
    public String SearchType();

    /**
     * 执行搜索
     */
    public List search(String searchText, HttpServletRequest request);

}
