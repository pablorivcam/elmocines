package es.udc.pa.pa009.elmocines.model.province;

import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;

@Repository("provinceDao")
public class ProvinceDaoHibernate extends GenericDaoHibernate<Province, Long> implements ProvinceDao {

}
