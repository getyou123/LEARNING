package com.getyou123.imperial.court.dao.api;

import com.getyou123.imperial.court.entity.Memorials;

import java.util.List;

public interface MemorialsDao {
    List<Memorials> selectAllMemorialsDigest();
}
