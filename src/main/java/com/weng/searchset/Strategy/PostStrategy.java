package com.weng.searchset.Strategy;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weng.searchset.model.dto.post.PostQueryRequest;
import com.weng.searchset.model.entity.Post;
import com.weng.searchset.model.enums.SearchTypeEnum;
import com.weng.searchset.service.PostService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 文章类型搜索
 */
@Component
public class PostStrategy implements DataSourceStrategy {

    @Resource
    private PostService postService;

    @Override
    public String SearchType() {
        return SearchTypeEnum.POST.getValue();
    }

    @Override
    public List search(String searchText, HttpServletRequest request) {
        PostQueryRequest postQueryRequest = new PostQueryRequest();
        postQueryRequest.setSearchText(searchText);
        //es搜索
        Page<Post> postPage = postService.searchFromEs(postQueryRequest);
        return postPage.getRecords();
        //mysql搜索
//        Page<PostVO> postVOPage = postService.listPostVOByPage(postQueryRequest, request);
//        return postVOPage.getRecords();
    }
}
