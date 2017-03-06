/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uniandes.bookrecomender.recommender;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.jdbc.PostgreSQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.model.jdbc.ReloadFromJDBCDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

/**
 *
 * @author juan
 */
@Named
@ApplicationScoped
public class BookItemRecommender {

    private DataModel model;
    private ItemBasedRecommender recommender;
    private int option = -1;
    private int type = -1;

    public void init(int type) {
        try {
            System.out.println("creando datasource");
            InitialContext context = new InitialContext();
            DataSource ds = (DataSource) context.lookup("java/booksRecomender");
            String table = (type == 1) ? "ratings_training" : "ratings_test";
            PostgreSQLJDBCDataModel databasemodel = new PostgreSQLJDBCDataModel(ds, table, "user_id", "books_id", "book_rating", null);
            model = new ReloadFromJDBCDataModel(databasemodel);
        } catch (NamingException ex) {
            Logger.getLogger(BookRecommender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TasteException ex) {
            Logger.getLogger(BookItemRecommender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createRecommender(int option, int type) {
        try {
            if (this.type != type) {
                
                init(type);
            }
            if (option != this.option || this.type == type) {
                ItemSimilarity similarity;
                switch (option) {
                    case 1:
                        similarity = new PearsonCorrelationSimilarity(model);
                        break;
                    case 2:
                        similarity = new UncenteredCosineSimilarity(model);
                        break;
                    case 3:
                        similarity = new TanimotoCoefficientSimilarity(model);
                        break;
                    default:
                        similarity = new PearsonCorrelationSimilarity(model);
                        break;
                }
                recommender = new GenericItemBasedRecommender(model, similarity);
                this.option = option;
                this.type = type;
            }
        } catch (TasteException ex) {
            Logger.getLogger(BookRecommender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List recommend(long user) {
        try {
            System.out.println("recomendar para user " + user);
            return recommender.recommend(user, 10);
        } catch (TasteException ex) {
            System.out.println("Boom!!! recomendacion");
            Logger.getLogger(BookRecommender.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public float predict(long user, long item){
        try {
            return recommender.estimatePreference(user, item);
        } catch (TasteException ex) {
            Logger.getLogger(BookRecommender.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

}
