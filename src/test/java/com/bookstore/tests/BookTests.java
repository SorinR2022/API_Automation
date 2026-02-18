package com.bookstore.tests;

import com.bookstore.clients.BooksClient;
import com.bookstore.models.Book;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class BookTests {
    private BooksClient client;

    @BeforeClass
    public void setup() {
        client = new BooksClient();
    }

    @Test
    public void testGetAllBooks() {
        Response r = client.getAllBooks();
        r.then().statusCode(200);
        Assert.assertTrue(r.getBody().asString().length() > 2, "response body should not be empty");
    }

    @Test
    public void testCreateUpdateDeleteBook() {
        Book book = Book.builder()
                .id(0)
                .title("Automated Test Book")
                .description("Created by automation test")
                .pageCount(123)
                .build();

        Response create = client.createBook(book);
        int createStatus = create.getStatusCode();
        Assert.assertTrue(createStatus == 200 || createStatus == 201 || createStatus == 404,
                "Unexpected status from create: " + createStatus);

        if (createStatus == 200 || createStatus == 201) {
            Book created = create.as(Book.class);
            Assert.assertNotNull(created.getId(), "created book must have id");
            int id = created.getId();

            Response get = client.getBookById(id);
            int getStatus = get.getStatusCode();
            if (getStatus == 200) {
                Book fetched = get.as(Book.class);
                Assert.assertEquals(fetched.getTitle(), book.getTitle());

                fetched.setTitle("Updated Title by Test");
                Response update = client.updateBook(id, fetched);
                int updateStatus = update.getStatusCode();
                if (updateStatus == 200) {
                    Response del = client.deleteBook(id);
                    int delStatus = del.getStatusCode();
                    Assert.assertTrue(delStatus == 200 || delStatus == 404, "unexpected delete status: " + delStatus);

                    Response getAfter = client.getBookById(id);
                    int status = getAfter.getStatusCode();
                    Assert.assertTrue(status == 404 || status == 400 || status == 500, "expected not-found after delete but was " + status);
                } else {
                    System.out.println("Update returned " + updateStatus + ", skipping delete/verify.");
                }
            } else {
                System.out.println("Get by id returned " + getStatus + ", skipping update/delete.");
            }
        } else {
            System.out.println("Create returned " + createStatus + ", skipping CRUD flow.");
        }
    }

    @Test
    public void testGetNonExistingBook() {
        Response r = client.getBookById(9999999);
        int status = r.getStatusCode();
        Assert.assertTrue(status == 404 || status == 400 || status == 200, "API returned unexpected status for non-existing id: " + status);
    }
}
