package ir.maktabsharif.achareh.user;

import ir.maktabsharif.achareh.dto.user.UserRequestDto;
import ir.maktabsharif.achareh.dto.user.UserResponseDto;
import ir.maktabsharif.achareh.enums.RoleUserEnum;
import ir.maktabsharif.achareh.utils.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    private String getBaseUrl(String endpoint) {
        return "http://localhost:" + port + "/user" + endpoint;
    }

    @Test
    public void testSaveUser() {
        String url = getBaseUrl("");
        UserRequestDto userRequestDto = new UserRequestDto(
                "string",
                "stringsdfew",
                "Aa@123456",
                 "string@ijlkwe.ir",
                RoleUserEnum.CUSTOMER
        );

        HttpHeaders headers = new HttpHeaders();

//        headers.set("Authorization", "Bearer your_token_with_register_user_authority");

        HttpEntity<UserRequestDto> request = new HttpEntity<>(userRequestDto, headers);

        ResponseEntity<ApiResponse> response = restTemplate.postForEntity(url, request, ApiResponse.class);
        System.out.println(response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getMessage()).isEqualTo("user.saved.successfully");
        assertThat(response.getBody().isSuccess()).isTrue();
    }

    @Test
    public void testConfirmedUser() {
        String url = getBaseUrl("/confirmed_user?id=1");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer your_token_with_confirmed_user_authority");
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<UserResponseDto> response = restTemplate.exchange(url, HttpMethod.PATCH, request, UserResponseDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().id()).isEqualTo(1L);
        // بررسی سایر ویژگی‌های UserResponseDto در صورت نیاز
    }
//
//    @Test
//    public void testSearchUsers() {
//        String url = getBaseUrl("/users/search?name=John&orderByScore=true");
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer your_token_with_search_user_authority");
//        HttpEntity<Void> request = new HttpEntity<>(headers);
//
//        ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, request, List.class);
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody()).isNotEmpty();
//        // بررسی سایر ویژگی‌های List<UserDTO> در صورت نیاز
//    }
}