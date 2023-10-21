package com.example.testcontainersdemo.service;

import com.example.testcontainersdemo.entity.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@Testcontainers
@ActiveProfiles("test")
@TestPropertySource(locations="classpath:application-test.properties")
@AutoConfigureTestDatabase(replace = NONE)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
//@Sql({"classpath:db.sql"})
class UserServiceTest {

    @Autowired
    public UserService userService;
    @Container
    static PostgreSQLContainer container = new
            PostgreSQLContainer("postgres:16.0")
            .withDatabaseName("test-db").withUsername("test").withPassword("test");

    @DynamicPropertySource
    public static void setdbprops(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url",container::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username",container::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password",container::getPassword);
        dynamicPropertyRegistry.add("spring.datasource.driver-class-name",container::getDriverClassName);
    }

    @BeforeAll
    public static void setup() {
        var containerDelegate = new JdbcDatabaseDelegate(container, "");
        ScriptUtils.runInitScript(containerDelegate,"db.sql");

        container.withReuse(true);
        container.start();
    }

    @Test
    public void whenGetUserByEmailCalled_ThenReturnUser() {
        User expectedUser = User.builder()
                .id(1L)
                .email("sarav95@gmail.com")
                .name("uma")
                .build();
        User actualUser = userService.getUserByEmail("sarav95@gmail.com");
        assertEquals(expectedUser.getId(),actualUser.getId());
    }

    @AfterAll
    public static  void finish(){
        container.stop();
    }
}