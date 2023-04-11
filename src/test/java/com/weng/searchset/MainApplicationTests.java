package com.weng.searchset;


import com.weng.searchset.common.ErrorCode;
import com.weng.searchset.exception.BusinessException;
import com.weng.searchset.model.entity.Post;
import com.weng.searchset.service.PostService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 主类测试
  */
@SpringBootTest
class MainApplicationTests {

    @Resource
    private PostService postService;

    @Test
    void getPostTest() {
        //https://www.coolapk.com/editorChoice?p=2
        String url1 = String.format("https://www.coolapk.com/editorChoice?p=6");
        Document doc = null;
        try {
            doc = Jsoup.connect(url1).get();
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据获取异常");
        }
        Elements elements = doc.selectXpath("//*[@id=\"feed-part\"]/div");
        List<String> urlEndList = new ArrayList<>();
        for (Element element : elements) {
            // 取标题
            String urlEnd = element.attr("id");
            if (urlEnd.length() > 8){
                String replace = urlEnd.replace("-", "/");
                urlEndList.add(replace);
            }

        }
        List<Post> postList = new ArrayList<>();
        for (String urlEnd : urlEndList) {
            String url2 = String.format("https://www.coolapk.com/" + urlEnd);

            Document doc2 = null;
            try {
                doc2 = Jsoup.connect(url2).get();
            } catch (IOException e) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据获取异常");
            }
            Post post = new Post();

            String title = doc2.select(".message-title").select("p").text();
            StringBuilder context = new StringBuilder();
            post.setTitle(title);
            for (Element element : doc2.select(".feed-article-message")) {
                String text1 = element.select("p").text();
                context.append(text1);
            }
            post.setContent(context.toString());
            post.setUserId(1645085736887676930L);
            post.setTags(("[\"a\",\"b\",\"c\"]"));
            postService.save(post);
        }


    }

}
