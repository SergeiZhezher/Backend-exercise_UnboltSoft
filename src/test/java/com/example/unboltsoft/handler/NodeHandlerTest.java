package com.example.unboltsoft.handler;

import com.example.unboltsoft.config.NodeRouter;
import com.example.unboltsoft.dto.NodeDesc;
import com.example.unboltsoft.dto.NodeRoot;
import com.example.unboltsoft.repository.NodeDescRepository;
import com.example.unboltsoft.repository.NodeRootRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {NodeRouter.class, NodeHandler.class})
@WebFluxTest
class NodeHandlerTest {

    @MockBean
    private NodeRootRepository nodeRootRepository;

    @MockBean
    private NodeDescRepository nodeDescRepository;

    @Autowired
    private WebTestClient webClient;

    private NodeRoot rootMono;
    private NodeDesc descMono;

    @BeforeEach
    void setUp() {
        rootMono = new NodeRoot("10", "name_test");
        descMono = new NodeDesc("10", "name_test", "description");
        when(nodeRootRepository.save(any())).thenReturn(Mono.just(rootMono));
        when(nodeDescRepository.save(any())).thenReturn(Mono.just(descMono));
    }

    @Test
    void createNodeRootTest() {
        webClient.post()
                .uri("/node")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(rootMono), NodeRoot.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(NodeRoot.class)
                .value(node -> {
                            Assertions.assertThat(node.getId()).isEqualTo("10");
                            Assertions.assertThat(node.getName()).isEqualTo("name_test");
                        }
                );

        verify(nodeRootRepository, times(1)).save(any());
    }

    @Test
    void createNodeDescTest() {
        webClient.post()
                .uri("/node")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(descMono), NodeDesc.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(NodeDesc.class)
                .value(node -> {
                            Assertions.assertThat(node.getId()).isEqualTo("10");
                            Assertions.assertThat(node.getName()).isEqualTo("name_test");
                            Assertions.assertThat(node.getDescription()).isEqualTo("description");
                        }
                );

        verify(nodeDescRepository, times(1)).save(any());
    }

    @Test
    void getNodesListTest() {
        when(nodeRootRepository.findAllNodeRoot()).thenReturn(Flux.just(rootMono));
        when(nodeDescRepository.findAllNodeDesc()).thenReturn(Flux.just(descMono));

        webClient.get()
                .uri("/node")
                .exchange()
                .expectStatus().isOk();

        verify(nodeRootRepository, times(1)).findAllNodeRoot();
        verify(nodeDescRepository, times(1)).findAllNodeDesc();
    }
}
