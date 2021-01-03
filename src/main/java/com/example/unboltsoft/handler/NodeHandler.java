package com.example.unboltsoft.handler;

import com.example.unboltsoft.dto.NodeDesc;
import com.example.unboltsoft.dto.NodeRoot;
import com.example.unboltsoft.repository.NodeDescRepository;
import com.example.unboltsoft.repository.NodeRootRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class NodeHandler {

    @Autowired
    private NodeRootRepository nodeRootRepository;

    @Autowired
    private NodeDescRepository nodeDescRepository;

    private Mono<ServerResponse> not_found = ServerResponse.notFound().build();

    public Mono<ServerResponse> getNodesList(ServerRequest request) {
        Flux<NodeRoot> nodeRoot = nodeRootRepository.findAllNodeRoot();
        Flux<NodeDesc> nodeDesc = nodeDescRepository.findAllNodeDesc();

        return ok()
                .render("index", Map.of("nodeRoot", nodeRoot, "nodeDesc", nodeDesc));
    }

    public Mono<ServerResponse> createNode(ServerRequest request) {
        Mono<Object> node = getExpectedNode(request);

        return node.flatMap(n -> ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(BodyInserters.fromObject(n))
                .switchIfEmpty(not_found));
    }

    private Mono<Object> getExpectedNode(ServerRequest request) {
        Mono<NodeDesc> descMono = request.bodyToMono(NodeDesc.class);

        return descMono.flatMap(dM -> {
            if (dM.getDescription() != null) {
                return nodeDescRepository.save(dM);
            }
            Mono<NodeRoot> rootMono = Mono.just(new NodeRoot(dM.getId(), dM.getName()));

            return rootMono.flatMap(r -> nodeRootRepository.save(r));
        });
    }
}
