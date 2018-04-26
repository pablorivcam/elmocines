package es.udc.pa.pa009.elmocines.web.components;

import java.util.List;
import java.util.StringJoiner;

import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Cookies;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

import es.udc.pa.pa009.elmocines.model.cinema.Cinema;
import es.udc.pa.pa009.elmocines.model.cinemaservice.CinemaService;
import es.udc.pa.pa009.elmocines.model.province.Province;
import es.udc.pa.pa009.elmocines.web.pages.Index;
import es.udc.pa.pa009.elmocines.web.services.AuthenticationPolicy;
import es.udc.pa.pa009.elmocines.web.services.AuthenticationPolicyType;
import es.udc.pa.pa009.elmocines.web.util.CookiesManager;
import es.udc.pa.pa009.elmocines.web.util.UserSession;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

@Import(library = { "tapestry5/bootstrap/js/collapse.js",
		"tapestry5/bootstrap/js/dropdown.js" }, stylesheet = "tapestry5/bootstrap/css/bootstrap-theme.css")
public class Layout {

	@Property
	private Long provinceId;

	@Property
	private Long cinemaId;

	@Property
	private String provincesSelect;

	@Property
	private String cinemasSelect;

	@Inject
	private CinemaService cinemaService;

	@Property
	@Parameter(required = true, defaultPrefix = "message")
	private String title;

	@Parameter(defaultPrefix = "literal")
	private Boolean showTitleInBody;

	@Property
	@SessionState(create = false)
	private UserSession userSession;

	@Inject
	private Cookies cookies;

	// AÑADIDO PARA QUE AJAX PUEDA RE RENDERIZAR EL SEGUNDO SELECTOR
	@InjectComponent
	private Zone cinemaSelectorZone;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;
	////////////////////////////////////////////////////////////////

	public boolean getShowTitleInBody() {

		if (showTitleInBody == null) {
			return true;
		} else {
			return showTitleInBody;
		}

	}

	void rechargeCinemaSelector(Long provinceId) {
		List<Cinema> cinemasList = null;
		try {
			cinemasList = cinemaService.findCinemasByProvinceId(provinceId);
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		}

		if (cinemasList != null) {
			StringJoiner cinemasJoiner = new StringJoiner(",");

			for (Cinema c : cinemasList) {
				cinemasJoiner.add(c.getCinemaId().toString() + "=" + c.getName());
			}

			cinemasSelect = cinemasJoiner.toString();

			if (!cinemasList.isEmpty()) {
				cinemaId = cinemasList.get(0).getCinemaId();
			}

		}

	}

	void onValueChangedFromProvinceId(String provinceId) {
		rechargeCinemaSelector(Long.parseLong(provinceId));
		ajaxResponseRenderer.addRender(cinemaSelectorZone);
	}

	// Método para inicializar el selector de las provincias
	void onPrepareForRender() {

		List<Province> provincesList = cinemaService.getProvinces();
		StringJoiner provincesJoiner = new StringJoiner(",");

		for (Province p : provincesList) {
			provincesJoiner.add(p.getProvinceId().toString() + "=" + p.getName());
		}

		provincesSelect = provincesJoiner.toString();

		if (!provincesList.isEmpty()) {
			provinceId = provincesList.get(0).getProvinceId();
		}

		/////////////////////////////////

		rechargeCinemaSelector(provincesList.get(0).getProvinceId());

	}

	@AuthenticationPolicy(AuthenticationPolicyType.AUTHENTICATED_USERS)
	Object onActionFromLogout() {
		userSession = null;
		CookiesManager.removeCookies(cookies);
		return Index.class;
	}

}
