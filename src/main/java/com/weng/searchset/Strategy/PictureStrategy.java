package com.weng.searchset.Strategy;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weng.searchset.model.entity.Picture;
import com.weng.searchset.model.enums.SearchTypeEnum;
import com.weng.searchset.service.PictureService;
import com.weng.searchset.Strategy.SearchTypeStrategy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
public class PictureStrategy implements SearchTypeStrategy {

    @Resource
    private PictureService pictureService;

    @Override
    public String SearchType() {
        return SearchTypeEnum.PICTURE.getValue();
    }

    @Override
    public List search(String searchText, HttpServletRequest request) {
        Page<Picture> picturePage = pictureService.searchPicture(searchText, 1, 10);
        return picturePage.getRecords();
    }
}
