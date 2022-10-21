package com.security.demo;

import com.security.demo.app.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
public class TransactionManagerTest {

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("[success] 커넥션확인")
    @Transactional
    public void 커넥션확인(){
        // given
        // hikari pool 10439
        //TransactionContext.startContext() -> TxManager.getTransaction -> TxManager.startTransaction -> doBegin -> newTransactionStatus -> prepareSynchronization
        // ->

        em.persist(new User());
        em.flush();
        em.persist(new User());
        // when

        // then
    }


}
