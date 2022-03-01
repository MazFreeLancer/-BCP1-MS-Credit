package com.nttada.bcp1.mscredit.repository;

import com.nttada.bcp1.mscredit.modelo.Credit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CreditRepository extends ReactiveMongoRepository<Credit, String> {

}
