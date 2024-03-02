package com.es.sample.repository;

import com.es.sample.po.EsBook;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EsBookRepository extends ElasticsearchRepository<EsBook,String> {
}
