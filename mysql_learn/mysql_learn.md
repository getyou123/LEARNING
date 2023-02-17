### mysql5.7 和 mysql8
- 驱动类不同 
  - 5.7 com.mysql.jdbc.Driver
  - 8 com.mysql.cj.jdbc.Driver
- 连接地址
  - 5 jdbc:mysql://localhost:3306/ssm
  - 8 jdbc:mysql://localhost:3306/ssm?serverTimezone=UTC 
    - 需要设置时区否则报错 java.sql.SQLException: The server time zone value 'ÖÐ¹ú±ê×¼Ê±¼ä' is unrecognized 
- 设置相应数据编码
