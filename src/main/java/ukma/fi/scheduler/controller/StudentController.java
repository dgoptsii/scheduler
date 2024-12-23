package ukma.fi.scheduler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ukma.fi.scheduler.controller.dto.SubjectGroupListDTO;
import ukma.fi.scheduler.entities.Subject;
import ukma.fi.scheduler.service.ScheduleService;
import ukma.fi.scheduler.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("student")
public class StudentController {

    @Autowired
    private UserService userService;

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/subject/groups")
    public ModelAndView addStudentGroup(Principal principal) {
        ModelAndView mav = new ModelAndView("student-add-group");
        mav.addObject("form", userService.getSubjectGroupDTOS(principal.getName()));
        return mav;
    }

    @PostMapping("/subject/groups")
    public RedirectView addStudentGroup(@ModelAttribute SubjectGroupListDTO form, Principal principal) throws Exception {
        userService.editSubjectGroup(principal.getName(), form);
        return new RedirectView("/student/subject/groups");
    }

    @GetMapping("/subject/add")
    public ModelAndView addStudentSubject(Principal principal) {
        ModelAndView mav = new ModelAndView("student-add-subject");
        List<Subject> addSubjects = userService.findNonNormativeFreeSubjects(principal.getName());
        List<Subject> userNonNormative = userService.findNonNormativeSubjects(principal.getName());
        List<Subject> userNormative = userService.findNormativeSubjects(principal.getName());
        mav.addObject("addSubjects", addSubjects);
        mav.addObject("userSubjects", userNonNormative);
        mav.addObject("userNormative", userNormative);
        return mav;
    }

    @PostMapping("/subject/{id}")
    public RedirectView addNonNormativeGroup(@PathVariable Long id, Principal principal) throws Exception {
        userService.addNonNormativeGroup(principal.getName(), id);
        return new RedirectView("/student/subject/add");
    }

    @DeleteMapping("/subject/{id}")
    public RedirectView deleteNonNormativeGroup(@PathVariable Long id, Principal principal) throws Exception {
        userService.deleteNonNormativeGroup(principal.getName(), id);
        return new RedirectView("/student/subject/add");
    }

    @GetMapping("/scheduler")
    public ModelAndView schedule(Principal principal) {
        ModelAndView mav = new ModelAndView("schedule");
        mav.addAllObjects(scheduleService.findLessonsForStudent(principal.getName()));
        return mav;
    }

    @ModelAttribute("currentUser")
    public UserDetails getCurrentUser(Authentication authentication) {
        return (authentication == null) ? null : (UserDetails) authentication.getPrincipal();
    }

}
