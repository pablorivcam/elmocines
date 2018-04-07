package es.udc.pa.pa009.elmocines.model.movie;

import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;

@Repository("movieDao")
public class MovieDaoHibernate extends GenericDaoHibernate<Movie, Long> implements MovieDao {

}
