package org.getyou123;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    String username;
    String password;
    String sex;
    int age;
    String email;
}
