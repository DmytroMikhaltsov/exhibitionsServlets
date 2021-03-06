package com.project.web.commands;

import com.project.services.UserService;
import com.project.services.ValidatorService;
import com.project.web.forms.RegistrationForm;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationCommand extends AbstractCommand {
    private static final Logger LOG = Logger.getLogger(RegistrationCommand.class);

    @Override
    protected String executeGet(HttpServletRequest request, HttpServletResponse response) {
        return "registration.jsp";
    }

    @Override
    protected String executePost(HttpServletRequest request, HttpServletResponse response) {
        RegistrationForm form = getRegistrationForm(request);

        if (ValidatorService.validate(form)) {
            if (!UserService.isUserExist(form.getEmail())) {
                UserService.createUser(form);
                return "redirect:login";
            } else {
                LOG.info("Already exist user with this email: " + form.getEmail());
                request.setAttribute("errorMessage", "User already exist");
            }
        }
        request.setAttribute("errorMessage", "Invalid Data");
        return "registration.jsp";
    }

    private RegistrationForm getRegistrationForm(HttpServletRequest request) {
        return new RegistrationForm(request.getParameter("name"), request.getParameter("email"),
                request.getParameter("password"), request.getParameter("confirmPassword"));
    }
}
