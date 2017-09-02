package com.example.demo;

import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.MultiGetResultMapper;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static java.util.stream.Collectors.joining;
import static org.elasticsearch.index.query.QueryBuilders.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;
import static org.junit.Assert.*;

/**
 * Created by 권성봉 on 8/24/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("ex-es")
public class TemplateTest {

//    @Autowired
//    private ElasticsearchTemplate elasticsearchTemplate;
//
//    private IndexQuery getIndexQuery(SearchContent searchContent) {
//        return new IndexQueryBuilder().withId(String.valueOf(searchContent.getIdx())).withObject(searchContent).build();
//    }
//
//    private List<IndexQuery> getIndexQueries(List<SearchContent> searchContents){
//        List<IndexQuery> indexQueires = new ArrayList<>();
//        for (SearchContent searchContent : searchContents){
//            indexQueires.add(new IndexQueryBuilder().withId(String.valueOf(searchContent.getIdx())).withObject(searchContent).build());
//        }
//        return indexQueires;
//    }
//
//    /*
//    curl -XGET http://localhost:9200/content/content/1?pretty
//     */
//    @Test
//    public void 아이디로_검색(){
//        GetQuery getQuery = new GetQuery();
//        getQuery.setId("1");
//        SearchContent searchContent = elasticsearchTemplate.queryForObject(getQuery, SearchContent.class);
//        log.debug("{}", searchContent);
//    }
//
//    /*
//    curl -XPOST http://localhost:9200/content/content/3?pretty -d '
//    {
//        "subject" : "testtestSubject4",
//        "contents" : "testtestContent"
//    }
//    '
//    */
//    @Test
//    public void 등록(){
//        SearchContent searchContent = new SearchContent();
//        searchContent.setIdx(Long.valueOf(8));
//        searchContent.setSubject("TESTTEST8");
//        searchContent.setContents("ASFASFASF");
//
//        IndexQuery indexQuery = getIndexQuery(searchContent);
//        elasticsearchTemplate.index(indexQuery);
//
//        GetQuery getQuery = new GetQuery();
//        getQuery.setId(String.valueOf(searchContent.getIdx()));
//
//        SearchContent searchContent1 = elasticsearchTemplate.queryForObject(getQuery, SearchContent.class);
//
//        assertNotNull("널이 아니에요", searchContent1);
//        assertEquals(searchContent, searchContent1);
//
//    }
//
//    /*
//    curl -XPOST http://localhost:9200/content/content/9?pretty -d '
//    {
//        "subject" : "TESTTEST8",
//        "contents" : "ASDASDASDASD"
//    }'
//    */
//    @Test
//    public void 등록2(){
//        SearchContent searchContent = SearchContent.builder()
//                .idx(Long.valueOf(9))
//                .subject("TESTTEST8")
//                .contents("ASDASDASDASD")
//                .build();
//
//        IndexQuery indexQuery = getIndexQuery(searchContent);
//        elasticsearchTemplate.index(indexQuery);
//
//        GetQuery getQuery = new GetQuery();
//        getQuery.setId(String.valueOf(searchContent.getIdx()));
//
//        SearchContent searchContent1 = elasticsearchTemplate.queryForObject(getQuery, SearchContent.class);
//
//        assertNotNull("널이 아니에요", searchContent1);
//        assertEquals(searchContent, searchContent1);
//    }
//
//
//    /*
//     */
//    @Test
//    public void 여러개등록_및_여러개가져오기(){
//        long documentId1 = new Random().nextInt(50);
//
//        SearchContent searchContent1 = SearchContent.builder().idx(documentId1).subject("testestAAA")
//                .contents("ASGASGASGAG").build();
//
//        long documentId2 = new Random().nextInt(50);
//
//        SearchContent searchContent2 = SearchContent.builder().idx(documentId2).subject("SDGSDGSDGSDG")
//                .contents("GDSSDGDSG").build();
//
//        List<IndexQuery> indexQueries = getIndexQueries(Arrays.asList(searchContent1, searchContent2));
//
//        elasticsearchTemplate.bulkIndex(indexQueries);
//        elasticsearchTemplate.refresh(SearchContent.class);
//
//        SearchQuery query = new NativeSearchQueryBuilder()
//                .withIds(Arrays.asList(String.valueOf(documentId1), String.valueOf(documentId2)))
//                .withFields("subject", "contents")
//                .build();
//
//        LinkedList<SearchContent> searchContents = elasticsearchTemplate.multiGet(query, SearchContent.class, new MultiGetResultMapper() {
//            @Override
//            public <T> LinkedList<T> mapResults(MultiGetResponse responses, Class<T> claszz) {
//                LinkedList<T> list = new LinkedList<T>();
//                for(MultiGetItemResponse response : responses.getResponses()){
//                    SearchContent content = new SearchContent();
//                    content.setIdx(Long.valueOf(response.getResponse().getId()));
//                    content.setSubject((String) response.getResponse().getField("subject").getValue());
//                    content.setContents((String) response.getResponse().getField("contents").getValue());
//                    list.add((T) content);
//                }
//                return list;
//            }
//        });
//
//        assertThat(searchContents.size(), is(equalTo(2)));
//    }
//
//    @Test
//    public void 여러개등록_및_여러개가져오기2(){
//        long documentId = new Random().nextInt(50);
//
//        SearchContent searchContent = SearchContent.builder().idx(documentId).subject("FFGDSGG")
//                .contents("HDSHSDHDSHSDH").build();
//
//        long documentId2 = new Random().nextInt(50);
//        SearchContent searchContent2 = SearchContent.builder().idx(documentId2).subject("FDSGL:KG:L")
//                .contents("FMDFMKDSLFMLKDS").build();
//
//        List<IndexQuery> indexQueries = getIndexQueries(Arrays.asList(searchContent, searchContent2));
//
//        elasticsearchTemplate.bulkIndex(indexQueries);
//        elasticsearchTemplate.refresh(SearchContent.class);
//
//        SearchQuery query = new NativeSearchQueryBuilder().withIds(Arrays.asList(String.valueOf(documentId), String.valueOf(String.valueOf(documentId2)))).build();
//        LinkedList<SearchContent> searchContents = elasticsearchTemplate.multiGet(query, SearchContent.class);
//
//        assertThat(searchContents.size(), is(equalTo(2)));
//        assertEquals(searchContents.get(0), searchContent);
//        assertEquals(searchContents.get(1), searchContent2);
//    }
//
//    /*
//    curl -XPOST http://localhost:9200/content/content/55?pretty -d '
//    {
//        "subject" : "GFDHR",
//        "contents" : "FSGKKJIOIOPO"
//    }'
//    */
//    @Test
//    public void 하나등록하나가져오기(){
//        long documentId = new Random().nextInt(50);
//
//        SearchContent searchContent = SearchContent.builder().idx(documentId).subject("GFDHR")
//                .contents("FSGKKJIOIOPO").build();
//
//        IndexQuery indexQuery = getIndexQuery(searchContent);
//
//        elasticsearchTemplate.index(indexQuery);
//        elasticsearchTemplate.refresh(SearchContent.class);
//
//        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery()).build();
//
//        Page<SearchContent> searchContents = elasticsearchTemplate.queryForPage(searchQuery, SearchContent.class);
//
//        assertThat(searchContents, is(notNullValue()));
//        assertThat(searchContents.getTotalElements(), greaterThanOrEqualTo(1L));
//    }
//
//    @Test
//    public void 두개등록두개가져오기(){
//        long documentId = new Random().nextInt(50);
//
//        SearchContent searchContent = SearchContent.builder().idx(documentId).subject("GGGGGG")
//                .contents("FKLDS:KF:LSDK").build();
//
//        long documentId2 = new Random().nextInt(50);
//
//        SearchContent searchContent2 = SearchContent.builder().idx(documentId2).subject("GFGJKFDLLG")
//                .contents("HFDKHJFDKLH").build();
//
//        List<IndexQuery> indexQueries = getIndexQueries(Arrays.asList(searchContent, searchContent2));
//
//        elasticsearchTemplate.bulkIndex(indexQueries);
//        elasticsearchTemplate.refresh(SearchContent.class);
//
//        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery()).build();
//        Page<SearchContent> searchContents = elasticsearchTemplate.queryForPage(searchQuery, SearchContent.class);
////        assertThat(searchContents.getTotalElements(), is(equalTo(2L)));
//    }
//
//    /* 수정
//    curl -XPUT http://localhost:9200/content/content/2?pretty -d '
//    {
//        "subject" : "testtestSubject2",
//        "contents" : "testtestContent2"
//    }
//    '
//    */
//    @Test
//    public void 수정(){
//        IndexRequest indexRequest = new IndexRequest();
//        indexRequest.source("subject", "testtest");
//        UpdateQuery updateQuery = new UpdateQueryBuilder()
//                .withId("1")
//                .withClass(SearchContent.class)
//                .withIndexRequest(indexRequest)
//                .build();
//        UpdateResponse update = elasticsearchTemplate.update(updateQuery);
//        log.debug("{}", update);
//    }
//
//    @Test
//    public void 대량수정(){
//        long documentId = new Random().nextInt(50);
//        String subjectBeforeUpdate = "SAFASASF";
//        String subjectAfterUpdate = "GFGFD";
//
//        SearchContent searchContent = SearchContent.builder().idx(documentId).subject(subjectBeforeUpdate)
//                .contents("GFGFDGFDGDFG").build();
//
//        IndexQuery indexQuery = getIndexQuery(searchContent);
//
//        elasticsearchTemplate.index(indexQuery);
//        elasticsearchTemplate.refresh(SearchContent.class);
//
//        IndexRequest indexRequest = new IndexRequest();
//        indexRequest.source("subject", subjectAfterUpdate);
//        UpdateQuery updateQuery = new UpdateQueryBuilder().withId(String.valueOf(documentId))
//                .withClass(SearchContent.class).withIndexRequest(indexRequest).build();
//
//        List<UpdateQuery> queries = new ArrayList<>();
//        queries.add(updateQuery);
//
//        elasticsearchTemplate.bulkUpdate(queries);
//
//        GetQuery getQuery = new GetQuery();
//        getQuery.setId(String.valueOf(documentId));
//        SearchContent indexedContent = elasticsearchTemplate.queryForObject(getQuery, SearchContent.class);
//        assertThat(indexedContent.getSubject(), is(subjectAfterUpdate));
//    }
//
//
//    /*
//    curl -XDELETE http://localhost:9200/content/content/3?pretty
//    */
//    @Test
//    public void 삭제(){
//        String delete = elasticsearchTemplate.delete(SearchContent.class, "1");
//        log.debug("{}", delete);
//    }
//
//    @Test
//    public void 삭제2(){
//        long documentId = new Random().nextInt(50);
//        SearchContent searchContent = SearchContent.builder().idx(documentId).subject("HDHFDHFDH")
//                .contents("ASGDSGWEEWG").build();
//
//        IndexQuery indexQuery = getIndexQuery(searchContent);
//
//        elasticsearchTemplate.index(indexQuery);
//
//        elasticsearchTemplate.delete("content", "content", String.valueOf(documentId));
//        elasticsearchTemplate.refresh(SearchContent.class);
//
//        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(termQuery("idx", documentId)).build();
//        Page<SearchContent> searchContents = elasticsearchTemplate.queryForPage(searchQuery, SearchContent.class);
//        assertThat(searchContents.getTotalElements(), equalTo(0L));
//    }
//
//    @Test
//    public void 삭제3(){
//        long documentId = new Random().nextInt(50);
//        SearchContent searchContent = SearchContent.builder().idx(documentId).subject("GDSGDSGSDG")
//                .contents("AAQQQQQQ").build();
//
//        IndexQuery indexQuery = getIndexQuery(searchContent);
//
//        elasticsearchTemplate.index(indexQuery);
//        elasticsearchTemplate.refresh(SearchContent.class);
//
//        DeleteQuery deleteQuery = new DeleteQuery();
//        deleteQuery.setQuery(termQuery("idx", documentId));
//        elasticsearchTemplate.delete(deleteQuery, SearchContent.class);
//        elasticsearchTemplate.refresh(SearchContent.class);
//
//        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(termQuery("idx", documentId)).build();
//        Page<SearchContent> searchContents = elasticsearchTemplate.queryForPage(searchQuery, SearchContent.class);
//        assertThat(searchContents.getTotalElements(), equalTo(0L));
//    }
//
//    /*
//    curl -XGET 'http://localhost:9200/content/_search?pretty' -d '{
//        "query": {
//            "bool" : {
//                "filter" : {
//                    "term" : { "idx" : "1" }
//                }
//            }
//        }
//    }'
//     */
//    @Test
//    public void 목록검색필터(){
//        long documentId = new Random().nextInt(50);
//        SearchContent searchContent = SearchContent.builder().idx(documentId).subject("GFGDG")
//                .contents("GFGDG").build();
//
//        IndexQuery indexQuery = getIndexQuery(searchContent);
//        elasticsearchTemplate.index(indexQuery);
//        elasticsearchTemplate.refresh(SearchContent.class);
//
//        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery())
//                .withFilter(boolQuery().filter(termQuery("idx", documentId))).build();
//
//        Page<SearchContent> searchContents = elasticsearchTemplate.queryForPage(searchQuery, SearchContent.class);
//
//        assertThat(searchContents.getTotalElements(), equalTo(1L));
//    }
//
//    /*
//    curl -XGET localhost:9200/content/_search?pretty -d '
//    {
//        "sort" : [{"startYear" : "asc"}]
//    }'
//     */
//    @Test
//    public void 목록검색정렬(){
//        long documentId = new Random().nextInt(50);
//        SearchContent searchContent = SearchContent.builder().idx(documentId)
//                .subject("GFDGFDG").contents("10").startYear(10).build();
//
//        long documentId2 = new Random().nextInt(50);
//        SearchContent searchContent2 = SearchContent.builder().idx(documentId2)
//                .subject("GFDGDFG").contents("5").startYear(5).build();
//
//        long documentId3 = new Random().nextInt(50);
//        SearchContent searchContent3 = SearchContent.builder().idx(documentId3)
//                .subject("HDFGHDFH").contents("15").startYear(15).build();
//
//        List<IndexQuery> indexQueries = getIndexQueries(Arrays.asList(searchContent, searchContent2, searchContent3));
//
//        elasticsearchTemplate.bulkIndex(indexQueries);
//        elasticsearchTemplate.refresh(SearchContent.class);
//
//        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery())
//                .withSort(new FieldSortBuilder("startYear").order(SortOrder.ASC)).build();
//
//        Page<SearchContent> searchContents = elasticsearchTemplate.queryForPage(searchQuery, SearchContent.class);
//
////        assertThat(searchContents.getTotalElements(), equalTo(3L));
//        assertThat(searchContents.getContent().get(0).getStartYear(), is(searchContent2.getStartYear()));
//    }
//
//
//    /*
//    curl -XGET localhost:9200/content/_search?pretty -d '
//    {
//        "sort" : [{"subject" : "asc", "contents" : "asc"}]
//    }'
//     */
//    @Test
//    public void 목록검색다중정렬(){
//        long documentId = new Random().nextInt(50);
//        SearchContent searchContent = SearchContent.builder().idx(documentId)
//                .subject("2B").contents("ASADASD").build();
//
//        long documentId2 = new Random().nextInt(50);
//        SearchContent searchContent2 = SearchContent.builder().idx(documentId2)
//                .subject("1A").contents("WQEWQE").build();
//
//        long documentId3 = new Random().nextInt(50);
//        SearchContent searchContent3 = SearchContent.builder().idx(documentId3)
//                .subject("3C").contents("QEIPORJOQW").build();
//
//        List<IndexQuery> indexQueries = getIndexQueries(Arrays.asList(searchContent, searchContent2, searchContent3));
//
//        elasticsearchTemplate.bulkIndex(indexQueries);
//        elasticsearchTemplate.refresh(SearchContent.class);
//
//        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery())
//                .withSort(new FieldSortBuilder("subject").order(SortOrder.ASC))
//                .withSort(new FieldSortBuilder("contents").order(SortOrder.ASC)).build();
//
//        Page<SearchContent> searchContents = elasticsearchTemplate.queryForPage(searchQuery, SearchContent.class);
//
////        assertThat(searchContents.getTotalElements(), equalTo(3L));
//        assertThat(searchContents.getContent().get(0).getSubject(), is(searchContent2.getSubject()));
////        assertThat(searchContents.getContent().get(0).getContents(), is(searchContent.getContents()));
//    }
//
//    /*
//    https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-query-string-query.html
//    curl -XGET 'http://localhost:9200/content/content/_search?pretty' -d '{
//    "query": {
//        "query_string" : {
//            "query" : "1 OR 3"
//        }
//    }}'
//    */
//    @Test
//    public void 문자열쿼리실행(){
//        long documentId = new Random().nextInt(50);
//        SearchContent searchContent = SearchContent.builder().idx(documentId).subject("FDSFSD")
//                .contents("FSDFSDF").build();
//
//        IndexQuery indexQuery = getIndexQuery(searchContent);
//
//        elasticsearchTemplate.index(indexQuery);
//        elasticsearchTemplate.refresh(SearchContent.class);
//
//        StringQuery stringQuery = new StringQuery(matchAllQuery().toString());
//
//        Page<SearchContent> searchContents = elasticsearchTemplate.queryForPage(stringQuery, SearchContent.class);
//
////        assertThat(searchContents.getTotalElements(), equalTo(1L));
//    }
//
//    /*
//    https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-query-string-query.html
//    curl -XGET 'http://localhost:9200/content/content/_search?pretty' -d '{
//    "query": {
//        "query_string" : {
//            "fields" : ["idx"],
//            "query" : "1 OR 3"
//        }
//    }}'
//    */
//    @Test
//    public void 문자열쿼리실행2(){
//        long documentId = new Random().nextInt(50);
//        SearchContent searchContent = SearchContent.builder().idx(documentId).subject("FFF")
//                .contents("FFASFASF").build();
//
//        IndexQuery indexQuery = getIndexQuery(searchContent);
//
//        elasticsearchTemplate.index(indexQuery);
//        elasticsearchTemplate.refresh(SearchContent.class);
//
//        StringQuery stringQuery = new StringQuery(termQuery("idx", documentId).toString());
//        SearchContent searchContent2 = elasticsearchTemplate.queryForObject(stringQuery, SearchContent.class);
//
//        assertThat(searchContent2, is(notNullValue()));
//        assertThat(searchContent2.getIdx(), is(equalTo(documentId)));
//    }
//
//    /*
//    curl -XGET localhost:9200/content/content/_search?pretty
//    */
//    @Test
//    public void 목록가져오기(){
//        SearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(matchAllQuery())
//                .withIndices("content")
//                .withTypes("content")
//                .build();
//        List<SearchContent> contents = elasticsearchTemplate.queryForList(searchQuery, SearchContent.class);
//        log.debug("{}", contents);
//    }
//
//    /*
//    curl -XGET localhost:9200/content/content/_count?pretty
//    */
//    @Test
//    public void 목록개수가져오기(){
//        SearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(matchAllQuery())
//                .withIndices("content")
//                .withTypes("content")
//                .build();
//        long count = elasticsearchTemplate.count(searchQuery, SearchContent.class);
//        log.debug("{}", count);
//    }
//
//    /*
//    curl -XGET localhost:9200/content/content/_search?pretty&fields=subject
//    */
//    @Test
//    public void 목록가져오기_특정필드(){
//        SearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(matchAllQuery())
//                .withIndices("content")
//                .withTypes("content")
//                .withFields("subject")
//                .build();
//        List<SearchContent> contents = elasticsearchTemplate.queryForList(searchQuery, SearchContent.class);
//        log.debug("{}", contents);
//    }
//
//    /*
//    https://www.elastic.co/guide/en/elasticsearch/reference/current/search-request-from-size.html
//    curl -XGET localhost:9200/content/_search?pretty -d '
//    {
//        "from" : 0, "size" : 5,
//        "sort" : [{"subject" : "desc"}]
//    }'
//    */
//    @Test
//    public void 목록가져오기_페이징(){
//        SearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withIndices("content")
//                .withTypes("content")
//                .withQuery(matchAllQuery())
//                .withSort(new FieldSortBuilder("subject").order(SortOrder.DESC))
//                .withPageable(new PageRequest(0, 5)).build();
//        Page<SearchContent> contents = elasticsearchTemplate.queryForPage(searchQuery, SearchContent.class);
//
//        String str = contents.getContent().stream()
//                .map(i -> i.toString())
//                .collect(joining("\n"));
//        log.debug("{}", str);
//        log.debug("{}", contents.getContent().size());
//    }
//
//    /*
//    curl 'localhost:9200/content/_search?pretty' -d '
//    {
//        "query" : {
//        "term" : { "subject" : "test" }
//    }
//    }'
//    */
//    @Test
//    public void 목록가져오기_검색조건(){
//        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(termQuery("subject", "testSubject")).build();
//        List<SearchContent> contents = elasticsearchTemplate.queryForList(searchQuery, SearchContent.class);
//        String str = contents.stream()
//                .map(i -> i.toString())
//                .collect(joining("\n"));
//        log.debug("{}", str);
//        log.debug("{}", contents.size());
//    }
//
//    /*
//    curl 'localhost:9200/content/_search?pretty' -d '
//        {
//        "_source" : [ "subject", "*test*" ]
//        }'
//    */
//    @Test
//    public void 목록가져오기_검색조건2(){
//        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(wildcardQuery("subject", "*test*")).build();
//        List<SearchContent> contents = elasticsearchTemplate.queryForList(searchQuery, SearchContent.class);
//        String str = contents.stream()
//                .map(i -> i.toString())
//                .collect(joining("\n"));
//        log.debug("{}", str);
//        log.debug("{}", contents.size());
//    }
//
//    /*
//    curl -XGET "http://localhost:9200/content/content/_search?pretty&q=subject:*test*"
//    */
//    @Test
//    public void 목록가져오기_검색조건3(){
//        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryStringQuery("*test*")).build();
//        List<SearchContent> contents = elasticsearchTemplate.queryForList(searchQuery, SearchContent.class);
//        String str = contents.stream()
//                .map(i -> i.toString())
//                .collect(joining("\n"));
//        log.debug("{}", str);
//        log.debug("{}", contents.size());
//    }
//
//    /*
//    curl 'localhost:9200/content/_search?pretty' -d '
//        {
//          "query": {
//            "bool": {
//              "must": [
//                { "match": { "subject":   "ab"        }},
//                { "match": { "contents": "ab" }}
//              ],
//              "filter": [
//                { "term":  { "subject": "ab" }}
//              ]
//            }
//          }
//        }
//    '
//    */
//    @Test
//    public void 목록가져오기_검색조건4(){
//        long documentId = new Random().nextInt(50);
//        SearchContent searchContent = SearchContent.builder().idx(documentId).subject("ab cd ef")
//                .contents("ab cd dd ee ff gg").build();
//        IndexQuery indexQuery = getIndexQuery(searchContent);
//        elasticsearchTemplate.index(indexQuery);
//        elasticsearchTemplate.refresh(SearchContent.class);
//
//        SearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(termQuery("subject", "ab"))
//                .withQuery(termQuery("contents", "ab"))
//                .withQuery(boolQuery().filter(termQuery("subject", "ab")))
//                .build();
//        List<SearchContent> contents = elasticsearchTemplate.queryForList(searchQuery, SearchContent.class);
//        String str = contents.stream()
//                .map(i -> i.toString())
//                .collect(joining("\n"));
//        log.debug("{}", str);
//        log.debug("{}", contents.size());
//
//    }
//
//    /*
//    curl -XPOST http://localhost:9200/content/content/51?pretty -d '
//    {
//        "subject" : "testtestSubject4",
//        "contents" : "testtestContent",
//        "createdDate" : "2013-01-30"
//    }
//    '
//    curl 'localhost:9200/content/_search?pretty' -d '
//        {
//          "query": {
//            "bool": {
//              "filter": [
//                { "range": { "createdDate": { "gte": "2013-01-20" }}}
//              ]
//            }
//          }
//        }
//    '
//    */
//    @Test
//    public void 목록가져오기_검색조건5(){
//        long documentId = new Random().nextInt(50);
//        SearchContent searchContent = SearchContent.builder().idx(documentId).subject("ab cd ef")
//                .contents("ab cd dd ee ff gg").createdDate("2013-01-30").build();
//        IndexQuery indexQuery = getIndexQuery(searchContent);
//        elasticsearchTemplate.index(indexQuery);
//        elasticsearchTemplate.refresh(SearchContent.class);
//
//        SearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(boolQuery().filter(termQuery("subject", "ab")))
//                .build();
//        List<SearchContent> contents = elasticsearchTemplate.queryForList(searchQuery, SearchContent.class);
//        String str = contents.stream()
//                .map(i -> i.toString())
//                .collect(joining("\n"));
//        log.debug("{}", str);
//        log.debug("{}", contents.size());
//    }
//
//    /*
//    curl 'localhost:9200/content/_search?pretty' -d '
//        {
//          "wildcard" : { "subject" : "*a*" }
//        }
//    '
//     */
//    @Test
//    public void 목록가져오기_검색조건6() {
//        SearchContent searchContent = SearchContent.builder().idx(101L).subject("ab").contents("ASADASD").build();
//        SearchContent searchContent2 = SearchContent.builder().idx(102L).subject("bc").contents("WQEWQE").build();
//        SearchContent searchContent3 = SearchContent.builder().idx(103L).subject("ac").contents("QEIPORJOQW").build();
//        List<IndexQuery> indexQueries = getIndexQueries(Arrays.asList(searchContent, searchContent2, searchContent3));
//
//        elasticsearchTemplate.bulkIndex(indexQueries);
//        elasticsearchTemplate.refresh(SearchContent.class);
//
//        SearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(boolQuery().must(wildcardQuery("subject", "*a*")).should(wildcardQuery("subject", "*b*")))
//                .withIndices("content")
//                .withTypes("content")
//                .withMinScore(0.5F)
//                .build();
//
//        Page<SearchContent> page = elasticsearchTemplate.queryForPage(searchQuery, SearchContent.class);
//
////        assertThat(page.getTotalElements(), is(1L));
////        assertThat(page.getContent().get(0).getSubject(), is("ab"));
//    }
//
//    /*
//    http://joelabrahamsson.com/elasticsearch-nested-mapping-and-filter/
//    https://www.elastic.co/guide/en/elasticsearch/guide/current/nested-objects.html
//    curl -XPOST http://localhost:9200/content/content/98?pretty -d '
//    {
//        "subject" : "testtestSubject4",
//        "contents" : "testtestContent",
//        "searchTagList" : [ {
//          "text" : "AA"
//        }, {
//          "text" : "BB"
//        }, {
//          "text" : "CC"
//        } ]
//    }
//    '
//    curl 'localhost:9200/content/_search?pretty' -d '
//        {
//          "query": {
//            "bool": {
//              "must": [
//                { "match": { "searchTagList.text":   "AA"        }}
//              ]
//            }
//          }
//        }
//    '
//    */
//    @Test
//    public void 태그등록_태그검색(){
//        SearchContent searchContent = SearchContent.builder()
//                .idx(Long.valueOf(99))
//                .subject("TESTTEST8")
//                .contents("ASDASDASDASD")
//                .build();
//
//        searchContent.setSearchTagList(Arrays.asList(new SearchTag("AA"), new SearchTag("BB"), new SearchTag("CC")));
//
//        IndexQuery indexQuery = getIndexQuery(searchContent);
//        elasticsearchTemplate.index(indexQuery);
//        elasticsearchTemplate.refresh(SearchContent.class);
//
//        SearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(termQuery("searchTagList.text", "AA"))
//                .build();
//        List<SearchContent> contents = elasticsearchTemplate.queryForList(searchQuery, SearchContent.class);
//        String str = contents.stream()
//                .map(i -> i.toString())
//                .collect(joining("\n"));
//
//        log.debug("print : {}", str);
//    }
//
//    /*
//    curl -XDELETE 'http://localhost:9200/tttt/'
//     */
//    public void 인덱스삭제(){
//        elasticsearchTemplate.deleteIndex("tttt");
//    }
//
//    /*
//    https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-update-settings.html#update-settings-analysis
//    curl -XPUT 'localhost:9200/content/_settings' -d '{
//          "analysis":{
//                "analyzer":{
//                    "korean":{
//                        "type":"custom",
//                        "tokenizer":"seunjeon_default_tokenizer"
//                    }
//                },
//                "tokenizer": {
//                    "seunjeon_default_tokenizer": {
//                        "type": "seunjeon_tokenizer",
//                        "user_words": ["낄끼빠빠,-100", "버카충", "abc마트", "떡", "복이"]
//                    }
//                }
//            }
//        }'
//    */
//    public void 인덱스수정(){
//        String settings = "{\n" +
//                "        \"index\": {\n" +
//                "            \"analysis\": {\n" +
//                "                \"analyzer\": {\n" +
//                "                    \"korean\": {\n" +
//                "                        \"type\": \"custom\",\n" +
//                "                        \"tokenizer\": \"seunjeon_default_tokenizer\"\n" +
//                "                    }\n" +
//                "                }\n"+
//                "                \"tokenizer\": {\n" +
//                "                    \"seunjeon_default_tokenizer\": {\n" +
//                "                        \"type\": \"seunjeon_tokenizer\",\n" +
//                "                        \"user_words\": [\"버카충\", \"abc마트\", \"떡\", \"복이\"]\n" +
//                "                    }\n" +
//                "                }\n" +
//                "            }\n" +
//                "        }\n" +
//                "}";
//
//        elasticsearchTemplate.deleteIndex("test-index");
//        elasticsearchTemplate.createIndex("test-index", settings);
//    }

