package inhatc.spring.CV_Site.Controller;

import inhatc.spring.CV_Site.dto.CvFormDto;
import inhatc.spring.CV_Site.dto.CvSearchDto;
import inhatc.spring.CV_Site.entity.CV;
import inhatc.spring.CV_Site.service.CvService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/cv")
public class CvController {

    private final CvService cvService;

    @GetMapping(value = "/new")
    public String cvForm(Model model) {
        model.addAttribute("cvFormDto", new CvFormDto());
        return "cv/cvForm";
    }

    @PostMapping(value = "/new")
    public String cvNew(@Valid CvFormDto cvFormDto, BindingResult bindingResult,
                        Model model, MultipartFile cvImgFile) {
        if(bindingResult.hasErrors()) {
            return "cv/cvForm";
        }

        if (cvImgFile.isEmpty() && cvFormDto.getId() == null) {
            model.addAttribute("errorMessage", "이력서 사진은 필수입니다.");
            return "cv/cvForm";
        }

        try {
            cvService.saveCv(cvFormDto, cvImgFile);
        } catch (IOException e) {
            model.addAttribute("errorMessage", "이력서 등록 실패. 다시 시도해주세요.");
            return "cv/cvForm";
        }
        return "redirect:/";
    }

    @GetMapping("/{cvId}") // /cv/1
    public String cvDetail(@PathVariable("cvId") Long cvId, Model model) {
        try {
            CvFormDto cvFormDto = cvService.getCvDetail(cvId);
            model.addAttribute("cvFormDto", cvFormDto);
            return "cv/cvForm";
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "존재하지 않는 이력서입니다.");
            model.addAttribute("cvFormDto", new CvFormDto());
            return "cv/cvForm";
        }
    }

    @PostMapping("/{cvId}") // /cv/1
    public String cvUpdate(@Valid CvFormDto cvFormDto, BindingResult bindingResult,
                           Model model, MultipartFile cvImgFile) {
        if(bindingResult.hasErrors()) {
            return "cv/cvForm";
        }

        if (cvImgFile.isEmpty() && cvFormDto.getId() == null) {
            model.addAttribute("errorMessage", "이력서 사진은 필수입니다.");
            return "cv/cvForm";
        }
        return "redirect:/";
    }



    @GetMapping(value = {"/cvs", "/cvs/{page}"})
    public String CvManage(CvSearchDto cvSearchDto, @PathVariable("page")Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);

        Page<CV> cvs = cvService.getCvPage(cvSearchDto, pageable);
        model.addAttribute("cvs", cvs);
        model.addAttribute("cvSearchDto", cvSearchDto);
        model.addAttribute("maxPage", 5);
        return "cv/cvMng";
    }

}
