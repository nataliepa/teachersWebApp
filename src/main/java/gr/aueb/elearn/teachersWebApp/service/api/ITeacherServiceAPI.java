package gr.aueb.elearn.teachersWebApp.service.api;

import gr.aueb.elearn.teachersWebApp.dto.TeacherDTO;
import gr.aueb.elearn.teachersWebApp.model.Teacher;
import gr.aueb.elearn.teachersWebApp.service.exceptions.TeacherIdAlreadyExistsException;
import gr.aueb.elearn.teachersWebApp.service.exceptions.TeacherNotFoundException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ITeacherServiceAPI {

	Teacher insertTeacher(TeacherDTO teacherDTO) throws TeacherIdAlreadyExistsException, SQLException;

	void deleteTeacher(int id) throws TeacherNotFoundException;
	Teacher updateTeacher(int id, TeacherDTO newTeacherDTO) throws TeacherNotFoundException;
	List<Teacher> getTeachers();
	Optional<Teacher> getTeacher(Integer id);
	List<Teacher> getTeachersBySurname(String surname);
}
