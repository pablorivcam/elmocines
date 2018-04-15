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
import es.udc.pa.pa009.elmocines.model.cinemaservice.ExpiredDateException;
import es.udc.pa.pa009.elmocines.model.cinemaservice.InputValidationException;
import es.udc.pa.pa009.elmocines.model.cinemaservice.TicketsAlreadyCollectedException;
import es.udc.pa.pa009.elmocines.model.cinemaservice.TooManyLocationsException;
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
import es.udc.pa.pa009.elmocines.model.util.MovieSessionsDto;
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

	public static final String CREDIT_CARD_TEST_NUMBER = "1234567AB";

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

	private Purchase createPurchase(String creditCardNumber, Calendar creditCardExpirationDate, int locationCount,
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
		List<Cinema> cinema_service = cinemaService.findCinemasByProvinceId(provinceId);
		assertEquals(cinema_test, cinema_service);

		Cinema cinema1 = createCinema("YelmoCines", province_test);
		Cinema cinema2 = createCinema("GaliCines", province_test);
		Cinema cinema3 = createCinema("MultiCines", province_test);

		cinema_test.add(cinema2);
		cinema_test.add(cinema3);
		cinema_test.add(cinema1);

		cinema_service = cinemaService.findCinemasByProvinceId(provinceId);
		assertEquals(cinema_test, cinema_service);

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
	public void purchaseTicketsTest() {
		Province province = createProvince(PROVINCE_TEST_NAME);
		Cinema cinema = createCinema(CINEMA_TEST_NAME, province);
		Room room = createRoom(ROOM_TEST_NAME, 10, cinema);
		Movie movie = createMovie(MOVIE_TEST_NAME, MOVIE_TEST_NAME, 10, Calendar.getInstance(), Calendar.getInstance());

		Session session = createSession(getTomorrow(), new BigDecimal(10.4), movie, room);
		UserProfile user = registerUser(USER_TEST_NAME, PASSWORD_TEST);
		Purchase expected = new Purchase();
		Purchase purchase = new Purchase();

		try {
			expected = cinemaService.purchaseTickets(user.getUserProfileId(), CREDIT_CARD_TEST_NUMBER,
					Calendar.getInstance(), session.getSessionId(), 20);
			purchase = purchaseDao.find(expected.getPurchaseId());

		} catch (TooManyLocationsException e) {
			e.printStackTrace();
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		} catch (InputValidationException e) {
			e.printStackTrace();
		}

		assertEquals(purchase.getPurchaseId(), expected.getPurchaseId());
		assertEquals(purchase.getUser(), expected.getUser());
		assertEquals(purchase.getSession(), expected.getSession());
		assertEquals(purchase.getCreditCardNumber(), expected.getCreditCardNumber());
		assertEquals(purchase.getDate(), expected.getDate());

	}

	@Test(expected = InputValidationException.class)
	public void purchaseTicketsInvalidLocationsAmoutTest() throws InputValidationException {
		Province province = createProvince(PROVINCE_TEST_NAME);
		Cinema cinema = createCinema(CINEMA_TEST_NAME, province);
		Room room = createRoom(ROOM_TEST_NAME, 10, cinema);
		Movie movie = createMovie(MOVIE_TEST_NAME, MOVIE_TEST_NAME, 10, Calendar.getInstance(), Calendar.getInstance());

		Session session = createSession(getTomorrow(), new BigDecimal(10.3), movie, room);
		UserProfile user = registerUser(USER_TEST_NAME, PASSWORD_TEST);

		try {
			cinemaService.purchaseTickets(user.getUserProfileId(), CREDIT_CARD_TEST_NUMBER, Calendar.getInstance(),
					session.getSessionId(), 20);
		} catch (TooManyLocationsException e) {
			e.printStackTrace();
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Test(expected = InstanceNotFoundException.class)
	public void purchaseTicketsInvalidSessionIdTest() throws InstanceNotFoundException {
		UserProfile user = registerUser(USER_TEST_NAME, PASSWORD_TEST);
		try {
			cinemaService.purchaseTickets(user.getUserProfileId(), CREDIT_CARD_TEST_NUMBER, Calendar.getInstance(),
					NON_EXISTENT_SESSION_ID, 6);
		} catch (TooManyLocationsException e) {
			e.printStackTrace();
		} catch (InputValidationException e) {
			e.printStackTrace();
		}
	}

	@Test(expected = InstanceNotFoundException.class)
	public void purchaseTicketsInvalidUserIdTest() throws InstanceNotFoundException {
		Province province = createProvince(PROVINCE_TEST_NAME);
		Cinema cinema = createCinema(CINEMA_TEST_NAME, province);
		Room room = createRoom(ROOM_TEST_NAME, 10, cinema);
		Movie movie = createMovie(MOVIE_TEST_NAME, MOVIE_TEST_NAME, 10, Calendar.getInstance(), Calendar.getInstance());

		Session session = createSession(getTomorrow(), new BigDecimal(10.4), movie, room);

		try {
			cinemaService.purchaseTickets(NON_EXISTENT_USER_ID, CREDIT_CARD_TEST_NUMBER, Calendar.getInstance(),
					session.getSessionId(), 6);
		} catch (TooManyLocationsException e) {
			e.printStackTrace();
		} catch (InputValidationException e) {
			e.printStackTrace();
		}
	}

	@Test(expected = TooManyLocationsException.class)
	public void purchaseTicketsInvalidLocationsFreeest() throws TooManyLocationsException {
		Province province = createProvince(PROVINCE_TEST_NAME);
		Cinema cinema = createCinema(CINEMA_TEST_NAME, province);
		Room room = createRoom(ROOM_TEST_NAME, 5, cinema);
		Movie movie = createMovie(MOVIE_TEST_NAME, MOVIE_TEST_NAME, 5, Calendar.getInstance(), Calendar.getInstance());

		Session session = createSession(getTomorrow(), new BigDecimal(10.4), movie, room);
		UserProfile user = registerUser(USER_TEST_NAME, PASSWORD_TEST);

		try {
			cinemaService.purchaseTickets(user.getUserProfileId(), CREDIT_CARD_TEST_NUMBER, Calendar.getInstance(),
					session.getSessionId(), 7);
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		} catch (InputValidationException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getPurchasesTest() {

		Province province = createProvince(PROVINCE_TEST_NAME);
		Cinema cinema = createCinema(CINEMA_TEST_NAME, province);
		Room room1 = createRoom(ROOM_TEST_NAME, 10, cinema);
		Room room2 = createRoom(ROOM_TEST_NAME, 10, cinema);
		Room room3 = createRoom(ROOM_TEST_NAME, 10, cinema);

		Movie movie1 = createMovie(MOVIE_TEST_NAME, MOVIE_TEST_NAME, 10, Calendar.getInstance(),
				Calendar.getInstance());
		Movie movie2 = createMovie(MOVIE_TEST_NAME, MOVIE_TEST_NAME, 10, Calendar.getInstance(),
				Calendar.getInstance());
		Movie movie3 = createMovie(MOVIE_TEST_NAME, MOVIE_TEST_NAME, 10, Calendar.getInstance(),
				Calendar.getInstance());

		Session session1 = createSession(getTomorrow(), new BigDecimal(10.4), movie1, room2);
		Session session2 = createSession(getTomorrow(), new BigDecimal(10.4), movie2, room3);
		Session session3 = createSession(getTomorrow(), new BigDecimal(10.4), movie3, room1);

		UserProfile user1 = registerUser(USER_TEST_NAME, PASSWORD_TEST);
		UserProfile user2 = registerUser("USER2", PASSWORD_TEST);

		Purchase purchase1 = createPurchase(CREDIT_CARD_TEST_NUMBER, Calendar.getInstance(), 10, Calendar.getInstance(),
				session1, user1);
		createPurchase(CREDIT_CARD_TEST_NUMBER, Calendar.getInstance(), 10, Calendar.getInstance(), session2, user2);
		Purchase purchase3 = createPurchase(CREDIT_CARD_TEST_NUMBER, Calendar.getInstance(), 10, Calendar.getInstance(),
				session3, user1);
		Purchase purchase4 = createPurchase(CREDIT_CARD_TEST_NUMBER, Calendar.getInstance(), 10, Calendar.getInstance(),
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
		assertEquals(purchases.getItems().get(0).getUser(), purchase1.getUser());
		assertEquals(purchases.getItems().get(0).getSession(), purchase1.getSession());

		assertEquals(purchases.getItems().get(1).getPurchaseId(), purchase3.getPurchaseId());
		assertEquals(purchases.getItems().get(1).getUser(), purchase3.getUser());
		assertEquals(purchases.getItems().get(1).getSession(), purchase3.getSession());

		assertEquals(purchases.getItems().get(2).getPurchaseId(), purchase4.getPurchaseId());
		assertEquals(purchases.getItems().get(2).getUser(), purchase4.getUser());
		assertEquals(purchases.getItems().get(2).getSession(), purchase4.getSession());

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
			cinemaService.getPurchases(user.getUserProfileId(), 0, -10);
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
		Session session = createSession(getTomorrow(), new BigDecimal(10.4), movie, room);
		UserProfile user = registerUser(USER_TEST_NAME, PASSWORD_TEST);

		Purchase purchaseExpected = createPurchase(CREDIT_CARD_TEST_NUMBER, Calendar.getInstance(), 10,
				Calendar.getInstance(), session, user);
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

		Session session = createSession(getTomorrow(), new BigDecimal(10.4), movie, room);

		UserProfile user = registerUser(USER_TEST_NAME, PASSWORD_TEST);

		Purchase purchase = createPurchase(CREDIT_CARD_TEST_NUMBER, Calendar.getInstance(), 10, Calendar.getInstance(),
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
		} catch (ExpiredDateException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals(purchase.getPurchaseState(), Purchase.PurchaseState.DELIVERED);
	}

	@Test(expected = InstanceNotFoundException.class)
	public void collectInvalidTicketsTest() throws InstanceNotFoundException, ExpiredDateException {
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

		Session session = createSession(getTomorrow(), new BigDecimal(10.4), movie, room);

		UserProfile user = registerUser(USER_TEST_NAME, PASSWORD_TEST);

		Purchase purchase = createPurchase(CREDIT_CARD_TEST_NUMBER, Calendar.getInstance(), 10, Calendar.getInstance(),
				session, user);

		assertEquals(purchase.getPurchaseState(), Purchase.PurchaseState.PENDING);

		try {
			cinemaService.collectTickets(purchase.getPurchaseId());
			assertEquals(purchase.getPurchaseState(), Purchase.PurchaseState.DELIVERED);
			cinemaService.collectTickets(purchase.getPurchaseId());
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		} catch (ExpiredDateException e) {
			e.printStackTrace();
		}

	}
	
	@Test(expected = ExpiredDateException.class)
	public void collectExpiredTicketsTest() throws ExpiredDateException {
		Province province = createProvince(PROVINCE_TEST_NAME);
		Cinema cinema = createCinema(CINEMA_TEST_NAME, province);
		Room room = createRoom(ROOM_TEST_NAME, 10, cinema);
		Movie movie = createMovie(MOVIE_TEST_NAME, MOVIE_TEST_NAME, 10, Calendar.getInstance(), Calendar.getInstance());

		Session session = createSession(getYesterday(), new BigDecimal(10.4), movie, room);

		UserProfile user = registerUser(USER_TEST_NAME, PASSWORD_TEST);

		Purchase purchase = createPurchase(CREDIT_CARD_TEST_NUMBER, Calendar.getInstance(), 10, Calendar.getInstance(),
				session, user);
		
		try {
			cinemaService.collectTickets(purchase.getPurchaseId());
			assertEquals(purchase.getSession().getDate(), session.getDate());
		} catch (InstanceNotFoundException e){
			e.printStackTrace();
		} catch (TicketsAlreadyCollectedException e){
			e.printStackTrace();
		}

	}

	@Test
	public void findSessionsByCinemaIdAndDateTest() {
		Province province = createProvince(PROVINCE_TEST_NAME);
		Cinema cinema = createCinema(CINEMA_TEST_NAME, province);
		Cinema cinema2 = createCinema(CINEMA_TEST_NAME + 2, province);

		Room room = createRoom(ROOM_TEST_NAME, 10, cinema);
		Room room2 = createRoom(ROOM_TEST_NAME + 2, 10, cinema);
		Room room3 = createRoom(ROOM_TEST_NAME + 3, 10, cinema2);

		Movie movie = createMovie(MOVIE_TEST_NAME, MOVIE_TEST_NAME, 90, getYesterday(), getTomorrow());
		Movie movie2 = createMovie(MOVIE_TEST_NAME + 2, MOVIE_TEST_NAME + 2, 90, getYesterday(), getTomorrow());

		Session s = createSession(getTomorrow(), new BigDecimal(10), movie, room);
		Session s2 = createSession(getTomorrow(), new BigDecimal(10), movie2, room2);
		Session s3 = createSession(getTomorrow(), new BigDecimal(10), movie, room3);

		List<MovieSessionsDto> movieSessions = null;

		try {
			movieSessions = cinemaService.findSessionsByCinemaIdAndDate(cinema.getCinemaId(), Calendar.getInstance(),
					getTomorrow());
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
			cinemaService.findSessionsByCinemaIdAndDate(cinema.getCinemaId(), getTomorrow(), getTomorrow());
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test(expected = InputValidationException.class)
	public void findSessionsByCinemaIdAndDateTestWithInvalidDateRange() throws InputValidationException {
		Province province = createProvince(PROVINCE_TEST_NAME);
		Cinema cinema = createCinema(CINEMA_TEST_NAME, province);

		try {
			cinemaService.findSessionsByCinemaIdAndDate(cinema.getCinemaId(), Calendar.getInstance(), getYesterday());
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Test(expected = InstanceNotFoundException.class)
	public void findSessionsByCinemaIdAndDateTestWithInvalidCinema() throws InstanceNotFoundException {
		try {
			cinemaService.findSessionsByCinemaIdAndDate(NON_EXISTENT_CINEMA_ID, Calendar.getInstance(), getTomorrow());
		} catch (InputValidationException e) {
			e.printStackTrace();
		}

	}

}
