package com.getyou123.imperial.court.service.api;

import com.getyou123.imperial.court.entity.Emp;

public interface EmpService {
    Emp getEmpByLoginAccount(String loginAccount, String loginPassword);
}
