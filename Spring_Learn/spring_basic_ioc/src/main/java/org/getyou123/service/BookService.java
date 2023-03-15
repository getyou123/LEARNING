package org.getyou123.service;

import org.springframework.transaction.annotation.Transactional;

public interface BookService {

    @Transactional
    void buyBook(Integer bookId, Integer userId);
}
