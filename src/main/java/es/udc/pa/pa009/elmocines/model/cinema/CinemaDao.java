package es.udc.pa.pa009.elmocines.model.cinema;

import java.util.List;

import es.udc.pojo.modelutil.dao.GenericDao;

public interface CinemaDao extends GenericDao<Cinema, Long> {
	
	public List<Cinema> findCinemasByProvinceId(Long provinceId);

}
