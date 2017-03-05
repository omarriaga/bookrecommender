/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uniandes.bookrecomender.recommender;

import java.io.File;
import java.io.IOException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;

/**
 *
 * @author oscar
 */
public class BookRecommender {
    
    public void init(){
        try {
            DataModel model = new FileDataModel(new File(""));
            
        } catch (IOException ex) {
            //Logger.getLogger(BookRecommender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
           
    
}
