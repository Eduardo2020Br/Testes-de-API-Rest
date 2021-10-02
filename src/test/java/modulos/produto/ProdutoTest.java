package modulos.produto;
import dataFactory.ProdutoDataFactory;
import dataFactory.UsusarioDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import pojo.ComponentePojo;
import pojo.ProdutoPojo;
import pojo.UsuarioPojo;

import java.util.ArrayList;
import java.util.List;
//import org.junit.jupiter.api.Assertions;
//import static io.restassured.matcher.RestAssuredMatchers.*;

@DisplayName("Testes de Api Rest do modulo de Produto")
public class ProdutoTest {
    private String token;



    @BeforeEach
    public void beforeEach() {
        // Configurando os dados da API Rest da Lojinha
        baseURI = "http://165.227.93.41"; // PostMan>Environments>Global
        // port = 8080;  se n estivessemos usando o caminho acima, usariamos
        basePath = "/lojinha"; // caminho inicial da Aplicação o RestAssured ja
                                // sabe onde vai fazer as requisições

        // Obter o TOKEN do usuario admin
        this.token = given()
                .contentType(ContentType.JSON) // isso vem do Header Postman
                .body(UsusarioDataFactory.criarUsuarioAdmnistrador())
            .when()
                .post("/v2/login")
            .then()
                .extract()
                    .path("data.token");
    }

    @Test
    @DisplayName("Validar que o vlr do produto igual a 0.00 não é permitido")
    public void testValidarLimitesProibidosValorProduto() {
        // Inserir um produto com o valor 0.00 e validar que
        // a msg de erro foi apresentada e recebido o STATUS CODE 422.



        given()
                .contentType(ContentType.JSON)
                .header("token", this.token)
                .body(ProdutoDataFactory.criarProdutoComumComOValorIgualA(0.00))
        .when()
                .post("/v2/produtos")
        .then()
                .assertThat()
                .body("error", equalTo("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00"))
                .statusCode(422);

    }

    @Test
    @DisplayName("Validar que o vlr do produto igual a 7000.01 não é permitido")
    public void testValidarLimitesMaiorSeteMilProibidoValorProduto() {
        // Inserir um produto com o valor 0.00 e validar que
        // a msg de erro foi apresentada e recebido o STATUS CODE 422.

        given()
                .contentType(ContentType.JSON)
                .header("token", this.token)
                .body(ProdutoDataFactory.criarProdutoComumComOValorIgualA(7000.01))
            .when()
                .post("/v2/produtos")
            .then()
                .assertThat()
                    .body("error", equalTo("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00"))
                    .statusCode(422);
    }
}


