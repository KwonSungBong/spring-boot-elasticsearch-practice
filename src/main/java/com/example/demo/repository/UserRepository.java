package com.example.demo.repository;

import com.example.demo.document.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by ksb on 2017. 9. 2..
 */
public interface UserRepository extends ElasticsearchRepository<User, Long> {
}
