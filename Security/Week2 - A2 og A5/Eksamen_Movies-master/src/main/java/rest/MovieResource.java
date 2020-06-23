/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import DTO.ADDTO;
import DTO.GenreDTO;
import DTO.MovieDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import errorhandling.NotFoundException;
import facades.MovieFacade;
import java.time.LocalDate;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import utils.EMF_Creator;

/**
 * REST Web Service
 *
 * @author sofieamalielandt
 */
@Path("movie")
public class MovieResource {

    private static final EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);
    private static final MovieFacade facade = MovieFacade.getMovieFacade(emf);
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Context
    private UriInfo context;

    @GET
    @Path("/populate")
    @Produces({MediaType.APPLICATION_JSON})
    public String populateDB() {

        facade.populateDB();
        return "{\"msg\":\"The database is now populated\"}";
    }

    @POST
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public MovieDTO addMovie(String movieAsJSON) {

        LocalDate today = LocalDate.now();
        try {
            MovieDTO movie = gson.fromJson(movieAsJSON, MovieDTO.class);

            if (movie.getTitle() == null || movie.getTitle().isEmpty() || movie.getTitle().length() < 2) {

                throw new WebApplicationException("Movie title must be 2 characters", 400);
            }

            if (movie.getYear() < 1940 || movie.getYear() > today.getYear()) {

                throw new WebApplicationException("Release year must be between 1940 and " + today.getYear(), 400);
            }
            
            if (movie.getVotes() < 0) {

                throw new WebApplicationException("Votes must be 0 or more", 400);
            }

            return facade.addMovie(movie);

        } catch ( NotFoundException | WebApplicationException e) {

            throw new WebApplicationException(e.getMessage(), 400);
        }
    }
    
    @PUT
    @Path("/{id}")
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public MovieDTO editMovie(@PathParam("id") long id, String movieAsJSON) {

        LocalDate today = LocalDate.now();
        
        try {
            MovieDTO movie = gson.fromJson(movieAsJSON, MovieDTO.class);
            
            if (id == 0) {

                throw new WebApplicationException("Id not passed correctly", 400);
            }

            if (movie.getTitle() == null || movie.getTitle().isEmpty() || movie.getTitle().length() < 2) {

                throw new WebApplicationException("Movie title must be 2 characters", 400);
            }

            if (movie.getYear() <  1940 || movie.getYear() > today.getYear()) {

                throw new WebApplicationException("Release year must be between 1940 and " + today.getYear(), 400);
            }
            
            if (movie.getVotes() < 0) {

                throw new WebApplicationException("Votes must be 0 or more", 400);
            }
            
            movie.setId(id);
            return facade.editMovie(movie);

        } catch ( NotFoundException | WebApplicationException e) {

            throw new WebApplicationException(e.getMessage(), 400);
        }
    }
    
    @DELETE
    @Path("/{id}")
    @RolesAllowed("admin")
    @Produces({MediaType.APPLICATION_JSON})
    public String deleteMovie(@PathParam("id") long id) {
        try {
            if (id == 0) {

                throw new WebApplicationException("Id not passed correctly", 400);
            }

            facade.deleteMovie(id);

            return "{\"status\": \"The movie has been deleted\"}";

        } catch (NotFoundException | WebApplicationException e) {

            throw new WebApplicationException(e.getMessage(), 400);
        }
    }
    
    @PUT
    @Path("actor/{movie_id}/{actor_id}")
    @RolesAllowed("admin")
    @Produces({MediaType.APPLICATION_JSON})
    public String addActorToMovie(@PathParam("movie_id") long movie_id, @PathParam("actor_id") long actor_id ) {
        try {
            if (movie_id == 0 || actor_id == 0) {

                throw new WebApplicationException("Id not passed correctly", 400);
            }

            facade.addActorToMovie(movie_id, actor_id);

            return "{\"status\": \"The actor has been added\"}";

        } catch (NotFoundException | WebApplicationException e) {

            throw new WebApplicationException(e.getMessage(), 400);
        }

    }
    
    @DELETE
    @Path("actor/{movie_id}/{actor_id}")
    @RolesAllowed("admin")
    @Produces({MediaType.APPLICATION_JSON})
    public String removeActorFromMovie(@PathParam("movie_id") long movie_id, @PathParam("actor_id") long actor_id ) {
        try {
            if (movie_id == 0 || actor_id == 0) {

                throw new WebApplicationException("Id not passed correctly", 400);
            }

            facade.removeActorFromMovie(movie_id, actor_id);

            return "{\"status\": \"The actor has been removed\"}";

        } catch (NotFoundException | WebApplicationException e) {

            throw new WebApplicationException(e.getMessage(), 400);
        }

    }
    
    @PUT
    @Path("director/{movie_id}/{director_id}")
    @RolesAllowed("admin")
    @Produces({MediaType.APPLICATION_JSON})
    public String addDirectorToMovie(@PathParam("movie_id") long movie_id, @PathParam("director_id") long director_id ) {
        try {
            if (movie_id == 0 || director_id == 0) {

                throw new WebApplicationException("Id not passed correctly", 400);
            }

            facade.addDirectorToMovie(movie_id, director_id);

            return "{\"status\": \"The director has been added\"}";

        } catch (NotFoundException | WebApplicationException e) {

            throw new WebApplicationException(e.getMessage(), 400);
        }

    }
    
    @DELETE
    @Path("director/{movie_id}/{director_id}")
    @RolesAllowed("admin")
    @Produces({MediaType.APPLICATION_JSON})
    public String removeDirectorFromMovie(@PathParam("movie_id") long movie_id, @PathParam("director_id") long director_id ) {
        try {
            if (movie_id == 0 || director_id == 0) {

                throw new WebApplicationException("Id not passed correctly", 400);
            }

            facade.removeDirectorFromMovie(movie_id, director_id);

            return "{\"status\": \"The director has been removed\"}";

        } catch (NotFoundException | WebApplicationException e) {

            throw new WebApplicationException(e.getMessage(), 400);
        }
    }
    
     @PUT
    @Path("genre/{movie_id}/{genre_id}")
    @RolesAllowed("admin")
    @Produces({MediaType.APPLICATION_JSON})
    public String addGenreToMovie(@PathParam("movie_id") long movie_id, @PathParam("genre_id") long genre_id ) {
        try {
            if (movie_id == 0 || genre_id == 0) {

                throw new WebApplicationException("Id not passed correctly", 400);
            }

            facade.addGenreToMovie(movie_id, genre_id);

            return "{\"status\": \"The genre has been added\"}";

        } catch (NotFoundException | WebApplicationException e) {

            throw new WebApplicationException(e.getMessage(), 400);
        }

    }
    
    @DELETE
    @Path("genre/{movie_id}/{genre_id}")
    @RolesAllowed("admin")
    @Produces({MediaType.APPLICATION_JSON})
    public String removeGenreFromMovie(@PathParam("movie_id") long movie_id, @PathParam("genre_id") long genre_id ) {
        try {
            if (movie_id == 0 || genre_id == 0) {

                throw new WebApplicationException("Id not passed correctly", 400);
            }

            facade.removeGenreFromMovie(movie_id, genre_id);

            return "{\"status\": \"The genre has been removed\"}";

        } catch (NotFoundException | WebApplicationException e) {

            throw new WebApplicationException(e.getMessage(), 400);
        }
    }
    
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<MovieDTO> getMovie(@PathParam("id") long id) {

        return facade.getMovie(id);
    }

    @GET
    @Path("/title/{title}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<MovieDTO> getMovie(@PathParam("title") String title) {

        return facade.getMovieByTitle(title);
    }

    @GET
    @Path("/all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<MovieDTO> getAllMovies() {

        return facade.getAllMovies();
    }

    @GET
    @Path("/actor/{actor}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<MovieDTO> getMoviesByActor(@PathParam("actor") String actor) {

        return facade.getPersonsByActor(actor);
    }

    @GET
    @Path("/director/{director}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<MovieDTO> getMoviesByDirector(@PathParam("director") String director) {

        return facade.getPersonsByDirector(director);
    }
    
    @GET
    @Path("/genre/{genre}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<MovieDTO> getMoviesByGenre(@PathParam("genre") String genre) {

        return facade.getPersonsByGenre(genre);
    }
    
    @GET
    @Path("actor/all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<ADDTO> getAllActors() {

        return facade.getAllActors();
    }
    
    @GET
    @Path("director/all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<ADDTO> getAllDirectors() {

        return facade.getAllDirectors();
    }
    
    @GET
    @Path("genre/all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<GenreDTO> getAllGenres() {

        return facade.getAllGenres();
    }

}
