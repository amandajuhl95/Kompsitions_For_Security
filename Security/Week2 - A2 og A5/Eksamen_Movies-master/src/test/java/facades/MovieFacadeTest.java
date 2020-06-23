/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import DTO.MovieDTO;
import entities.Actor;
import entities.Director;
import entities.Genre;
import entities.Movie;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import utils.EMF_Creator;

/**
 *
 * @author sofieamalielandt
 */
public class MovieFacadeTest {

    private static EntityManagerFactory emf;
    private static MovieFacade facade;

    private Movie m1;
    private Movie m2;
    private Movie m3;

    private Genre g1;
    private Genre g2;
    private Genre g3;

    private Actor a1;
    private Actor a2;
    private Actor a3;
    private Actor a4;

    private Director d1;
    private Director d2;
    private Director d3;

    public MovieFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.TEST, EMF_Creator.Strategy.DROP_AND_CREATE);
        facade = MovieFacade.getMovieFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {

        EntityManager em = emf.createEntityManager();

        a1 = new Actor("Johnny Depp", "John Christopher Depp II (born June 9, 1963) is an American actor, producer, and musician. He has been nominated for 10 Golden Globe Awards,");
        a2 = new Actor("Leonardo DiCaprio", "Leonardo Wilhelm DiCaprio (born November 11, 1974) is an American actor, producer, and environmentalist.");
        a3 = new Actor("Will Smith", "Willard Carroll Smith Jr. (born September 25, 1968) is an American actor and rapper. In April 2007, Newsweek called him \"the most powerful actor in Hollywood\".");
        a4 = new Actor("Margot Robbie", "Margot Elise Robbie (born 2 July 1990) is an Australian actress and film producer. She has received nominations for two Academy Awards and five BAFTA Awards.");

        d1 = new Director("Steven Spielberg", "Steven Allan Spielberg (born December 18, 1946) is an American filmmaker. He is considered one of the founding pioneers of the New Hollywood era and one of the most popular directors and producers in film history.");
        d2 = new Director("Quentin Tarantino", "Quentin Jerome Tarantino (born March 27, 1963) is an American filmmaker, actor, film programmer, and cinema owner. His films are characterized by nonlinear storylines, satirical subject matter, aestheticization of violence.");
        d3 = new Director("Tim Burton", "Timothy Walter Burton (born August 25, 1958) is an American director, producer, artist, writer, and animator. He is known for his dark, gothic, and eccentric horror and fantasy films such as Beetlejuice (1988) and Edward Scissorhands (1990).");

        g1 = new Genre("Comedy");
        g2 = new Genre("Drama");
        g3 = new Genre("Thriller");

        m1 = new Movie("Edward Scissorhands", 1990, 4);
        m2 = new Movie("Once Upon a Time in Hollywood", 2019, 3);
        m3 = new Movie("Men in Black", 1997, 5);

        m1.addActor(a1);
        m1.addDirector(d3);
        m1.addDirector(d1);
        m1.addGenre(g1);
        m1.addGenre(g2);

        m2.addActor(a2);
        m2.addActor(a4);
        m2.addDirector(d2);
        m2.addGenre(g2);
        m2.addGenre(g3);

        m3.addActor(a3);
        m3.addDirector(d1);
        m3.addGenre(g1);
        m3.addGenre(g2);

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Movie.deleteAllRows").executeUpdate();
            em.createNamedQuery("Genre.deleteAllRows").executeUpdate();
            em.createNamedQuery("Actor.deleteAllRows").executeUpdate();
            em.createNamedQuery("Director.deleteAllRows").executeUpdate();

            em.persist(m1);
            em.persist(m2);
            em.persist(m3);

            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getMovieFacade method, of class MovieFacade.
     */
    @Test
    public void testGetMovieFacade() {
        System.out.println("getMovieFacade");
        MovieFacade expResult = facade;
        MovieFacade result = MovieFacade.getMovieFacade(emf);
        assertEquals(expResult, result);
    }

    /**
     * Test of getMovie method, of class MovieFacade.
     */
    @Test
    public void testGetMovie() {
        System.out.println("getMovie");

        List<MovieDTO> result = facade.getMovie(m1.getId());
        assertEquals(1, result.size());
        assertEquals(m1.getTitle(), result.get(0).getTitle());
        assertEquals(m1.getYear(), result.get(0).getYear());
        assertEquals(1, result.get(0).getActors().size());
    }

    /**
     * Test of getMovieByTitle method, of class MovieFacade.
     */
    @Test
    public void testGetMovieByTitle() {
        System.out.println("getMovieByTitle");

        List<MovieDTO> result = facade.getMovie(m2.getId());
        assertEquals(1, result.size());
        assertEquals(m2.getTitle(), result.get(0).getTitle());
        assertEquals(m2.getYear(), result.get(0).getYear());
        assertEquals(2, result.get(0).getActors().size());
    }

    /**
     * Test of getAllMovies method, of class MovieFacade.
     */
    @Test
    public void testGetAllMovies() {
        System.out.println("getAllMovies");
        List<MovieDTO> movies = facade.getAllMovies();
        assertEquals(3, movies.size());
    }

    /**
     * Test of getPersonsByActor method, of class MovieFacade.
     */
    @Test
    public void testGetPersonsByActor() {
        System.out.println("getPersonsByActor");

        List<MovieDTO> movies = facade.getPersonsByActor(a1.getName());
        assertEquals(1, movies.size());
    }

    /**
     * Test of getPersonsByDirector method, of class MovieFacade.
     */
    @Test
    public void testGetPersonsByDirector() {
        System.out.println("getPersonsByDirector");

        List<MovieDTO> movies = facade.getPersonsByDirector(d1.getName());
        assertEquals(2, movies.size());
    }

    /**
     * Test of getPersonsByGenre method, of class MovieFacade.
     */
    @Test
    public void testGetPersonsByGenre() {
        System.out.println("getPersonsByGenre");

        List<MovieDTO> movies = facade.getPersonsByGenre(g2.getName());
        assertEquals(3, movies.size());
    }

    /**
     * Test of addMovie method, of class MovieFacade.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testAddMovie() throws Exception {
        System.out.println("addMovie");

        int before = facade.getAllMovies().size();
        MovieDTO movie = new MovieDTO("Maleficent", "2014", "6");
        facade.addMovie(movie);
        int after = facade.getAllMovies().size();

        assertTrue(before < after);
    }

    /**
     * Test of editMovie method, of class MovieFacade.
     */
    @Test
    public void testEditMovie() throws Exception {
        System.out.println("editMovie");

        m1.setTitle("Beetlejuice");
        m1.setYear(1988);
        m1.setVotes(2);

        MovieDTO movie = new MovieDTO(m1);

        facade.editMovie(movie);
        movie = facade.getMovie(m1.getId()).get(0);

        assertEquals("Beetlejuice", movie.getTitle());
        assertEquals(1988, movie.getYear());
        assertEquals(2, movie.getVotes());
    }

    /**
     * Test of deleteMovie method, of class MovieFacade.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testDeleteMovie() throws Exception {
        System.out.println("deleteMovie");

        int before = facade.getAllMovies().size();
        facade.deleteMovie(m1.getId());
        int after = facade.getAllMovies().size();

        assertTrue(before > after);

    }

    /**
     * Test of addActorToMovie method, of class MovieFacade.
     */
    @Test
    public void testAddActorToMovie() throws Exception {
        System.out.println("addActorToMovie");

        int before = m2.getActors().size();
        facade.addActorToMovie(m2.getId(), a1.getId());
        MovieDTO movie = facade.getMovie(m2.getId()).get(0);
        int after = movie.getActors().size();

        assertTrue(before < after);
    }

    /**
     * Test of removeActorFromMovie method, of class MovieFacade.
     */
    @Test
    public void testRemoveActorFromMovie() throws Exception {
        System.out.println("removeActorFromMovie");

        int before = m2.getActors().size();
        facade.removeActorFromMovie(m2.getId(), a2.getId());
        MovieDTO movie = facade.getMovie(m2.getId()).get(0);
        int after = movie.getActors().size();

        assertTrue(before > after);
    }

    /**
     * Test of addDirectorToMovie method, of class MovieFacade.
     */
    @Test
    public void testAddDirectorToMovie() throws Exception {
        System.out.println("addDirectorToMovie");

        int before = m2.getDirectors().size();
        facade.addDirectorToMovie(m2.getId(), d1.getId());
        MovieDTO movie = facade.getMovie(m2.getId()).get(0);
        int after = movie.getDirectors().size();

        assertTrue(before < after);
    }

    /**
     * Test of removeDirectorFromMovie method, of class MovieFacade.
     */
    @Test
    public void testRemoveDirectorFromMovie() throws Exception {
        System.out.println("removeDirectorFromMovie");

        int before = m2.getDirectors().size();
        facade.removeDirectorFromMovie(m2.getId(), d2.getId());
        MovieDTO movie = facade.getMovie(m2.getId()).get(0);
        int after = movie.getDirectors().size();

        assertTrue(before > after);
    }

    /**
     * Test of addGenreToMovie method, of class MovieFacade.
     */
    @Test
    public void testAddGenreToMovie() throws Exception {
        System.out.println("addGenreToMovie");

        int before = m2.getGenres().size();
        facade.addGenreToMovie(m2.getId(), g1.getId());
        MovieDTO movie = facade.getMovie(m2.getId()).get(0);
        int after = movie.getGenres().size();

        assertTrue(before < after);
    }

    /**
     * Test of removeGenreFromMovie method, of class MovieFacade.
     */
    @Test
    public void testRemoveGenreFromMovie() throws Exception {
        System.out.println("removeGenreFromMovie");

        int before = m2.getGenres().size();
        facade.removeGenreFromMovie(m2.getId(), g2.getId());
        MovieDTO movie = facade.getMovie(m2.getId()).get(0);
        int after = movie.getGenres().size();

        assertTrue(before > after);
    }

}
