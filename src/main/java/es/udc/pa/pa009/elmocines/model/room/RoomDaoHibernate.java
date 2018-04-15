package es.udc.pa.pa009.elmocines.model.room;

import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;

@Repository("roomDao")
public class RoomDaoHibernate extends GenericDaoHibernate<Room, Long> implements RoomDao {

}
