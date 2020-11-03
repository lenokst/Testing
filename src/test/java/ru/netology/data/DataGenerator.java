package ru.netology.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private static RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    static void setUpAll (Registration registration){
        given()
                .spec(requestSpecification)
                .body(registration)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static Registration validActiveUser(){
        Faker faker = new Faker(new Locale("en"));
        String login = faker.name().firstName().toLowerCase();
        String password = faker.internet().password();
        String status = "active";
        Registration registrationDto = new Registration(login, password, status);
        setUpAll(registrationDto);
        return registrationDto;
    }

    public static Registration validBlockedUser(){
        Faker faker = new Faker(new Locale("en"));
        String login = faker.name().firstName().toLowerCase();
        String password = faker.internet().password();
        String status = "blocked";
        Registration registrationDto = new Registration(login, password, status);
        setUpAll(registrationDto);
        return registrationDto;
    }

    public static Registration invalidLogin() {
        Faker faker = new Faker(new Locale("en"));
        String login = "vasya";
        String password = faker.internet().password();
        String status = "active";
        setUpAll(new Registration(login, password, status));
        return new Registration("kolya", password, status);
    }

    public static Registration invalidPassword() {
        Faker faker = new Faker(new Locale("en"));
        String login = faker.name().firstName().toLowerCase();
        String password = "password";
        String status = "active";
        setUpAll(new Registration(login, password, status));
        return new Registration(login, "pasword", status);
    }
}
