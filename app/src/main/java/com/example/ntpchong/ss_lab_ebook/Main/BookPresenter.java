package com.example.ntpchong.ss_lab_ebook.Main;

import com.example.ntpchong.ss_lab_ebook.BookShop.Book;
import com.example.ntpchong.ss_lab_ebook.BookShop.User;
import com.example.ntpchong.ss_lab_ebook.Data.JsonRepository;

import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class BookPresenter implements Observer{
    JsonRepository repo;
    User user;
    private BookView view;

    public BookPresenter(BookView view){
        this.view = view;
        this.user = new User();
    }

    public void listAllBook(){
        repo = new JsonRepository();
        repo.addObserver(this);
        repo.fetchAllBooks();
    }

    public void search(String text,String filter) {
        List<Book> books = repo.getBooks(text);
        books = sortBook(books, filter);
        view.updateAll(books);
    }

    public List<Book> sortBook(List<Book> books,String filter) {
        Book temp;
        if(filter.equals("Searching by Title")){
            for (int i = 0;i<books.size()-1;i++) {
                for (int j = i+1;j<books.size();j++) {
                    if (books.get(i).getTitle().charAt(0) > books.get(j).getTitle().charAt(0)) {
                        temp = books.get(j);
                        books.set(j, books.get(i));
                        books.set(i, temp);
                    }
                }
            }
        } else {
            for (int i = 0;i<books.size()-1;i++) {
                for (int j = i+1;j<books.size();j++) {
                    if (books.get(i).getPub_year() > books.get(j).getPub_year()) {
                        temp = books.get(j);
                        books.set(j,books.get(i));
                        books.set(i,temp);
                    }
                }
            }
        }
        return books;
    }

    @Override
    public void update(Observable o, Object arg) {
        List<Book> books = repo.getAllBooks();
        view.updateAll(books);

    }

    public void addToCart(int position) {
        user.addToCart(repo.getBookById(position));
    }
    public void setCash(double cash){
        user.cash = cash;
    }




}

