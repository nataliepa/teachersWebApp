package gr.aueb.elearn.teachersWebApp.service.exceptions;

import java.io.Serial;

public class TeacherNotFoundException extends RuntimeException {
	
	@Serial
	private static final long serialVersionUID = 1L;

	public TeacherNotFoundException(Integer id) {
		super("Teacher with id = " + id + " does no exist");
	}

}
