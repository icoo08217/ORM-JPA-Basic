package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin(); // db 트랜잭션 실행

        try { // 정석 코드

            //영속
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

            Member member = new Member();
            member.setUsername("C");

            System.out.println("========");
            em.persist(member);
            System.out.println("member.id = " + member.getId());
            System.out.println("========");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
