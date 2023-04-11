package com.weng.searchset.model.dto.search;

import com.weng.searchset.common.PageRequest;
import lombok.Data;

import java.io.Serializable;

@Data
public class SearchRequest extends PageRequest implements Serializable {
    /**
     * 搜索词
     */
    private String searchText;

    /**
     * 搜索类型
     */
    private String searchType;

    private static final long serialVersionUID = 1L;

}
