package inhatc.spring.CV_Site.Controller;

import inhatc.spring.CV_Site.dto.MemberFormDto;
import inhatc.spring.CV_Site.entity.Member;
import inhatc.spring.CV_Site.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/member")
@Slf4j //로그를 위한 어노테이션
@RequiredArgsConstructor //final로 선언된 객체를 자동으로 생성해주는 어노테이션
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "/new")
    public String memberForm(Model model) {

        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/memberForm";
    }

    @PostMapping(value = "/new")
    public String insertMember(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
        log.info("=========>" + memberFormDto);

        if(bindingResult.hasErrors()) {
            return "member/memberForm";
        }

        try {
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            Member m = memberService.saveMember(member);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberForm";
        }

        return "redirect:/";
    }

    @GetMapping("/login") //로그인
    public String loginMember() {
        return "member/memberLoginForm";
    }

    @GetMapping("/logout") //로그아웃
    public String logoutMember(HttpServletRequest request, HttpServletResponse response) { //요청, 응답
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null) { //로그인 정보가 있으면
            new SecurityContextLogoutHandler().logout(request, response, authentication); //로그아웃하면서 세션 정보 지우기
        }
        return "redirect:/"; //로그아웃 페이지 불러오기
    }

    @GetMapping("/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호가 올바르지 않습니다.");
        return "member/memberLoginForm";
    }

}
