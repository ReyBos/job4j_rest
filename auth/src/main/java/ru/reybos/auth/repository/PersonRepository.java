package ru.reybos.auth.repository;

import org.springframework.data.repository.CrudRepository;
import ru.reybos.auth.model.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> { }