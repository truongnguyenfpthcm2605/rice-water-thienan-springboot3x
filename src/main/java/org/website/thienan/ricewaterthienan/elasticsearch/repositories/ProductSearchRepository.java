package org.website.thienan.ricewaterthienan.elasticsearch.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import org.website.thienan.ricewaterthienan.elasticsearch.documments.ProductDocument;

import java.time.LocalDateTime;

@Repository
public interface ProductSearchRepository extends ElasticsearchRepository<ProductDocument, String> {

    @Query("{\"bool\": {" +
            "\"should\": [" +
            "{\"wildcard\": {\"name\": \"*?0*\"}}," +
            "{\"range\": {\"price\": {\"gte\": ?1, \"lte\": ?2}}}," +
            "{\"range\": {\"createAt\": {\"gte\": \"?3\"}}}," +
            "{\"range\": {\"views\": {\"gte\": ?4}}}" +
            "]," +
            "\"filter\": {" +
            "\"term\": {\"active\": ?5}" +
            "}" +
            "}}"
    )
    Page<ProductDocument> search(Pageable pageable,
                                        String name,
                                        Double minPrice,
                                        Double maxPrice,
                                        LocalDateTime create,
                                        Long minViews,
                                        Boolean active);

}
