package inhatc.spring.CV_Site.service;

import inhatc.spring.CV_Site.entity.Member;
import inhatc.spring.CV_Site.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository; //MemberRepository 변수, 객체 생성 가능

    public Member saveMember(Member member) { //Member 변수를 받아서 등록을 해주는 메소드
        validateDuplicationMember(member);
        return memberRepository.save(member); //Member 등록
    }

    private void validateDuplicationMember(Member member) { //Member 변수가 기존에 이미 등록되어 있는가?
        Optional<Member> findMember = memberRepository.findByEmail(member.getEmail()); //Member를 찾아주세요.
        if(findMember.isPresent()) { //만약에 Member가 이미 있으면 ~
            System.out.println(findMember.get().getEmail()); //이메일을 가져오세요.
            throw new IllegalStateException("이미 이 이름으로 가입된 회원입니다."); //예외 처리
        }

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("해당 사용자(" + email + ")를 찾을 수 없습니다."));

        log.info("============> [로그인된 사용자]: " + member);

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }
}
