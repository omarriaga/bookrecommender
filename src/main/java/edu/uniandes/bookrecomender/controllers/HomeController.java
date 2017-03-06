/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uniandes.bookrecomender.controllers;

import edu.uniandes.bookrecomender.entities.Books;
import edu.uniandes.bookrecomender.services.BooksFacade;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

/**
 *
 * @author juan
 */
@ManagedBean
@Named(value = "homeController")
@ViewScoped
public class HomeController implements Serializable {

    private long user_id;
    private long item_id;
    private Books book_selected;
    private float estimatedPreference;
    private int option = 1;
    private int based = 1;
    private int type = 1;
    private List<Books> books_recommended;
    private List<Books> books = new LinkedList<>();

    @ManagedProperty(value = "#{sessionController}")
    private SessionController sessionController;
    @EJB
    private BooksFacade booksFacade;

    /**
     * Creates a new instance of HomeController
     */
    public HomeController() {
    }


    public void sendRecommend() {
        System.out.println("enviando recomendaciones");
        List<RecommendedItem> recomendaciones = sessionController.generateRecommender(user_id, option, based, type);
        System.out.println("pintando recomendaciones " + recomendaciones.size());
        List<Long> items = recomendaciones.stream().map(s -> s.getItemID()).collect(Collectors.toList());
        if (items.size() > 0) {
            books_recommended = booksFacade.getRecommendedBooks(items);
        }else{
            books_recommended = new LinkedList<>();
        }
        for (RecommendedItem item : recomendaciones) {
            System.out.println("item:" + item);
        }
    }
    
    public List<Books> completeBooks(String query){
        return booksFacade.getBooksBytitle(query);
    }
    
    public void sendPredict(){
        estimatedPreference = sessionController.generatePrediction(user_id, book_selected.getBooksId(), based);
    }

    public long getItem_id() {
        return item_id;
    }

    public void setItem_id(long item_id) {
        this.item_id = item_id;
    }
    
    public int getOption() {
        return option;
    }

    public void setOption(int option) {
        this.option = option;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public List<Books> getBooks_recommended() {
        return books_recommended;
    }

    public void setBooks_recommended(List<Books> books_recommended) {
        this.books_recommended = books_recommended;
    }

    public List<Books> getBooks() {
        return books;
    }

    public void setBooks(List<Books> books) {
        this.books = books;
    }
    
    public int getBased() {
        return based;
    }

    public void setBased(int based) {
        this.based = based;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Books getBook_selected() {
        return book_selected;
    }

    public void setBook_selected(Books book_selected) {
        this.book_selected = book_selected;
    }

    public float getEstimatedPreference() {
        return estimatedPreference;
    }

    public void setEstimatedPreference(float estimatedPreference) {
        this.estimatedPreference = estimatedPreference;
    }
    
    public SessionController getSessionController() {
        return sessionController;
    }

    public void setSessionController(SessionController sessionController) {
        this.sessionController = sessionController;
    }

}
