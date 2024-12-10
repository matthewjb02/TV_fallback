package nl.hu.inno.hulp.consumer;

import nl.hu.inno.hulp.users.application.service.StudentService;
import nl.hu.inno.hulp.users.application.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(UsersConsumer.class);

    private StudentService studentService;
    private TeacherService teacherService;

    @Autowired
    public UsersConsumer(StudentService studentService) {
        this.studentService = studentService;
    }

    @RabbitListener(queues = {"examination"})
    public void consumeStudentId(Long studentId) {
        LOGGER.info(String.format("Received message -> %s", studentId));
        studentService.processAndSendStudentResponse(studentId);
    }
//    @RabbitListener(queues = {"exam"})
//    public void consumeTeacherId(Long teacherId) {
//        LOGGER.info(String.format("Received message -> %s", teacherId));
//        teacherService.processAndSendTeacherResponse(teacherId);
//    }
}