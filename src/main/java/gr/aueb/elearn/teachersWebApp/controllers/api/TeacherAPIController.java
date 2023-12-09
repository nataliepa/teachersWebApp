package gr.aueb.elearn.teachersWebApp.controllers.api;

import gr.aueb.elearn.teachersWebApp.assembler.TeacherModelAssembler;
import gr.aueb.elearn.teachersWebApp.dto.TeacherDTO;
import gr.aueb.elearn.teachersWebApp.model.Teacher;
import gr.aueb.elearn.teachersWebApp.service.api.ITeacherServiceAPI;
import gr.aueb.elearn.teachersWebApp.service.exceptions.TeacherIdAlreadyExistsException;
import gr.aueb.elearn.teachersWebApp.service.exceptions.TeacherNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class TeacherAPIController {

    @Autowired
    ITeacherServiceAPI teacherServiceAPI;

    @Autowired
    TeacherModelAssembler assembler;

    @GetMapping("/teachers")
    public CollectionModel<EntityModel<Teacher>> getAllTeachers() {
        List<EntityModel<Teacher>> teachers = teacherServiceAPI.getTeachers().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(teachers, linkTo(methodOn(TeacherAPIController.class).getAllTeachers()).withSelfRel());
    }

    @GetMapping("/teachers/{id}")
    public EntityModel<Teacher> getById(@PathVariable Integer id) {

        Teacher teacher = teacherServiceAPI.getTeacher(id)
                .orElseThrow(() -> new TeacherNotFoundException(id));

        return assembler.toModel(teacher);
    }

    @GetMapping(value = "/teachers", params = "searchTerm")
    public CollectionModel<EntityModel<Teacher>> getByLastname(@RequestParam String searchTerm) {
        List<EntityModel<Teacher>> teachers = teacherServiceAPI.getTeachersBySurname(searchTerm).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(teachers, linkTo(methodOn(TeacherAPIController.class).getAllTeachers()).withSelfRel());
    }

    @PostMapping("/teachers")
    ResponseEntity<?> insertTeacher(@RequestBody TeacherDTO teacherDTO) throws TeacherIdAlreadyExistsException, SQLException {

        EntityModel<Teacher> entityModel = assembler.toModel(teacherServiceAPI.insertTeacher(teacherDTO));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @PutMapping("/teachers/{id}")
    public ResponseEntity<?> updateTeacher(@PathVariable Integer id, @RequestBody TeacherDTO teacherDTO) {

        EntityModel<Teacher> entityModel = assembler.toModel(teacherServiceAPI.updateTeacher(id, teacherDTO));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/teachers/{id}")
    ResponseEntity<?> deleteTeacher(@PathVariable int id) {

        teacherServiceAPI.deleteTeacher(id);

        return ResponseEntity.noContent().build();
    }
}
