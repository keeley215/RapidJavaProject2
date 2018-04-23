/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gradeExercise;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import splitter.CompileRun;
import splitter.ExerciseSplitter;
import splitter.InputSplitter;

/**
 *
 * @author Ginger
 */
@Named(value = "backendBean")
@SessionScoped
public class BackendBean implements Serializable {

    /**
     * Creates a new instance of BackendBean
     */
    //the compileOutput is used for the check and the formatedCompileOutput is used for the display
    private String sampleInput, compileOutput, formatedCompileOutput;
    
    private String inputStyle;
    private String outputStyle;
 
    private int currentChapter = 1;
    private int currentExercise = 1;
    
    private String titleLabel = "Exercise01_01";
    private String exercise = titleLabel;
    private String chapter = "Chapter 1";
    
    private ExerciseSplitter exerciseClass= new ExerciseSplitter(currentChapter, currentExercise);
    private String[] chapters = exerciseClass.getChapters();
    private String[] exercises = exerciseClass.getExercises();
    
    private boolean hasInput = false;
    private String inputLabel = "Enter input data for the program (Sample data provided below. You may modify it.)";
    private InputSplitter inputClass = new InputSplitter(exercise);
    private String[] inputValue;
    private String inputString;
    private String inputString4Show;
    
    private String codeBody = "/*Paste your Exercise01_01 here and click Automatic Check.\n"
            + "For all programming projects, the numbers should be double\n"
            + "unless it is explicitly stated as integer.\n"
            + "If you get a java.util.InputMismatchException error, check if\n"
            + "your code used input.readInt(), but it should be input.readDouble().\n"
            + "For integers, use int unless it is explicitly stated as long.*/";
    
    private boolean canRun = exerciseClass.getIsCompilable();
    private String automaticCheckBL = "Automatic Check"; //change if there is description says it cannot be run
    
    private String noCompileButton = "This exercise cannot be graded automatically";
    private String noCompileCodeBody = "/* This exercise cannot be graded automatically becuase it may use random \n" +
                                        " numbers, file input/output, or graphics. */";
    private String noCompileBottomText = "This exercise cannot be auto graded. But you can still run it.";
    
    private boolean isCompiled = false;
    private String compileMessage;
    private boolean compiledCorrectly = false;
    
    //stuff for automatic check
    private String[] inputs4Exercise, outputs4Exercise;
    private String currentOutput, currentInput;
    private String automaticCheckOutputMessage;
    private String automaticCheckLabel;
    private boolean hasRun = false;
    private boolean isCorrect = false; //decides if input boxes are shown after auto check is run
    
    
    public BackendBean() {
    }
    
     public void automaticCheck() throws FileNotFoundException{
         
         compileAndRun();
         hasRun = true;
         isCompiled = false;
         
        if (compiledCorrectly) {
            if (hasInput) {
                if (gradeExercise()) {
                    automaticCheckLabel = "Your Program is Correct";
                    isCorrect = true;
                } else {
                    automaticCheckLabel = "Your Program is Incorrect";
                    isCorrect = false;
                }
            } else {
                if (checkCorrect(convertOutput(outputs4Exercise[0]), compileOutput)){
                    automaticCheckLabel = "Your Program is Correct";
                    isCorrect = true;
                } else {
                    automaticCheckLabel = "Your Program is Incorrect";
                    isCorrect = false;
                }
                currentOutput = formatCompileOutput(outputs4Exercise[0]);
            }
        } else {
            isCompiled = true;
            hasRun = false;
            isCorrect = false;
        }
        //compare the codes
    }
     
     public boolean checkCorrect(String[] output, String codeOutput){
         boolean bool = true;
         for(int i = 0; i < output.length && bool; i++){
             if(!codeOutput.contains(output[i])){
                 System.out.println(codeOutput + " does not contain " + output[i]);
                 bool = false;
             }
             else{
                 codeOutput = codeOutput.replaceFirst(output[i], "");
             }
         }
         return bool;
     }
     
     public String[] convertOutput(String s){
         return s.split("#");
     }
     
     public boolean gradeExercise() throws FileNotFoundException{
         //exerciseInputs //exerciseOutputs
         //compile results
         boolean temp = true;
         String s;
         for(int i = 0; i < inputs4Exercise.length && temp; i++){
             setInputString(inputs4Exercise[i]);
             CompileRun c = new CompileRun(titleLabel, getCodeBody(), inputString);
             compileOutput = c.run();
             
             if ((s = c.write()).equals("File was written successfully")) {
            setCompileOutput(s);
            formatedCompileOutput = compileOutput;
            
            if ((compileMessage = c.compile()).equals("Program compiled successfully")) {
                compileOutput = c.run();
                formatedCompileOutput = compileOutput;

                c.delete(titleLabel + ".java");
                c.delete(titleLabel + ".class");
                if(!hasErrors(compileOutput)){
                    compiledCorrectly = true;
                    formatedCompileOutput = formatCompileOutput(compileOutput);
                    //setCompileOutput(formatCompileOutput(compileOutput));
                }
                
            } else {
                compiledCorrectly = false;
                compileMessage = "Program was not compiled successfully";
                setCompileOutput(s);
                formatedCompileOutput = compileOutput;
                c.delete(titleLabel + ".java");
            }
        } else {
            compiledCorrectly = false;
            setCompileOutput(s);
            formatedCompileOutput = compileOutput;
        }
             temp = checkCorrect(convertOutput(outputs4Exercise[i]), compileOutput);  
             currentOutput = outputs4Exercise[i];
             currentInput = inputString;
         }
         return temp;
     }
    
