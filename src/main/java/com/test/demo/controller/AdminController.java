package com.test.demo.controller;

import com.test.demo.model.Pet;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Controller
public class AdminController {

        @RequestMapping(value = "/admin", produces = {"application/json"}, method = RequestMethod.GET)
        public Mono<ResponseEntity<Pet>> showPetById(String petId, ServerWebExchange exchange) {
            return ApiUtil.getExampleResponse(exchange, "{content: \"ADMIN ACCESS!\"}").then(Mono.empty());
        }

}
