package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.*;
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

            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(new Address("homeCity" , "street" , "10000"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            member.getAddressHistory().add(new AddressEntity("old1" , "street" , "10000"));
            member.getAddressHistory().add(new AddressEntity("old2" , "street" , "10000"));

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("============ START ===============");
            Member findMember = em.find(Member.class, member.getId());

            //homeCity => newCity
//            Address a = findMember.getHomeAddress();
//            findMember.setHomeAddress(new Address("newCity" , a.getStreet() , a.getZipcode()));
//
//            // 치킨 -> 파전
//            findMember.getFavoriteFoods().remove("치킨");
//            findMember.getFavoriteFoods().add("파전");
//
//            findMember.getAddressHistory().remove(new Address("old1" , "street" , "10000"));
//            findMember.getAddressHistory().add(new Address("newCity1" , "street" , "10000"));

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
