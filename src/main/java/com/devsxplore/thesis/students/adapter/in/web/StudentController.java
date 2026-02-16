package com.devsxplore.thesis.students.adapter.in.web;

import com.devsxplore.thesis.accounts.adapter.in.security.CurrentUser;
import com.devsxplore.thesis.students.adapter.in.web.dto.request.ChangeCourseDTO;
import com.devsxplore.thesis.students.adapter.in.web.dto.request.ChangeInterestDTO;
import com.devsxplore.thesis.students.adapter.in.web.dto.request.RegisterStudentDTO;
import com.devsxplore.thesis.students.adapter.in.web.dto.request.UpdateStudentProfileDTO;
import com.devsxplore.thesis.students.adapter.in.web.mapper.StudentRequestMapper;
import com.devsxplore.thesis.students.adapter.in.web.mapper.StudentResponseMapper;
import com.devsxplore.thesis.students.application.port.in.usecase.*;
import com.devsxplore.thesis.students.domain.model.Student;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class StudentController {

    private final StudentRequestMapper requestMapper;
    private final StudentResponseMapper responseMapper;
    private final RegisterStudentUseCase registerStudentUseCase;
    private final LoadStudentByUserIdUseCase loadStudentByUserIdUseCase;
    private final UpdateStudentProfileUseCase updateStudentProfileUseCase;
    private final AddCourseUseCase addCourseUseCase;
    private final RemoveCourseUseCase removeCourseUseCase;
    private final AddInterestUseCase addInterestUseCase;
    private final RemoveInterestUseCase removeInterestUseCase;

    @GetMapping("/register")
    public String showRegisterStudentForm() {
        return "student/registerStudentForm";
    }

    @PostMapping("/register")
    public String registerStudent(@AuthenticationPrincipal CurrentUser user, @Valid RegisterStudentDTO dto) {
        registerStudentUseCase.registerStudent(
                requestMapper.mapToRegisterStudentCommand(user.githubId(), dto)
        );
        return "redirect:/student/profile";
    }

    @GetMapping("/profile")
    public String showProfile(@AuthenticationPrincipal CurrentUser user, Model model) {
        Student student = loadStudentByUserIdUseCase.loadStudentByStudentUserId(
                requestMapper.mapToLoadStudentByUserIdCommand(user.githubId())
        );
        model.addAttribute("student", responseMapper.mapToUserProfileDTO(student));
        return "student/showProfile";
    }

    @PutMapping("/profile/update")
    public String editProfile(@AuthenticationPrincipal CurrentUser user, @Valid UpdateStudentProfileDTO dto) {
        updateStudentProfileUseCase.updateProfile(
                requestMapper.mapToUpdateStudentProfileCommand(user.githubId(), dto)
        );
        return "redirect:/student/profile";
    }

    @PostMapping("/course")
    public String addCourse(@AuthenticationPrincipal CurrentUser user, @Valid ChangeCourseDTO dto) {
        addCourseUseCase.addCourse(
                requestMapper.mapToChangeCourseCommand(user.githubId(), dto)
        );
        return "redirect:/student/profile";
    }

    @DeleteMapping("/course")
    public String removeCourse(@AuthenticationPrincipal CurrentUser user, @Valid ChangeCourseDTO dto) {
        removeCourseUseCase.removeCourse(
                requestMapper.mapToChangeCourseCommand(user.githubId(), dto)
        );
        return "redirect:/student/profile";
    }

    @PostMapping("/interest")
    public String addInterest(@AuthenticationPrincipal CurrentUser user, @Valid ChangeInterestDTO dto) {
        addInterestUseCase.addInterest(
                requestMapper.mapToChangeInterestCommand(user.githubId(), dto)
        );
        return "redirect:/student/profile";
    }

    @DeleteMapping("/interest")
    public String removeInterest(@AuthenticationPrincipal CurrentUser user, @Valid ChangeInterestDTO dto) {
        removeInterestUseCase.removeInterest(
                requestMapper.mapToChangeInterestCommand(user.githubId(), dto)
        );
        return "redirect:/student/profile";
    }
}
