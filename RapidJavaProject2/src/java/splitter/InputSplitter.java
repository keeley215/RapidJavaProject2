/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package splitter;

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

/**
 *
 * @author Ginger
 */
public class InputSplitter {
    private String path = "C:\\ags10e\\gradeexercise";
    private String exercise; //done
    private boolean extraFlag;
    private File[] inputFiles, outputFiles; //done
    private String[] exerciseInputs, exerciseOutputs;

    public InputSplitter(String exercise) {
        this.exercise = exercise;
        extraFlag = isExtra(exercise);

        try {
            setInput_OutputFiles(path);
        } catch (IOException ex) {
            System.out.println("oooooo nnooooo");
        }
    }
    
    //for help understanding go to this site http://winterbe.com/posts/2014/07/31/java8-stream-tutorial-examples/
    //file walk help https://www.brushmyskills.com/java-8/list-walk-method-files-factory-class-in-java-8/
    public File[] getInput_OutputFiles(String path, String fileType) throws IOException {
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
    
    //gets outputs for the selected exercise
    public String[] intExerciseOutputs(){
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
    
    //gets inputs for the selected exercise
    public String[] intExerciseInputs(){
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
    
    public boolean hasInput(String exercise)throws IOException{
        this.exercise = exercise;
        extraFlag = isExtra(exercise);
        setInput_OutputFiles(path);
        for(int i = 0; i < inputFiles.length; i++){
            if(inputFiles[i].getName().contains(exercise)){
                return true;
            }
        }
        return false;
    }
    
    public String[] getFirstInput(String exercise) throws FileNotFoundException{
        ArrayList<String> temp = new ArrayList<>();
        for(int i = 0; i < inputFiles.length; i++){
                    if(inputFiles[i].getName().contains(exercise + "a")){
                        Scanner scan = new Scanner(new File(inputFiles[i].getPath()));
                        while(scan.hasNext()){
                            temp.add(scan.nextLine());
                        }
                    }
                }
                return temp.toArray(new String[temp.size()]);
    }
    
    private void setInput_OutputFiles(String path) throws IOException {
        inputFiles = getInput_OutputFiles(path, "input");
        outputFiles = getInput_OutputFiles(path, "output");
    }
    
    public boolean isExtra(String exercise){
        return exercise.contains("Extra");
    }
    
    
    public String getExercise() {
        return exercise;
    }
    
    public void setExercise(String exercise){
        extraFlag = isExtra(exercise);
        this.exercise = exercise;
    }
    
    public File[] getInputFiles(){
        return inputFiles;
    }
    
    public void setInputFiles(File[] file){
        this.inputFiles = file;
    }

    public File[] getOutputFiles(){
        return outputFiles;
    }
    
    public void setOutputFiles(File[] files){
        outputFiles = files;
    }
}
