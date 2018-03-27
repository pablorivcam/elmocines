package es.udc.pa.pa009.elmocines.test.model.cinemaservice;

import static es.udc.pa.pa009.elmocines.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static es.udc.pa.pa009.elmocines.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
	private final long NON_EXISTENT_USER_ID = -1;	
	private final long NON_EXISTENT_MOVIE_ID = -1;

	public static final String USER_TEST_NAME = "TEST_USER";
	public static final String PASSWORD_TEST = "TEST_PASSWORD";
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
	public void getProvincesTest() {
		
		List<Province> province_test = new ArrayList<>();		
		List<Province> province_service = cinemaService.getProvinces();
		assertEquals(province_test,province_service);
		
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
		assertEquals(province_test,province_service);
		
		
	}
	
	@Test
	public void findCinemasByProvinceIdTest() {
		
		Province province_test = createProvince(PROVINCE_TEST_NAME);
		Long provinceId = province_test.getProvinceId();
	
		List<Cinema> cinema_test = new ArrayList<>();		
		List<Cinema> cinema_service = cinemaService.findCinemasByProvinceId(provinceId);
		assertEquals(cinema_test,cinema_service);
		
		Cinema cinema1 = createCinema("YelmoCines",province_test);
		Cinema cinema2 = createCinema("GaliCines",province_test);
		Cinema cinema3 = createCinema("MultiCines",province_test);
		
		cinema_test.add(cinema2);
		cinema_test.add(cinema3);
		cinema_test.add(cinema1);
		
		cinema_service = cinemaService.findCinemasByProvinceId(provinceId);
		assertEquals(cinema_test,cinema_service);
		
		
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
	public void findMovieByIdTest() {
		Movie movieExpected = createMovie(MOVIE_TEST_NAME, MOVIE_TEST_NAME, 10, Calendar.getInstance(), Calendar.getInstance());
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
	public void getPurchasesTest() {
		
		Province province = createProvince(PROVINCE_TEST_NAME);
		Cinema cinema = createCinema(CINEMA_TEST_NAME, province);
		Room room1 = createRoom(ROOM_TEST_NAME, 10, cinema);
		Room room2 = createRoom(ROOM_TEST_NAME, 10, cinema);
		Room room3 = createRoom(ROOM_TEST_NAME, 10, cinema);
		
		Movie movie1 = createMovie(MOVIE_TEST_NAME, MOVIE_TEST_NAME, 10, Calendar.getInstance(), Calendar.getInstance());
		Movie movie2 = createMovie(MOVIE_TEST_NAME, MOVIE_TEST_NAME, 10, Calendar.getInstance(), Calendar.getInstance());
		Movie movie3 = createMovie(MOVIE_TEST_NAME, MOVIE_TEST_NAME, 10, Calendar.getInstance(), Calendar.getInstance());
		
		Session session1 = createSession(100, new Date(), new BigDecimal(10.4), movie1, room2);
		Session session2 = createSession(100, new Date(), new BigDecimal(10.4), movie2, room3);
		Session session3 = createSession(100, new Date(), new BigDecimal(10.4), movie3, room1);

		UserProfile user1 = registerUser(USER_TEST_NAME, PASSWORD_TEST);
		UserProfile user2 = registerUser("USER2", PASSWORD_TEST);

		Purchase purchase1 = createPurchase(new BigDecimal(12345), Calendar.getInstance(), 10, Calendar.getInstance(),
				session1, user1);
		createPurchase(new BigDecimal(12345), Calendar.getInstance(), 10, Calendar.getInstance(),
				session2, user2);
		Purchase purchase3 = createPurchase(new BigDecimal(12345), Calendar.getInstance(), 10, Calendar.getInstance(),
				session3, user1);
		Purchase purchase4 = createPurchase(new BigDecimal(12345), Calendar.getInstance(), 10, Calendar.getInstance(),
				session2, user1);
		
		Block<Purchase> purchases = null;

		try {
			purchases = cinemaService.getPurchases(user1.getUserProfileId(), 0, 10);

		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		} catch (InputValidationException e) {
			e.printStackTrace();
		}

		assertEquals(purchases.getItems().size(), 3);
		
		assertEquals(purchases.getItems().get(0).getPurchaseId(), purchase1.getPurchaseId());
		assertEquals(purchases.getItems().get(0).getUser(),purchase1.getUser());
		assertEquals(purchases.getItems().get(0).getSession(),purchase1.getSession());
		
		assertEquals(purchases.getItems().get(1).getPurchaseId(), purchase3.getPurchaseId());
		assertEquals(purchases.getItems().get(1).getUser(),purchase3.getUser());
		assertEquals(purchases.getItems().get(1).getSession(),purchase3.getSession());
		
		assertEquals(purchases.getItems().get(2).getPurchaseId(), purchase4.getPurchaseId());
		assertEquals(purchases.getItems().get(2).getUser(),purchase4.getUser());
		assertEquals(purchases.getItems().get(2).getSession(),purchase4.getSession());
		
		
		
	}
	
	
	@Test(expected = InstanceNotFoundException.class)
	public void findNonExistentUserPurchaseTest() throws InstanceNotFoundException {

		try {
			cinemaService.getPurchases(NON_EXISTENT_USER_ID, 0, 10);
		} catch (InputValidationException e) {
			e.printStackTrace();
		}

	}

	@Test(expected = InputValidationException.class)
	public void getPurchasesTestWithInvalidCountParameter() throws InputValidationException {
		
		UserProfile user = registerUser(USER_TEST_NAME, PASSWORD_TEST);
		try {
			cinemaService.getPurchases(user.getUserProfileId(), -2, 10);
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Test(expected = InputValidationException.class)
	public void getPurchasesTestWithInvalidStartIndexParameter() throws InputValidationException {
		UserProfile user = registerUser(USER_TEST_NAME, PASSWORD_TEST);
		try {
			cinemaService.getSessionsByCinemaId(user.getUserProfileId(), 0, -10);
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void getPurchaseTest() {
		Province province = createProvince(PROVINCE_TEST_NAME);
		Cinema cinema = createCinema(CINEMA_TEST_NAME, province);
		Room room = createRoom(ROOM_TEST_NAME, 10, cinema);
		Movie movie = createMovie(MOVIE_TEST_NAME, MOVIE_TEST_NAME, 10, Calendar.getInstance(), Calendar.getInstance());
		Session session = createSession(100, new Date(), new BigDecimal(10.4), movie, room);
		UserProfile user = registerUser(USER_TEST_NAME, PASSWORD_TEST);

		Purchase purchaseExpected =  createPurchase(new BigDecimal(12345), Calendar.getInstance(), 10, Calendar.getInstance(),
				session, user);
		Purchase purchase = null;
		try {
			purchase = cinemaService.getPurchase(purchaseExpected.getPurchaseId());
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		}
		assertEquals(purchaseExpected, purchase);
	}

	@Test(expected = InstanceNotFoundException.class)
	public void getNonExistentPurchaseTest() throws InstanceNotFoundException {
		cinemaService.getPurchase(NON_EXISTENT_PURCHASE_ID);
	}
	
	@Test
	public void collectTicketsTest() {
		Province province = createProvince(PROVINCE_TEST_NAME);
		Cinema cinema = createCinema(CINEMA_TEST_NAME, province);
		Room room = createRoom(ROOM_TEST_NAME, 10, cinema);
		Movie movie = createMovie(MOVIE_TEST_NAME, MOVIE_TEST_NAME, 10, Calendar.getInstance(), Calendar.getInstance());

		Session session = createSession(100, new Date(), new BigDecimal(10.4), movie, room);

		UserProfile user = registerUser(USER_TEST_NAME, PASSWORD_TEST);

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

		UserProfile user = registerUser(USER_TEST_NAME, PASSWORD_TEST);

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
