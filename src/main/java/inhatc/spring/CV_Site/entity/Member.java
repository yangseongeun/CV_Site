package inhatc.spring.CV_Site.entity;

import inhatc.spring.CV_Site.common.entity.BaseEntity;
import inhatc.spring.CV_Site.constant.Role;
import inhatc.spring.CV_Site.dto.MemberFormDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor //기본 생성자
@AllArgsConstructor //모든 인자가 있는 생성자
@Builder //인자를 가진 생성자
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private LocalDate birthdate;

    @Column(unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Role role;

    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        Member member = Member.builder()
                .name(memberFormDto.getName()) //set 커맨드
                .email(memberFormDto.getEmail())
                .password(passwordEncoder.encode(memberFormDto.getPassword()))
                .birthdate(memberFormDto.getBirthdate())
                .nickname(memberFormDto.getNickname())
                .role(Role.USER)
                .build();
        return member;
    }
}
