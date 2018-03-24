package es.udc.pa.pa009.elmocines.model.cinemaservice;

import java.util.List;

import es.udc.pa.pa009.elmocines.model.cinema.Cinema;
import es.udc.pa.pa009.elmocines.model.movie.Movie;
import es.udc.pa.pa009.elmocines.model.province.Province;
import es.udc.pa.pa009.elmocines.model.purchase.Purchase;
import es.udc.pa.pa009.elmocines.model.session.Session;
import es.udc.pojo.modelutil.data.Block;

public class CinemaServiceImpl implements CinemaService {

	@Override
	public List<Province> getProvinces() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cinema> findCinemasByProvinceId(long provinceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Block<Session> getSessions(long cinemaId, int startIndex, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Movie findMovieById(Movie movie) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Session findSessionBySessionId(long sessionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void purchaseTickets(long sessionId, int locationsAmmount) {
		// TODO Auto-generated method stub

	}

	@Override
	public Block<Purchase> getPurchases(long userId, int startIndex, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Purchase getPurchase(long purchaseId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Purchase collectTickets(long purchaseId) {
		// TODO Auto-generated method stub
		return null;
	}

}
