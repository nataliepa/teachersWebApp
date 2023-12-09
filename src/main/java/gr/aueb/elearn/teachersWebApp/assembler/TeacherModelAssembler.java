package gr.aueb.elearn.teachersWebApp.assembler;

import gr.aueb.elearn.teachersWebApp.controllers.api.TeacherAPIController;
import gr.aueb.elearn.teachersWebApp.model.Teacher;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TeacherModelAssembler implements RepresentationModelAssembler<Teacher, EntityModel<Teacher>> {

    @Override
    @NonNull
    public EntityModel<Teacher> toModel(@NonNull Teacher teacher) {
        return EntityModel.of(teacher, //
                linkTo(methodOn(TeacherAPIController.class).getById(teacher.getId())).withSelfRel(),
                linkTo(methodOn(TeacherAPIController.class).getAllTeachers()).withRel("teachers"));
    }
}
