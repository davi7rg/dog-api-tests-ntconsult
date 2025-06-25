package br.com.ntconsult.api.tests.scenarios;

import br.com.ntconsult.core.BaseTest;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Feature("Raças")
@DisplayName("Teste de listagem de raças")
class BreedsListTest extends BaseTest {

    @Test
    @Story("Listar todas as raças")
    @DisplayName("Listar todas as raças disponíveis")
    @Description("Chama GET /breeds/list/all e valida que o campo 'message' não está vazio")
    void listarTodasAsRacas() {
        given()
        .when()
            .get("/breeds/list/all")
        .then()
            .statusCode(200)
            .body("message", aMapWithSize(greaterThan(0)));
    }

    @Test
    @Story("Exibir quantidade de raças")
    @DisplayName("Mostrar total de raças retornadas")
    @Description("Chama GET /breeds/list/all, conta quantas raças retornaram e registra no relatório")
    void mostrarQuantidadeDeRacas() {
        Response resp = given()
        .when()
            .get("/breeds/list/all")
        .then()
            .statusCode(200)
            .extract()
            .response();

        Map<String, ?> breedsMap = resp.jsonPath().getMap("message");
        int totalBreeds = breedsMap.size();

        Allure.parameter("Total de raças retornadas", totalBreeds);

        assertTrue(totalBreeds > 0, "Esperado pelo menos uma raça, mas obteve " + totalBreeds);
    }
}