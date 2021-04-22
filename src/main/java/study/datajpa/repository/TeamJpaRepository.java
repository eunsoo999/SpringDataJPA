package study.datajpa.repository;

import org.springframework.stereotype.Repository;
import study.datajpa.Entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class TeamJpaRepository {

    @PersistenceContext
    private EntityManager em;

    //팀 추가
    public Team save(Team team) {
        em.persist(team);
        return team;
    }

    //팀 삭제
    public void delete(Team team) {
        em.remove(team);
    }

    //팀 단건 조회
    public Team find(Long id) {
        return em.find(Team.class, id);
    }

    public Optional<Team> findById(Long id) {
        Team team = em.find(Team.class, id);
        return Optional.ofNullable(team);
    }

    public Long count() {
        return em.createQuery("select count(t) from Team t", Long.class).getSingleResult();
    }

    //팀 전체 조회
    public List<Team> findAll() {
        return em.createQuery("select t from Team t").getResultList();
    }
}