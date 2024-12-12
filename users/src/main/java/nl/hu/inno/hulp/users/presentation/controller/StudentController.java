package nl.hu.inno.hulp.users.presentation.controller;

import nl.hu.inno.hulp.commons.request.ExtraTimeRequest;
import nl.hu.inno.hulp.commons.request.StudentRequest;
import nl.hu.inno.hulp.commons.response.StudentResponse;
import nl.hu.inno.hulp.users.application.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    public StudentResponse getStudent(@PathVariable String id) {
        return studentService.getStudentResponse(id);
    }

    @PostMapping("/add")
    public StudentResponse addStudent(@RequestBody StudentRequest studentRequest) {
        return studentService.addStudent(studentRequest);
    }

    @PatchMapping("/extratime")
    public StudentResponse changeExtraTimeRight(@RequestBody ExtraTimeRequest studentRequest) {
        return studentService.changeExtraTimeRight(studentRequest);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteStudent(@PathVariable String id) {
        studentService.deleteStudent(id);
    }
}
