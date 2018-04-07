package es.udc.pa.pa009.elmocines.model.province;

import java.util.List;

import es.udc.pojo.modelutil.dao.GenericDao;

public interface ProvinceDao extends GenericDao<Province, Long> {
	
	public List<Province> getProvinces();

}
