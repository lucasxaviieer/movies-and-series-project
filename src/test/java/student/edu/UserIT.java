package student.edu;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import student.edu.controller.exceptions.ErrorMessage;
import student.edu.domain.model.User;
import student.edu.dto.UserDto;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/users/users-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/users/users-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void createUser_WithValidData_ReturnUserCreatedStatus201(){
        UserDto responseBody = testClient.post().uri("/users").contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserDto("Lucas Xavier", "lucas.lima.xaviier@gmail.com")).exchange().expectStatus().isCreated()
                .expectBody(UserDto.class).returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getEmail()).isNotNull();
        Assertions.assertThat(responseBody.getEmail()).isEqualTo("lucas.lima.xaviier@gmail.com");
        Assertions.assertThat(responseBody.getName()).isEqualTo("Lucas Xavier");
    }


    @Test
    public void createUser_WithNameInvalid_ReturnErrorMessageStatus422(){
        ErrorMessage responseBody = testClient.post().uri("/users").contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserDto("", "lucas@gmail.com")).exchange().expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class).returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient.post().uri("/users").contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserDto("    ", "lucas@gmail.com")).exchange().expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class).returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    void createUser_WithEmailInvalid_ReturnErrorMessageStatus422(){
        ErrorMessage responseBody = testClient.post().uri("/users").contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserDto("Lucas Xavier", "lucas")).exchange().expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class).returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient.post().uri("/users").contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserDto("Lucas Xavier", "lucas@")).exchange().expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class).returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient.post().uri("/users").contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserDto("Lucas Xavier", "lucas@email.")).exchange().expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class).returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

    }

    @Test
    public void createUser_WithExistingEmail_ReturnErrorMessageStatus409(){
        ErrorMessage responseBody = testClient.post().uri("/users").contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserDto("Lucas Xavier", "lucas@gmail.com")).exchange().expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class).returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);
    }

    @Test
    public void findUser_WithAnExistingId_ReturnUserStatus200(){
        EntityExchangeResult<User> result = testClient.get().uri("/users/100").exchange().expectStatus()
                .isOk().expectBody(User.class).returnResult();

        var statusCode = result.getStatus().value();
        Assertions.assertThat(statusCode).isEqualTo(200);

        User responseBody = result.getResponseBody();
        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getId()).isEqualTo(100);
        Assertions.assertThat(responseBody.getEmail()).isEqualTo("lucas@gmail.com");
    }

    @Test
    public void findUser_WithAnNotExistingId_ReturnErrorMessageStatus404(){
        ErrorMessage responseBody = testClient.get().uri("/users/10").exchange().expectStatus()
                .isNotFound().expectBody(ErrorMessage.class).returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void findAllUser_WithDataInDataBase_ReturnListUserWithStatus200(){
        List<User> responseBody = testClient.get().uri("/users").exchange().expectStatus().isOk()
                .expectBodyList(User.class).returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody).isNotEmpty();
        Assertions.assertThat(responseBody.size()).isEqualTo(3);

    }

    @Test
    @Sql(scripts = "/sql/users/users-delete.sql")
    public void findAllUser_WithNoDataInDataBase_ReturnErrorMessageWithStatus404(){
        ErrorMessage responseBody = testClient.get().uri("/users").exchange().expectStatus().isNotFound()
                .expectBody(ErrorMessage.class).returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "/sql/users/users-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/sql/series/series-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/sql/tb_user_serie/tb_user_serie-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
            @Sql(scripts = "/sql/users/users-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
            @Sql(scripts = "/sql/series/series-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void addSerieToUser_WithAExistUserAndSerie_ReturnVoidStatus204(){
        WebTestClient.ResponseSpec noContent = testClient.put().uri("/users/addSeries/100/100")
                .exchange().expectStatus().isNoContent();
    }


    @Test
    @SqlGroup({
            @Sql(scripts = "/sql/series/series-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/sql/series/series-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void addSerieToUser_WithANonExistUser_ReturnErrorMessageStatus404(){
        ErrorMessage responseBody = testClient.put().uri("/users/addSeries/100/100")
                .exchange().expectStatus().isNotFound().expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
        Assertions.assertThat(responseBody.getMessage()).isEqualTo("This user ID do not exists!");


    }

    @Test
    public void addSerieToUser_WithANonExistSerie_ReturnErrorMessageStatus404(){
        ErrorMessage responseBody = testClient.put().uri("/users/addSeries/100/100")
                .exchange().expectStatus().isNotFound().expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
        Assertions.assertThat(responseBody.getMessage()).isEqualTo("This serie ID do not exists!");

    }

    @Test
    @SqlGroup({
            @Sql(scripts = "/sql/users/users-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/sql/series/series-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/sql/tb_user_serie/tb_user_serie-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/sql/tb_user_serie/tb_user_serie-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
            @Sql(scripts = "/sql/users/users-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
            @Sql(scripts = "/sql/series/series-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void addSerieToUser_WithASerieAlreadyInUser_ReturnErrorMessageStatus404(){
        ErrorMessage responseBody = testClient.put().uri("/users/addSeries/100/100")
                .exchange().expectStatus().isEqualTo(409).expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);
        Assertions.assertThat(responseBody.getMessage()).isEqualTo("This serie has already been added!");

    }

    @Test
    @SqlGroup({
            @Sql(scripts = "/sql/users/users-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/sql/series/series-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/sql/tb_user_serie/tb_user_serie-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/sql/tb_user_serie/tb_user_serie-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
            @Sql(scripts = "/sql/users/users-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
            @Sql(scripts = "/sql/series/series-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void removeSerieFromUser_WithAnExistSerieInUser_ReturnStatus204(){
        WebTestClient.ResponseSpec noContent = testClient.put().uri("/users/removeSeries/100/100")
                .exchange().expectStatus().isNoContent();

    }

    @Test
    @SqlGroup({
            @Sql(scripts = "/sql/users/users-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/sql/series/series-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/sql/tb_user_serie/tb_user_serie-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/sql/tb_user_serie/tb_user_serie-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
            @Sql(scripts = "/sql/users/users-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
            @Sql(scripts = "/sql/series/series-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void removeSerieFromUser_WithANonExistSerieInUser_ReturnErrorMessageStatus404(){
        ErrorMessage responseBody = testClient.put().uri("/users/removeSeries/100/101")
                .exchange().expectStatus().isNotFound().expectBody(ErrorMessage.class).returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
        Assertions.assertThat(responseBody.getMessage()).isEqualTo("This serie is not in user series list");

    }

    @Test
    @SqlGroup({
            @Sql(scripts = "/sql/users/users-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/sql/series/series-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/sql/tb_user_serie/tb_user_serie-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/sql/tb_user_serie/tb_user_serie-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
            @Sql(scripts = "/sql/users/users-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
            @Sql(scripts = "/sql/series/series-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    public void removeSerieFromUser_WithANonExistUser_ReturnErrorMessageStatus404(){
        ErrorMessage responseBody = testClient.put().uri("/users/removeSeries/1/100")
                .exchange().expectStatus().isNotFound().expectBody(ErrorMessage.class).returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
        Assertions.assertThat(responseBody.getMessage()).isEqualTo("This user ID do not exists!");

    }

    @Test
    @SqlGroup({
            @Sql(scripts = "/sql/users/users-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/sql/movies/movies-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/sql/tb_user_movie/tb_user_movie-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
            @Sql(scripts = "/sql/users/users-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
            @Sql(scripts = "/sql/movies/movies-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
    })
    public void addMovieToUser_WithAnExistMovieAndUser_ReturnVoidStatus204(){
        testClient.put().uri("/users/addMovies/100/100").exchange().expectStatus().isNoContent();
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "/sql/movies/movies-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/sql/movies/movies-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
    })
    public void addMovieToUser_WithANonExistUser_ReturnErrorMessageStatus404(){
        ErrorMessage responseBody = testClient.put().uri("/users/addMovies/1/100")
                .exchange().expectStatus().isNotFound().expectBody(ErrorMessage.class).returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
        Assertions.assertThat(responseBody.getMessage()).isEqualTo("This user ID do not exists!");
    }

    @Test
    public void addMovieToUser_WithANonExistMovie_ReturnErrorMessageStatus404() {
        ErrorMessage responseBody = testClient.put().uri("/users/addMovies/100/100")
                .exchange().expectStatus().isNotFound().expectBody(ErrorMessage.class).returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
        Assertions.assertThat(responseBody.getMessage()).isEqualTo("This movie ID do not exists!");
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "/sql/users/users-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/sql/movies/movies-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/sql/tb_user_movie/tb_user_movie-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "/sql/tb_user_movie/tb_user_movie-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
            @Sql(scripts = "/sql/users/users-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
            @Sql(scripts = "/sql/movies/movies-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
    })
    public void addMovieToUser_WithAMovieAlreadyInUser_ReturnErrorMessageStatus404(){
        ErrorMessage responseBody = testClient.put().uri("/users/addMovies/100/100")
                .exchange().expectStatus().isEqualTo(409).expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);
        Assertions.assertThat(responseBody.getMessage()).isEqualTo("This movie has already been added!");
    }
}
