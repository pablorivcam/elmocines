package es.udc.pa.pa009.elmocines.model.cinema;

import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;

@Repository("cinemaDao")
public class CinemaDaoHibernate extends GenericDaoHibernate<Cinema, Long> implements CinemaDao {

}
