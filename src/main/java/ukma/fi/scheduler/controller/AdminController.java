package ukma.fi.scheduler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ukma.fi.scheduler.entities.User;
import ukma.fi.scheduler.service.AuthService;

import javax.validation.Valid;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private AuthService authService;

    @GetMapping("add_teacher")
    public ModelAndView addTeacherPage(){
        return new ModelAndView("teacher_registration");
    }

    @PostMapping("add_teacher")
    public RedirectView addTeacher(@Valid @ModelAttribute User user) throws Exception{
        authService.registration(user, "TEACHER");
        return new RedirectView("/admin/add_teacher");
    }

    @ModelAttribute("currentUser")
    public UserDetails getCurrentUser(Authentication authentication) {
        return (authentication == null) ? null : (UserDetails) authentication.getPrincipal();
    }



}
