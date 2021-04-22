package study.datajpa.repository;

import org.springframework.stereotype.Repository;
import study.datajpa.Entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberJpaRepository {

    @PersistenceContext
    private EntityManager em;

    //회원 가입
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    //회원 탈퇴
    public void delete(Member member) {
        em.remove(member);
    }

    //회원 단 건 조회
    public Member find(Long id) {
        return em.find(Member.class, id);
    }

    //회원 단건 조회 Optional 반환
   public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    //회원 수
    public long count() {
        return em.createQuery("select count(m) from Member m", Long.class)
                .getSingleResult();
    }

    //회원 전체 조회
    public List<Member> findAll() {
        return em.createQuery("select m from Member m").getResultList();
    }

    //회원명과 나이 비교 후 조회
    public List<Member> findByUsernameAndAgeGreaterThan(String username, int age) {
        return em.createQuery("select m from Member m where m.username = :username and m.age > :age")
                .setParameter("username", username)
                .setParameter("age", age)
                .getResultList();
    }
}
