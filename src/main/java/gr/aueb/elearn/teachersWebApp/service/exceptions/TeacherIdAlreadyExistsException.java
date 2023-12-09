package gr.aueb.elearn.teachersWebApp.service.exceptions;
import gr.aueb.elearn.teachersWebApp.model.Teacher;

import java.io.Serial;

public class TeacherIdAlreadyExistsException extends Exception {

	@Serial
	private static final long serialVersionUID = 1L;

	public TeacherIdAlreadyExistsException(Teacher teacher) {
		super("Teacher with id = " + teacher.getId() + " already exist");
	}

}