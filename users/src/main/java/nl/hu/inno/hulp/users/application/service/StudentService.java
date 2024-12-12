package nl.hu.inno.hulp.users.application.service;

import nl.hu.inno.hulp.commons.request.ExtraTimeRequest;
import nl.hu.inno.hulp.commons.request.StudentRequest;
import nl.hu.inno.hulp.commons.response.StudentResponse;
import nl.hu.inno.hulp.publisher.UsersProducer;
import nl.hu.inno.hulp.users.data.StudentRepository;
import nl.hu.inno.hulp.users.domain.Student;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final UsersProducer usersProducer;

    public StudentService(StudentRepository studentRepository, UsersProducer usersProducer) {
        this.studentRepository = studentRepository;
        this.usersProducer = usersProducer;
    }

    public Student getStudentById(String id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No student with id: " + id + " found!"));
    }

    public StudentResponse getStudentResponse(String id) {
        Student student = getStudentById(id);
        return new StudentResponse(student.getId(), student.getFirstName(), student.getLastName(),
                student.isExtraTimeRight(), student.getEmail().getEmailString());
    }

    public void processAndSendStudentResponse(String id) {
        usersProducer.sendStudentResponse(getStudentResponse(id));
    }

    public StudentResponse addStudent(StudentRequest studentRequest) {
        Student student = new Student(studentRequest.firstName, studentRequest.lastName,
                studentRequest.extraTimeRight,
                studentRequest.email);
        studentRepository.save(student);

        return getStudentResponse(student.getId());
    }

    public StudentResponse changeExtraTimeRight(ExtraTimeRequest studentRequest) {
        Student student = getStudentById(studentRequest.studentId);
        student.changeExtraTimeRight(studentRequest.extraTime);
        studentRepository.save(student);

        return getStudentResponse(student.getId());
    }

    public void deleteStudent(String id) {
        studentRepository.delete(getStudentById(id));
    }
}