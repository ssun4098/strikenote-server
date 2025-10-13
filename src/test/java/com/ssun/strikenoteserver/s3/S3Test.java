package com.ssun.strikenoteserver.s3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class S3Test {

    @Autowired
    private S3Client s3Client;

    private final String path = "game/test";
    private final String bucketName = "strike-note";

    @Test
    @DisplayName("S3에 이미지 파일을 업로드할 수 있어야 한다")
    void uploadTest() throws IOException {
        // given
        Resource resource = new ClassPathResource("static/game/example.webp");
        String key = path + "/example.webp";

        // when
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType("image/webp")
                .build();

        try (InputStream inputStream = resource.getInputStream()) {
            PutObjectResponse response = s3Client.putObject(
                    putObjectRequest,
                    RequestBody.fromInputStream(inputStream, resource.contentLength())
            );

            // then
            assertThat(response.sdkHttpResponse().isSuccessful()).isTrue();
            assertThat(response.eTag()).isNotNull();
        }
    }
}
