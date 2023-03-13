package org.getyou123.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.getyou123.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Controller
public class StudentController {
    @Autowired
    private StudentService studentService;

    public void saveUser() {
        studentService.saveStudent();
    }
}
