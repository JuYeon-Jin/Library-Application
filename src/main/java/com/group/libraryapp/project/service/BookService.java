package com.group.libraryapp.project.service;

import com.group.libraryapp.project.domain.book.Book;
import com.group.libraryapp.project.domain.book.BookRepository;
import com.group.libraryapp.project.dto.book.*;
import com.group.libraryapp.project.exception.custom.ImageSaveException;
import com.group.libraryapp.project.exception.custom.InvalidFormatException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class BookService {

    private final BookRepository book;

    public BookService(BookRepository bookRepository) {
        this.book = bookRepository;
    }


    /**
     * 도서 목록을 조회합니다.
     * 요청된 페이지 번호와 페이지 크기를 사용하여 도서 목록을 제한된 크기로 조회하며,
     * 검색 키워드가 주어진 경우 해당 키워드를 포함하는 도서명을 필터링합니다.
     *
     * @param keyword 검색 키워드 (도서명에 포함된 부분 문자열, 선택 사항)
     * @param page 요청된 페이지 번호 (Spring Data JPA 는 0부터 시작하는 페이지 인덱스를 사용)
     * @return 검색 키워드 및 페이징 조건에 부합하는 도서 목록을 {@link BookListDTO} 형식으로 반환합니다.
     */
    public List<BookListDTO> listAllBooks(String keyword, String page) {
        Pageable pageable = PageRequest.of(Integer.parseInt(page) - 1, 7);
        return book.findAllBooks(keyword, pageable);
    }



    /**
     * 도서의 총 개수를 계산하고
     * 이를 기반으로 요청된 페이지 번호 및 페이지 크기 정보를 반환합니다.
     *
     * @param keyword 검색 키워드 (선택 사항)
     * @param page 요청된 페이지 번호
     * @return 페이지네이션 정보를 포함하는 {@link PaginationDTO} 객체
     */
    public PaginationDTO pagination(String keyword, String page) {
        int totalCount = book.countAllBooks(keyword);
        return new PaginationDTO(Integer.parseInt(page), totalCount, 7);
    }



    /**
     * 검색 조건으로 필터링 된 도서 목록을 조회하여 {@link UserBookListDTO} 형식으로 반환합니다.
     * 검색 조건이 null 이라면 전체 도서 목록을 반환합니다.
     *
     * @param userId  대출한 도서를 조회하려는 사용자 ID
     * @param keyword 검색을 원하는 도서명 keyword (null 일 수 있음)
     * @return 사용자가 대출한 도서 목록
     */
    public List<UserBookListDTO> listAllBooksForUsers(String userId, String keyword) {
        return book.findAllWithLoanStatus(userId, keyword);
    }



    /**
     * 대출 횟수가 많은 상위 5개의 도서를 조회하여 {@link BestBookListDTO} 형식으로 반환합니다.
     * 반환되는 목록은 대출 횟수가 많은 순서대로 정렬되어 있으며, 각 도서에는 대출 순위가 포함됩니다.
     *
     * @return 대출 횟수가 많은 상위 5개 도서 목록.
     */
    public List<BestBookListDTO> listBest5Books() {
        return book.findTop5BooksByLoanCount();
    }



    /**
     * saveBookImg() 를 호출하여 도서 이미지 파일을 서버에 저장한 후,
     * saveBookData() 를 호출하여 도서 정보를 데이터베이스에 저장합니다.
     *
     * @param dto 도서 등록 정보를 포함한 {@link BookRegistrationDTO}
     * @param bookImage 업로드된 도서 이미지 파일
     */
    public void saveBook(BookRegistrationDTO dto, MultipartFile bookImage) {
        String imgPath = saveBookImg(bookImage);
        saveBookData(dto.getBookName(), dto.getAuthor(), dto.getPublisher(), imgPath, LocalDate.parse(dto.getPublishedAt()));
    }



    /**
     * 이미지를 서버에 저장하고(파일명은 UUID 로 저장), 저장된 파일의 경로를 반환합니다.
     * 이 이미지 경로는 DB 에 도서정보와 함께 저장되어 나중에 서버에서 이미지를 가져올때 사용됩니다.
     *
     * @param bookImage 업로드된 도서 이미지 파일
     * @return 서버에 저장된 이미지 파일의 경로
     * @throws ImageSaveException 이미지 저장 과정에서 파일 입출력 오류가 발생한 경우
     */
    public String saveBookImg(MultipartFile bookImage) {
        String absolutePath = System.getProperty("user.dir");
        String serverPath = "/src/main/resources/static";
        String uploadDir = "/img/book-img/";

        String originalFilename = bookImage.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String imgName = UUID.randomUUID() + extension;

        try {
            Path filePath = Paths.get(absolutePath, serverPath, uploadDir, imgName);
            File dir = new File(absolutePath + serverPath + uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            Files.write(filePath, bookImage.getBytes());

        } catch (IOException e) {
            throw new ImageSaveException("이미지 저장 중 오류가 발생했습니다: " + e);
        }

        return (uploadDir+imgName);
    }



    /**
     * 입력된 도서 정보의 유효성을 검증하고, 유효한 정보라면 {@link Book} 엔티티를 생성하여 저장합니다.
     *
     * @param bookName 도서명
     * @param author 저자명
     * @param publisher 출판사명
     * @param imgPath 저장된 도서 이미지 경로
     * @param publishedAt 출판일
     * @throws InvalidFormatException 입력값이 유효하지 않은 경우
     */
    public void saveBookData(String bookName, String author, String publisher, String imgPath, LocalDate publishedAt) {
        if (bookName == null || bookName.trim().isEmpty()
            || author == null || author.trim().isEmpty()
            || publisher == null || publisher.trim().isEmpty()
            || publishedAt == null) {

            throw new InvalidFormatException("도서 등록시 입력사항을 모두 작성해야합니다.");
        }

        if (bookName.length() > 255 || author.length() > 80 || publisher.length() > 50) {
            throw new InvalidFormatException("글자 수 제한을 준수해주세요.");
        }

        if (publishedAt.isAfter(LocalDate.now())) {
            throw new InvalidFormatException("출판일은 현재 날짜보다 늦게 설정할 수 없습니다.");
        }

        Book bookEntity = new Book(bookName, author, publisher, imgPath, publishedAt);
        book.save(bookEntity);
    }
}
// TODO [공부] MaxUploadSizeExceededException, Spring 의 기본적으로 설정된 파일 업로드 크기 제한의 존재(1MB?)