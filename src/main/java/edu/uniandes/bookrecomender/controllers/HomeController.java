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
    private int option = 1;
    private int based = 1;
    private int type = 1;
    private List<Books> books;

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
            books = booksFacade.getRecommendedBooks(items);
        }else{
            books = new LinkedList<>();
        }
        for (RecommendedItem item : recomendaciones) {
            System.out.println("item:" + item);
        }

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
    
    public SessionController getSessionController() {
        return sessionController;
    }

    public void setSessionController(SessionController sessionController) {
        this.sessionController = sessionController;
    }

}