     public void compileAndRun() throws FileNotFoundException{
         hasRun = false;
         compileMessage = "";
         setInputValue(inputClass.getFirstInput(titleLabel));
         setInputString(inputString4Show);
         setInputString4Show(inputString4Show);
         isCompiled = true;
        CompileRun c = new CompileRun(titleLabel, getCodeBody(), inputString);
        String s;
        
        setOutputStyle("");
        
        if ((s = c.write()).equals("File was written successfully")) {
            setCompileOutput(s);
            formatedCompileOutput = compileOutput;
            
            if ((compileMessage = c.compile()).equals("Program compiled successfully")) {
                compileOutput = c.run();
                formatedCompileOutput = compileOutput;

                c.delete(titleLabel + ".java");
                c.delete(titleLabel + ".class");
                if(!hasErrors(compileOutput)){
                    compiledCorrectly = true;
                    formatedCompileOutput = formatCompileOutput(compileOutput);
                    //setCompileOutput(formatCompileOutput(compileOutput));
                }
                
            } else {
                compiledCorrectly = false;
                compileMessage = "Program was not compiled successfully";
                setCompileOutput(s);
                formatedCompileOutput = compileOutput;
                c.delete(titleLabel + ".java");
            }
        } else {
            compiledCorrectly = false;
            setCompileOutput(s);
            formatedCompileOutput = compileOutput;
        }
    }
     
    public boolean hasErrors(String s){
        return s.contains("Error");
    } 
     
    public String formatCompileOutput(String output){
        String temp = "";
        try{
        if (inputClass.hasInput(titleLabel)) {
            int t = output.indexOf(":");
            temp = output.substring(0, t + 1) + inputString + "\n" +output.substring(t + 1);
            temp = temp.replaceAll("#", "\n");
        } else {
            temp = output.replaceAll("#", "\n");
            return temp;
        }
        }
        catch(IOException ex){
            System.out.println("formatCompileOutput method throws IOException");
        }
         return temp;
     }
    
    public void setCurrentInput(String s){
        currentInput = s;
    }
    
    public String getCurrentInput(){
        return currentInput;
    }
    
    public void setCurrentOutput(String s){
        currentOutput = s;
    }
    
    public String getCurrentOutput(){
        return currentOutput;
    }
    
    public void setAutomaticCheckLabel(String s){
        automaticCheckLabel = s;
    }
    
    public String getAutomaticCheckLabel(){
        return automaticCheckLabel;
    }
    
    public String getAutomaticCheckOutputMessage(){
        return automaticCheckOutputMessage;
    }
    
    public void setAutomaticCheckOutputMessage(String s){
        automaticCheckOutputMessage = s;
    }
    
    public boolean getIsCorrect(){
        return isCorrect;
    }
    
    public void setIsCorrect(boolean bool){
        isCorrect = bool;
    }
    
    public boolean getHasRun(){
        return hasRun;
    }
    
    public void setHasRun(boolean bool){
        hasRun = bool;
    }
    
    public boolean getCompiledCorrectly(){
        return compiledCorrectly;
    }
    
    public void setCompiledCorrectly(boolean bool){
        compiledCorrectly = bool;
    }
    
    public String getCompileMessage(){
        return compileMessage;
    }
    
    public void setCompileMessage(String s){
        compileMessage = s;
    }
    
    public String getFormatedCompileOutput(){
         return formatedCompileOutput;
     }
     
     public void setFormatedCompileOutput(String s){
         formatedCompileOutput = s;
     }
    
     public String getCompileOutput(){
         return compileOutput;
     }
     
     public void setCompileOutput(String s){
         compileOutput = s;
     }
     
    public boolean getIsCompiled(){
        return isCompiled;
    }
    
    public void setIsCompiled(boolean bool){
        isCompiled = bool;
    }
    
    public String getChapter(){
        return chapter;
    }
    
    public void setChapter(String chapter){
        this.chapter = chapter;
    }
    
    public int getCurrentChapter(){
        return currentChapter;
    }
    
    public void setCurrentChapter(int chapter){
        this.currentChapter = chapter;
    }
    
    public int getCurrentExercise(){
        return currentExercise;
    }
    
    public void setCurrentExercise(int exercise){
        this.currentExercise = exercise;
    }
    
    public String[] getChapters(){
        return chapters;
    }
    
