/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uniandes.bookrecomender.controllers;

import edu.uniandes.bookrecomender.recommender.BookItemRecommender;
import edu.uniandes.bookrecomender.recommender.BookRecommender;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author juan
 */
@ManagedBean
@Named(value = "sessionController")
@ApplicationScoped
public class SessionController {

    @Inject
    private BookRecommender bookRecommender;
    @Inject
    private BookItemRecommender bookItemRecommender;

    /**
     * Creates a new instance of SessionController
     */
    public SessionController() {
    }

    public List generateRecommender(long user, int option, int based, int type) {
        if (based == 1) {
            bookRecommender.createRecommender(option, type);
            return bookRecommender.recommend(user);
        }else{
            bookItemRecommender.createRecommender(option, type);
            return bookItemRecommender.recommend(user);
        }
    }
    
    public float generatePrediction(long user, long item, int based){
        if(based == 1){
            System.out.println("Entro a user");
            return bookRecommender.predict(user, item);
        }else{
            return bookItemRecommender.predict(user, item);
        }
    }

    public BookRecommender getBookRecommender() {
        return bookRecommender;
    }

    public void setBookRecommender(BookRecommender bookRecommender) {
        this.bookRecommender = bookRecommender;
    }

}
