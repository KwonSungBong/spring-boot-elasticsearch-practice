package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by 권성봉 on 8/24/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("ex-es")
public class ServiceTest {

//    @Autowired
//    private SearchContentRepository contentRepository;
//
//    @Test
//    public void 저장(){
//        SearchContent searchContent = new SearchContent();
//        searchContent.setIdx(Long.valueOf(1));
//        searchContent.setSubject("testSubject");
//        searchContent.setContents("testContent");
//
//        SearchContent savedSearchContent = contentRepository.save(searchContent);
//
//        log.debug("{}", savedSearchContent.getSubject());
//    }
//
//    @Test
//    public void 모두가져오기(){
//        List<SearchContent> searchContents = (List<SearchContent>) contentRepository.findAll();
//
//        log.debug("{}", searchContents);
//    }
//
//    @Test
//    public void 이름모두가져오기(){
//        String searchSubject = "test";
//
//        List<SearchContent> searchContents = contentRepository.findBysubject(searchSubject);
//
//        log.debug("{}", searchContents);
//    }

}
