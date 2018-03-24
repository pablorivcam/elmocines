package es.udc.pa.pa009.elmocines.test.model.cinemaservice;

import static es.udc.pa.pa009.elmocines.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static es.udc.pa.pa009.elmocines.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import es.udc.pa.pa009.elmocines.model.cinema.Cinema;
import es.udc.pa.pa009.elmocines.model.cinema.CinemaDao;
import es.udc.pa.pa009.elmocines.model.cinemaservice.CinemaService;
import es.udc.pa.pa009.elmocines.model.cinemaservice.InputValidationException;
import es.udc.pa.pa009.elmocines.model.movie.Movie;
import es.udc.pa.pa009.elmocines.model.movie.MovieDao;
import es.udc.pa.pa009.elmocines.model.province.Province;
import es.udc.pa.pa009.elmocines.model.province.ProvinceDao;
import es.udc.pa.pa009.elmocines.model.room.Room;
import es.udc.pa.pa009.elmocines.model.room.RoomDao;
import es.udc.pa.pa009.elmocines.model.session.Session;
import es.udc.pa.pa009.elmocines.model.session.SessionDao;
import es.udc.pojo.modelutil.data.Block;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class CinemaServiceTest {

	public static final String PROVINCE_TEST_NAME = "TEST_PROVINCE";
	public static final String CINEMA_TEST_NAME = "TEST_CINEMA";
	public static final String ROOM_TEST_NAME = "TEST_ROOM";

	@Autowired
	private CinemaService cinemaService;

	@Autowired
	private CinemaDao cinemaDao;

	@Autowired
	private RoomDao roomDao;

	@Autowired
	private SessionDao sessionDao;

	@Autowired
	private ProvinceDao provinceDao;

	@Autowired
	private MovieDao movieDao;

	public Cinema createCinema() {
		Province province = new Province(PROVINCE_TEST_NAME, null);

		Cinema c = new Cinema(CINEMA_TEST_NAME, province, null);
		Cinema c2 = new Cinema(CINEMA_TEST_NAME + 2, province, null);
		ArrayList<Cinema> cinemas = new ArrayList<>();
		cinemas.add(c);
		cinemas.add(c2);

		province.setCinemas(cinemas);

		Room r = new Room(ROOM_TEST_NAME, 10, c);
		Room r2 = new Room(ROOM_TEST_NAME + 2, 20, c);
		ArrayList<Room> rooms = new ArrayList<>();
		rooms.add(r);
		rooms.add(r2);

		c.setRooms(rooms);
		provinceDao.save(province);
		cinemaDao.save(c);
		cinemaDao.save(c2);
		roomDao.save(r);
		roomDao.save(r2);

		return c;

	}

	public Session createSession(Cinema c) {

		Movie m = new Movie("no", "no", 20, Calendar.getInstance(), Calendar.getInstance());
		movieDao.save(m);

		Session s = new Session(20, new Date(), new BigDecimal(10), m, c.getRooms().get(0));
		sessionDao.save(s);
		return s;
	}

	@Test
	public void getSessionsTest() {

		Session s = createSession(createCinema());

		Block<Session> sessions = null;

		try {
			sessions = cinemaService.getSessions(3L, 0, 10);

		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		} catch (InputValidationException e) {
			e.printStackTrace();
		}

		assertEquals(sessions.getItems().size(), 1);
		assertEquals(sessions.getItems().get(0).getRoom(), s.getRoom());
		assertEquals(sessions.getItems().get(0).getPrice(), s.getPrice());
	}

}
