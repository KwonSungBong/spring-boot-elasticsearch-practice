package com.example.demo.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * Created by ksb on 2017. 9. 2..
 */
@Data
@Document(indexName = "user", type = "user", shards = 1, replicas = 0, refreshInterval = "-1")
public class User {

    @Id
    private long id;
    private String email;
    private String name;
    private String password;

}
