package org.getyou123.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
        private Integer empId;
        private String empName;
        private Integer age;
        private String sex;
        private String email;
}
