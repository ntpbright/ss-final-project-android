package com.example.ntpchong.ss_lab_ebook.Data;

import com.example.ntpchong.ss_lab_ebook.BookShop.Book;

import java.util.List;
import java.util.Observable;


public abstract class Repository extends Observable {
    public abstract List<Book> getAllBooks();
    public abstract List<Book> getBooks(String bookName);
}
