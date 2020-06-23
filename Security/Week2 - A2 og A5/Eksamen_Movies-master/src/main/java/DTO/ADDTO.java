/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import entities.Actor;
import entities.Director;

/**
 *
 * @author sofieamalielandt
 */
public class ADDTO {

    private long id;
    private String name;
    private String about;

    public ADDTO(Actor actor) {
        this.id = actor.getId();
        this.name = actor.getName();
        this.about = actor.getAbout();
    }

    public ADDTO(Director director) {
        this.id = director.getId();
        this.name = director.getName();
        this.about = director.getAbout();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAbout() {
        return about;
    }

}
