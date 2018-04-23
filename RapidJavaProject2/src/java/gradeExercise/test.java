/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gradeExercise;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import splitter.CompileRun;

/**
 *
 * @author Ginger
 */
public class test {
    
    public static String compileOutput, titleLabel, outputStyle;
    public static boolean extraFlag = false;
    private static String path = "C:\\Exercises\\gradeexercise";
    private static File[] inputFiles,outputFiles; 
    private static String exercise = "Exercise02_02";
    
    public static void main(String[] args){
        String[] boi;
        String[] boi2;
        try{
        inputFiles = getInput_OutputFiles(path, "input");
        outputFiles = getInput_OutputFiles(path, "output");
        boi = exerciseInputs();
        boi2 = intExerciseOutputs();
        for(int i = 0; i < boi.length; i++){
            System.out.println("input " + boi[i] + " should give you " + boi2[i]);
        }
        }
        catch(IOException ex){
            System.out.println("nununununununu");
        }
 
    }
    
    public static File[] getInput_OutputFiles(String path, String fileType) throws IOException {
        Stream<File> files = Files.walk(Paths.get(path))//lists all files and children of them
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .filter(p -> p.getName().endsWith(fileType))
                .sorted();
        
        if (extraFlag) 
            files = files.filter(p -> p.getName().contains("Extra"));
         else 
            files = files.filter(p -> !p.getName().contains("Extra"));
        
        List<File> list = files.collect(Collectors.toList());
        return list.toArray(new File[list.size()]);
    }
    
    //gets inputs for the selected exercise
    public static String[] exerciseInputs(){
        ArrayList<String> temp = new ArrayList<>();
        String temp3;
        for(int i = 0; i < inputFiles.length; i++){
            if(inputFiles[i].getName().startsWith(exercise)){
                temp3 = "";
                try{
                Scanner scan = new Scanner(new File(inputFiles[i].getPath()));
                while(scan.hasNext()){
                   temp3 += scan.nextLine();
                }
                temp.add(temp3);
                }
                catch(FileNotFoundException ex){
                    System.out.println("method exerciseInputs throws file not found exception");
                }
            }
        }
        return temp.toArray(new String[temp.size()]);
    }
    
    public static String[] intExerciseOutputs(){
        ArrayList<String> temp = new ArrayList<>();
        String temp3;
        for(int i = 0; i < outputFiles.length; i++){
            if(outputFiles[i].getName().startsWith(exercise)){
                temp3 = "";
                try{
                Scanner scan = new Scanner(new File(outputFiles[i].getPath()));
                while(scan.hasNext()){
                   temp3 += scan.nextLine();
                }
                temp.add(temp3);
                }
                catch(FileNotFoundException ex){
                    System.out.println("method exerciseInputs throws file not found exception");
                }
            }
        }
        return temp.toArray(new String[temp.size()]);
    }
    
}




