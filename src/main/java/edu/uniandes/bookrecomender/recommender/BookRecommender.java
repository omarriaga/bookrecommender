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
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.jdbc.PostgreSQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.model.jdbc.ReloadFromJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

/**
 *
 * @author oscar
 */
@Named
@ApplicationScoped
public class BookRecommender {

    private DataModel model;
    private UserBasedRecommender recommender;
    private int option = -1;
    private int type = -1;

    public void init(int type) {
        try {
            System.out.println("creando datasource");
            InitialContext context = new InitialContext();
            DataSource ds = (DataSource) context.lookup("java/booksRecomender");
            //model = new PostgreSQLJDBCDataModel(ds, "book_ratings_prueba", "user_id", "books_id", "book_rating", null);
            String table = (type == 1) ? "ratings_training" : "ratings_test";
            PostgreSQLJDBCDataModel databasemodel = new PostgreSQLJDBCDataModel(ds, table, "user_id", "books_id", "book_rating", null);
            model = new ReloadFromJDBCDataModel(databasemodel);
        } catch (NamingException | TasteException ex) {
            Logger.getLogger(BookRecommender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createRecommender(int option, int type) {
        try {
            System.out.println("type "+type+ " local "+this.type);
            if (this.type != type) {
                System.out.println("*llego");
                
                init(type);
            }
            System.out.println("validacion : "+(option != this.option || this.type != type));
            if (option != this.option || this.type != type) {
                UserSimilarity similarity;
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
                UserNeighborhood neighborhood = new NearestNUserNeighborhood(20, similarity, model);
                //UserNeighborhood neighborhood = new NearestNUserNeighborhood(20, similarity, model);
                recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
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

    public void evaluate() {
        try {
            RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
            RecommenderBuilder builder = new MyRecommenderBuilder();
            double result = evaluator.evaluate(builder, null, model, 0.9, 1.0);
            System.out.println(result);
        } catch (TasteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    class MyRecommenderBuilder implements RecommenderBuilder {

        @Override
        public Recommender buildRecommender(DataModel dataModel) throws TasteException {
            UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
            //UserNeighborhood neighborhood = new NearestNUserNeighborhood(20, similarity, model);
            UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
            return new GenericUserBasedRecommender(dataModel, neighborhood, similarity);
        }

    }

}
