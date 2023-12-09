package gr.aueb.elearn.teachersWebApp.controllers;

import gr.aueb.elearn.teachersWebApp.dto.TeacherDTO;
import gr.aueb.elearn.teachersWebApp.dto.UpdateTeacherDTO;
import gr.aueb.elearn.teachersWebApp.service.ITeacherService;
import gr.aueb.elearn.teachersWebApp.service.exceptions.TeacherIdAlreadyExistsException;
import gr.aueb.elearn.teachersWebApp.service.exceptions.TeacherNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;

@Controller
@RequestMapping("/teachers")
public class TeachersController {


    @Autowired
    private ITeacherService teacherService;

    @GetMapping("")
    public String showTeachersMenu() {
        return "teachers/teachers-menu";
    }

    @GetMapping("/insert")
    public String showInsertForm(Model model) {
        TeacherDTO teacherDTO = new TeacherDTO();
        model.addAttribute("teacherDTO", teacherDTO);
        model.addAttribute("showModal", false);
        return "teachers/insert-form";
    }

    @PostMapping("/insert")
    public String submitForm(@ModelAttribute("teacherDTO") TeacherDTO teacherDTO, Model model) {
        model.addAttribute("showModal", true);
        try {
            teacherService.insertTeacher(teacherDTO);
            model.addAttribute("existsFlag", false);
            model.addAttribute("teacherDTO", new TeacherDTO());
        } catch (TeacherIdAlreadyExistsException e) {
            model.addAttribute("existsFlag", true);
        } catch (SQLException e) {
            model.addAttribute("errorFlag", true);
        }
        return "teachers/insert-form";
    }

    @GetMapping("/search")
    public String showSearchForm(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(required = false) Integer recordsToShow, @RequestParam(required = false, defaultValue = "") String searchTerm,HttpSession httpSession, @ModelAttribute("teacherSurname") String teacherSurname) {

        if(page < 1) page=1;

        Integer recordsToShowSession = (Integer)httpSession.getAttribute("recordsToShowSession");
        int pageSize = recordsToShow == null ? (recordsToShowSession == null ? 10 : recordsToShowSession) : recordsToShow;

        httpSession.setAttribute("recordsToShowSession", pageSize);

        if(searchTerm.trim().isEmpty()) {
            model.addAttribute("teachersList", teacherService.getTeachers(PageRequest.of(page-1, pageSize)));
        } else {
            model.addAttribute("teachersList", teacherService.getTeachersBySname(0, pageSize, searchTerm.trim()));
        }

        model.addAttribute("currentPage", page);
        model.addAttribute("recordsToShow", pageSize);
        model.addAttribute("searchTerm", searchTerm);
        model.addAttribute("updateTeacherDTO", new UpdateTeacherDTO());
        model.addAttribute("deleteTeacherDTO", new TeacherDTO());

        return "teachers/search-form";
    }

    @PostMapping("/search")
    public String submitUpdateForm(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(required = false) Integer recordsToShow,
                                   HttpSession httpSession, @ModelAttribute("updateTeacherDTO") UpdateTeacherDTO updateTeacherDTO) {

        if(page < 1) page=1;

        Integer recordsToShowSession = (Integer)httpSession.getAttribute("recordsToShowSession");
        int pageSize = recordsToShow == null ? (recordsToShowSession == null ? 10 : recordsToShowSession) : recordsToShow;

        httpSession.setAttribute("recordsToShowSession", pageSize);

        TeacherDTO oldTeacherDTO = new TeacherDTO(updateTeacherDTO.getId(), updateTeacherDTO.getOldFname(), updateTeacherDTO.getOldSname());
        TeacherDTO newTeacherDTO = new TeacherDTO(updateTeacherDTO.getId(), updateTeacherDTO.getNewFname(), updateTeacherDTO.getNewSname());

        try {
            teacherService.updateTeacher(oldTeacherDTO, newTeacherDTO);
        } catch (TeacherNotFoundException e) {
            throw new RuntimeException(e);
        }

        model.addAttribute("teachersList", teacherService.getTeachers(PageRequest.of(page-1, pageSize)));
        model.addAttribute("currentPage", page);
        model.addAttribute("recordsToShow", pageSize);
        model.addAttribute("searchTerm", "");

        return "teachers/search-form";
    }

    @PostMapping("/delete")
    public String deleteTeacher(@ModelAttribute("deleteTeacherDTO") TeacherDTO deleteTeacherDTO, RedirectAttributes redirectAttributes, HttpSession httpSession) {

        try {
            teacherService.deleteTeacher(deleteTeacherDTO);
        } catch (TeacherNotFoundException e) {
            throw new RuntimeException(e);
        }

        Integer recordsToShowSession = (Integer)httpSession.getAttribute("recordsToShowSession");
        int pageSize = recordsToShowSession == null ? 10 : recordsToShowSession;

        redirectAttributes.addFlashAttribute("teachersList", teacherService.getTeachers(PageRequest.of(0, pageSize)));
        redirectAttributes.addFlashAttribute("currentPage", 1);


        return "redirect:/teachers/search";
    }

    @GetMapping("/v2")
    public String showRestTeachersMenu() {
        return "teachersAPI/teachers";
    }


}
