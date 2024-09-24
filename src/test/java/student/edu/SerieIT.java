package student.edu;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import student.edu.controller.exceptions.ErrorMessage;
import student.edu.dto.SerieDto;
import student.edu.dto.UserDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/series/series-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/series/series-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class SerieIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void createSerie_WithValidData_ReturnSerieCreatedWithStatus201(){
        SerieDto responseBody = testClient.post().uri("/series").contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new SerieDto("Um amor", 2022, 1))
                .exchange().expectStatus().isCreated().expectBody(SerieDto.class).returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getTitle()).isEqualTo("Um amor");
        Assertions.assertThat(responseBody.getYear()).isEqualTo(2022);
        Assertions.assertThat(responseBody.getSeasons()).isEqualTo(1);
    }

    @Test
    public void createSerie_WithInvalidData_ReturnErrorMessageWithStatus422(){
        ErrorMessage responseBody = testClient.post().uri("/series").contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new SerieDto("", 2022, 1))
                .exchange().expectStatus().isEqualTo(422).expectBody(ErrorMessage.class).returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient.post().uri("/series").contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new SerieDto("Um amor", 20, 1))
                .exchange().expectStatus().isEqualTo(422).expectBody(ErrorMessage.class).returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);


        responseBody = testClient.post().uri("/series").contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new SerieDto("Um amor", 2022, null))
                .exchange().expectStatus().isEqualTo(422).expectBody(ErrorMessage.class).returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);


        responseBody = testClient.post().uri("/series").contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new SerieDto("Um amor", 2022, 0))
                .exchange().expectStatus().isEqualTo(422).expectBody(ErrorMessage.class).returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }
}
