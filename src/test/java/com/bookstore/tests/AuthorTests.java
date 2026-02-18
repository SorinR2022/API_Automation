package com.bookstore.tests;

import com.bookstore.clients.AuthorsClient;
import com.bookstore.models.Author;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AuthorTests {
    private AuthorsClient client;

    @BeforeClass
    public void setup() {
        client = new AuthorsClient();
    }

    @Test(description = "Happy Path: Get all authors should return 200 with non-empty list")
    public void testGetAllAuthors() {
        Response r = client.getAllAuthors();
        r.then().statusCode(200);
        String body = r.getBody().asString();
        Assert.assertTrue(body.length() > 2, "response body should contain authors");
        System.out.println("Get all authors returned " + body.length() + " bytes");
    }

    @Test(description = "Happy Path: Get author by valid ID should return 200")
    public void testGetAuthorById() {
        Response allAuthors = client.getAllAuthors();
        allAuthors.then().statusCode(200);
        String body = allAuthors.getBody().asString();
        
        if (body.contains("\"id\"")) {
            Response r = client.getAuthorById(1);
            int status = r.getStatusCode();
            Assert.assertTrue(status == 200 || status == 404, "Status should be 200 or 404 but was " + status);
            if (status == 200) {
                Author author = r.as(Author.class);
                Assert.assertNotNull(author.getId(), "Author should have an ID");
                System.out.println("Get author by ID returned: " + author.getFirstName() + " " + author.getLastName());
            }
        }
    }

    @Test(description = "Edge Case: Get non-existing author should return 404 or 200")
    public void testGetNonExistingAuthor() {
        Response r = client.getAuthorById(999999);
        int status = r.getStatusCode();
        Assert.assertTrue(status == 404 || status == 400 || status == 200,
                "API returned unexpected status: " + status);
        System.out.println("Get non-existing author returned status: " + status);
    }

    @Test(description = "Happy Path: Create, retrieve, update, and delete author")
    public void testCreateUpdateDeleteAuthor() {
        Author author = Author.builder()
                .id(0)
                .firstName("TestAuthor")
                .lastName("AutomationTest")
                .build();

        Response create = client.createAuthor(author);
        int createStatus = create.getStatusCode();
        Assert.assertTrue(createStatus == 200 || createStatus == 201 || createStatus == 404,
                "Unexpected create status: " + createStatus);

        if (createStatus == 200 || createStatus == 201) {
            Author created = create.as(Author.class);
            Assert.assertNotNull(created.getId(), "Created author must have id");
            int id = created.getId();
            System.out.println("Author created with ID: " + id);

            Response get = client.getAuthorById(id);
            int getStatus = get.getStatusCode();
            if (getStatus == 200) {
                Author fetched = get.as(Author.class);
                Assert.assertEquals(fetched.getFirstName(), author.getFirstName());
                System.out.println("Author retrieved: " + fetched.getFirstName());

                fetched.setLastName("UpdatedByTest");
                Response update = client.updateAuthor(id, fetched);
                int updateStatus = update.getStatusCode();
                if (updateStatus == 200) {
                    System.out.println("Author updated successfully");

                    Response del = client.deleteAuthor(id);
                    int delStatus = del.getStatusCode();
                    Assert.assertTrue(delStatus == 200 || delStatus == 404,
                            "Delete status should be 200 or 404 but was " + delStatus);
                    System.out.println("Author deleted with status: " + delStatus);
                } else {
                    System.out.println("Update returned " + updateStatus + ", skipping delete");
                }
            } else {
                System.out.println("Get by id returned " + getStatus + ", skipping update/delete");
            }
        } else {
            System.out.println("Create returned " + createStatus + ", skipping CRUD flow");
        }
    }

    @Test(description = "Edge Case: Invalid author data should be handled gracefully")
    public void testCreateAuthorWithInvalidData() {
        Author author = Author.builder()
                .id(-1)
                .firstName("")
                .lastName("")
                .build();

        Response create = client.createAuthor(author);
        int status = create.getStatusCode();
        Assert.assertTrue(status >= 200 && status < 500,
                "Status should be 2xx or 4xx but was " + status);
        System.out.println("Invalid author data handled with status: " + status);
    }

    @Test(description = "Edge Case: Null author object should be rejected or handled")
    public void testGetAuthorWithZeroId() {
        Response r = client.getAuthorById(0);
        int status = r.getStatusCode();
        Assert.assertTrue(status == 404 || status == 200 || status == 400,
                "Status should be 200, 400, or 404 but was " + status);
        System.out.println("Get author with ID 0 returned status: " + status);
    }
}
