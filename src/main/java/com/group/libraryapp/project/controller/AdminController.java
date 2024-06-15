package com.group.libraryapp.project.controller;

import com.group.libraryapp.project.dto.book.BookDTO;
import com.group.libraryapp.project.service.book.BookService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.Iterator;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final BookService bookService;

    public AdminController(BookService bookService) {
        this.bookService = bookService;
    }

    // 책 등록 PAGE
    @GetMapping("/book")
    public String book(Model model) {
        //-------------------------------------------------------------------------------------
        String id = SecurityContextHolder.getContext().getAuthentication().getName();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();


        model.addAttribute("id", id);
        model.addAttribute("role", role);
        //-------------------------------------------------------------------------------------
        return "view/admin/book";
    }

    // 도서 등록 (CREATE)
    @PostMapping("/book")
    public String book(BookDTO dto) {
        bookService.saveBook(dto);
        return "redirect:/admin/book";
    }

    // 도서 제목 수정 (UPDATE)
    @PutMapping("/updateBook")
    public String updateBook() {

        return "redirect:/admin/bookList";
    }

    // 도서 삭제 (DELETE)

    // 도서 전체 목록 PAGE
    @GetMapping("/bookList")
    public String bookList(Model model) {
        //-------------------------------------------------------------------------------------
        String id = SecurityContextHolder.getContext().getAuthentication().getName();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();


        model.addAttribute("id", id);
        model.addAttribute("role", role);
        //-------------------------------------------------------------------------------------

        model.addAttribute("books", bookService.bookList());
        return "view/admin/bookList";

    }

    /*

    // READ   * 회원 전체 목록 조회
    @GetMapping("/admin/userlist")
    public List<UserResponse> getUsers(ModelAndView mav) {
        return userService.getUsers();
        //        mav.setViewName("/post/my-posts");
        //        mav.addObject("posts",postService.allPosts());
    }

    *
    * */

    // 유저 전체 목록 PAGE
    @GetMapping("/userList")
    public String userList(Model model) {
        //-------------------------------------------------------------------------------------
        String id = SecurityContextHolder.getContext().getAuthentication().getName();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();


        model.addAttribute("id", id);
        model.addAttribute("role", role);
        //-------------------------------------------------------------------------------------
        return "view/admin/userList";
    }


}