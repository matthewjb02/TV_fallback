package nl.hu.inno.hulp.exam.presentation.controller;

import nl.hu.inno.hulp.commons.request.CourseRequest;
import nl.hu.inno.hulp.commons.response.CourseResponse;
import nl.hu.inno.hulp.commons.response.ExamResponse;
import nl.hu.inno.hulp.exam.application.service.CourseService;
import nl.hu.inno.hulp.exam.domain.question.QuestionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<CourseResponse> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public CourseResponse getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id);
    }

    @PostMapping
    public CourseResponse addCourse(@RequestBody CourseRequest course) {
        return courseService.addCourse(course);
    }

    @DeleteMapping("/{id}")
    public CourseResponse deleteCourse(@PathVariable Long id) {
        return courseService.deleteCourse(id);
    }

    @PostMapping("/{courseId}/teachers/{teacherId}")
    public CourseResponse addTeacherToCourse(@PathVariable Long courseId, @PathVariable Long teacherId) {
        return courseService.addTeacherToCourse(courseId, teacherId);
    }

    @PostMapping("/{courseId}/exams/{examId}")
    public CourseResponse addTestToCourse(@PathVariable Long courseId, @PathVariable Long examId) {
        return courseService.addTestToCourse(courseId, examId);
    }
    @GetMapping("/{courseId}/exams/{examId}")
    public ExamResponse getExamById(@PathVariable Long courseId, @PathVariable Long examId) {
        return courseService.getApprovedExamByCourse(courseId, examId);
    }

    @PutMapping("{id}/exams/{examId}/accept")
    public ExamResponse acceptExam(@PathVariable Long id, @PathVariable Long examId) throws Exception {
        return courseService.acceptExam(examId, id);
    }

    @PutMapping("{id}/exams/{examId}/reject")
    public ExamResponse rejectExam(@PathVariable Long id, @PathVariable Long examId, @RequestBody String reason) throws Exception {
        return courseService.rejectExam(examId, id, reason);
    }

    @GetMapping("/{id}/exams/{examId}/reject/view")
    public ExamResponse viewDeniedExam(@PathVariable Long examId, @PathVariable Long id) throws Exception {
        return courseService.viewDeniedExam(examId, id);
    }

    @PutMapping("/{id}/exams/{examId}/reject/modify")
    public ExamResponse modifyWrongExam(@PathVariable Long examId, @PathVariable Long id, @RequestBody List<QuestionEntity> newQuestions) throws Exception {
        return courseService.modifyWrongExam(examId, id, newQuestions);
    }

    // rpc
    @GetMapping("/exams/{examId}/course")
    public CourseResponse getCourseByExamId(@PathVariable Long examId) {
        return courseService.findCourseResponseByExamId(examId);
    }
}
