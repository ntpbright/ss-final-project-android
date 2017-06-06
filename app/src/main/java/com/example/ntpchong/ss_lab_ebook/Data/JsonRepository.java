package com.example.ntpchong.ss_lab_ebook.Data;

import android.os.AsyncTask;

import com.example.ntpchong.ss_lab_ebook.BookShop.Book;
import com.example.ntpchong.ss_lab_ebook.BookShop.BookJSONDecoder;
import com.example.ntpchong.ss_lab_ebook.BookShop.Book;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class JsonRepository extends Repository {
    private List<Book> books;

    private static JsonRepository instance = null;

    public static JsonRepository getInstance() {
        if(instance == null) {
            instance = new JsonRepository();
        }
        return instance;
    }

    @Override
    public List<Book> getBooks(String text) {
        List<Book> result = new ArrayList<Book>();
        for(Book b:books) {
            if (b.toString().contains(text)) result.add(b);
        }
        return result;
    }
    @Override
    public List<Book> getAllBooks() {
        return books;
    }

    public void fetchAllBooks() {
        BookFetcherTask task = new BookFetcherTask();
        task.execute();
    }

    public Book getBookById(int position){
        return books.get(position);
    }

    public JsonRepository() {
        books = new ArrayList<Book>();
    }

    public class BookFetcherTask extends AsyncTask<Void,Void,ArrayList<Book>> {
        @Override
        protected ArrayList<Book> doInBackground(Void... params) {
            String bookListJsonStr = loadJSON();
            if(bookListJsonStr != null) {
                return BookJSONDecoder.createListFromJSONStr(bookListJsonStr);
            } else {
                return null;
            }
        }

        private String loadJSON() {
            URL booksURL = null;
            try {
                booksURL = new URL("https://theory.cpe.ku.ac.th/~jittat/courses/sw-spec/ebooks/books.json");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return (new UrlFetcher(booksURL)).fetch();
        }

        @Override
        protected void onPostExecute(ArrayList<Book> results) {
            if(results != null) {
                books.clear();
                books.addAll(results);
                setChanged();
                notifyObservers(books);
            }
        }
    }
}
