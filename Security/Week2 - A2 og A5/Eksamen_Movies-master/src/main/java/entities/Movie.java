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
import javax.persistence.CascadeType;
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
@Table(name = "MOVIES")
@NamedQuery(name = "Movie.deleteAllRows", query = "DELETE from Movie")
public class Movie implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String title;
    private int year;
    private int votes;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Genre> genres  = new HashSet();
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Actor> actors = new HashSet();
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Director> directors = new HashSet();

    public Movie() {
    }

    public Movie(String title, int year, int votes) {
        this.title = title;
        this.year = year;
        this.votes = votes;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void addGenre(Genre genre) {
        this.genres.add(genre);
        genre.addMovie(this);
    }

    public void removeGenre(Genre genre) {
        this.genres.remove(genre);
        genre.removeMovie(this);
    }

    public Set<Actor> getActors() {
        return actors;
    }

    public void addActor(Actor actor) {
        this.actors.add(actor);
        actor.addMovie(this);
    }

    public void removeActor(Actor actor) {
        this.actors.remove(actor);
        actor.removeMovie(this);
    }

    public Set<Director> getDirectors() {
        return directors;
    }

    public void addDirector(Director director) {
        this.directors.add(director);
        director.addMovie(this);
    }

    public void removeDirector(Director director) {
        this.directors.remove(director);
        director.removeMovie(this);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.title);
        hash = 83 * hash + this.year;
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
        final Movie other = (Movie) obj;
        if (this.year != other.year) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        return true;
    }

}
