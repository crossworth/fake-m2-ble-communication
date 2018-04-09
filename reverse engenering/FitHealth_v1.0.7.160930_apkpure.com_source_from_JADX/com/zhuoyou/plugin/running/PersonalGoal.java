package com.zhuoyou.plugin.running;

public class PersonalGoal {
    public long id;
    public int mGoalCalories;
    public int mGoalSteps;

    public PersonalGoal(int step, int cal) {
        this.mGoalSteps = step;
        this.mGoalCalories = cal;
    }

    public int getStep() {
        return this.mGoalSteps;
    }

    public void setStep(int step) {
        this.mGoalSteps = step;
    }

    public int getCal() {
        return this.mGoalCalories;
    }

    public void setCal(int cal) {
        this.mGoalCalories = cal;
    }

    public String toString() {
        return this.mGoalSteps + "|" + this.mGoalCalories + "|";
    }
}
