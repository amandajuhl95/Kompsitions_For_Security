/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import entities.Movie;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author sofieamalielandt
 */
public class MovieDTO {
    
    private long id;
    private String title;
    private int year;
    private int votes;
    private Set<ADDTO> actors = new HashSet();
    private Set<ADDTO> directors = new HashSet();
    private Set<GenreDTO> genres = new HashSet();

    public MovieDTO(Movie movie) {

        this.id = movie.getId();
        this.title = movie.getTitle();
        this.year = movie.getYear();
        this.votes = movie.getVotes();
        
        movie.getActors().forEach((actor) -> {
            this.actors.add(new ADDTO(actor));
        });
        
        movie.getDirectors().forEach((director) -> {
            this.directors.add(new ADDTO(director));
        });
        
        movie.getGenres().forEach((genre) -> {
            this.genres.add(new GenreDTO(genre));
        });
    }
    
    public MovieDTO(String title, String year, String votes){
        
        this.title = title;
        this.year = Integer.parseInt(year);
        this.votes = Integer.parseInt(votes);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public int getVotes() {
        return votes;
    }

    public Set<ADDTO> getActors() {
        return actors;
    }

    public Set<ADDTO> getDirectors() {
        return directors;
    }

    public Set<GenreDTO> getGenres() {
        return genres;
    }
    
}
