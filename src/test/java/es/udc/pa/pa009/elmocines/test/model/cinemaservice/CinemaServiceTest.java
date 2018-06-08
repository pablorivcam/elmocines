package es.udc.pa.pa009.elmocines.test.model.cinemaservice;

import static es.udc.pa.pa009.elmocines.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static es.udc.pa.pa009.elmocines.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
import es.udc.pa.pa009.elmocines.model.util.MovieSessionsDto;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class CinemaServiceTest {

	private final long NON_EXISTENT_PROVINCE_ID = -1;
	private final long NON_EXISTENT_CINEMA_ID = -1;
	private final long NON_EXISTENT_SESSION_ID = -1;
	private final long NON_EXISTENT_MOVIE_ID = -1;

	public static final String USER_TEST_NAME = "TEST_USER";
	public static final String PASSWORD_TEST = "TEST_PASSWORD";
	public static final String PROVINCE_TEST_NAME = "TEST_PROVINCE";
	public static final String CINEMA_TEST_NAME = "TEST_CINEMA";
	public static final String ROOM_TEST_NAME = "TEST_ROOM";
	public static final String SESSION_TEST_NAME = "TEST_SESSION";
	public static final String MOVIE_TEST_NAME = "TEST_MOVIE";

	public static final String CREDIT_CARD_TEST_NUMBER = "1234567AB";

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

	public Province createProvince(String name) {

		Province province = new Province(name, new ArrayList<>());
		provinceDao.save(province);
		return province;

	}

	private Calendar getTomorrow() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, 1);
		return c;
	}

	private Calendar getYesterday() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, -1);
		return c;
	}

	private Cinema createCinema(String name, Province province) {
		ArrayList<Room> rooms = new ArrayList<>();
		Cinema cinema = new Cinema(name, province, rooms);
		cinemaDao.save(cinema);
		return cinema;
	}

	private Room createRoom(String name, int capacity, Cinema cinema) {
		Room room = new Room(name, capacity, cinema);
		roomDao.save(room);
		cinemaDao.save(cinema);
		return room;
	}

	private Movie createMovie(String title, String review, int lenght, Calendar initDate, Calendar finalDate) {
		Movie movie = new Movie(title, review, lenght, initDate, finalDate);
		movieDao.save(movie);
		return movie;
	}

	private Session createSession(Calendar date, BigDecimal price, Movie movie, Room room) {
		Session session = new Session(date, price, movie, room);
		sessionDao.save(session);
		roomDao.save(room);
		return session;
	}

	@Test
	public void getProvincesTest() {

		List<Province> province_test = new ArrayList<>();
		List<Province> province_service = cinemaService.getProvinces();
		assertEquals(province_test, province_service);

		Province province1 = createProvince("Madrid");
		Province province2 = createProvince("Barcelona");
		Province province3 = createProvince("Sevilla");
		Province province4 = createProvince("Valencia");
		Province province5 = createProvince("Coruña");
		Province province6 = createProvince("Zaragoza");

		province_test.add(province2);
		province_test.add(province5);
		province_test.add(province1);
		province_test.add(province3);
		province_test.add(province4);
		province_test.add(province6);
		province_service = cinemaService.getProvinces();
		assertEquals(province_test, province_service);

	}

	@Test
	public void findCinemasByProvinceIdTest() {

		Province province_test = createProvince(PROVINCE_TEST_NAME);
		Long provinceId = province_test.getProvinceId();

		List<Cinema> cinema_test = new ArrayList<>();
		List<Cinema> cinema_service = null;
		try {
			cinema_service = cinemaService.findCinemasByProvinceId(provinceId);
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		}
		assertEquals(cinema_test, cinema_service);

		Cinema cinema1 = createCinema("YelmoCines", province_test);
		Cinema cinema2 = createCinema("GaliCines", province_test);
		Cinema cinema3 = createCinema("MultiCines", province_test);

		cinema_test.add(cinema2);
		cinema_test.add(cinema3);
		cinema_test.add(cinema1);

		try {
			cinema_service = cinemaService.findCinemasByProvinceId(provinceId);
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		}
		assertEquals(cinema_test, cinema_service);

	}

	@Test(expected = InstanceNotFoundException.class)
	public void findCinemasByProvinceIdInvalidIdTest() throws InstanceNotFoundException {
		cinemaService.findCinemasByProvinceId(NON_EXISTENT_PROVINCE_ID);
	}

	@Test
	public void findMovieByIdTest() {
		Movie movieExpected = createMovie(MOVIE_TEST_NAME, MOVIE_TEST_NAME, 10, Calendar.getInstance(),
				Calendar.getInstance());
		Movie movie = null;
		try {
			movie = cinemaService.findMovieById(movieExpected.getMovieId());
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		}

		assertEquals(movieExpected, movie);
	}

	@Test(expected = InstanceNotFoundException.class)
	public void findMovieByInvalidIdTest() throws InstanceNotFoundException {
		cinemaService.findMovieById(NON_EXISTENT_MOVIE_ID);
	}

	@Test
	public void findSessionBySessionIdTest() {
		Province province = createProvince(PROVINCE_TEST_NAME);
		Cinema cinema = createCinema(CINEMA_TEST_NAME, province);
		Room room = createRoom(ROOM_TEST_NAME, 10, cinema);
		Movie movie = createMovie(MOVIE_TEST_NAME, MOVIE_TEST_NAME, 10, Calendar.getInstance(), Calendar.getInstance());

		Session expected = createSession(getTomorrow(), new BigDecimal(10.4), movie, room);
		Session session = null;
		try {
			session = cinemaService.findSessionBySessionId(expected.getSessionId());
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		}

		assertEquals(expected, session);

	}

	@Test(expected = InstanceNotFoundException.class)
	public void findSessionsByInvalidSessionIdTest() throws InstanceNotFoundException {
		cinemaService.findSessionBySessionId(NON_EXISTENT_SESSION_ID);
	}

	@Test
	public void findSessionsByCinemaIdAndDateTest() {
		Calendar afterTomorrow = getTomorrow();
		afterTomorrow.add(Calendar.DAY_OF_YEAR, 2);
		Calendar beforeToday = Calendar.getInstance();
		beforeToday.add(Calendar.DAY_OF_YEAR, -2);
		Calendar tomorrow = getTomorrow();
		tomorrow.add(Calendar.MINUTE, 2);

		Province province = createProvince(PROVINCE_TEST_NAME);
		Cinema cinema = createCinema(CINEMA_TEST_NAME, province);
		Cinema cinema2 = createCinema(CINEMA_TEST_NAME + 2, province);

		Room room = createRoom(ROOM_TEST_NAME, 10, cinema);
		Room room2 = createRoom(ROOM_TEST_NAME + 2, 10, cinema);
		Room room3 = createRoom(ROOM_TEST_NAME + 3, 10, cinema2);

		Movie movie = createMovie(MOVIE_TEST_NAME, MOVIE_TEST_NAME, 90, getYesterday(), tomorrow);
		Movie movie2 = createMovie(MOVIE_TEST_NAME + 2, MOVIE_TEST_NAME + 2, 90, getYesterday(), tomorrow);

		Session s = createSession(tomorrow, new BigDecimal(10), movie, room);
		Session s2 = createSession(tomorrow, new BigDecimal(10), movie2, room2);
		createSession(tomorrow, new BigDecimal(10), movie2, room3);
		createSession(afterTomorrow, new BigDecimal(10), movie2, room2);
		createSession(beforeToday, new BigDecimal(10), movie2, room2);

		createSession(getTomorrow(), new BigDecimal(10), movie, room3);

		List<MovieSessionsDto> movieSessions = null;

		try {
			movieSessions = cinemaService.findSessionsByCinemaIdAndDate(cinema.getCinemaId(), getTomorrow());
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		} catch (InputValidationException e) {
			e.printStackTrace();
		}

		assertEquals(2, movieSessions.size());
		assertEquals(movie, movieSessions.get(0).getMovie());
		assertEquals(s, movieSessions.get(0).getSessions().get(0));
		assertEquals(movie2, movieSessions.get(1).getMovie());
		assertEquals(s2, movieSessions.get(1).getSessions().get(0));
		assertEquals(1, movieSessions.get(0).getSessions().size());
		assertEquals(1, movieSessions.get(1).getSessions().size());

	}

	@Test(expected = InputValidationException.class)
	public void findSessionsByCinemaIdAndDateTestWithInvalidInitDate() throws InputValidationException {
		Province province = createProvince(PROVINCE_TEST_NAME);
		Cinema cinema = createCinema(CINEMA_TEST_NAME, province);

		try {
			cinemaService.findSessionsByCinemaIdAndDate(cinema.getCinemaId(), getYesterday());
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test(expected = InstanceNotFoundException.class)
	public void findSessionsByCinemaIdAndDateTestWithInvalidCinema() throws InstanceNotFoundException {
		try {
			cinemaService.findSessionsByCinemaIdAndDate(NON_EXISTENT_CINEMA_ID, Calendar.getInstance());
		} catch (InputValidationException e) {
			e.printStackTrace();
		}

	}

}
