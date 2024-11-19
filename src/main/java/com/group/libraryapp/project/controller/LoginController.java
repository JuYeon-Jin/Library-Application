package com.group.libraryapp.project.controller;

import com.group.libraryapp.project.dto.error.ErrorResponseDTO;
import com.group.libraryapp.project.dto.user.SignUpDTO;
import com.group.libraryapp.project.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 로그인과 회원가입을 담당하는 컨트롤러 클래스입니다.
 */
@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }



    /**
     * 에러 페이지에 대한 엔드포인트입니다.
     */
    @GetMapping("/error")
    public String errorPage(Model model) {
        ErrorResponseDTO errorResponse = (ErrorResponseDTO) model.asMap().get("errorResponse");

        if (errorResponse.getCode() < 10) {
            model.addAttribute("role", "user");
        } else if (errorResponse.getCode() >= 10) {
            model.addAttribute("role", "admin");
        }
        model.addAttribute("errorMessage", errorResponse.getMessage());
        return "view/error-page";
    }



    /**
     * 홈페이지 엔드포인트입니다.
     */
    @GetMapping("/")
    public String homepage() {
        return "view/home";
    }

/*
    TODO [공부] view 를 직접 반환하는 대신 redirect 하는 이유

    새로 고침(리프레시)에 안전:
    사용자가 페이지를 새로 고침할 때, 브라우저가 동일한 POST 요청을 다시 보내지 않도록 하기 위함입니다.
    만약 회원가입이 완료된 후 바로 view/user/home을 반환했다면, 사용자가 새로 고침을 할 때 또 다시 회원가입이 발생할 수 있습니다. 하지만 redirect:/를 사용하면 새로 고침을 하더라도 GET 요청만 보내지므로 같은 데이터가 여러 번 처리되는 문제를 방지할 수 있습니다.

    사용자의 경험(UX) 향상:
    리다이렉트 방식은 "회원가입 완료 후 홈으로 돌아갑니다"는 흐름을 자연스럽게 만들어줍니다. 사용자에게 성공적인 회원가입 후 홈페이지로 자동으로 돌아간다는 피드백을 주는 것이 일반적입니다.
    또한, /join을 POST로 처리한 후, / 경로로 리다이렉트되면 홈 화면이 새로 로딩되기 때문에 사용자가 POST 요청을 통해 이루어진 작업(회원가입)이 명확하게 분리되고, 사용자는 상태 변경 후 새로운 페이지를 경험하게 됩니다.

    뷰 로직 분리:
    리다이렉트를 통해 비즈니스 로직(signUp)과 뷰 로직을 명확히 분리할 수 있습니다. 비즈니스 로직을 처리한 후, 별도의 뷰를 렌더링하는 것이기 때문에 컨트롤러의 역할이 명확히 분리됩니다.
    직접 뷰를 반환하는 방식은 주로 컨트롤러에서 데이터 처리와 뷰 로직을 혼합할 때 사용되며, 이는 점차 복잡해질 수 있기 때문에 보통 리다이렉트를 사용해 컨트롤러의 책임을 분리합니다.

    - 요약
    리다이렉트(redirect:/)**는 회원가입이 성공한 후 사용자가 홈 페이지로 이동하는 흐름을 명확히 만들어주며, 새로 고침을 방지하고, POST-REDIRECT-GET 패턴을 구현하는 방식입니다.
    뷰 반환은 서버가 바로 해당 뷰를 렌더링하여 사용자에게 보여주는 방식이며, 새로 고침 시 같은 POST 요청이 발생할 수 있다는 단점이 있습니다.

* */

    /**
     * 회원 가입 엔드포인트입니다.
     *
     * @param dto 회원 가입 정보를 포함하는 UserDTO 객체
     */
    @PostMapping("/join")
    public String signUp(SignUpDTO dto) {
        userService.signUp(dto);
        return "redirect:/";
    }



    /**
     * 이 메서드는 Spring Security 를 통해 현재 인증된 사용자의 권한을 확인하고,
     * 권한에 따라 관리자 페이지 또는 사용자 페이지로 리다이렉트합니다.
     *
     * <ul>
     *   <li>ROLE_ADMIN 권한을 가진 사용자는 관리자 페이지("/admin/book")로 리다이렉트됩니다.</li>
     *   <li>ROLE_USER 권한을 가진 사용자는 사용자 페이지("/user/myBook")로 리다이렉트됩니다.</li>
     * </ul>
     *
     * @return "redirect:/admin/book" - ADMIN 권한일 경우 관리자 페이지로 리다이렉트
     *         "redirect:/user/myBook" - USER 권한일 경우 사용자 페이지로 리다이렉트
     *         "redirect:/login" - 권한이 없거나 알 수 없는 경우 로그인 페이지로 리다이렉트
     */
    @GetMapping("/authBranch")
    public String redirectBasedOnRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse(null);

        if ("ROLE_ADMIN".equals(role)) {
            return "redirect:/admin/library";
        } else if ("ROLE_USER".equals(role)) {
            return "redirect:/user/library";
        } else {
            return "redirect:/test";
        }
    }

    /*
    TODO [공부] stream 과 Iterator
    * < redirectBasedOnRole()의 Iterator 버전 >
    public String redirectBasedOnRoleWithIterator() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();

        String role = iter.hasNext() ? iter.next().getAuthority() : null;
    }
    */
}
