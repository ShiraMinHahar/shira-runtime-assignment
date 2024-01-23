package core.servises;

import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


public class ApiServise {

    private final WebClient webClient;

    public ApiServise(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://localhost:8080")
                .build();
    }

    private Mono<ResponseEntity<String>> addRuntimePolicy(String policyName, String policyValue) {
        return webClient
                .post()
                .uri("/runtime-policies")
                .bodyValue(String.format("{\"policyName\":\"%s\",\"policyValue\":\"%s\"}", policyName, policyValue))
                .retrieve()
                .toEntity(String.class);
    }
}
