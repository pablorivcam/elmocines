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
import es.udc.pa.pa009.elmocines.model.cinemaservice.TicketsAlreadyCollectedException;
import es.udc.pa.pa009.elmocines.model.movie.Movie;
import es.udc.pa.pa009.elmocines.model.movie.MovieDao;
import es.udc.pa.pa009.elmocines.model.province.Province;
import es.udc.pa.pa009.elmocines.model.province.ProvinceDao;
import es.udc.pa.pa009.elmocines.model.purchase.Purchase;
import es.udc.pa.pa009.elmocines.model.purchase.PurchaseDao;
import es.udc.pa.pa009.elmocines.model.room.Room;
import es.udc.pa.pa009.elmocines.model.room.RoomDao;
import es.udc.pa.pa009.elmocines.model.session.Session;
import es.udc.pa.pa009.elmocines.model.session.SessionDao;
import es.udc.pa.pa009.elmocines.model.userprofile.UserProfile;
import es.udc.pa.pa009.elmocines.model.userprofile.UserProfile.Role;
import es.udc.pa.pa009.elmocines.model.userservice.UserProfileDetails;
import es.udc.pa.pa009.elmocines.model.userservice.UserService;
import es.udc.pojo.modelutil.data.Block;
import es.udc.pojo.modelutil.exceptions.DuplicateInstanceException;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class CinemaServiceTest {

	private final long NON_EXISTENT_CINEMA_ID = -1;
	private final long NON_EXISTENT_SESSION_ID = -1;
	private final long NON_EXISTENT_PURCHASE_ID = -1;

	public static final String PROVINCE_TEST_NAME = "TEST_PROVINCE";
	public static final String CINEMA_TEST_NAME = "TEST_CINEMA";
	public static final String ROOM_TEST_NAME = "TEST_ROOM";
	public static final String SESSION_TEST_NAME = "TEST_SESSION";
	public static final String MOVIE_TEST_NAME = "TEST_MOVIE";

	@Autowired
	private CinemaService cinemaService;

	@Autowired
	private UserService userService;

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

	@Autowired
	private PurchaseDao purchaseDao;

	public Province createProvince(String name) {

		Province province = new Province(name, new ArrayList<>());
		provinceDao.save(province);
		return province;

	}

	private UserProfile registerUser(String loginName, String clearPassword) {

		UserProfileDetails userProfileDetails = new UserProfileDetails("name", "lastName", "user@udc.es");

		try {

			return userService.registerUser(loginName, clearPassword, userProfileDetails, Role.CLIENT);

		} catch (DuplicateInstanceException e) {
			throw new RuntimeException(e);
		}

	}

	private Cinema createCinema(String name, Province province) {
		ArrayList<Room> rooms = new ArrayList<>();
		Cinema cinema = new Cinema(name, province, rooms);
		cinemaDao.save(cinema);
		return cinema;
	}

	private Room createRoom(String name, int capacity, Cinema cinema) {
		Room room = new Room(name, capacity, cinema);
		roomDao.save(room); // FIXME: creo que no hace falta actualizar la entidad cinema con la nueva room
							// y que hibernate lo hace automáticamente.
		return room;
	}

	private Movie createMovie(String title, String review, int lenght, Calendar initDate, Calendar finalDate) {
		Movie movie = new Movie(title, review, lenght, initDate, finalDate);
		movieDao.save(movie);
		return movie;
	}

	private Session createSession(int freeLocationsCount, Date hour, BigDecimal price, Movie movie, Room room) {
		Session session = new Session(freeLocationsCount, hour, price, movie, room);
		sessionDao.save(session);
		return session;
	}

	private Purchase createPurchase(BigDecimal creditCardNumber, Calendar creditCardExpirationDate, int locationCount,
			Calendar date, Session session, UserProfile user) {
		Purchase purchase = new Purchase(creditCardNumber, creditCardExpirationDate, locationCount,
				Purchase.PurchaseState.PENDING, date, session, user);

		purchaseDao.save(purchase);

		return purchase;
	}

	@Test
	public void getSessionsByCinemaIdTest() {

		Province province = createProvince(PROVINCE_TEST_NAME);
		Cinema cinema = createCinema(CINEMA_TEST_NAME, province);
		Room room = createRoom(ROOM_TEST_NAME, 10, cinema);
		Room room2 = createRoom(ROOM_TEST_NAME + 2, 10, cinema);
		Movie movie = createMovie(MOVIE_TEST_NAME, MOVIE_TEST_NAME, 10, Calendar.getInstance(), Calendar.getInstance());

		Session session = createSession(100, new Date(), new BigDecimal(10.4), movie, room);
		Session session2 = createSession(100, new Date(), new BigDecimal(11.4), movie, room2);

		Block<Session> sessions = null;

		try {
			sessions = cinemaService.getSessionsByCinemaId(cinema.getCinemaId(), 0, 10);

		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		} catch (InputValidationException e) {
			e.printStackTrace();
		}

		assertEquals(sessions.getItems().size(), 2);
		assertEquals(sessions.getItems().get(0).getRoom(), session.getRoom());
		assertEquals(sessions.getItems().get(0).getPrice(), session.getPrice());
		assertEquals(sessions.getItems().get(1).getRoom(), session2.getRoom());
		assertEquals(sessions.getItems().get(1).getPrice(), session2.getPrice());
	}

	@Test(expected = InstanceNotFoundException.class)
	public void findNonExistentCinemaTest() throws InstanceNotFoundException {

		try {
			cinemaService.getSessionsByCinemaId(NON_EXISTENT_CINEMA_ID, 0, 10);
		} catch (InputValidationException e) {
			e.printStackTrace();
		}

	}

	@Test(expected = InputValidationException.class)
	public void getSessionsByCinemaIdTestWithInvalidCountParameter() throws InputValidationException {
		Province province = createProvince(PROVINCE_TEST_NAME);
		Cinema cinema = createCinema(CINEMA_TEST_NAME, province);
		try {
			cinemaService.getSessionsByCinemaId(cinema.getCinemaId(), -2, 10);
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Test(expected = InputValidationException.class)
	public void getSessionsByCinemaIdTestWithInvalidStartIndexParameter() throws InputValidationException {
		Province province = createProvince(PROVINCE_TEST_NAME);
		Cinema cinema = createCinema(CINEMA_TEST_NAME, province);
		try {
			cinemaService.getSessionsByCinemaId(cinema.getCinemaId(), 0, -10);
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void findSessionBySessionIdTest() {
		Province province = createProvince(PROVINCE_TEST_NAME);
		Cinema cinema = createCinema(CINEMA_TEST_NAME, province);
		Room room = createRoom(ROOM_TEST_NAME, 10, cinema);
		Movie movie = createMovie(MOVIE_TEST_NAME, MOVIE_TEST_NAME, 10, Calendar.getInstance(), Calendar.getInstance());

		Session expected = createSession(100, new Date(), new BigDecimal(10.4), movie, room);
		Session session = null;
		try {
			session = cinemaService.findSessionBySessionId(expected.getSessionId());
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals(expected, session);

	}

	@Test(expected = InstanceNotFoundException.class)
	public void findSessionsByInvalidSessionIdTest() throws InstanceNotFoundException {
		cinemaService.findSessionBySessionId(NON_EXISTENT_SESSION_ID);
	}

	@Test
	public void collectTicketsTest() {
		Province province = createProvince(PROVINCE_TEST_NAME);
		Cinema cinema = createCinema(CINEMA_TEST_NAME, province);
		Room room = createRoom(ROOM_TEST_NAME, 10, cinema);
		Movie movie = createMovie(MOVIE_TEST_NAME, MOVIE_TEST_NAME, 10, Calendar.getInstance(), Calendar.getInstance());

		Session session = createSession(100, new Date(), new BigDecimal(10.4), movie, room);

		UserProfile user = registerUser("Miguel", "Miguel");

		Purchase purchase = createPurchase(new BigDecimal(12345), Calendar.getInstance(), 10, Calendar.getInstance(),
				session, user);

		assertEquals(purchase.getPurchaseState(), Purchase.PurchaseState.PENDING);

		try {
			cinemaService.collectTickets(purchase.getPurchaseId());
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TicketsAlreadyCollectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals(purchase.getPurchaseState(), Purchase.PurchaseState.DELIVERED);
	}

	@Test(expected = InstanceNotFoundException.class)
	public void collectInvalidTicketsTest() throws InstanceNotFoundException {
		try {
			cinemaService.collectTickets(NON_EXISTENT_PURCHASE_ID);
		} catch (TicketsAlreadyCollectedException e) {
			e.printStackTrace();
		}
	}

	@Test(expected = TicketsAlreadyCollectedException.class)
	public void collectCollectedTicketsTest() throws TicketsAlreadyCollectedException {
		Province province = createProvince(PROVINCE_TEST_NAME);
		Cinema cinema = createCinema(CINEMA_TEST_NAME, province);
		Room room = createRoom(ROOM_TEST_NAME, 10, cinema);
		Movie movie = createMovie(MOVIE_TEST_NAME, MOVIE_TEST_NAME, 10, Calendar.getInstance(), Calendar.getInstance());

		Session session = createSession(100, new Date(), new BigDecimal(10.4), movie, room);

		UserProfile user = registerUser("Miguel", "Miguel");

		Purchase purchase = createPurchase(new BigDecimal(12345), Calendar.getInstance(), 10, Calendar.getInstance(),
				session, user);

		assertEquals(purchase.getPurchaseState(), Purchase.PurchaseState.PENDING);

		try {
			cinemaService.collectTickets(purchase.getPurchaseId());
			assertEquals(purchase.getPurchaseState(), Purchase.PurchaseState.DELIVERED);
			cinemaService.collectTickets(purchase.getPurchaseId());
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		}

	}

}
