package gr.aueb.elearn.teachersWebApp.service;

import gr.aueb.elearn.teachersWebApp.dto.TeacherDTO;
import gr.aueb.elearn.teachersWebApp.model.Teacher;
import gr.aueb.elearn.teachersWebApp.repositories.TeacherRepository;
import gr.aueb.elearn.teachersWebApp.service.exceptions.TeacherIdAlreadyExistsException;
import gr.aueb.elearn.teachersWebApp.service.exceptions.TeacherNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TeacherServiceImpl implements ITeacherService {
	
	@Autowired
	private TeacherRepository teacherRepository;

	@Override
	public void insertTeacher(TeacherDTO teacherDTO) 
			throws TeacherIdAlreadyExistsException {
		
		Teacher newTeacher = new Teacher();
		newTeacher.setId(Integer.parseInt(teacherDTO.getId()));
		newTeacher.setSname(teacherDTO.getSname());
		newTeacher.setFname(teacherDTO.getFname());	
		
		if (!teacherRepository.existsById(newTeacher.getId()))
			teacherRepository.save(newTeacher);
		else {
			throw new TeacherIdAlreadyExistsException(newTeacher);
		}
	}

	@Override
	public void deleteTeacher(TeacherDTO teacherDTO) throws TeacherNotFoundException {
		int id = Integer.parseInt(teacherDTO.getId());
		Teacher teacherToDelete = teacherRepository.findById(id).orElseThrow(() -> new TeacherNotFoundException(id));

		teacherRepository.delete(teacherToDelete);
	}

	@Override
	public void updateTeacher(TeacherDTO oldTeacherDTO, TeacherDTO newTeacherDTO) throws TeacherNotFoundException {
		int id = Integer.parseInt(oldTeacherDTO.getId());
		Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new TeacherNotFoundException(id));
		teacher.setId(id);
		teacher.setFname(newTeacherDTO.getFname());
		teacher.setSname(newTeacherDTO.getSname());

		teacherRepository.save(teacher);
	}

	@Override
	public Page<Teacher> getTeachers(Pageable pageable) {
		return teacherRepository.findAll(pageable);
	}

	@Override
	public Page<Teacher> getTeachersBySname(int page, int size, String surname) {

		Pageable pageRequest = PageRequest.of(page, size);

		List<Teacher> teachersBySname = teacherRepository.findBySnameStartsWith(surname);
		int start = (int) pageRequest.getOffset();
		int end = Math.min((start + pageRequest.getPageSize()), teachersBySname.size());

		List<Teacher> pageContent = teachersBySname.subList(start, end);
		return new PageImpl<>(pageContent, pageRequest, teachersBySname.size());
	}

}