    public void setChapters(String[] chapters){
        this.chapters = chapters;
    }
    
    public String[] getExercises(){
        return exercises;
    }
    
    public void setExercises(String[] exercises){
        this.exercises = exercises;
    }
    
    public String getExercise(){
        return exercise;
    }
    
    public void setExercise(String exercise){
        this.exercise = exercise;
    }
    
    public String getCodeBody(){
        return codeBody;
    }
    
    public void setCodeBody(String codeBody){
        this.codeBody = codeBody;
    }
    
    public String getNoCompileBottomText(){
        return noCompileBottomText;
    }
    
    public void setNoCompileBottomText(){
    }
    
    public String getNoCompileCodeBody(){
        return noCompileCodeBody;
    }
    
    public void setNoCompileCodeBody(){
    }
    
    public String getNoCompileButton(){
        return noCompileButton;
    }
    
    public void setNoCompileButton(){
    }
    
    public boolean getCanCompile(){
        return canRun;
    }
    
    public void setCanCompile(boolean bool){
        canRun = bool;
    }
    
    public String getTitleLabel(){
        return titleLabel;
    }
    
    public void setTitleLabel(String label){
        titleLabel = label;
    }
    
    public String getInputLabel(){
        return inputLabel;
    }
    
    public void setInputLabel(String label){
        inputLabel = label;
    }
    
    public boolean getHasInput(){
        return hasInput;
    }
    
    public void setHasInput(boolean bool){
        this.hasInput = bool;
    }
    
    public String[] getInputValue(){
        return inputValue;
    }
    
    public void setInputValue(String[] value){
        inputValue = value;
    }
    
    public String getInputString(){
        return inputString;
    }
    
    public void setInputString(String s){
        inputString = s;
    }
    
    public String getInputString4Show(){
        return inputString4Show;
    }
    
    public void setInputString4Show(String s){
        inputString4Show = s;
    }
    
    public void setExerciseButton() throws IOException{
        hasRun = false;
        isCompiled = false;
        setTitleLabel(exercise);
        //exercises should be set already
        inputClass.setExercise(exercise);
        
        exerciseClass.setChapter(chapter);
        exerciseClass.setExercise(exercise);
        setCurrentExercise(exerciseClass.getExercise());
        setCurrentChapter(exerciseClass.getChapter());
        setCanCompile(exerciseClass.isCompilable(currentExercise));
        if(canRun){
            setCodeBody("/*Paste your " + titleLabel + " here and click Automatic Check.\n"
            + "For all programming projects, the numbers should be double\n"
            + "unless it is explicitly stated as integer.\n"
            + "If you get a java.util.InputMismatchException error, check if\n"
            + "your code used input.readInt(), but it should be input.readDouble().\n"
            + "For integers, use int unless it is explicitly stated as long.*/");
        setHasInput(inputClass.hasInput(exercise));
        if(hasInput){ //input class exercise has been set already
            setInputValue(inputClass.getFirstInput(exercise));
            setInputString(makeInputString4Compile(inputValue));
            setInputString4Show(makeInputString(inputValue));
            inputs4Exercise = inputClass.intExerciseInputs();
            outputs4Exercise = inputClass.intExerciseOutputs();
        }
        else{
            outputs4Exercise = inputClass.intExerciseOutputs();
        }
        }
        else{
            codeBody = noCompileCodeBody;
            setHasInput(false);
        }
    }
    
    public String setPattern(int chapter) {
        String pattern;
        if (chapter < 10) {
            return ("0" + chapter + "_");
        }
        return (chapter + "_");
    }
    
    public String makeInputString4Compile(String[] s){
        String temp = "";
        if(s.length == 1){
            return s[0];
        }
        for(int i = 0; i < s.length; i++){
            temp += s[i] + " ";
        }
        return temp;
    }
    
    public String makeInputString(String[] s){
        String temp = "";
        if(s.length == 1){
            return s[0];
        }
        for(int i = 0; i < s.length; i++){
            temp += s[i] + "\n";
        }
        return temp;
    }
    
    public void changeChapter(){
        currentChapter = Integer.parseInt(chapter.substring(8));
        exerciseClass.setExercises(currentChapter);
        setExercises(exerciseClass.getExercises());
    }
    
    public void changeExercises(){
        exerciseClass.setExercises(currentChapter);
        setExercises(exerciseClass.getExercises());
    }

    public String getSampleInput() {
        return sampleInput;
    }

    public void setSampleInput(String s) {
        sampleInput = s;
    }

    public String getOutput() {
        return compileOutput;
    }

    public void setOutput(String s) {
        compileOutput = s;
    }

    public String getInputStyle() {
        return inputStyle;
    }

    public void setInputStyle(String inputStyle) {
        this.inputStyle = inputStyle;
    }

    public String getOutputStyle() {
        return outputStyle;
    }

    public void setOutputStyle(String outputStyle) {
        this.outputStyle = outputStyle;
    }
}
