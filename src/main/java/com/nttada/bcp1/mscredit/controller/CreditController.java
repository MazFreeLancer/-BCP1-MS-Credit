package com.nttada.bcp1.mscredit.controller;

import com.nttada.bcp1.mscredit.model.Credit;
import com.nttada.bcp1.mscredit.service.CreditService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@RestController
@RequiredArgsConstructor
@RequestMapping("/credit")
public class CreditController {

    @Autowired
    CreditService service;

    @GetMapping
    public Flux<Credit> getCredits(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Mono<Credit> getCreditById(@PathVariable("id") String id){
        return service.getCreditById(id);
    }

    @GetMapping("/byPersonalCustomer/{id}")
    public Mono<Credit> getCreditByIdCustomer(@PathVariable("id") String idCustomer){
        return service.getCreditByIdCustomer(idCustomer);
    }

    @GetMapping("/allByCustomer/{id}")
    public Flux<Credit> getAllCreditByIdCustomer(@PathVariable("id") String idCustomer){
        return service.getAllByIdCustomer(idCustomer);
    }

    @PostMapping
    public Mono<Credit> postCredit(@RequestBody Credit credit){
        return service.save(credit);
    }

    @PutMapping
    public Mono<Credit> updCredit(@RequestBody Credit credit){
        return service.save(credit);
    }

    @DeleteMapping("/{id}")
    void dltCredit(@PathVariable("id") String id){
        service.delete(id);
    }
}