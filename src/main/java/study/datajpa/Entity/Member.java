package study.datajpa.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter // 가급적 Setter는 엔티티에서 사용하지않기. 예제이므로 작성 (실무에서는 꼭 필요한 곳에서만)
@NoArgsConstructor(access = AccessLevel.PROTECTED) //기본생성자는 접근을 막고 싶지만, JPA스펙상 열어두어야함.
@ToString(of = {"id", "username", "age"})
@NamedQuery(
        name="Member.findByUsername",
        query="select m from Member m where m.username = :username"
        ) //Named 정의
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    public Member(String username) {
        this.username = username;
    }

    public Member(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;
        if (team != null) {
            changeTeam(team);
        }
    }

    //Member에서 Team을 변경
    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this); //Team에 있는 Member도 셋팅
    }
}
