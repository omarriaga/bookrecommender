/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uniandes.bookrecomender.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author laurasofiarestrepolondono
 */
@Entity
@Table(name = "book_ratings")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BookRatings.findAll", query = "SELECT b FROM BookRatings b")
    , @NamedQuery(name = "BookRatings.findByUserId", query = "SELECT b FROM BookRatings b WHERE b.bookRatingsPK.userId = :userId")
    , @NamedQuery(name = "BookRatings.findByIsbn", query = "SELECT b FROM BookRatings b WHERE b.bookRatingsPK.isbn = :isbn")
    , @NamedQuery(name = "BookRatings.findByBookRating", query = "SELECT b FROM BookRatings b WHERE b.bookRating = :bookRating")})
public class BookRatings implements Serializable {

    

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BookRatingsPK bookRatingsPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "book_rating")
    private int bookRating;
    @Column(name = "books_id")
    private Integer booksId;
    

    public BookRatings() {
    }

    public BookRatings(BookRatingsPK bookRatingsPK) {
        this.bookRatingsPK = bookRatingsPK;
    }

    public BookRatings(BookRatingsPK bookRatingsPK, int bookRating) {
        this.bookRatingsPK = bookRatingsPK;
        this.bookRating = bookRating;
    }

    public BookRatings(int userId, String isbn) {
        this.bookRatingsPK = new BookRatingsPK(userId, isbn);
    }

    public BookRatingsPK getBookRatingsPK() {
        return bookRatingsPK;
    }

    public void setBookRatingsPK(BookRatingsPK bookRatingsPK) {
        this.bookRatingsPK = bookRatingsPK;
    }

    public int getBookRating() {
        return bookRating;
    }

    public void setBookRating(int bookRating) {
        this.bookRating = bookRating;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bookRatingsPK != null ? bookRatingsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BookRatings)) {
            return false;
        }
        BookRatings other = (BookRatings) object;
        if ((this.bookRatingsPK == null && other.bookRatingsPK != null) || (this.bookRatingsPK != null && !this.bookRatingsPK.equals(other.bookRatingsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.uniandes.bookrecomender.entities.BookRatings[ bookRatingsPK=" + bookRatingsPK + " ]";
    }

    public Integer getBooksId() {
        return booksId;
    }

    public void setBooksId(Integer booksId) {
        this.booksId = booksId;
    }

}
