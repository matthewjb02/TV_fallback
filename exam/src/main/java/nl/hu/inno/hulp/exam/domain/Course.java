package nl.hu.inno.hulp.exam.domain;

import jakarta.persistence.*;
import lombok.Getter;
import nl.hu.inno.hulp.commons.enums.ValidationStatus;
import nl.hu.inno.hulp.exam.domain.question.QuestionEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Course {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Lob
    private List<Long> teacherIds =new ArrayList<>();
    @OneToMany
    private List<Exam> approvedExams =new ArrayList<>();
    @OneToMany
    private List<Exam> validatingExams =new ArrayList<>();
    @OneToMany
    private List<Exam> rejectedExams =new ArrayList<>();

    protected Course(){

    }

    public Course(String name){
        this.name = name;
    }

    public void addTeacher(Long teacherId){
        teacherIds.add(teacherId);
    }

    public void addExam(Exam exam){
        if (exam.getValidationStatus().equals(ValidationStatus.APPROVED)){
        approvedExams.add(exam);}
        else if(exam.getValidationStatus().equals(ValidationStatus.WAITING)){
            validatingExams.add(exam);
        } else if (exam.getValidationStatus().equals(ValidationStatus.DENIED) ) {
            rejectedExams.add(exam);
        }
    }

    private boolean doesTeacherTeachCourse(Exam exam) throws Exception {

        if (this.getTeacherIds().contains(exam.getExamMakerId()) && exam.getExamMakerId()!=exam.getExamValidatorId() ||
                this.getTeacherIds().contains(exam.getExamValidatorId())&&exam.getExamValidatorId() != exam.getExamMakerId()) {
            return true;
        } else throw new Exception("The Teacher does not teach this course");
    }

    private boolean canIApproveThisExam(Long examValidatorId, Exam exam) throws Exception {
        if (getValidatingExams().contains(exam) && exam.getExamValidatorId() == examValidatorId){
            return true;
        }
        else if(exam.getExamValidatorId() != examValidatorId && getValidatingExams().contains(exam)){
            throw new Exception("The Teacher is not assigned as validator, but the exam needs to be Validated");
        }
        else throw new Exception("The exam cannot be validated");
    }

    public void approveExam(Exam exam, Long examValidatorId) throws Exception {
        if (doesTeacherTeachCourse(exam)&& canIApproveThisExam(examValidatorId,exam)){
            exam.setValidationStatus(ValidationStatus.APPROVED);
            this.getValidatingExams().remove(exam);
            this.getApprovedExams().add(exam);
        }
    }

    public void rejectExam(Exam exam, Long examValidatorId, String reason) throws Exception {
        if (doesTeacherTeachCourse(exam)&& canIApproveThisExam(examValidatorId, exam)){
            exam.setValidationStatus(ValidationStatus.DENIED);
            this.getValidatingExams().remove(exam);
            this.getRejectedExams().add(exam);
            exam.setReason(reason);
        }
    }

    public void viewWrongExam(Exam exam) throws Exception {
        if (this.getRejectedExams().contains(exam)){
            System.out.println(exam.getReason());
        }
        else throw new Exception("This exam was not rejected");
    }

    public void modifyQuestions(Exam exam, List<QuestionEntity> oldQuestions, List<QuestionEntity> newQuestion) {
        if (this.getRejectedExams().contains(exam)){
            exam.removeQuestions(oldQuestions);
            exam.addQuestions(newQuestion);
            this.getValidatingExams().add(exam);
            this.getRejectedExams().remove(exam);
            exam.setValidationStatus(ValidationStatus.WAITING);
            exam.setReason("");
        }
    }
}
