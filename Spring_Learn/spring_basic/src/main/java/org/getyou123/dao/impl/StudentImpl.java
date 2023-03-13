package org.getyou123.dao.impl;

import org.getyou123.dao.StudentDao;

public class StudentImpl implements StudentDao {
    @Override
    public int saveStudent() {
        System.out.println("保存成功");
        return 0;
    }
}
