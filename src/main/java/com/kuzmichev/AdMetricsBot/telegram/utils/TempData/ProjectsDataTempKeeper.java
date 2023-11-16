package com.kuzmichev.AdMetricsBot.telegram.utils.TempData;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProjectsDataTempKeeper {

    HashMap<String, Integer> projectPage = new HashMap<>();
    HashMap<String, String> lastProjectId = new HashMap<>();
    public void setNewPage(String chatId, int newPage) {
        projectPage.put(chatId, newPage);
    }

    public int getPage(String chatId){
        if(projectPage.containsKey(chatId)){
            return projectPage.get(chatId);
        }
        return 1;
    }

    public void setLastProjectId(String chatId, String projectId){
        lastProjectId.put(chatId,projectId);
    }

    public String getLastProjectId(String chatId){
            return lastProjectId.get(chatId);
    }
}
