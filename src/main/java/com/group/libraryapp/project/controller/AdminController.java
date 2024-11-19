package com.group.libraryapp.project.controller;

import com.group.libraryapp.project.dto.book.BookRegistrationDTO;
import com.group.libraryapp.project.service.BookService;
import com.group.libraryapp.project.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 관리자와 관련된 요청을 처리하는 컨트롤러입니다.
 * 관리자 페이지에서 도서 관리, 사용자 관리 등과 관련된 작업을 담당합니다.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final BookService bookService;
    private final UserService userService;

    public AdminController(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }


    /**
     * 검색 키워드와 페이지 번호를 기준으로 필터링 된 도서 목록을 반환합니다.
     * 검색 키워드가 없으면 전체 도서 목록을 조회합니다.
     *
     * @param keyword 검색 키워드 (선택 사항, 없을 경우 전체 목록 조회)
     * @param page    요청한 페이지 번호 (선택 사항, 기본값은 "1")
     * @return 관리자 도서 목록 페이지 뷰 이름
     */
    @GetMapping("/library")
    public String book(@RequestParam(required = false) String keyword,
                       @RequestParam(required = false, defaultValue = "1") String page,
                       Model model) {
        model.addAttribute("pagination", bookService.pagination(keyword, page));
        model.addAttribute("books", bookService.listAllBooks(keyword, page));
        model.addAttribute("keyword", keyword);
        return "view/admin-book-list";
    }


    /**
     * 새로운 도서를 추가합니다.
     * 도서 정보는 {@link BookRegistrationDTO} 객체로 전달되며,
     * 도서의 이미지 파일은 {@link MultipartFile} 객체로 전달됩니다.
     * 성공적으로 추가된 경우, 관리자 도서 목록 페이지로 리다이렉트됩니다.
     *
     * @param bookImage 추가할 도서의 이미지 파일
     * @param dto       추가할 도서의 정보를 담고 있는 데이터 전송 객체 (DTO)
     * @return 관리자 도서 목록 페이지로 리다이렉트
     */
    @PostMapping("/book")
    public String addBook(@RequestPart("bookImage") MultipartFile bookImage,
                          @ModelAttribute BookRegistrationDTO dto) {
        bookService.saveBook(dto, bookImage);
        return "redirect:/admin/library";
    }
}