/*
1. 한글형태소 설치 과정
 전체적인 설치방법은 아래 링크를 참조
 https://medium.com/@ProgrammingPearls/elasticsearch%EC%97%90%EC%84%9C-%ED%95%9C%EA%B8%80-%ED%98%95%ED%83%9C%EC%86%8C-%EB%B6%84%EC%84%9D%EA%B8%B0-%EC%9D%80%EC%A0%84-%ED%95%9C-%EB%8B%A2-%EB%A5%BC-os-x-%EC%97%90%EC%84%9C-%EC%A0%81%EC%9A%A9%ED%95%B4%EB%B3%B4%EC%9E%90-5f879962339#.miyak74xj

 ElasticSearch 2.3.3.0 설치
 https://www.elastic.co/downloads/past-releases/elasticsearch-2-3-3

 mecab-0.996-ko-0.9.2.tar.gz 설치
 https://bitbucket.org/eunjeon/mecab-ko/downloads

 mecab-ko-dic-2.0.1-20150920.tar.gz 설치
 https://bitbucket.org/eunjeon/mecab-ko-dic/downloads

 mecab-java-0.996.tar.gz 설치
 https://bitbucket.org/eunjeon/mecab-java/downloads

 elasticsearch-analysis-seunjeon 2.3.3 설치
 http://eunjeon.blogspot.kr

 설치 후 사용방법은
 https://bitbucket.org/eunjeon/seunjeon/src/ad2e2655ac940d2a6cc8d002c1dad1b5f807a01c/elasticsearch/?at=es-2.3.3.0


2. 한글형태소 테스트
curl -XPUT "http://localhost:9200/tttt/?pretty" -d '{
    "settings" : {
        "index":{
            "analysis":{
                "analyzer":{
                    "korean":{
                        "type":"custom",
                        "tokenizer":"seunjeon_default_tokenizer"
                    }
                },
                "tokenizer": {
                    "seunjeon_default_tokenizer": {
                        "type": "seunjeon_tokenizer",
                        "user_words": ["낄끼빠빠,-100", "버카충", "abc마트", "떡", "복이"]
                    }
                }
            }
        }
    }
}'
curl -XGET "http://localhost:9200/tttt/_analyze?analyzer=korean&pretty" -d '떡복이'

curl -XPUT "http://localhost:9200/tttttt/?pretty" -d '
{
    "settings" : {
        "analysis" : {
            "analyzer" : {
                "korean_analyzer" : {
                    "type":"custom",
                    "tokenizer":"seunjeon_default_tokenizer"
                }
            },
            "tokenizer": {
                "seunjeon_default_tokenizer": {
                    "type": "seunjeon_tokenizer"
                }
            }
        }
    },
    "mappings" : {
        "content" : {
            "properties" : {
                "idx" : { "type" : "integer" },
                "subject" : { "type" : "string" },
                "content" : { "type" : "string", "analyzer" : "korean_analyzer" }
                }
            }
        }
    }
}
'


curl -XPOST http://localhost:9200/test/content/97?pretty -d '
    {
        "subject" : "안녕하세요",
        "contents" : "testtestContent",
        "searchTagList" : [ {
          "text" : "AA"
        }, {
          "text" : "BB"
        }, {
          "text" : "CC"
        } ]
    }
    '
curl 'localhost:9200/test/content/_search?pretty' -d '
        {
          "query": {
            "bool": {
              "must": [
                { "match": { "subject":   "안녕"        }}
              ]
            }
          }
        }
    '
    curl -XGET http://localhost:9200/content/content/97?pretty

     */


