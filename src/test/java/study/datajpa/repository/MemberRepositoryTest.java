package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.Entity.Member;
import study.datajpa.Entity.Team;
import study.datajpa.dto.MemberDto;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired TeamRepository teamRepository;

    @Test
    public void testMember() {
        Member member = new Member("memberA");
        Member savedMember = memberRepository.save(member);

        //Optional findById() : 해당 id를 가진 데이터가 없을 수도 있기 때문에 Optional타입
        Member findMember = memberRepository.findById(savedMember.getId()).get();

        //검증
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        Assertions.assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void basicCRUD() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        memberRepository.save(member1);
        memberRepository.save(member2);

        //단건 조회 검증
        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();
        Assertions.assertThat(findMember1).isEqualTo(member1);
        Assertions.assertThat(findMember2).isEqualTo(member2);

        //트랙잭션이 종료되면서 변경감지로 수정
        //findMember1.setUsername("haha");

        //리스트 조회 검증
        List<Member> all = memberRepository.findAll();
        Assertions.assertThat(all.size()).isEqualTo(2);

        //카운트 검증
        long count = memberRepository.count();
        Assertions.assertThat(count).isEqualTo(2);

        //삭제 검증
        memberRepository.delete(member1);
        memberRepository.delete(member2);

        count = memberRepository.count();
        Assertions.assertThat(count).isEqualTo(0);
    }

    @Test
    public void findByUsernameAndAgeGreaterThan() {
        Member m1 = new Member("ma", 10);
        Member m2 = new Member("ma", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("ma", 15);

        Assertions.assertThat(result.get(0).getUsername()).isEqualTo("ma");
        Assertions.assertThat(result.get(0).getAge()).isEqualTo(20);
        Assertions.assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void testQuery() {
        Member m1 = new Member("ma", 10);
        Member m2 = new Member("mb", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findUser("ma", 10);
        Assertions.assertThat(result.get(0)).isEqualTo(m1);
    }

    @Test
    public void findUsernameList() {
        Member m1 = new Member("ma", 10);
        Member m2 = new Member("mb", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<String> usernameList = memberRepository.findUsernameList();

        for (String s : usernameList) {
            System.out.println("s = " + s); //실제 테스트 시에는 assert로 해야한다.
        }
    }

    //@Query Dto 조회 테스트
    @Test
    public void findMemberDto() {
        Team team = new Team("teamA");
        teamRepository.save(team);

        Member m1 = new Member("ma", 10);
        m1.setTeam(team);
        memberRepository.save(m1);

        List<MemberDto> memberDto = memberRepository.findMemberDto();

        for (MemberDto dto : memberDto) {
            System.out.println("dto = " + dto);
        }
    }

    //findByNames 테스트
    @Test
    public void findByNames() {
        Member m1 = new Member("ma", 10);
        Member m2 = new Member("mb", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByNames(Arrays.asList("AAA", "BBB"));

        for (Member member : result) {
            System.out.println("member = " + member); //실제 테스트 시에는 assert로 해야한다.
        }
    }


}