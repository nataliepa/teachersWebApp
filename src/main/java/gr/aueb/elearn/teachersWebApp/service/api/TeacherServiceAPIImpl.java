package gr.aueb.elearn.teachersWebApp.service.api;

import gr.aueb.elearn.teachersWebApp.dto.TeacherDTO;
import gr.aueb.elearn.teachersWebApp.model.Teacher;
import gr.aueb.elearn.teachersWebApp.repositories.TeacherRepository;
import gr.aueb.elearn.teachersWebApp.service.exceptions.TeacherIdAlreadyExistsException;
import gr.aueb.elearn.teachersWebApp.service.exceptions.TeacherNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TeacherServiceAPIImpl implements ITeacherServiceAPI {
	
	@Autowired
	private TeacherRepository teacherRepository;

	@Override
	public Teacher insertTeacher(TeacherDTO teacherDTO)
			throws TeacherIdAlreadyExistsException {
		
		Teacher newTeacher = new Teacher();
		newTeacher.setId(Integer.parseInt(teacherDTO.getId()));
		newTeacher.setSname(teacherDTO.getSname());
		newTeacher.setFname(teacherDTO.getFname());	
		
		if (!teacherRepository.existsById(newTeacher.getId()))
			return teacherRepository.save(newTeacher);
		else {
			throw new TeacherIdAlreadyExistsException(newTeacher);
		}
	}

	@Override
	public void deleteTeacher(int id) throws TeacherNotFoundException {

		Teacher teacherToDelete = teacherRepository.findById(id).orElseThrow(() -> new TeacherNotFoundException(id));

		teacherRepository.delete(teacherToDelete);
	}

	@Override
	public Teacher updateTeacher(int id, TeacherDTO newTeacherDTO) throws TeacherNotFoundException {

			Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new TeacherNotFoundException(id));
			teacher.setId(id);
			teacher.setFname(newTeacherDTO.getFname());
			teacher.setSname(newTeacherDTO.getSname());

			return teacherRepository.save(teacher);
	}

	@Override
	public List<Teacher> getTeachers() {
		return (List<Teacher>) teacherRepository.findAll();
	}
	@Override
	public Optional<Teacher> getTeacher(Integer id) {
		return teacherRepository.findById(id);
	}

	@Override
	public List<Teacher> getTeachersBySurname(String surname) {
		return teacherRepository.findBySnameStartsWith(surname);
	}

}
