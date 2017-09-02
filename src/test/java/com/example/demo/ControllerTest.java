package com.example.demo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by 권성봉 on 8/24/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("ex-es")
public class ControllerTest {

//    private MockMvc mockMvc;
//
//    private HttpMessageConverter mappingJackson2HttpMessageConverter;
//
//    @Autowired
//    private SearchContentRepository searchContentRepository;
//
//    @Autowired
//    private SearchContentController searchContentController;
//
//    @Autowired
//    void setConverters(HttpMessageConverter<?>[] converters) {
//        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(
//                hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();
//    }
//
//    @Before
//    public void setUp(){
//        this.mockMvc = MockMvcBuilders.standaloneSetup(searchContentController).setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();
//    }
//
//    protected String json(Object o) throws IOException {
//        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
//        this.mappingJackson2HttpMessageConverter.write(
//                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
//        return mockHttpOutputMessage.getBodyAsString();
//    }
//
//    private SearchContent searchContent;
//
//    public SearchContent lastOne(){
//        List<SearchContent> contents = (List<SearchContent>)searchContentRepository.findAll();
//
//        if(contents.size() > 0){
//            return contents.get(contents.size() - 1);
//        }
//        return null;
//    }
//
//    @Test
//    public void 목록가져오기() throws Exception {
//        this.mockMvc.perform(
//                get("/search-content")
//                        .contentType(MediaType.ALL.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(jsonPath("$.content", is(notNullValue())))
//                .andExpect(status().isOk());
//    }
}