/*한글형태소 테스트
http://geniedev.tistory.com/6
curl -X POST 'localhost:9200/test' -d '
{
    "mappings" : {
        "users" : {
            "properties" : {
                "index" : { "type" : "integer" },
                "email" : { "type" : "string" },
                "nickName" : { "type" : "string"},
                "tag" : { "type" : "string"}
            }
        }
    }
}'
curl 'localhost:9200/enjun?pretty' -d '
{
    "settings" : {
        "analysis" : {
            "analyzer" : {
                "korean_analyzer" : {
                    "type":"custom",
                    "tokenizer":"seunjeon_default_tokenizer"
                }
            },
            "tokenizer": {
                "seunjeon_default_tokenizer": {
                    "type": "seunjeon_tokenizer"
                }
            }
        }
    },
    "mappings" : {
        "users" : {
            "properties" : {
                "index" : { "type" : "integer" },
                "email" : { "type" : "string" },
                "nickName" : { "type" : "string", "analyzer" : "korean_analyzer" },
                "tag" : { "type" : "string", "analyzer" : "korean_analyzer"
                }
            }
        }
    }
}'
curl -X POST http://localhost:9200/test/uesrs/1 -d '
{
    "email":"test@gmail.com",
    "nickName":"지니",
    "tag":"보도자료"
}'
curl -X POST http://localhost:9200/enjun/uesrs/1 -d '
{
    "email":"test@gmail.com",
    "nickName":"지니",
    "tag":"보도자료"
}'
curl -XGET http://localhost:9200/test/uesrs/_search?q=tag:'보도 자료'
curl -XGET http://localhost:9200/enjun/uesrs/_search?q=tag:'보도 자료'
curl 'localhost:9200/enjun/uesrs/_search?pretty' -d '
        {
          "query": {
            "bool": {
              "must": [
                { "match": { "tag":   "보도 자료"        }}
              ]
            }
          }
        }
    '

    curl -XGET localhost:9200/test/uesrs/_search?pretty


curl -X POST 'localhost:9200/test/content/_search?pretty' -d '
        {
        "from" : 0, "size" : 18,
        "sort" : [{"idx" : "desc"}],
          "query": {
            "bool": {
              "should": [
                { "match": { "tagList.text":   "BMW"        }}
              ]
            }
          }
        }
    '
curl -X POST 'localhost:9200/test/content/_search?pretty' -d '
        {
        "from" : 0, "size" : 18,
        "sort" : [{"idx" : "desc"}],
          "query": {
            "bool": {
              "should": [
                { "match": { "subject":   "BMW"        }}
                ,
                {
          "nested": {
            "path": "tagList",
            "query": {
              "bool": {
                "should": [
                  {
                    "match": {
                      "tagList.text": "BMW"
                    }
                  }
                ]
              }
            }
          }
          }
              ]
            }
          }
        }
    '

curl 'localhost:9200/content?pretty' -d '
{
    "settings" : {
        "analysis" : {
            "analyzer" : {
                "korean_analyzer" : {
                    "type":"custom",
                    "tokenizer":"seunjeon_default_tokenizer"
                }
            },
            "tokenizer": {
                "seunjeon_default_tokenizer": {
                    "type": "seunjeon_tokenizer"
                }
            }
        }
    },
    "mappings" : {
        "users" : {
            "properties" : {
                "index" : { "type" : "integer" },
                "email" : { "type" : "string" },
                "nickName" : { "type" : "string", "analyzer" : "korean_analyzer" },
                "tag" : { "type" : "string", "analyzer" : "korean_analyzer"
                }
            }
        }
    }
}'

curl -X POST 'http://localhost:9200/content/_search?pretty'

curl -X POST 'http://localhost:9200/content/_search?pretty' -d '
        {
        "from" : 0, "size" : 18,
        "sort" : [{"idx" : "desc"}],
          "query": {
            "bool": {
              "should": [
                { "match": { "subject":   "BMW"        }}
                ,
                {
          "nested": {
            "path": "tagList",
            "query": {
              "bool": {
                "should": [
                  {
                    "match": {
                      "tagList.text": "BMW"
                    }
                  }
                ]
              }
            }
          }
          }
              ]
            }
          }
        }
    '

curl -X GET 'http://search.carlab.co.kr/content/_status'

curl -XGET localhost:9200/_mappings?pretty

curl -X POST 'http://search.carlab.co.kr/re_content/_search?pretty' -d '
        {
        "from" : 0, "size" : 18,
        "sort" : [{"idx" : "desc"}],
          "query": {
            "bool": {
              "should": [
                { "match": { "subject":   "BMW"        }}
                ,
                {
          "nested": {
            "path": "tagList",
            "query": {
              "bool": {
                "should": [
                  {
                    "match": {
                      "tagList.text": "BMW"
                    }
                  }
                ]
              }
            }
          }
          }
              ]
            }
          }
        }
    '


curl 'localhost:9200/content?pretty' -d '
{
  "content" : {
    "mappings" : {
      "content" : {
        "properties" : {
          "content" : {
            "type" : "string",
            "analyzer" : "korean_analyzer"
          },
          "idx" : {
            "type" : "integer"
          },
          "subject" : {
            "type" : "string"
          }
        }
      }
    }
  },
  "content" : {
    "mappings" : {
      "content" : {
        "properties" : {
          "carCode" : {
            "type" : "string"
          },
          "category" : {
            "type" : "string"
          },
          "categoryName" : {
            "type" : "string"
          },
          "contents" : {
            "type" : "string",
            "analyzer" : "korean_analyzer"
          },
          "createdDate" : {
            "type" : "date",
            "format" : "strict_date_optional_time||epoch_millis"
          },
          "createdUser" : {
            "properties" : {
              "email" : {
                "type" : "string"
              },
              "userName" : {
                "type" : "string"
              }
            }
          },
          "idx" : {
            "type" : "long"
          },
          "imageGroup" : {
            "properties" : {
              "imageGroupIdx" : {
                "type" : "long"
              },
              "imageList" : {
                "properties" : {
                  "awsThumbUrl" : {
                    "type" : "string"
                  },
                  "awsUrl" : {
                    "type" : "string"
                  },
                  "imageIdx" : {
                    "type" : "long"
                  },
                  "imageName" : {
                    "type" : "string"
                  },
                  "main" : {
                    "type" : "boolean"
                  }
                }
              },
              "mainImageOriginUrl" : {
                "type" : "string"
              },
              "mainImageThumbUrl" : {
                "type" : "string"
              }
            }
          },
          "lastModifiedDate" : {
            "type" : "date",
            "format" : "strict_date_optional_time||epoch_millis"
          },
          "lastModifiedUser" : {
            "properties" : {
              "email" : {
                "type" : "string"
              },
              "userName" : {
                "type" : "string"
              }
            }
          },
          "option" : {
            "type" : "string"
          },
          "subject" : {
            "type" : "string",
            "analyzer" : "korean_analyzer"
          },
          "summaryContents" : {
            "type" : "string"
          },
          "tagList" : {
            "type" : "nested",
            "properties" : {
              "idx" : {
                "type" : "long"
              },
              "text" : {
                "type" : "string",
                "analyzer" : "korean_analyzer"
              }
            }
          },
          "view" : {
            "type" : "long"
          },
          "visible" : {
            "type" : "boolean"
          }
        }
      }
    }
  }
'




curl -XGET search.carlab.co.kr/re_content/_search?pretty

curl -XGET localhost:9200/new_index/_search?pretty

curl -XGET localhost:9200/_mappings?pretty

curl -XDELETE localhost:9200/tttttt?pretty

curl -XDELETE search.carlab.co.kr/content?pretty



curl -XGET search.carlab.co.kr/_mappings?pretty

curl -XPUT search.carlab.co.kr/content?pretty -d '
{
  "settings": {
    "analysis": {
      "analyzer": {
        "korean_analyzer": {
          "type": "custom",
          "tokenizer": "seunjeon_default_tokenizer"
        }
      },
      "tokenizer": {
        "seunjeon_default_tokenizer": {
          "type": "seunjeon_tokenizer"
        }
      }
    }
  },
  "mappings": {
    "content": {
      "properties": {
        "idx": {
          "type": "integer"
        },
        "subject": {
          "type": "string",
          "analyzer" : "korean_analyzer"
        },
        "content": {
          "type": "string",
          "analyzer": "korean_analyzer"
        },
        "tagList": {
          "type": "nested",
          "properties": {
            "idx": {
              "type": "long"
            },
            "text": {
              "type": "string",
              "analyzer": "korean_analyzer"
            }
          }
        }
      }
    }
  }
}
'

curl -XPOST search.carlab.co.kr/_reindex?pretty -d '
{
  "source": {
    "index": "re_content"
  },
  "dest": {
    "index": "content"
  }
}'


 */
}
