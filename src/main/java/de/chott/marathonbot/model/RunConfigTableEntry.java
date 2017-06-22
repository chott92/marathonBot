/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.marathonbot.model;

import javafx.beans.property.SimpleStringProperty;


public class RunConfigTableEntry {
    
    private final SimpleStringProperty game;
    private final SimpleStringProperty runnerName;
    private final SimpleStringProperty wrTime;
    private final SimpleStringProperty runnerPB;
    private final SimpleStringProperty wrHolderName;
    private final SimpleStringProperty category;
    private final SimpleStringProperty speedrunComLink;
    
    public RunConfigTableEntry(String game, String runnerName, String wrTime, String runnerPB,
            String wrHolderName, String category, String speedrunComLink) {
        this.game = new SimpleStringProperty(game);
        this.runnerName = new SimpleStringProperty(runnerName);
        this.wrTime = new SimpleStringProperty(wrTime);
        this.runnerPB = new SimpleStringProperty(runnerPB);
        this.wrHolderName = new SimpleStringProperty(wrHolderName);
        this.category = new SimpleStringProperty(category);
        this.speedrunComLink = new SimpleStringProperty(speedrunComLink);
    }
    
    public String getGame (){
        return game.get();
    }
    
    public void setGame(String newValue){
        this.game.set(newValue);
    }
    
    public String getRunnerName (){
        return runnerName.get();
    }
    
    public void setRunnerName (String newValue){
        this.runnerName.set(newValue);
    }
    
    public String getRunnerPB (){
        return runnerPB.get();
    }
    
    public void setRunnerPB (String newValue){
        this.runnerPB.set(newValue);
    }
    
    public String getWrTime (){
        return wrTime.get();
    }
    
    public void setWrTime (String newValue){
        this.wrTime.set(newValue);
    }
    
    public String getWrHolderName (){
        return wrHolderName.get();
    }
    
    public void setWrHolderName (String newValue){
        this.wrHolderName.set(newValue);
    }
    
    public String getCategory (){
        return category.get();
    }
    
    public void setCategory (String newValue){
        this.category.set(newValue);
    }
    
    public String getSpeedrunComLink (){
        return speedrunComLink.get();
    }
    
    public void setSpeedrunComLink (String newValue){
        this.speedrunComLink.set(newValue);
    }

    @Override
    public String toString() {
        return getGame() + " (" + getCategory()+ ") - " + getRunnerName();
    }
    
    
}
