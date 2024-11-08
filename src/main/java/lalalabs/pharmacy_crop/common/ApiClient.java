package lalalabs.pharmacy_crop.common;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ApiClient {
    private final RestClient restClient;

    public ApiClient() {
        this.restClient = RestClient.create();
    }

    public <T> T get(String uri, Class<T> responseType) {
        return restClient
                .get()
                .uri(uri)
                .retrieve()
                .onStatus(HttpStatusCode::isError, ((request, response) -> {
                    throw new RuntimeException(String.valueOf(response.getStatusCode()));
                }))
                .body(responseType);
    }

    public <T> T get(String uri, Class<T> responseType, String accessToken) {
        return restClient
                .get()
                .uri(uri)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .onStatus(HttpStatusCode::isError, ((request, response) -> {
                    throw new RuntimeException(String.valueOf(response.getStatusCode()));
                }))
                .body(responseType);
    }

    public <T> T post(String uri, Class<T> responseType) {
        return restClient
                .post()
                .uri(uri)
                .retrieve()
                .onStatus(HttpStatusCode::isError, ((request, response) -> {
                    throw new RuntimeException(String.valueOf(response.getStatusCode()));
                })).body(responseType);
    }

    public <T> T post(String uri, String accessToken, Class<T> responseType) {
        return restClient
                .post()
                .uri(uri)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .onStatus(HttpStatusCode::isError, ((request, response) -> {
                    throw new RuntimeException(String.valueOf(response.getStatusCode()));
                })).body(responseType);
    }

    public <T> T reissueTokenApi(String uri, Object dto, Class<T> responseType) {
        return restClient
                .post()
                .uri(uri)
                .body(dto)
                .retrieve()
                .onStatus(HttpStatusCode::isError, ((request, response) -> {
                    throw new RuntimeException(String.valueOf(response.getStatusCode()));
                })).body(responseType);
    }
}
