package com.infy.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.infy.dto.MovieDTO;
import com.infy.entity.Movie;

@Repository(value="movieRepository")
public class MovieRepositoryImpl implements MovieRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public MovieDTO getMovieByName(String movieName) {
		String query="select m from Movie m where m.movieName = ?1";
		Query q=entityManager.createQuery(query);
		q.setParameter(1,movieName);
		List<Movie> res=q.getResultList();
		Movie m=res.get(0);
		MovieDTO m1=new MovieDTO();
		m1.setDirectorName(m.getDirectorName());
		m1.setImdbRating(m.getImdbRating());
		m1.setMovieId(m.getMovieId());
		m1.setMovieName(m.getMovieName());
		m1.setYear(m.getYear());
		return m1;
	}

	@Override
	public List<MovieDTO> getMoviesByImdbRating(Double fromRating, Double toRating) {
		String query = "select m from Movie m where m.imdbRating between :fromRating and :toRating";
		Query q=entityManager.createQuery(query);
		q.setParameter("fromRating", fromRating.floatValue());
		q.setParameter("toRating", toRating.floatValue());
		List<Movie> list=q.getResultList();
		List<MovieDTO> result=new ArrayList<>();
		
		for(Movie m:list) {
			MovieDTO md=new MovieDTO();
			md.setDirectorName(m.getDirectorName());
			md.setImdbRating(m.getImdbRating());
			md.setMovieId(m.getMovieId());
			md.setMovieName(m.getMovieName());
			md.setYear(m.getYear());
			result.add(md);
		}
		return result;
	}

	@Override
	public List<Object[]> getMoviesNameAndYear(String directorName) {
		String query="select m.movieName,m.year from Movie m where m.directorName = ?1";
		Query q=entityManager.createQuery(query);
		q.setParameter(1, directorName);
		List<Object[]> i=q.getResultList();
		return i;
	}



}
