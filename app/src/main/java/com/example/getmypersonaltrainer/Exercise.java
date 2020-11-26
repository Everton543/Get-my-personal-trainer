package com.example.getmypersonaltrainer;

import java.time.DayOfWeek;

public class Exercise {
   private String name;
   private DayOfWeek dayOfWeek;
   private String emphasis;
   private String repetitionTime;
   private int series;
   private String intervalBetweenSeries;
   private String intervalBetweenExercises;
   private String videoLink;
   private String exerciseId;
   private boolean free = false;

   public Exercise(){   }

   public Exercise(String name, String exerciseId, String emphasis, String videoLink, boolean free){
      this.name = name;
      this.exerciseId = exerciseId;
      this.emphasis = emphasis;
      this.videoLink = videoLink;
      this.free = free;
   }

   public Exercise(String name, DayOfWeek dayOfWeek, String emphasis, String repetitionTime,
                   int series, String intervalBetweenSeries,
                   String intervalBetweenExercises, String videoLink) {
      this.name = name;
      this.dayOfWeek = dayOfWeek;
      this.emphasis = emphasis;
      this.repetitionTime = repetitionTime;
      this.series = series;
      this.intervalBetweenSeries = intervalBetweenSeries;
      this.intervalBetweenExercises = intervalBetweenExercises;
      this.videoLink = videoLink;
   }

   public String getName() {
      return name;
   }

   public boolean getFree(){return free;}

   public void setName(String name) {
      this.name = name;
   }

   public void setFree(boolean free) { this.free = free;}

   public DayOfWeek getDaysOfWeek() {
      return dayOfWeek;
   }

   public void setDayOfWeek(DayOfWeek dayOfWeek) {
      this.dayOfWeek = dayOfWeek;
   }

   public String getEmphasis() {
      return emphasis;
   }

   public void setEmphasis(String emphasis) {
      this.emphasis = emphasis;
   }

   public String getRepetitionTime() {
      return repetitionTime;
   }

   public void setRepetitionTime(String repetitionTime) {
      this.repetitionTime = repetitionTime;
   }

   public int getSeries() {
      return series;
   }

   public void setSeries(int series) {
      this.series = series;
   }

   public String getIntervalBetweenSeries() {
      return intervalBetweenSeries;
   }

   public void setIntervalBetweenSeries(String intervalBetweenSeries) {
      this.intervalBetweenSeries = intervalBetweenSeries;
   }

   public String getIntervalBetweenExercises() {
      return intervalBetweenExercises;
   }

   public void setIntervalBetweenExercises(String intervalBetweenExercises) {
      this.intervalBetweenExercises = intervalBetweenExercises;
   }

   public String getVideoLink() {
      return videoLink;
   }

   public void setVideoLink(String videoLink) {
      this.videoLink = videoLink;
   }

   public String getExerciseId() {
      return exerciseId;
   }

   public void setExerciseId(String exerciseId) {
      this.exerciseId = exerciseId;
   }
}
