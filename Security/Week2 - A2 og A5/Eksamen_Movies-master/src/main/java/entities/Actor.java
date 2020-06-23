/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author sofieamalielandt
 */
@Entity
@Table(name = "ACTORS")
@NamedQuery(name = "Actor.deleteAllRows", query = "DELETE from Actor")
public class Actor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private Long id;
    private String name;
    private String about;
    @ManyToMany(mappedBy = "actors")
    private Set<Movie> movies = new HashSet();

    public Actor() {
    }

    public Actor(String name, String about) {
        this.name = name;
        this.about = about;
    }
    
    public Long getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void addMovie(Movie movie) {
        this.movies.add(movie);
    }
    
    public void removeMovie(Movie movie) {
        this.movies.remove(movie);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.name);
        hash = 47 * hash + Objects.hashCode(this.about);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Actor other = (Actor) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.about, other.about)) {
            return false;
        }
        return true;
    }
  
}
