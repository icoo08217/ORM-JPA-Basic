package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin(); // db 트랜잭션 실행

        try { // 정석 코드

//            // JPQL --> 객체지향 SQL
//            String qlString = "select m From Member as m where m.username like '%kim%'"; // 문자 형식이기때문에 동적 쿼리를 해결하기 너무 어렵다.
//            List<Member> resultList = em.createQuery(
//                    qlString,
//                    Member.class
//            ).getResultList();
//
//            for (Member member : resultList) {
//                System.out.println("member = " + member);
//            }

            //Criteria 사용 준비
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Member> query = cb.createQuery(Member.class);

            Root<Member> m = query.from(Member.class);

            CriteriaQuery<Member> cq = query.select(m).where(cb.equal(m.get("username"), "kim"));
            List<Member> resultList = em.createQuery(cq).getResultList();

            // flush -> commit , query 전에 항상 실행됨.

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
