package com.example.unboltsoft.repository;

import com.example.unboltsoft.dto.NodeDesc;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface NodeDescRepository extends ReactiveMongoRepository<NodeDesc, String> {

    @Query("{description: {$exists:true}}")
    Flux<NodeDesc> findAllNodeDesc();
}
