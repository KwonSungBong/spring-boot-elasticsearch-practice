package com.example.demo.document;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.util.Date;
import java.util.List;


/**
 * Created by 권성봉 on 2016. 9. 5..
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "content", type = "content")
public class Content {

    @Id
    private Long id;

    private String subject;

    private boolean visible;

    private String content;

    private long view;

    private User createdUser;

    private User lastModifiedUser;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd'T'HH:mm:ss.SSSZZ")
    @Field(type = FieldType.Date, index = FieldIndex.not_analyzed, store = true,
            format = DateFormat.custom, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ")
    private Date createdDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd'T'HH:mm:ss.SSSZZ")
    @Field(type = FieldType.Date, index = FieldIndex.not_analyzed, store = true,
            format = DateFormat.custom, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ")
    private Date lastModifiedDate;

    private List<Tag> tagList;

}
