package com.example.demo.repository;


import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean //generic repository serve per venire esteso
public interface ReadOnlyRepository<T,ID> extends Repository<T,ID> {
    Optional<T> findById(ID id);
    List<T> findAll();
}
