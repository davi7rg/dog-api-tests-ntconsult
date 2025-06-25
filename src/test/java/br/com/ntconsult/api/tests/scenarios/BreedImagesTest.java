package br.com.ntconsult.api.tests.scenarios;

import br.com.ntconsult.core.BaseTest;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Feature("Imagens de Raças")
@DisplayName("Teste de imagens de raças")
class BreedImagesTest extends BaseTest {

    @Test
    @Story("Buscar imagens válidas")
    @DisplayName("Buscar imagens da raça Chihuahua")
    @Description("Chama GET /breed/{breed}/images para 'Chihuahua' e verifica URLs válidas")
    void retornarImagensDaRacaHusky() {
        given()
            .pathParam("breed", "chihuahua")
        .when()
            .get("/breed/{breed}/images")
        .then()
            .statusCode(200)
            .body("message", everyItem(startsWith("https://")))
            .body("status", equalTo("success"));
    }

    @Test
    @Story("Erro para raça inexistente")
    @DisplayName("Retornar 404 para raça inexistente")
    @Description("Chama GET /breed/{breed}/images para 'ntconsult-agibank' e espera 404")
    void validarRacaInexistente() {
        given()
            .pathParam("breed", "ntconsult-agibank")
        .when()
            .get("/breed/{breed}/images")
        .then()
            .statusCode(404);
    }
}