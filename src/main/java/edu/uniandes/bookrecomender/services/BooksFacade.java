/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uniandes.bookrecomender.services;

import edu.uniandes.bookrecomender.entities.Books;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author juan
 */
@Stateless
public class BooksFacade extends AbstractFacade<Books> {

    @PersistenceContext(unitName = "edu.uniandes_bookRecomender_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BooksFacade() {
        super(Books.class);
    }
    
    public List<Books> getRecommendedBooks(List<Long> ids){
        Query q = em.createNativeQuery("Select * from books where books_id in :books_ids", Books.class);
        q.setParameter("books_ids", ids);
        return q.getResultList();
    }
    
}
