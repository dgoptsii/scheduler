package ukma.fi.scheduler.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ukma.fi.scheduler.entities.Lesson;
import ukma.fi.scheduler.entities.Subject;
import ukma.fi.scheduler.entities.User;
import ukma.fi.scheduler.repository.LessonRepository;
import ukma.fi.scheduler.service.ScheduleService;
import ukma.fi.scheduler.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private UserService userService;

    @Autowired
    private LessonRepository lessonRepository;

    private void addLecturesToResult(Map<String, Lesson> result, List<Subject> subjects){
        List<Lesson> normativeLectures = lessonRepository.findLessonsBySubjectInAndGroupNumber(subjects, 0);
        normativeLectures.forEach(lecture -> {
            result.put(lecture.getDayOfWeek() + "-" + lecture.getLessonNumber(), lecture);
        });
    }

    private void addLessonsToResult(Map<String, Lesson> result, Map<Subject, Integer> lessons){
        lessons.forEach((subject, groupN) -> {
            List<Lesson> normativeLectures = lessonRepository.findBySubjectAndGroupNumber(subject, groupN);
            normativeLectures.forEach(lesson -> {
                result.put(lesson.getDayOfWeek() + "-" + lesson.getLessonNumber(), lesson);
            });
        });
    }

    @Override
    public Map<String, Lesson> findLessonsForStudent(String login) {
        User user = userService.findUserByLogin(login);
        Map<String, Lesson> res = new HashMap<>();

        Set<Subject> subjectsLectures = user.getGroups().keySet();
        subjectsLectures.addAll(userService.findNormativeSubjects(login));

        addLecturesToResult(res, new ArrayList<>(subjectsLectures));
        addLessonsToResult(res, user.getGroups());

        return res;
    }

    public Map<String, Lesson> findLessonsForTeacher(String login) {
        User user = userService.findUserByLogin(login);
        Map<String, Lesson> res = new HashMap<>();

        return res;
    }

}
