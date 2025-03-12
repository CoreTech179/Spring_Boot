package com.example.journalApp.cache;

import com.example.journalApp.entity.ConfigJournalAppEntity;
import com.example.journalApp.repository.ConfigJournalAppRepository;
import jakarta.annotation.PostConstruct;
//import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class ApplicationCache {

    @Autowired
    ConfigJournalAppRepository configJournalAppRepositoryObj;

    public HashMap<String, String> APP_CACHE = new HashMap<>();

    @PostConstruct // When a bean is created for this class then, automatically this method will be called
    public void init(){
        List<ConfigJournalAppEntity> all = configJournalAppRepositoryObj.findAll();

        for(ConfigJournalAppEntity configJournalAppEntity : all){
            APP_CACHE.put(configJournalAppEntity.getKey(), configJournalAppEntity.getValue());
        }
    }

}
