package com.example.unboltsoft.repository;

import com.example.unboltsoft.dto.NodeRoot;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface NodeRootRepository extends ReactiveMongoRepository<NodeRoot, String> {

    @Query("{description: {$exists:false}}")
    Flux<NodeRoot> findAllNodeRoot();
}
