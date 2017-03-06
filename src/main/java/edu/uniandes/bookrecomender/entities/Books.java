/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uniandes.bookrecomender.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "books")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Books.findAll", query = "SELECT b FROM Books b")
    , @NamedQuery(name = "Books.findByIsbn", query = "SELECT b FROM Books b WHERE b.isbn = :isbn")
    , @NamedQuery(name = "Books.findByBookTitle", query = "SELECT b FROM Books b WHERE b.bookTitle = :bookTitle")
    , @NamedQuery(name = "Books.findByBookAuthor", query = "SELECT b FROM Books b WHERE b.bookAuthor = :bookAuthor")
    , @NamedQuery(name = "Books.findByYearOfPublication", query = "SELECT b FROM Books b WHERE b.yearOfPublication = :yearOfPublication")
    , @NamedQuery(name = "Books.findByPublisher", query = "SELECT b FROM Books b WHERE b.publisher = :publisher")
    , @NamedQuery(name = "Books.findByImageUrlS", query = "SELECT b FROM Books b WHERE b.imageUrlS = :imageUrlS")
    , @NamedQuery(name = "Books.findByImageUrlM", query = "SELECT b FROM Books b WHERE b.imageUrlM = :imageUrlM")
    , @NamedQuery(name = "Books.findByImageUrlL", query = "SELECT b FROM Books b WHERE b.imageUrlL = :imageUrlL")})
public class Books implements Serializable {

    

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "isbn")
    private String isbn;
    @Column(name = "books_id")
    private Integer booksId;
    @Size(max = 255)
    @Column(name = "book_title")
    private String bookTitle;
    @Size(max = 255)
    @Column(name = "book_author")
    private String bookAuthor;
    @Column(name = "year_of_publication")
    private Integer yearOfPublication;
    @Size(max = 255)
    @Column(name = "publisher")
    private String publisher;
    @Size(max = 255)
    @Column(name = "image_url_s")
    private String imageUrlS;
    @Size(max = 255)
    @Column(name = "image_url_m")
    private String imageUrlM;
    @Size(max = 255)
    @Column(name = "image_url_l")
    private String imageUrlL;

    public Books() {
    }

    public Books(String isbn) {
        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public Integer getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(Integer yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getImageUrlS() {
        return imageUrlS;
    }

    public void setImageUrlS(String imageUrlS) {
        this.imageUrlS = imageUrlS;
    }

    public String getImageUrlM() {
        return imageUrlM;
    }

    public void setImageUrlM(String imageUrlM) {
        this.imageUrlM = imageUrlM;
    }

    public String getImageUrlL() {
        return imageUrlL;
    }

    public void setImageUrlL(String imageUrlL) {
        this.imageUrlL = imageUrlL;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (isbn != null ? isbn.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Books)) {
            return false;
        }
        Books other = (Books) object;
        if ((this.isbn == null && other.isbn != null) || (this.isbn != null && !this.isbn.equals(other.isbn))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.uniandes.bookrecomender.entities.Books[ isbn=" + isbn + " ]";
    }

    public Integer getBooksId() {
        return booksId;
    }

    public void setBooksId(Integer booksId) {
        this.booksId = booksId;
    }
    
}
