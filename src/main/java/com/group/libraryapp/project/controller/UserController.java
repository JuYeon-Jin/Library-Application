package com.group.libraryapp.project.controller;

import com.group.libraryapp.project.dto.security.CustomUserDetails;
import com.group.libraryapp.project.service.BookLoanService;
import com.group.libraryapp.project.service.BookService;
import com.group.libraryapp.project.service.ReservationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 사용자와 관련된 요청을 처리하는 컨트롤러입니다.
 * 사용자 페이지에서 도서 목록 조회, 대출, 예약 및 반납과 같은 작업을 담당합니다.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private final BookService bookService;
    private final BookLoanService loanService;
    private final ReservationService reservationService;

    public UserController(BookService bookService, BookLoanService loanService, ReservationService reservationService) {
        this.bookService = bookService;
        this.loanService = loanService;
        this.reservationService = reservationService;
    }


    /**
     * '도서 전체 목록'을 조회하여 '인증된 사용자 닉네임', '검색어(존재할 시)' 도 같이 뷰에 담아 반환합니다.
     * 검색 키워드가 존재하면 필터링 된 도서 목록이 조회됩니다.
     *
     * @param keyword 검색 키워드 (선택 사항, 없을 경우 전체 목록 조회)
     * @param model   뷰에 전달할 데이터를 담는 객체
     * @return 사용자 도서 전체 목록 페이지 뷰 이름
     */
    @GetMapping("/library")
    public String userBookList(@RequestParam(required = false) String keyword,
                               Model model) {

        model.addAttribute("userInfo", getSecurityUsername());
        model.addAttribute("books", bookService.listAllBooks(getSecurityUserId(), keyword));
        model.addAttribute("keyword", keyword);
        // TODO 인기도서 5개, (1순위 대출기록 순, 2순위 BookId)
        // TODO 대출버튼, 예약버튼 active 상태에 대해 로직 다시 고민하기

        return "view/user-book-list";
    }


    /**
     * 사용자가 '대여한 도서'와 '예약한 도서'를 List<dto> 형태로 조회하여 뷰에 담아 반환합니다.
     * '인증된 사용자 닉네임' 도 같이 뷰에 담아 반환합니다.
     *
     * @param model 뷰에 전달할 데이터를 담는 객체
     * @return 사용자 대여 및 예약 도서 목록 페이지의 뷰 이름
     */
    @GetMapping("/my-book")
    public String userLoanList(Model model) {

        model.addAttribute("userInfo", getSecurityUsername());

        String userId = getSecurityUserId();
        model.addAttribute("reservation", reservationService.listAllReservedBooks(userId));
        model.addAttribute("loan", loanService.listAllLoanBooks(userId));

        return "view/user-loan-list";
    }


    /**
     * 대출이 가능한 상태의 도서를 대출합니다.
     * 사용자의 도서대출이 성공적으로 완료되면 대출 목록 페이지("/user/my-book")로 리다이렉트됩니다.
     *
     * @param bookId 대출할 도서의 고유 ID
     * @return 사용자 대출 목록 페이지로 리다이렉트
     */
    @PostMapping("/loan/{bookId}")
    public String loanBook(@PathVariable int bookId) {

        loanService.borrowBook(getSecurityUserId(), bookId);
        return "redirect:/user/my-book";
    }


    /**
     * 예약한 상태에서 도서 대출 순서가 도래하였을 때 해당 도서를 대출합니다.
     * 예약 취소 후 대출이 진행되며, 처리 완료 후 사용자 대출 목록 페이지("/user/my-book")로 리다이렉트됩니다.
     *
     * @param reserveId 예약 고유 ID
     * @return 사용자 대출 목록 페이지로 리다이렉트
     */
    @PostMapping("/reserve/loan/{reserveId}")
    public String loanBookFromReservation(@PathVariable int reserveId) {

        loanService.proceedReservationToCheckout(getSecurityUserId(), reserveId);
        return "redirect:/user/my-book";
    }


    /**
     * loanId 를 통해 대출 도서를 반납합니다.
     * 도서 반납 후, 사용자 대출 목록 페이지("/user/my-book")로 리다이렉트됩니다.
     *
     * @param loanId 반납할 대출 기록의 고유 ID
     * @return 사용자 대출 목록 페이지로 리다이렉트
     */
    @PutMapping("/loan/{loanId}")
    public String returnBook(@PathVariable int loanId) {

        loanService.returnBook(getSecurityUserId(),loanId);
        return "redirect:/user/my-book";
    }


    /**
     * 특정 도서를 예약합니다.
     * 예약 완료 후 사용자 대출 목록 페이지("/user/my-book")로 리다이렉트됩니다.
     *
     * @param bookId 예약할 도서의 고유 ID
     * @return 사용자 대출 목록 페이지로 리다이렉트
     */
    @PostMapping("/reserve/{bookId}")
    public String reserveBook(@PathVariable int bookId) {
        reservationService.reservationBook(getSecurityUserId(), bookId);
        return "redirect:/user/my-book";
    }


    /**
     * 특정 예약을 취소합니다.
     * 예약 취소 후 사용자 대출 목록 페이지("/user/my-book")로 리다이렉트됩니다.
     *
     * @param reserveId 취소할 예약의 고유 ID
     * @return 사용자 대출 목록 페이지로 리다이렉트
     */
    @DeleteMapping("/reserve/{reserveId}")
    public String cancelReserve(@PathVariable int reserveId) {
        reservationService.cancelReservation(getSecurityUserId(), reserveId);
        return "redirect:/user/my-book";
    }


    /**
     * 현재 인증된 사용자(로그인된 사용자)의 username(이 프로젝트에서는 ID) 을 반환합니다.
     *
     * Spring Security 를 통해 현재 인증된 사용자의 정보를 가져와 사용자명을 추출합니다.
     * 이 값은 사용자가 애플리케이션에 로그인할 때, `Authentication` 객체에 저장되며,
     * `SecurityContextHolder`를 통해 애플리케이션의 전역 어디서든 접근할 수 있습니다.
     * `CustomUserDetails` 클래스에서 관리되는 사용자 정보에서 `getName()` 메서드를 호출하여 username 을 반환합니다.
     * 만약 인증된 사용자가 없다면, 즉 로그인되지 않은 경우에는 `null`을 반환합니다.
     *
     * @return 인증된 사용자의 사용자명(로그인한 사용자)의 문자열 값. 만약 인증된 사용자가 없다면 `null`을 반환.
     */
    private String getSecurityUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getName();
        }
        return null;
    }

    /*
    // TODO [공부] if (authentication != null) { return authentication.getName(); } 조건문이 필요한 이유

    → authentication 이 null 이면 authentication.getName()을 호출할 수 없습니다
      authentication 이 null 이면 getName()을 호출하려고 할 때 NullPointerException 이 발생합니다.

      사실, Spring Security 에서 경로(Path) 를 기반으로 ROLE_USER 와 같은 권한을 필터링하고 있다면,
      SecurityContext 에 인증 정보가 설정되어 있어야 정상적으로 작동합니다.
      하지만, 여전히 null 이 나올 수 있는 몇 가지 상황이 있을 수 있습니다.
      여기서 중요한 것은 Spring Security 의 필터 체인과 SecurityContext 의 동작 방식입니다.
      Spring Security 의 인증/권한 처리 방식이 경로에 대한 접근 제어와는 별개로 인증 상태나 SecurityContext 관리와 밀접하게 연관되기 때문입니다.
      경로 필터링만으로는 인증이 완료되었는지, 세션이나 인증 정보가 유효한지 등을 보장할 수 없기 때문에,
      특정 조건에 따라 SecurityContext 가 null 이 되거나 인증 정보가 누락될 수 있습니다.

      정리하자면,
      경로 기반 권한 필터링은 인증 정보가 설정된 후에만 제대로 작동합니다.
      인증되지 않았거나 세션이 만료되거나, 인증 정보가 누락되거나, 비동기 요청에서 인증이 제대로 설정되지 않으면
      SecurityContext 에 Authentication 이 없다면 경로 필터링도 제대로 동작하지 않습니다.
    * */


    /**
     * 현재 인증된 사용자(로그인된 사용자)의 고유 사용자 ID를 반환합니다.
     *
     * Spring Security 를 통해 현재 인증된 사용자의 정보를 가져와 사용자의 고유 ID(userId)를 추출합니다.
     * 여기서 추출하는 사용자의 고유 ID(userId)는 데이터베이스에서 관리되는 Primary Key 입니다.
     * 이 값은 사용자가 애플리케이션에 로그인할 때, `Authentication` 객체에 저장되며,
     * `SecurityContextHolder`를 통해 애플리케이션의 전역 어디서든 접근할 수 있습니다.
     * `CustomUserDetails` 클래스에서 관리되는 사용자 정보에서 `getUserId()` 메서드를 호출하여 userId 를 반환합니다.
     * 만약 인증된 사용자가 없다면, 즉 로그인되지 않은 경우에는 `null`을 반환합니다.
     *
     * @return 인증된 사용자의 고유 사용자 ID 값. 만약 인증된 사용자가 없다면 `null`을 반환.
     */
    private String getSecurityUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            return userDetails.getUserId();
        }
        return null;
    }


    // TODO 추후 추가할 계획이 있는 사용자 기능 : 유저 프로필 사진, 도서 연체금 시스템, 과거+현재 대출 기록 전체 조회, 유저 탈퇴
}
