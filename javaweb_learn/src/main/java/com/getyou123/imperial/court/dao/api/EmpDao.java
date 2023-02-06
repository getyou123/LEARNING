package com.getyou123.imperial.court.dao.api;

import com.getyou123.imperial.court.entity.Emp;

public interface EmpDao {
    Emp selectEmpByLoginAccount(String loginAccount, String encodedLoginPassword);
}
