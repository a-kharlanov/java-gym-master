package ru.yandex.practicum.gym;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CounterOfSessions {
    private Coach coach;
    private int count;

    public CounterOfSessions(Coach coach, int count) {
        this.coach = coach;
        this.count = count;
    }

    public Coach getCoach() {
        return coach;
    }

    public int getCount() {
        return count;
    }
}
