package jpabook;

import jakarta.persistence.*;
import jpabook.entity.*;
import org.hibernate.Hibernate;

import java.util.List;

public class App {
    static EntityManagerFactory emf
            = Persistence.createEntityManagerFactory("jpabook");
    public static void main( String[] args ) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
            try {
                //saveMember();
                tx.begin();
                Parent parent = new Parent("부모1");
                Child child1 = new Child("자식1");
                Child child2 = new Child("자식2");

                child1.setParent(parent); // 자식 -> 부모 연관관계 설정
                child2.setParent(parent); // 자식 -> 부모 연관관계 설정
                parent.getChildList().add(child1); // 부모 -> 자식 연관관계 설정
                parent.getChildList().add(child2); // 부모 -> 자식 연관관계 설정

                em.persist(parent);
                tx.commit();
            }catch (Exception e){
                tx.rollback();
                e.printStackTrace();
            } finally {
                em.close();
            }
        emf.close();
    }

    private static void saveMember() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Team team1 = new Team();
        team1.setId("team1");
        team1.setName("팀1");
        em.persist(team1);

        Team team2 = new Team();
        team2.setId("team2");
        team2.setName("팀2");
        em.persist(team2);

        Member member1 = new Member();
        member1.setId("member1");
        member1.setUsername("회원1");
        member1.setTeam(team1);
        em.persist(member1);

        Member member2 = new Member();
        member2.setId("member2");
        member2.setUsername("회원2");
        member2.setTeam(team2);
        em.persist(member2);

        tx.commit();
        em.close();
    }

    private static void printUserAndTeam(String memberId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Member member = em.find(Member.class, memberId);
        Team team = member.getTeam();
        System.out.println("회원 이름: " + member.getUsername());
        System.out.println("소속팀: " + team.getName());
        tx.commit();
        em.close();
    }

    private static void printMember(String memberId){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        System.out.println("==========================");
        Member member = em.find(Member.class, memberId);
        System.out.println("==========================");
        System.out.println("회원이름 = " + member.getUsername());
        tx.commit();
    }

    private static void printProxyMember(String memberId){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        System.out.println("----------------------------------");
        Member member = em.getReference(Member.class, memberId);
        System.out.println("member.getClass().getName() = " + member.getClass().getName());
        System.out.println("----------------------------------");
        System.out.println("====================================");
        System.out.println("member.username() = " + member.getUsername());
        System.out.println("====================================");
        tx.commit();
        em.close();
    }


    private static void proxyInitInDetachedMode(String memberId){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        /* 준영속 상태에서는 초기화할 수 없다. */

        // MemberProxy 반환
        Member member = em.getReference(Member.class, memberId);
        tx.commit();
        em.close(); // 영속성 컨텍스트 종료
        // 준영속 상태에서 초기화 시도
        System.out.println("member = " + member.getUsername());
    }

    private static void proxyIdAccessTest(){
        EntityManager em = emf.createEntityManager();
        Member member = em.find(Member.class, "member1");
        Team team = em.getReference(Team.class, "team1");

        System.out.println("================================");
        System.out.println("team.id :  "+team.getId()); // SQL문을 실행하지 않음
        System.out.println("================================");
    }

    private static void testGetReference() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Member member = em.find(Member.class, "member1");
        Member proxyMember = em.getReference(Member.class, "member1");
        System.out.println("proxyMember.getClass() = " + proxyMember.getClass());
        System.out.println("member == proxyMember : " + (member == proxyMember));
        tx.commit();
        em.close();
    }

    private static void testProxyReturnedAfterFind() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Member proxyMember = em.getReference(Member.class, "member1");
        Member member = em.find(Member.class, "member1");
        System.out.println("proxyMember.getClass() = " + proxyMember.getClass());
        System.out.println("member == proxyMember : " + (member == proxyMember));
        tx.commit();
        em.close();
    }

    private static void testProxyLazyRelation(){
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Member member = em.find(Member.class, "member2");
        Team team = em.getReference(Team.class, "team1");  // SQL을 실행하지 않음
        // find()와 비교해보라!
        member.setTeam(team); // save()에서 member2의 null 로 설정
        tx.commit();
        em.close();
    }
}
