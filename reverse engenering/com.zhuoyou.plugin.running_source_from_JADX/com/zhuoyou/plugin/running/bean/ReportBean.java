package com.zhuoyou.plugin.running.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReportBean {
    public FirstData firstData;
    public List<Range> range = new ArrayList();
    public Record record;
    public Standard standard;
    public Statistics statistics;
    public List<Step> steps = new ArrayList();

    public static class FirstData {
        public SportData data;

        public String toString() {
            return "FirstData{data=" + this.data + '}';
        }
    }

    public static class Range {
        public int color;
        public int count;
        public String label;

        public String toString() {
            return "Range{count=" + this.count + ", color=" + this.color + ", label='" + this.label + '\'' + '}';
        }
    }

    public static class Record {
        public SportData data;
        public int percent;
        public int rank;
        public int total;

        public String toString() {
            return "Record{data=" + this.data + ", rank=" + this.rank + ", total=" + this.total + ", percent=" + this.percent + '}';
        }
    }

    public static class SportData {
        public String account = "";
        public float cal;
        public int complete;
        public String date = "";
        public float distance;
        public int stepPhone;
        public int stepTarget;

        public String toString() {
            return "SportData{account='" + this.account + '\'' + ", date='" + this.date + '\'' + ", stepPhone=" + this.stepPhone + ", stepTarget=" + this.stepTarget + ", complete=" + this.complete + ", distance=" + this.distance + ", cal=" + this.cal + '}';
        }
    }

    public static class Standard {
        public int achieve;
        public int total;

        public String toString() {
            return "Standard{total=" + this.total + ", achieve=" + this.achieve + '}';
        }
    }

    public static class Statistics {
        public SportData average;
        public SportData total;

        public String toString() {
            return "Statistics{total=" + this.total + ", average=" + this.average + '}';
        }
    }

    public static class Step {
        public String date;
        public int step;

        public String toString() {
            return "Step{step=" + this.step + ", date='" + this.date + '\'' + '}';
        }
    }

    public String toString() {
        return "ReportBean{firstData=" + this.firstData + ", record=" + this.record + ", standard=" + this.standard + ", statistics=" + this.statistics + ", steps=" + Arrays.toString(this.steps.toArray()) + ", range=" + Arrays.toString(this.range.toArray()) + '}';
    }
}
