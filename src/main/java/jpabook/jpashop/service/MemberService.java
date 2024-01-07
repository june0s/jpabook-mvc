package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // data 변경은 Transaction 내에서 반드시 일어나야 한다.
@RequiredArgsConstructor // final 인 멤버만 생성자 생성해 준다.
public class MemberService {

    private final MemberRepository memberRepository;

    // 생성자 injection, @Autowired 없어도, 생성자가 1개면 spring 이 알아서 해준다.
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // 중복 회원 Exception 처리
        // Tip. db 에서도 name 을 unique 제약 조건을 걸어둬야 안전하다!
        final List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 화원 전체 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public void update(Long id, String name) {
        // 영속 상태 객체 가져와서 수정한다. 그러면 JPA 가 Transaction 끝나면 변경 감지해서 커밋해준다.
        final Member member = memberRepository.findOne(id);
        member.setName(name);
    }
}
