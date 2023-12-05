package inhatc.spring.CV_Site.repository;

import inhatc.spring.CV_Site.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email); //이메일에 따른 사용자가 없을 수도 있기 때문에 Optional로 받는다.
}
