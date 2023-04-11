package com.weng.searchset.Strategy;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weng.searchset.model.dto.user.UserQueryRequest;
import com.weng.searchset.model.enums.SearchTypeEnum;
import com.weng.searchset.model.vo.UserVO;
import com.weng.searchset.service.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
public class UserStrategy implements DataSourceStrategy {

    @Resource
    private UserService userService;

    @Override
    public String SearchType() {
        return SearchTypeEnum.USER.getValue();
    }

    @Override
    public List search(String searchText, HttpServletRequest request) {
        UserQueryRequest userQueryRequest = new UserQueryRequest();
        userQueryRequest.setUserName(searchText);
        Page<UserVO> userVOPage = userService.listUserVOByPage(userQueryRequest);
        return userVOPage.getRecords();
    }
}
