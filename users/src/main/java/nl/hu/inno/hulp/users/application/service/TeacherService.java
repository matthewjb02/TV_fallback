package nl.hu.inno.hulp.users.application.service;

import nl.hu.inno.hulp.commons.request.TeacherRequest;
import nl.hu.inno.hulp.commons.response.TeacherResponse;
import nl.hu.inno.hulp.publisher.UsersProducer;
import nl.hu.inno.hulp.users.data.TeacherRepository;
import nl.hu.inno.hulp.users.domain.Teacher;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final UsersProducer usersProducer;

    public TeacherService(TeacherRepository teacherRepository, UsersProducer usersProducer) {
        this.teacherRepository = teacherRepository;
        this.usersProducer = usersProducer;
    }

    public Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id).orElseThrow();
    }

    public TeacherResponse getTeacherResponse(Long id) {
        Teacher teacher = getTeacherById(id);
        return new TeacherResponse(teacher.getId(), teacher.getFirstName(), teacher.getLastName(),
                teacher.getEmail().getEmailString());
    }

    public TeacherResponse saveTeacher(TeacherRequest teacherRequest) {
        Teacher teacher = new Teacher(teacherRequest.firstName(), teacherRequest.lastName(), teacherRequest.email());
        teacherRepository.save(teacher);
        return getTeacherResponse(teacher.getId());
    }
    public void processAndSendTeacherResponse(Long id){
        this.usersProducer.sendTeacherResponse(getTeacherResponse(id));
    }
    public void removeTeacher(Long id ) {
        teacherRepository.deleteById(id);
    }
}