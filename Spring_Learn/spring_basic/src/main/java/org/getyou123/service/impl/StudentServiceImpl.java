package org.getyou123.service.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.getyou123.dao.StudentDao;
import org.getyou123.service.StudentService;
import org.springframework.stereotype.Service;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {
    private StudentDao studentDao;

    @Override
    public void saveStudent() {
        studentDao.saveStudent();
    }
}