package com.example.demo.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * Created by ksb on 2017. 9. 2..
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "user", type = "user")
public class User {

    @Id
    private long id;

    private String email;

    private String name;

    private String password;

}
