package com.nttada.bcp1.mscredit.service;

import com.nttada.bcp1.mscredit.model.Credit;
import com.nttada.bcp1.mscredit.model.Customer;
import com.nttada.bcp1.mscredit.proxy.CreditProxy;
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
    CreditRepository repository;

    private CreditProxy creditProxy = new CreditProxy();

    @Override
    public Flux<Credit> getAll() {
        return repository.findAll();
    }

    @Override
    public Flux<Credit> getAllByIdCustomer(String idCustomer) {
        return repository.findAllByIdCustomer(idCustomer);
    }

    @Override
    public Mono<Credit> getCreditById(String id) {
        return repository.findById(id);
    }

    @Override
    public Mono<Credit> save(Credit credit) {
        switch (credit.getCreditType()) {
            case "PERSONAL":
                return createPersonalCredit(credit).flatMap(repository::save);
            case "BUSINESS":
                return createBusinessCredit(credit).flatMap(repository::save);
            case "PERSONAL CREDIT CARD":
                return createPersonalCreditCard(credit).flatMap(repository::save);
            case "BUSINESS CREDIT CARD":
                return createBusinessCreditCard(credit).flatMap(repository::save);
            default:
                return Mono.error(() -> new IllegalArgumentException("Invalid Credit type"));
        }
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id).subscribe();
    }

    @Override
    public Mono<Credit> getCreditByIdCustomer(String idCustomer) {
        return repository.findByIdCustomer(idCustomer);
    }

    @Override
    public Mono<Customer> test(String idCustomer) {
        return creditProxy.getCustomer(idCustomer);
    }


    //PRODUCT VALIDATION METHODS
    public Mono<Credit> createPersonalCredit(Credit credit) {
        return Mono.just(credit).flatMap(this::clientIsPersonal)
                .flatMap(this::clientHasCreditAlready);
    }

    public Mono<Credit> createBusinessCredit(Credit credit) {
        return Mono.just(credit).flatMap(this::clientIsBusiness);
    }

    public Mono<Credit> createPersonalCreditCard(Credit credit) {
        return Mono.just(credit).flatMap(this::clientIsPersonal);
    }

    public Mono<Credit> createBusinessCreditCard(Credit credit) {
        return Mono.just(credit).flatMap(this::clientIsBusiness);
    }


    //PRODUCT UTIL METHODS
    public Mono<Customer> getCustomer(Credit credit) {
        return creditProxy.getCustomer(credit.getIdCustomer());
    }

    public Mono<Credit> clientIsPersonal(Credit credit) {
        return getCustomer(credit).flatMap(resp -> {
            return resp.getCustomerType().equals("PERSONAL") ? Mono.just(credit)
                    : Mono.error(() -> new IllegalArgumentException("Client is not personal"));
        });
    }

    public Mono<Credit> clientIsBusiness(Credit credit) {
        return getCustomer(credit).flatMap(resp -> {
            return resp.getCustomerType().equals("business") ? Mono.just(credit)
                    : Mono.error(() -> new IllegalArgumentException("Client is not business"));
        });
    }

    public Mono<Credit> clientHasCreditAlready(Credit credit) {
        return getCreditByIdCustomer(credit.getIdCustomer()).switchIfEmpty(Mono.just(new Credit()))
                .flatMap(resp -> {
                    if (resp.getId() == null || resp.getId().equals(credit.getId())) {
                        return Mono.just(credit);
                    }
                    return Mono.error(() -> new IllegalArgumentException("Client has a personal credit already"));
                });
    }
}