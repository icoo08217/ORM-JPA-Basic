package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin(); // db 트랜잭션 실행

        try { // 정석 코드

            Member member1 = new Member();
            member1.setUsername("hello1");

            em.persist(member1);

            em.flush();
            em.clear();
            //

            Member refMember = em.getReference(Member.class, member1.getId());
            System.out.println("refMember = " + refMember.getClass()); //Proxy
//            refMember.getUsername(); // 프록시 강제 초기화
            Hibernate.initialize(refMember);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }

}

/*
*           //영속
            // em.persist();
            // 준영속
            // 특정 엔티티만 준영속 상태로 전환
            // em.detach(member);
            // 영속성 컨텍스트를 완전히 초기화

            // em.flush();
            // 영속성 컨텍스트를 비우지 않음
            // 영속성 컨텍스트의 변경 내용을 데이터베이스에 동기화
            // 트랜잭션이라는 작업 단위가 중요 -> 커밋 직전에만 동기화하면 된다.

            // JDBC Batch : Buffering 과 유사한 기능을 한다.
*/
