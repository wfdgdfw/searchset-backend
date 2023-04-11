package com.weng.searchset.Strategy;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DataSourceStrategyHelper {

    private Map<String, DataSourceStrategy> strategyMap;

    @Resource
    public void setStrategyMap(List<DataSourceStrategy> list) {
        System.out.println(list);
        strategyMap = list.stream().collect(Collectors.toMap(item -> item.SearchType(), item -> item, (v1, v2) -> v1));
        System.out.println(strategyMap);
    }

    public DataSourceStrategy getStrategy(String searchType) {
        if (strategyMap == null) {
            return null;
        }
        return strategyMap.get(searchType);
    }
}
