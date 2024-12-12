package nl.hu.inno.hulp.users.presentation.controller;

import nl.hu.inno.hulp.commons.request.TeacherRequest;
import nl.hu.inno.hulp.commons.response.TeacherResponse;
import nl.hu.inno.hulp.users.application.service.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeacherResponse saveTeacher(@RequestBody TeacherRequest teacher) {
       return teacherService.saveTeacher(teacher);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public TeacherResponse getTeacher(@PathVariable String id) {
        return teacherService.getTeacherResponse(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteTeacher(@PathVariable String id) {
        teacherService.removeTeacher(id);}
}