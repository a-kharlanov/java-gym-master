package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        SortedMap<TimeOfDay, List<TrainingSession>> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);

        Assertions.assertEquals(1, mondaySessions.size());
        Assertions.assertEquals(1, mondaySessions.get(new TimeOfDay(13, 0)).size());
        Assertions.assertEquals(singleTrainingSession, mondaySessions.get(new TimeOfDay(13, 0)).getFirst());

        SortedMap<TimeOfDay, List<TrainingSession>> tuesdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);

        Assertions.assertTrue(tuesdaySessions.isEmpty());
        //Проверить, что за понедельник вернулось одно занятие
        //Проверить, что за вторник не вернулось занятий
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);

        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        SortedMap<TimeOfDay, List<TrainingSession>> mondaySessions = timetable.getTrainingSessionsForDay(
                DayOfWeek.MONDAY);

        Assertions.assertEquals(1, mondaySessions.size());

        Assertions.assertEquals(
                mondayChildTrainingSession,
                mondaySessions.get(new TimeOfDay(13, 0)).getFirst()
        );

        SortedMap<TimeOfDay, List<TrainingSession>> tuesdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);

        Assertions.assertTrue(tuesdaySessions.isEmpty());

        SortedMap<TimeOfDay, List<TrainingSession>> thursdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);

        Assertions.assertEquals(2, thursdaySessions.size());
        Assertions.assertEquals(thursdayChildTrainingSession, thursdaySessions.get(
                new TimeOfDay(13, 0)).getFirst());
        Assertions.assertEquals(thursdayAdultTrainingSession, thursdaySessions.get(
                new TimeOfDay(20, 0)).getFirst());
        // Проверить, что за понедельник вернулось одно занятие
        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        // Проверить, что за вторник не вернулось занятий
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> mondaySession13 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        Assertions.assertEquals(1, mondaySession13.size());
        Assertions.assertEquals(singleTrainingSession, mondaySession13.getFirst());

        List<TrainingSession> mondaySession14 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0));

        Assertions.assertTrue(mondaySession14.isEmpty());
        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        //Проверить, что за понедельник в 14:00 не вернулось занятий
    }

    @Test
    void testGetTrainingSessionsForDayAndTimeMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);

        TrainingSession childSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        TrainingSession adultSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(childSession);
        timetable.addNewTrainingSession(adultSession);

        List<TrainingSession> sessions = timetable.getTrainingSessionsForDayAndTime(
                        DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        Assertions.assertEquals(2, sessions.size());
        Assertions.assertEquals(childSession, sessions.get(0));
        Assertions.assertEquals(adultSession, sessions.get(1));
    }

    @Test
    void testGetTrainingSessionsForEmptyTimetable() {
        Timetable timetable = new Timetable();

        SortedMap<TimeOfDay, List<TrainingSession>> sessions = timetable.getTrainingSessionsForDay(
                DayOfWeek.MONDAY);

        Assertions.assertTrue(sessions.isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayAndTimeReturnsOnlySelectedTime() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group group = new Group("Акробатика", Age.ADULT, 60);

        TrainingSession session13 = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        TrainingSession session15 = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(15, 0));

        timetable.addNewTrainingSession(session13);
        timetable.addNewTrainingSession(session15);

        List<TrainingSession> sessions = timetable.getTrainingSessionsForDayAndTime(
                        DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        Assertions.assertEquals(1, sessions.size());
        Assertions.assertEquals(session13, sessions.getFirst());
    }

    @Test
    void testGetCountByCoachesOneCoach() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group group = new Group("Акробатика", Age.ADULT, 60);

        TrainingSession session1 = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        TrainingSession session2 = new TrainingSession(group, coach,
                DayOfWeek.TUESDAY, new TimeOfDay(10, 0));
        TrainingSession session3 = new TrainingSession(group, coach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);
        timetable.addNewTrainingSession(session3);

        List<CounterOfSessions> result = timetable.getCountByCoaches();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(3, result.getFirst().getCount());
    }

    @Test
    void testGetCountByCoachesSeveralCoaches() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Петров", "Иван", "Александрович");

        Group group = new Group("Акробатика", Age.ADULT, 60);

        TrainingSession session1 = new TrainingSession(group, coach1,
                        DayOfWeek.MONDAY, new TimeOfDay(10, 0));

        TrainingSession session2 = new TrainingSession(group, coach1,
                        DayOfWeek.TUESDAY, new TimeOfDay(10, 0));

        TrainingSession session3 = new TrainingSession(group, coach2,
                        DayOfWeek.WEDNESDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);
        timetable.addNewTrainingSession(session3);

        List<CounterOfSessions> result = timetable.getCountByCoaches();

        Assertions.assertEquals(2, result.size());

        Assertions.assertEquals(coach1, result.get(0).getCoach());
        Assertions.assertEquals(2, result.get(0).getCount());

        Assertions.assertEquals(coach2, result.get(1).getCoach());
        Assertions.assertEquals(1, result.get(1).getCount());
    }

    @Test
    void testGetCountByCoachesSortDescending() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Петров", "Иван", "Александрович");
        Coach coach3 = new Coach("Сидоров", "Алексей", "Петрович");

        Group group = new Group("Акробатика", Age.ADULT, 60);

        TrainingSession session1 = new TrainingSession(group, coach1,
                        DayOfWeek.MONDAY, new TimeOfDay(10, 0));

        TrainingSession session2 = new TrainingSession(group, coach2,
                        DayOfWeek.TUESDAY, new TimeOfDay(10, 0));

        TrainingSession session3 = new TrainingSession(group, coach2,
                        DayOfWeek.WEDNESDAY, new TimeOfDay(10, 0));

        TrainingSession session4 = new TrainingSession(group, coach2,
                        DayOfWeek.THURSDAY, new TimeOfDay(10, 0));

        TrainingSession session5 = new TrainingSession(group, coach3,
                        DayOfWeek.FRIDAY, new TimeOfDay(10, 0));

        TrainingSession session6 = new TrainingSession(group, coach3,
                        DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);
        timetable.addNewTrainingSession(session3);
        timetable.addNewTrainingSession(session4);
        timetable.addNewTrainingSession(session5);
        timetable.addNewTrainingSession(session6);

        List<CounterOfSessions> result = timetable.getCountByCoaches();

        Assertions.assertEquals(3, result.size());

        Assertions.assertEquals(coach2, result.get(0).getCoach());
        Assertions.assertEquals(3, result.get(0).getCount());

        Assertions.assertEquals(coach3, result.get(1).getCoach());
        Assertions.assertEquals(2, result.get(1).getCount());

        Assertions.assertEquals(coach1, result.get(2).getCoach());
        Assertions.assertEquals(1, result.get(2).getCount());
    }

    @Test
    void testGetTrainingSessionsForDayAndTimeReturnsUnmodifiableList() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика", Age.ADULT, 60);

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        TrainingSession session =
                new TrainingSession(group, coach,
                        DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(session);

        List<TrainingSession> sessions = timetable.getTrainingSessionsForDayAndTime(
                        DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        Assertions.assertThrows(UnsupportedOperationException.class, sessions::clear);
    }

    @Test
    void testGetTrainingSessionsForDayReturnsUnmodifiableMap() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика", Age.ADULT, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        TrainingSession session = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(session);

        SortedMap<TimeOfDay, List<TrainingSession>> sessions = timetable.getTrainingSessionsForDay(
                DayOfWeek.MONDAY);

        Assertions.assertThrows(UnsupportedOperationException.class, sessions::clear);
    }

}
