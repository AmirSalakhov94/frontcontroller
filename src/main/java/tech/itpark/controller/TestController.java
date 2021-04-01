package tech.itpark.controller;

import org.springframework.stereotype.Controller;
import tech.itpark.annotation.*;
import tech.itpark.http.model.Request;
import tech.itpark.model.ManyUser;
import tech.itpark.model.User;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
public class TestController {

    @GetMapping("/getString")
    public List<User> getString(@RequestParam("request") Request request) {
        return Arrays.asList(User.builder().name("Лол").value("kek").build(),
                User.builder().name("биш").value("kek").build(),
                User.builder().name("таш").value("кент").build());
    }

    @GetMapping("/getString1")
    public void getString1(@RequestParam("b") String b,
                           @RequestParam("a") String a) {
        System.out.println(b + a);
    }

    @GetMapping("/get/String/5")
    public void getStringeee(@RequestParam("b") String b,
                           @RequestParam("a") String a) {
        System.out.println(b + a);
    }

    @PostMapping("/getString")
    public List<String> postString(@RequestBody("manyUser") ManyUser manyUser,
                                   @RequestHeader("Authorization") String auth) {
        return Collections.singletonList("user");
    }
}
