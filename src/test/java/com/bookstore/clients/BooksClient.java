package com.bookstore.clients;

import com.bookstore.models.Book;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BooksClient {
    private final RequestSpecification spec;

    public BooksClient() {
        String base = System.getenv("BASE_URL");
        if (base == null || base.isEmpty()) {
            base = "https://fakerestapi.azurewebsites.net";
        }
        this.spec = new RequestSpecBuilder()
                .setBaseUri(base)
                .setContentType(ContentType.JSON)
                .build();
    }

    public Response getAllBooks() {
        return given().spec(spec).when().get("/api/v1/Books");
    }

    public Response getBookById(int id) {
        return given().spec(spec).when().get("/api/v1/Books/{id}", id);
    }

    public Response createBook(Book book) {
        return given().spec(spec).body(book).when().post("/api/v1/Books");
    }

    public Response updateBook(int id, Book book) {
        return given().spec(spec).body(book).when().put("/api/v1/Books/{id}", id);
    }

    public Response deleteBook(int id) {
        return given().spec(spec).when().delete("/api/v1/Books/{id}", id);
    }
}
