package com.weng.searchset.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class SearchAllVO implements Serializable {

    private List<Object> searchList;

    private static final long serialVersionUID = 1L;
}
