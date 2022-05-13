package com.shop.projectlion.db;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
@Rollback(value = false)
@TestPropertySource(properties = "spring.config.location=classpath:application-test.yml")
public class DatabaseConnectionTest {

    @Autowired
    private EntityManager em;

    @Test
    public void h2DbConnectionTest() {
        //given
//        Member member = new Member();
//        Member member1 = new Member();
//        Member member2 = new Member();

        //when
//        em.persist(member);
//        em.persist(member1);
//        em.persist(member2);
//        em.flush();

        //then
    }
}
