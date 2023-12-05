package inhatc.spring.CV_Site.Controller;

import inhatc.spring.CV_Site.dto.MemberFormDto;
import inhatc.spring.CV_Site.entity.Member;
import inhatc.spring.CV_Site.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberControllerTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Member createMember(String email, String password, LocalDate birthdate) {
        MemberFormDto memberFormDto = MemberFormDto.builder()
                .email(email)
                .name("헝골덩")
                .password(password)
                .birthdate(birthdate)
                .nickname("HeongGolDeong").build();
        Member member = Member.createMember(memberFormDto, passwordEncoder);
        return memberService.saveMember(member);
    }

    // 로그인 성공 테스트
    @Test
    @DisplayName("로그인 성공 테스트")
    public void loginSuccessTest() throws Exception {
        String email = "test@test.com";
        String password = "1111";
        createMember(email, password, LocalDate.parse("2023-11-01"));

        mockMvc.perform(formLogin().userParameter("email")
                .loginProcessingUrl("/member/login")
                .user(email).password(password))
                .andExpect(SecurityMockMvcResultMatchers.authenticated()); //인증 여부
    }

    //로그인 실패 테스트
    @Test
    @DisplayName("로그인 실패 테스트")
    public void loginFailTest() throws Exception {
        String email = "test@test.com";
        String password = "1111";
        createMember(email, password, LocalDate.parse("2023-11-01"));

        mockMvc.perform(formLogin().userParameter("email")
                        .loginProcessingUrl("/member/login")
                        .user(email).password("무야호~"))
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated()); //비인증 여부
    }
}