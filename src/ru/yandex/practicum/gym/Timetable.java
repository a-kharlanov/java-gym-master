package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private final HashMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        if (!timetable.containsKey(day)) {
            timetable.put(day, new TreeMap<>());
        }

        TreeMap<TimeOfDay, List<TrainingSession>> dailyMap = timetable.get(day);

        if (!dailyMap.containsKey(time)) {
            dailyMap.put(time, new ArrayList<>());
        }

        dailyMap.get(time).add(trainingSession);
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        if (!timetable.containsKey(dayOfWeek)) {
            return new ArrayList<>();
        }

        TreeMap<TimeOfDay, List<TrainingSession>> sessionsForDay = timetable.get(dayOfWeek);
        List<TrainingSession> orderedSessions = new ArrayList<>();

        for (TimeOfDay time : sessionsForDay.navigableKeySet()) {
            List<TrainingSession> sessionsAtTime = sessionsForDay.get(time);
            if (sessionsAtTime != null) {
                orderedSessions.addAll(sessionsAtTime);
            }
        }
        return orderedSessions;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        if (!timetable.containsKey(dayOfWeek)) {
            return new ArrayList<>();
        }

        TreeMap<TimeOfDay, List<TrainingSession>> sessionForDay = timetable.get(dayOfWeek);

        return sessionForDay.getOrDefault(timeOfDay, new ArrayList<>());
    }

    public List<CounterOfSessions> getCountByCoaches() {
        Map<Coach, Integer> countCoachSessions = new HashMap<>();

        for (TreeMap<TimeOfDay, List<TrainingSession>> dailyMap : timetable.values()) {
            for (List<TrainingSession> sessions : dailyMap.values()) {
                for (TrainingSession trainingSession : sessions) {
                    Coach coach = trainingSession.getCoach();
                    countCoachSessions.put(coach, countCoachSessions.getOrDefault(coach, 0) + 1);
                }
            }
        }
        List<CounterOfSessions> counters = new ArrayList<>();
        for (Map.Entry<Coach, Integer> entry : countCoachSessions.entrySet()) {
            counters.add(new CounterOfSessions(entry.getKey(), entry.getValue()));
        }

        counters.sort((o1, o2) -> Integer.compare(o2.getCount(), o1.getCount()));

        return counters;
    }
}
