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
	public List<MovieDTO> getMovieByRating(Double fromRating) {
		String str="select m from Movie m where m.imdbRating >= ?1";
		Query query=entityManager.createQuery(str);
		query.setParameter(1, fromRating.floatValue());
		List<Movie> list=query.getResultList();
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
	public List<MovieDTO> getHighestRatedMovie(String directorName) {
		//SELECT imdb_rating FROM movie WHERE director_name="Joss Whedon" ORDER BY imdb_rating DESC
		String str="select m from Movie m where m.directorName= ?1 order by m.imdbRating desc";
		Query query=entityManager.createQuery(str);
		query.setParameter(1, directorName);
		List<Movie> list=query.getResultList();
		List<MovieDTO> result=new ArrayList<>();
		Movie m=list.get(0);
		MovieDTO md=new MovieDTO();
		md.setDirectorName(m.getDirectorName());
		md.setImdbRating(m.getImdbRating());
		md.setMovieId(m.getMovieId());
		md.setMovieName(m.getMovieName());
		md.setYear(m.getYear());
		result.add(md);
		return result;
	}

	@Override
	public Float getAverageDirectorRating(String directorName) {
		return null;
	}

	@Override
	public Long getNumberOfMoviesReleased(Integer fromYear, Integer toYear) {
		return null;
	}

	

}
