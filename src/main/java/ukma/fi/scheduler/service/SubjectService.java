package ukma.fi.scheduler.service;

import ukma.fi.scheduler.entities.Subject;

import java.util.List;

public interface SubjectService {

    Subject findSubjectById(Long id);

    List<Subject> findSubjectByIdIn(List<Long> id);

    Subject create(Subject subject);

    List<Subject> findAll();

}
