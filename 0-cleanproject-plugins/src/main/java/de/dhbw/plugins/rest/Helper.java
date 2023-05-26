package de.dhbw.plugins.rest;

import de.dhbw.cleanproject.application.bar.BarService;
import de.dhbw.cleanproject.application.exceptions.MyErrorCode;
import de.dhbw.cleanproject.application.exceptions.MyException;
import de.dhbw.cleanproject.application.person.PersonService;
import de.dhbw.cleanproject.domain.bar.Bar;
import de.dhbw.cleanproject.domain.person.Person;

import javax.servlet.http.HttpServletRequest;

public class Helper {

    public static String getIdFromRequest(HttpServletRequest request) throws MyException {
        String id = (String) request.getSession().getAttribute("person");
        if (id == null) throw new MyException(MyErrorCode.NO_ID);
        return id;
    }

    public static Bar getBarFromRequest(HttpServletRequest request, PersonService personService, BarService barService) throws MyException {
        String id = getIdFromRequest(request);
        Person p = personService.getPersonFromId(id);
        return barService.getBarFromPerson(p);
    }
}
