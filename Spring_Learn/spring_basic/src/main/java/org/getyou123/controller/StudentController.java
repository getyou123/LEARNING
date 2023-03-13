package org.getyou123.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.getyou123.service.StudentService;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentController {
    private StudentService studentService;

    public void saveUser() {
        studentService.saveStudent();
    }
}
