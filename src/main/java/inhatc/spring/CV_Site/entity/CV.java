package inhatc.spring.CV_Site.entity;

import inhatc.spring.CV_Site.common.entity.BaseEntity;
import inhatc.spring.CV_Site.constant.MillitaryService;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor //기본 생성자
@AllArgsConstructor //모든 인자가 있는 생성자
@Builder //인자를 가진 생성자
public class CV extends BaseEntity {
    @Id //기본키 어노테이션
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cv_id")
    private Long id; //이력서 아이디

    @Column(name = "cv_name")
    private String cvName; //이력서명

    @Column(name = "name", nullable = false, length = 50)
    private String name; //이력서에 들어갈 이름

    @Column(name = "cv_birthday")
    private LocalDate birth_cv; //이력서에 들어갈 생년월일

    @Column(name = "cv_phone")
    private String phone; //이력서에 들어갈 전화번호

    @Column(name = "cv_address")
    private String addr; //이력서에 들어갈 주소

    @Column(name = "cv_millitary_service")
    @Enumerated(EnumType.STRING)
    private MillitaryService millitaryService; //병역사항

    @Column(name = "cv_education")
    private String education; //학력

    @Column(name = "cv_certificate")
    private String certificate; //자격증

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}