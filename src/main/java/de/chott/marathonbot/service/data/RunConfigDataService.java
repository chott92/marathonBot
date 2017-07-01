/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.chott.marathonbot.service.data;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.chott.marathonbot.model.RunConfigTableEntry;
import de.chott.marathonbot.service.SingletonService;
import de.chott.marathonbot.service.SingletonServiceFactory;
import de.chott.marathonbot.service.util.UtilService;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.toList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RunConfigDataService implements SingletonService {

    private final ObservableList<RunConfigTableEntry> data;

    private UtilService utilService;
    private File file;

    public RunConfigDataService() {

        utilService = SingletonServiceFactory.getInstance(UtilService.class);

        data = FXCollections.observableArrayList();

        String filename = "/runData.json";
        try {
            file = new File(utilService.getAppFolderFilepath() + filename);
            if (!file.exists()) {
                file.createNewFile();
            }

            readFile(file);

        } catch (IOException ex) {

            Logger.getLogger(RunConfigDataService.class.getName()).log(Level.SEVERE,
                    "Could not initialize RunConfigDataServiceService", ex);
        }

    }

    private void readFile(File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try{
            ArrayList<LinkedHashMap<String, String>> readValues = mapper.readValue(file, ArrayList.class);

            data.addAll(readValues.stream().map(toTableEntry()).collect(toList()));
        } catch(JsonMappingException e){
            Logger.getLogger(RunConfigDataService.class.getName()).info(
                    "Could not read RunData-file. Creating dummy-data");
        }
        if (data.isEmpty()) {
            //intentional Mockup for testing;
            data.add(new RunConfigTableEntry("Donkey Kong Country Returns", "MrTiger92, Lukas1337", "1:29:39", "1:34:29, 1:34:30", "JXD", "Any%", "speedrun.com/dkcr"));
            data.add(new RunConfigTableEntry("Titan Quest", "MrTiger92", "1:18:22", "1:18:22", "MrTiger92", "Any% (AE)", "speedrun.com/Titan_Quest"));
            data.add(new RunConfigTableEntry("Titan Quest", "MrTiger92", "4:20:02", "4:20:02", "MrTiger92", "Any% legendary", "speedrun.com/Titan_Quest"));
            data.add(new RunConfigTableEntry("Titan Quest", "MrTiger92", "2:08:01", "2:08:01", "MrTiger92", "Any% (IT)", "speedrun.com/Titan_Quest"));
        }

    }
    
    private Function<LinkedHashMap<String, String>, RunConfigTableEntry> toTableEntry(){
        return map -> new  RunConfigTableEntry(map.get("game"),
                map.get("runnerName"), map.get("wrTime"), map.get("runnerPB"),
                map.get("wrHolderName"), map.get("category"),
                map.get("speedrunComLink"));
    }

    @Override
    public void close() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.writeValue(file, data);
        } catch (IOException ex) {
            Logger.getLogger(RunConfigDataService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ObservableList<RunConfigTableEntry> getData() {
        return data;
    }
    
    public Optional<RunConfigTableEntry> getForString(String input){
        return data.stream().filter(byString(input)).findFirst();
    }

    private Predicate<RunConfigTableEntry> byString(String input){
        return entry -> entry.toString().equals(input);
    }
    
}
