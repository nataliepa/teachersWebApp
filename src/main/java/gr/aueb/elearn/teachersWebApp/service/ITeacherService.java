package gr.aueb.elearn.teachersWebApp.service;

import gr.aueb.elearn.teachersWebApp.dto.TeacherDTO;
import gr.aueb.elearn.teachersWebApp.model.Teacher;
import gr.aueb.elearn.teachersWebApp.service.exceptions.TeacherIdAlreadyExistsException;
import gr.aueb.elearn.teachersWebApp.service.exceptions.TeacherNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.SQLException;

public interface ITeacherService {	
	
	/**
	 * Inserts a {@link Teacher} based on the data carried by the
	 * {@link TeacherDTO}.
	 * 
	 * @param teacherDTO 
	 * 			DTO object that contains the data.
	 * @throws TeacherIdAlreadyExistsException
	 * 			if any Teacher identified by their id 
	 * 			needed to be inserted has been already
	 * 			inserted. 
	 * @throws SQLException
	 * 			if any error happens between the driver
	 * 			and the server.
	 */
	void insertTeacher(TeacherDTO teacherDTO) 
			throws TeacherIdAlreadyExistsException, SQLException;
	
	/**
	 * Deletes a {@link Teacher} based on the data carried by the
	 * {@link TeacherDTO}.
	 * 
	 * @param teacherDTO
	 * 			DTO object that contains the data.
	 * @throws TeacherNotFoundException
	 * 			if any Teacher identified by their id
	 * 			needed to be inserted has been already
	 * 			inserted.
	 */
	void deleteTeacher(TeacherDTO teacherDTO)
			throws TeacherNotFoundException;
	
	
	/**
	 * Updates a {@link Teacher} based on the data carried by the
	 * {@link TeacherDTO}.
	 * 
	 * @param oldTeacherDTO
	 * 			DTO object that contains the data -mainly the id-
	 * 			of the {@link Teacher} that will be updated.  
	 * @param newTeacherDTO
	 * 			DTO object that contains the data of the 
	 * 			new {@link Teacher}.
	 * @throws TeacherNotFoundException
	 * 			if any Teacher identified by their id 
	 * 			was not found.
	 */
	void updateTeacher(TeacherDTO oldTeacherDTO, TeacherDTO newTeacherDTO) 
			throws TeacherNotFoundException;


	Page<Teacher> getTeachers(Pageable pageable);

	Page<Teacher> getTeachersBySname(int page, int size, String surname);
}
