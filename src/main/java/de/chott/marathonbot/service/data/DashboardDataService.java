/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.marathonbot.service.data;

import de.chott.marathonbot.model.RunConfigTableEntry;
import de.chott.marathonbot.service.SingletonService;

public class DashboardDataService implements SingletonService{

    private RunConfigTableEntry currentEntry;
    private RunConfigTableEntry nextEntry;
    
    private String wrCommandOutput;

    public DashboardDataService(){
        wrCommandOutput = "Kein run gesetzt.";
    }

    public RunConfigTableEntry getCurrentEntry() {
        return currentEntry;
    }

    public void setCurrentEntry(RunConfigTableEntry currentEntry) {
        this.currentEntry = currentEntry;
    }

    public RunConfigTableEntry getNextEntry() {
        return nextEntry;
    }

    public void setNextEntry(RunConfigTableEntry nextEntry) {
        this.nextEntry = nextEntry;
    }
    
    public String getWrCommandOutput() {
        return wrCommandOutput;
    }

    public void setWrCommandOutput(String wrCommandOutput) {
        this.wrCommandOutput = wrCommandOutput;
    }
    
    
}
