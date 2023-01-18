package org.jdbc_basic;

public class t_emp {
    public t_emp() {
    }

    public int getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(int emp_id) {
        this.emp_id = emp_id;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public String getEmp_position() {
        return emp_position;
    }

    public void setEmp_position(String emp_position) {
        this.emp_position = emp_position;
    }

    public String getLogin_account() {
        return login_account;
    }

    public void setLogin_account(String login_account) {
        this.login_account = login_account;
    }

    @Override
    public String toString() {
        return "" + emp_id + "\t" + emp_name + "\t" + emp_position + "\t" + login_account;
    }

    private int emp_id;
    private String emp_name;
    private String emp_position;
    private String login_account;


}
