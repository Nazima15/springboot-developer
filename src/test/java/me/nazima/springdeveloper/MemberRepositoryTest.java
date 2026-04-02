package me.nazima.springdeveloper;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/*
 * @DataJpaTest
 * - JPA 관련 기능만 테스트
 * - 각 테스트는 트랜잭션으로 실행됨
 * - 테스트 종료 시 자동 롤백 (DB에 영향 없음)
 */
@DataJpaTest
public class MemberRepositoryTest {

    /*
     * @Autowired
     * - 스프링이 MemberRepository 객체를 자동으로 생성해서 주입
     */
    @Autowired
    MemberRepository memberRepository;

    /*
     * 전체 회원 조회 테스트
     */
    @Test
    @Sql("/insert-members.sql") // 테스트 전에 SQL 실행 (데이터 3개 삽입)
    void getAllMembers() {

        // given
        // insert-members.sql 실행 → 회원 3명 존재

        // when
        // select * from member;
        List<Member> members = memberRepository.findAll();

        // then
        // 조회된 데이터 개수 확인
        assertThat(members.size()).isEqualTo(3);
    }

    /*
     * ID로 회원 조회 테스트
     */
    @Test
    @Sql("/insert-members.sql")
    void getMemberId() {

        // when
        // select * from member where id = 2;
        // 반환 타입: Optional<Member>
        Member member = memberRepository.findById(2L).get();

        // then
        assertThat(member.getName()).isEqualTo("B");
    }

    /*
     * 이름으로 회원 조회 테스트
     */
    @Test
    @Sql("/insert-members.sql")
    void getMemberByName() {

        // when
        // select * from member where name = 'C';
        Member member = memberRepository.findByName("C").get();

        // then
        assertThat(member.getId()).isEqualTo(3L);
    }

    /*
     * 레코드 삽입 테스트
     */
    @DisplayName("레코드 삽입 테스트")
    @Test
    @Transactional
    void saveMember() {

        // given
        // Member 객체 생성 (id 없음 → 새 데이터 INSERT 대상)
        Member m = new Member("scpark");

        /*
         * save() 동작 원리
         *
         * 1. Member 객체에 id가 없으면 (null)
         *    → INSERT 실행
         *    → insert into member(name) values ("scpark");
         *
         * 2. Member 객체에 id가 이미 있으면
         *    → UPDATE 실행
         *    → update member set name="scpark" where id=1;
         *
         * 3. save() 결과
         *    → 저장된 데이터를 Member 객체로 반환
         *    → return Member(생성된 id, "scpark");
         */

        // when
        // INSERT 실행
        Member savedMember = memberRepository.save(m);

        // then

        // 1. ID 생성 확인 → INSERT 성공 여부
        assertThat(savedMember.getId()).isNotNull();

        /*
         * findById() 동작
         *
         * 1. select * from member where id = :id
         * 2. Optional<Member> 형태로 반환
         */

        // 2. 실제 DB 조회 후 값 검증
        assertThat(
                memberRepository.findById(savedMember.getId()).get().getName()
        ).isEqualTo("scpark");
    }

    @DisplayName("2개의 레코드를 한 번에 삽입하는 테스트")
    @Test
    void saveMembers(){
        //given
        List<Member> members=List.of(new Member("HongGilDong"),
                new Member("Park MungSu"));

        //when
        memberRepository.saveAll(members);

        //then
        assertThat(memberRepository.findAll().size()).isEqualTo(2);
    }

    @Sql("/insert-members.sql")
    @DisplayName("레코드 삭제 테스트 ")
    @Test
    void deleteAll(){
        //given

        //when
        memberRepository.deleteAll();

        //then
        assertThat(memberRepository.findAll().size()).isZero();
    }

    @Sql("/insert-members.sql")
    @DisplayName("Update Test")
    @Test
    void update(){
        //given
        Member member=memberRepository.findById(2L).get();
        //when
        member.changeName("scpark");
        //memberRepository.save(member);
        //then
        assertThat(memberRepository.findById(2L).get().getName()).isEqualTo("scpark");

    }
}