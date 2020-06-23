/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import entities.Genre;

/**
 *
 * @author sofieamalielandt
 */
public class GenreDTO {

    private long id;
    private String name;

    public GenreDTO(Genre genre) {

        this.id = genre.getId();
        this.name = genre.getName();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    

}
