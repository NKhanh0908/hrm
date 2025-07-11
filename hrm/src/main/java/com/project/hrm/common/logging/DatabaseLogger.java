package com.project.hrm.common.logging;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.event.spi.*;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DatabaseLogger implements
        PreInsertEventListener,
        PreUpdateEventListener,
        PreDeleteEventListener,
        PostInsertEventListener,
        PostUpdateEventListener,
        PostDeleteEventListener {

    @Override
    public boolean onPreInsert(PreInsertEvent event) {
        logDatabaseOperation("INSERT", event.getEntity(), "BEFORE");
        return false;
    }

    @Override
    public boolean onPreUpdate(PreUpdateEvent event) {
        logDatabaseOperation("UPDATE", event.getEntity(), "BEFORE");
        return false;
    }

    @Override
    public boolean onPreDelete(PreDeleteEvent event) {
        logDatabaseOperation("DELETE", event.getEntity(), "BEFORE");
        return false;
    }

    @Override
    public void onPostInsert(PostInsertEvent event) {
        logDatabaseOperation("INSERT", event.getEntity(), "AFTER");
    }

    @Override
    public void onPostUpdate(PostUpdateEvent event) {
        logDatabaseOperation("UPDATE", event.getEntity(), "AFTER");
    }

    @Override
    public void onPostDelete(PostDeleteEvent event) {
        logDatabaseOperation("DELETE", event.getEntity(), "AFTER");
    }

    private void logDatabaseOperation(String operation, Object entity, String phase) {
        String entityName = entity.getClass().getSimpleName();

        if ("BEFORE".equals(phase)) {
            log.debug("DB Operation: {} {} - {}", phase, operation, entityName);
        } else {
            log.info("DB Operation: {} {} - {} completed", phase, operation, entityName);
        }
    }

    @Override
    public boolean requiresPostCommitHandling(EntityPersister persister) {
        return false;
    }
}