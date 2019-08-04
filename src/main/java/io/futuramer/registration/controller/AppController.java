package io.futuramer.registration.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import io.futuramer.registration.dao.UserDao;
import io.futuramer.registration.model.Appuser;
import io.futuramer.registration.utilities.DictionaryUtil;
import io.futuramer.registration.utilities.PasswordUtil;

import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.Calendar;

@Controller
@PropertySources({
    @PropertySource("classpath:messages.properties"),
    @PropertySource("classpath:application.properties")
})
@RequestMapping("/")
public class AppController {

    @Autowired
    private Environment environment;

    @Autowired
    private UserDao userDao;
    
    @Autowired
    private MessageSource messageSource;

    private static final String PASSWORD_REGEXP = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,160}$";

    /*
     * This method will provide a registration form for new user.
     */
    @RequestMapping(value = { "/", "/registration" }, method = RequestMethod.GET)
    public ModelAndView newUser() {
    	ModelAndView model = new ModelAndView();
        Appuser appuser = new Appuser();
        model.addObject("appuser", appuser);
        model.setViewName("/registration");
        return model;
    }
    
    /*
     * This method will be called on form submission, handling POST request for
     * saving user in database. It also validates the user input.
     */
    @RequestMapping(value = { "/", "/registration" }, method = RequestMethod.POST)
    public ModelAndView saveUser(@Valid Appuser appuser, BindingResult result) {
        validateAppuser(appuser, result);
        if (result.hasErrors()) {
            ModelAndView model = new ModelAndView();
            model.addObject("appuser", appuser);
            model.setViewName("/registration");
            return model;
        }

    	appuser.setPassword(PasswordUtil.hash(
                (environment.getRequiredProperty("secret.salt") + appuser.getPassword()).toCharArray(),
                Charset.defaultCharset()));
    	appuser.setVersion(Integer.parseInt(environment.getRequiredProperty("secret.protection.schema.version")));

    	userDao.saveIn(appuser);
    	return registered();
    }
    
    /*
     * This method will redirect the user to a welcome homepage after a successful registration.
     */
	@RequestMapping(value = "/successRegistration", method = RequestMethod.GET)
	public ModelAndView registered() {
		ModelAndView model = new ModelAndView();
		model.addObject("success", "Successful Registration");
		model.setViewName("/successRegistration");
		return model;
	}

    /**
     * This method validates Appuser input data
     * @param appuser Entity, encapsulating users` input
     * @param result object, encapsulating the results of validation
     */
	private void validateAppuser(Appuser appuser, BindingResult result) {
        if (appuser.getBirthdate() != null && Calendar.getInstance().getTime().before(appuser.getBirthdate())) {
            result.rejectValue(
                    "birthdate",
                    "error.user",
                    environment.getRequiredProperty("non.valid.birthdate"));
        }
        if (!userDao.isUsernameUnique(appuser.getId(), appuser.getUsername())) {
            result.rejectValue(
                    "username",
                    "error.user",
                    MessageFormat.format(environment.getRequiredProperty("non.unique.appusername"), appuser.getUsername()));
        }
        if (!userDao.isEmailUnique(appuser.getId(), appuser.getEmail())) {
            result.rejectValue(
                    "email",
                    "error.user",
                    MessageFormat.format(environment.getRequiredProperty("non.unique.email"), appuser.getEmail()));
        }
        if (!appuser.getPassword().matches(PASSWORD_REGEXP)) {
            result.rejectValue(
                    "password",
                    "error.user",
                    environment.getRequiredProperty("password.weak"));
        }
        if (DictionaryUtil.contains(appuser.getPassword())) {
            result.rejectValue(
                    "password",
                    "error.user",
                    environment.getRequiredProperty("password.dictionary"));
        }
    }

}
