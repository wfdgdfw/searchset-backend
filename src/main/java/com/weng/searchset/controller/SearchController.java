package com.weng.searchset.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weng.searchset.common.BaseResponse;
import com.weng.searchset.common.ErrorCode;
import com.weng.searchset.common.ResultUtils;
import com.weng.searchset.exception.BusinessException;
import com.weng.searchset.model.dto.post.PostQueryRequest;
import com.weng.searchset.model.dto.search.SearchRequest;
import com.weng.searchset.model.dto.user.UserQueryRequest;
import com.weng.searchset.model.entity.Picture;
import com.weng.searchset.model.vo.PostVO;
import com.weng.searchset.model.vo.SearchAllVO;
import com.weng.searchset.model.vo.SearchVO;
import com.weng.searchset.model.vo.UserVO;
import com.weng.searchset.service.PictureService;
import com.weng.searchset.service.PostService;
import com.weng.searchset.Strategy.SearchTypeStrategy;
import com.weng.searchset.Strategy.SearchTypeStrategyHelper;
import com.weng.searchset.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 查询接口
 */
@RestController
@RequestMapping("/search")
@Slf4j
public class SearchController {

    @Resource
    private UserService userService;

    @Resource
    private PostService postService;

    @Resource
    private PictureService pictureService;

    @Resource
    private SearchTypeStrategyHelper searchTypeStrategyHelper;

    @PostMapping("/alll")
    public BaseResponse<SearchVO> searchAlll(@RequestBody SearchRequest searchRequest, HttpServletRequest request) {
        String searchText = searchRequest.getSearchText();

//        CompletableFuture<Page<UserVO>> userTask = CompletableFuture.supplyAsync(() -> {
//            UserQueryRequest userQueryRequest = new UserQueryRequest();
//            userQueryRequest.setUserName(searchText);
//            Page<UserVO> userVOPage = userService.listUserVOByPage(userQueryRequest);
//            return userVOPage;
//        });
//
//        CompletableFuture<Page<PostVO>> postTask = CompletableFuture.supplyAsync(() -> {
//            PostQueryRequest postQueryRequest = new PostQueryRequest();
//            postQueryRequest.setSearchText(searchText);
//            Page<PostVO> postVOPage = postService.listPostVOByPage(postQueryRequest, request);
//            return postVOPage;
//        });
//
//        CompletableFuture<Page<Picture>> pictureTask = CompletableFuture.supplyAsync(() -> {
//            Page<Picture> picturePage = pictureService.searchPicture(searchText, 1, 10);
//            return picturePage;
//        });

//        CompletableFuture.allOf(userTask, postTask, pictureTask).join();


        UserQueryRequest userQueryRequest = new UserQueryRequest();
        userQueryRequest.setUserName(searchText);
        Page<UserVO> userVOPage = userService.listUserVOByPage(userQueryRequest);


        PostQueryRequest postQueryRequest = new PostQueryRequest();
        postQueryRequest.setSearchText(searchText);
        Page<PostVO> postVOPage = postService.listPostVOByPage(postQueryRequest, request);

        // 开始时间
        long stime = System.currentTimeMillis();
        Page<Picture> picturePage = pictureService.searchPicture(searchText, 1, 10);
        // 开始时间
        long etime = System.currentTimeMillis();
        log.info("执行时间：" + (etime-stime));
        try {
            SearchVO searchVO = new SearchVO();
            searchVO.setUserList(userVOPage.getRecords());
            searchVO.setPostList(postVOPage.getRecords());
            searchVO.setPictureList(picturePage.getRecords());
            return ResultUtils.success(searchVO);
        } catch (Exception e) {
            log.error("查询异常", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "查询异常");
        }
    }

    @PostMapping("/all")
    public BaseResponse<SearchAllVO> searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request) {
        String searchText = searchRequest.getSearchText();
        String searchType = searchRequest.getSearchType();
        SearchTypeStrategy strategy = searchTypeStrategyHelper.getStrategy(searchType);
        if (strategy==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List search = strategy.search(searchText, request);
        SearchAllVO searchAllVO = new SearchAllVO();
        searchAllVO.setSearchList(search);
        return ResultUtils.success(searchAllVO);
    }
}




