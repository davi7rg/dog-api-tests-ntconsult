package br.com.ntconsult.api.tests.scenarios;

import br.com.ntconsult.core.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.DisplayName;        
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Feature("Imagem Aleatória")
@DisplayName("Teste de múltiplas imagens aleatórias")
class RandomImageTest extends BaseTest {

    @Test
    @Story("Obter imagem única aleatória")
    @DisplayName("Obter imagem aleatória de cachorro")
    @Description("Chama GET /breeds/image/random e valida URL e status")
    void retornarImagemAleatoriaValida() {
        given()
        .when()
            .get("/breeds/image/random")
        .then()
            .statusCode(200)
            .body("message", startsWith("https://"))
            .body("status", equalTo("success"));
    }

    @Test
    @Story("Obter múltiplas imagens aleatórias")
    @DisplayName("Obter 5 imagens aleatórias")
    @Description("Chama GET /breeds/image/random/5 e valida que retorna exatamente 5 URLs válidas")
    void retornarCincoImagensAleatorias() {
        given()
        .when()
            .get("/breeds/image/random/5")
        .then()
            .statusCode(200)
            .body("message", hasSize(5))
            .body("message", everyItem(startsWith("https://")))
            .body("status", equalTo("success"));
    }

    @Test
    @Story("Obter múltiplas imagens aleatórias")
    @DisplayName("Obter 20 imagens aleatórias")
    @Description("Chama GET /breeds/image/random/20 e valida que retorna exatamente 20 URLs válidas")
    void retornarVinteImagensAleatorias() {
        given()
        .when()
            .get("/breeds/image/random/20")
        .then()
            .statusCode(200)
            .body("message", hasSize(20))
            .body("message", everyItem(startsWith("https://")))
            .body("status", equalTo("success"));
    }

    @Test
    @Story("Obter múltiplas imagens aleatórias")
    @DisplayName("Obter 35 imagens aleatórias")
    @Description("Chama GET /breeds/image/random/35 e valida que retorna exatamente 35 URLs válidas")
    void retornarTrintaCincoImagensAleatorias() {
        given()
        .when()
            .get("/breeds/image/random/35")
        .then()
            .statusCode(200)
            .body("message", hasSize(35))
            .body("message", everyItem(startsWith("https://")))
            .body("status", equalTo("success"));
    }

    @Test
    @Story("Obter múltiplas imagens aleatórias")
    @DisplayName("Obter 50 imagens aleatórias")
    @Description("Chama GET /breeds/image/random/50 e valida que retorna exatamente 50 URLs válidas")
    void retornarCinquentaImagensAleatorias() {
        given()
        .when()
            .get("/breeds/image/random/50")
        .then()
            .statusCode(200)
            .body("message", hasSize(50))
            .body("message", everyItem(startsWith("https://")))
            .body("status", equalTo("success"));
    }

    @Test
    @Story("Obter múltiplas imagens aleatórias")
    @DisplayName("Obter 51 imagens aleatórias (limite da API)")
    @Description("Chama GET /breeds/image/random/51 e valida que retorna no máximo 50 URLs válidas")
    void retornarNoMaximoCinquentaImagens() {
        given()
        .when()
            .get("/breeds/image/random/51")
        .then()
            .statusCode(200)
            .body("message", hasSize(lessThanOrEqualTo(50)))
            .body("message", everyItem(startsWith("https://")))
            .body("status", equalTo("success"));
    }

    @Test
    @Story("Obter múltiplas imagens aleatórias")
    @DisplayName("Obter 100 imagens aleatórias (Falha Propostial para Demonstrar a Divisão de Categorias do Relatório)") // <--- OBSERVAÇÃO
    @Description("Chama GET /breeds/image/random/100 e valida que retorna exatamente 100 URLs — Vai Falhar Propositalmente")
    void falharAoTentarObterCemImagens() {
        try {
            given()
            .when()
                .get("/breeds/image/random/100")
            .then()
                .statusCode(200)
                .log().ifValidationFails()              
                .body("message", hasSize(100))         
                .body("message", everyItem(startsWith("https://")))
                .body("status", equalTo("success"));
        } catch (AssertionError e) {
            
            fail("Teste proposital: esperado 100 URLs, mas a API retorna no máximo 50");
        }
    }
}