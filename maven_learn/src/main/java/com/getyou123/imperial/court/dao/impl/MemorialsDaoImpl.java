package com.getyou123.imperial.court.dao.impl;

import com.getyou123.imperial.court.dao.BaseDao;
import com.getyou123.imperial.court.dao.api.MemorialsDao;
import com.getyou123.imperial.court.entity.Memorials;

import java.util.List;

public class MemorialsDaoImpl extends BaseDao<Memorials> implements MemorialsDao {
    @Override
    public List<Memorials> selectAllMemorialsDigest() {

        String sql = "select memorials_id memorialsId,\n" +
                "       memorials_title memorialsTitle,\n" +
                "       concat(left(memorials_content, 10), \"...\") memorialsContentDigest,\n" +
                "       emp_name memorialsEmpName,\n" +
                "       memorials_create_time memorialsCreateTime,\n" +
                "       memorials_status memorialsStatus\n" +
                "from t_memorials m left join  t_emp e on m.memorials_emp=e.emp_id;";

        return getBeanList(sql, Memorials.class);
    }
}
