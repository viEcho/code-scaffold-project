package com.es.sample.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "test_index_books",shards = 1,replicas = 0)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EsBook {

    @Id
    @Field(type = FieldType.Keyword)
    private String id;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Keyword)
    private String language;

    @Field(type = FieldType.Keyword)
    private String author;

    @Field(type = FieldType.Float)
    private Float price;

    @Field(type = FieldType.Text)
    private String description;
}


