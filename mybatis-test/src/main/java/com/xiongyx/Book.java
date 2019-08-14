package com.xiongyx;

/**
 * @author xiongyx
 * on 2019/7/31.
 */
public class Book {

    private String id;
    private String bookName;
    private String authorId;
    private Integer price;
    private String libraryId;
    private Library library;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(String libraryId) {
        this.libraryId = libraryId;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", bookName='" + bookName + '\'' +
                ", authorId='" + authorId + '\'' +
                ", price=" + price +
                ", libraryId='" + libraryId + '\'' +
                ", library=" + library +
                '}';
    }
}
