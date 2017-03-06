/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uniandes.bookrecomender.services;

import edu.uniandes.bookrecomender.entities.BookRatings;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author juan
 */
@Stateless
public class BookRatingsFacade extends AbstractFacade<BookRatings> {

    @PersistenceContext(unitName = "edu.uniandes_bookRecomender_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BookRatingsFacade() {
        super(BookRatings.class);
    }
    
    
    
}
