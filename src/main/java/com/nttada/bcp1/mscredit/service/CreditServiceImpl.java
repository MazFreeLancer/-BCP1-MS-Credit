package com.nttada.bcp1.mscredit.service;

import com.nttada.bcp1.mscredit.modelo.Credit;
import com.nttada.bcp1.mscredit.repository.CreditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CreditServiceImpl implements CreditService {

    @Autowired
    CreditRepository creditRepository;

    @Override
    public Mono<Credit> createCredit(Credit c) {
        return creditRepository.save(c);
    }

    @Override
    public Mono<Credit> findByCustomerId(String id) {
        return creditRepository.findById(id);
    }

    @Override
    public Flux<Credit> findAllCredit() {
        return creditRepository.findAll();
    }

    @Override
    public Mono<Credit> updateCredit(Credit c) {
        return creditRepository.save(c);
    }

    @Override
    public Mono<Void> deleteCredit(String id) {
        return creditRepository.deleteById(id);
    }

}
