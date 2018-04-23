/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package splitter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Ginger
 */

public class ExerciseSplitter {

    private boolean isCompilable;
    private int chapter;
    private int exercise;
    private String[] chapters;
    private String[] exercises;
    private String[] descriptions; // without extras
    private String path = "C:\\ags10e\\exercisedescription";
    private boolean extraFlag;
    
    public ExerciseSplitter(int chapter, int exercise) {
        this.chapter = chapter;
        this.exercise = exercise;
        setExercises(chapter);
        isCompilable(exercise);
        initializeChapters();
    }

    public String setPattern(int chapter) {
        if (chapter < 10) {
            return ("0" + chapter + "_");
        }
        return (chapter + "_");
    }

    public String[] getExercises() {
        return exercises;
    }
    
    public void setExercises(int chapter){
        this.chapter = chapter;
        File[] files = new File(path).listFiles();
        ArrayList<String> temp = new ArrayList<>();
        ArrayList<String> description = new ArrayList<>();
        String pattern = setPattern(chapter);

        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().contains(pattern)) {
                temp.add(files[i].getName());
                File file2 = new File(files[i].getPath());
                try {
                    Scanner scan = new Scanner(file2);
                    description.add(scan.nextLine());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        descriptions = description.toArray(new String[description.size()]);
        exercises = temp.toArray(new String[temp.size()]);
    }
    
    public String[] getDescriptions(){
        return descriptions;
    }
    
    //chapter/exercise has to be changed before this is run
    public boolean isCompilable(int exercise){
        if(descriptions[exercise-1].contains("cannot be run and automatically graded")){
            isCompilable = false;
            return false;
        }
        isCompilable = true;
        return true;
    }
    
    public boolean getIsCompilable(){
        return isCompilable;
    }
    
    public String[] initializeChapters(){
        File[] files = new File(path).listFiles();
        ArrayList<String> temp = new ArrayList<>();
        ArrayList<String> temp2 = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            if(!temp.contains(files[i].getName().substring(8,10))){
            temp.add(files[i].getName().substring(8,10));
            }
        }
        temp = chapterHelper(temp);
        chapters = temp.toArray(new String[temp.size()]);
        return chapters;
    }
    
    public ArrayList<String> chapterHelper(ArrayList<String> temp){
        ArrayList<String> replacement = new ArrayList<>();
        String temp2;
        for(int i = 0; i < temp.size(); i++){
            temp2 = "Chapter " + Integer.parseInt(temp.get(i));
            replacement.add(i, temp2);
        }
        return replacement;
    }
    
    public String[] getChapters(){
        return chapters;
    }
    
    public int getChapter(){
        return chapter;
    }
    
    public void setChapter(String selectedChapter) {
        chapter = Integer.parseInt(selectedChapter.substring(8));
    }
    
    public int getExercise(){
        return exercise;
    }
    
    public void setExercise(String newExercise){
        extraFlag = isExtra(newExercise);
        if(extraFlag)
            exercise = Integer.parseInt(newExercise.substring(11,13));
        else
            exercise = Integer.parseInt(newExercise.substring(11));
    }
    
    public boolean getExtraFlag(){
        return extraFlag;
    }
    
    public void setExtraFlag(boolean bool){
        extraFlag = bool;
    }
    
    public boolean isExtra(String exerciseName){
        return exerciseName.contains("Extra");
    }
    
}
