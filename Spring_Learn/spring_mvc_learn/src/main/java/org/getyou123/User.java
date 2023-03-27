package org.getyou123;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    String username;
    String password;
    String sex;
    int age;
    String email;
}
