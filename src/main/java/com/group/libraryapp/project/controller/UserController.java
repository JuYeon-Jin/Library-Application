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
     * 사용자 페이지 - 도서 전체 목록
     */
    @GetMapping("/library")
    public String userBookList(@RequestParam(required = false) String keyword,
                               Model model) {
        model.addAttribute("userInfo", getSecurityUsername());
        model.addAttribute("books", bookService.listAllBooks(getSecurityUserId(), keyword));
        model.addAttribute("keyword", keyword);
        // 인기도서 불러오기

        return "view/user-book-list";
    }


    /**
     * 사용자 페이지 - 대여 도서 목록
     */
    @GetMapping("/my-book")
    public String userLoanList(Model model) {
        model.addAttribute("userInfo", getSecurityUsername());

        String userId = getSecurityUserId();
        model.addAttribute("reservation", reservationService.listAllReservedBooks(userId));
        model.addAttribute("loan", loanService.listAllLoanBooks(userId));

        return "view/user-loan-list";
    }

    @PostMapping("/loan/{bookId}")
    public String loanBook(@PathVariable int bookId) {
        return "redirect:/user/my-book";
    }

    @PutMapping("/loan/{bookId}")
    public String returnBook(@PathVariable int bookId) {
        return "redirect:/user/my-book";
    }





    // 예약
    @PostMapping("/reserve/{bookId}")
    public String reserveBook(@PathVariable int bookId) {
        return "redirect:/user/my-book";
    }


    /**
     * TODO 자바독스 수정 필요
     * 세션에서 유저 닉네임 들고옴. 나중에 유저 정보 전체가 필요해지면 (가입일 등등) dto 타입으로 변경할 것
     * @return
     */
    private String getSecurityUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getName();
        }
        return null;
    }


    // userId 가져오기
    private String getSecurityUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            return userDetails.getUserId();
        }
        return null;
    }

    // ---------------------------------------------------------------------------------
    // 대여 도서 목록
    /*
    @GetMapping("/user/mybook")
    public String myBookList(Model model) {
        model.addAttribute("books", bookService.loanBookList());
        return "view/user/loanHistory";
    }

    // 도서 전체 목록
    @GetMapping("/bookList")
    public String bookList(Model model) {
        model.addAttribute("books", bookService.bookList());
        return "view/user/bookList";
    }

    // 도서 대여 (CREATE)
    @PostMapping("/loanBook")
    public String loanBook(BookDTO dto) {
        bookService.loanBook(dto.getBookId());
        return "redirect:/user/bookList";
    }

    // 도서 반납 (PUT)
    @PostMapping("/returnBook")
    public String returnBook(BookLoanDTO dto) {
        bookService.returnBook(dto.getId(), dto.getBookId());
        return "redirect:/user/myBook";
    }

    // 도서 예약
    @PostMapping("/reservation")
    public String reservation() {
        // 서비스 미구현
        return "redirect:/user/bookList";
    }*/


/*

    // READ   * 회원 전체 목록 조회
    @GetMapping("/admin/userlist")
    public List<UserResponse> getUsers(ModelAndView mav) {
        return userService.getUsers();
        //        mav.setViewName("/post/my-posts");
        //        mav.addObject("posts",postService.allPosts());
    }
    // UPDATE   * 비밀번호 수정
    @PutMapping("/user")
    public void updateUser(@RequestBody UserUpdateRequest request) {
       userService.updateUser(request);
    }
    // DELETE   * 회원 탈퇴
    @DeleteMapping("/user")
    public void deleteUser(@RequestParam String name) {
        userService.deleteUser(name);
    }
    */

}
