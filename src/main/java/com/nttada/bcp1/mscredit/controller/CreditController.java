package com.nttada.bcp1.mscredit.controller;

import com.nttada.bcp1.mscredit.modelo.Credit;
import com.nttada.bcp1.mscredit.service.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/credit")
public class CreditController {
    @Autowired
    private CreditService creditService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Credit> createCredit(@RequestBody Credit credit){return creditService.createCredit(credit);}

    @GetMapping(value = "/getAll",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus
    public Flux<Credit> findAll(){
        return creditService.findAllCredit();
    }

    @GetMapping("/{id}")
    @ResponseStatus
    public ResponseEntity<Mono<Credit>> findById(@PathVariable("id") String id){
        Mono<Credit> creditMono=creditService.findByCustomerId(id);
        return new ResponseEntity<Mono<Credit>>(creditMono,creditMono != null? HttpStatus.OK:HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Mono<Credit>> updateCredit(@RequestBody Credit credit){
        Mono<Credit> creditMono = creditService.updateCredit(credit);
        return new ResponseEntity<Mono<Credit>>(creditMono, creditMono!=null? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Void> deleteCredit(@PathVariable("id") String id){
        return creditService.deleteCredit(id);
    }

}