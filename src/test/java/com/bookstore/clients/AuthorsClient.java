package com.bookstore.clients;

import com.bookstore.models.Author;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class AuthorsClient {
    private final RequestSpecification spec;

    public AuthorsClient() {
        String base = System.getenv("BASE_URL");
        if (base == null || base.isEmpty()) {
            base = "https://fakerestapi.azurewebsites.net";
        }
        this.spec = new RequestSpecBuilder()
                .setBaseUri(base)
                .setContentType(ContentType.JSON)
                .build();
    }

    public Response getAllAuthors() {
        return given().spec(spec).when().get("/api/v1/Authors");
    }

    public Response getAuthorById(int id) {
        return given().spec(spec).when().get("/api/v1/Authors/{id}", id);
    }

    public Response createAuthor(Author author) {
        return given().spec(spec).body(author).when().post("/api/v1/Authors");
    }

    public Response updateAuthor(int id, Author author) {
        return given().spec(spec).body(author).when().put("/api/v1/Authors/{id}", id);
    }

    public Response deleteAuthor(int id) {
        return given().spec(spec).when().delete("/api/v1/Authors/{id}", id);
    }
}
