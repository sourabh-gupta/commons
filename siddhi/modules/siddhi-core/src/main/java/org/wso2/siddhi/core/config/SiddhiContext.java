/*
*  Copyright (c) 2005-2012, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/
package org.wso2.siddhi.core.config;

import org.wso2.siddhi.core.persistence.PersistenceService;
import org.wso2.siddhi.core.query.stream.handler.RunnableHandler;

import java.util.ArrayList;
import java.util.List;

public class SiddhiContext {

    private int threads;
    private boolean singleThreading;
    private int eventBatchSize;
    private List<RunnableHandler> runnableHandlerList = new ArrayList<RunnableHandler>();
    private PersistenceService persistenceService;
    private String executionPlanIdentifier;


    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    public boolean isSingleThreading() {
        return singleThreading;
    }

    public void setSingleThreading(boolean singleThreading) {
        this.singleThreading = singleThreading;
    }

    public int getEventBatchSize() {
        return eventBatchSize;
    }

    public void setEventBatchSize(int eventBatchSize) {
        this.eventBatchSize = eventBatchSize;
    }

    public void addRunnableHandler(RunnableHandler runnableHandler) {
        runnableHandlerList.add(runnableHandler);
    }

    public List<RunnableHandler> getRunnableHandlerList() {
        return runnableHandlerList;
    }

    public void setPersistenceService(PersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }

    public PersistenceService getPersistenceService() {
        return persistenceService;
    }

    public void setExecutionPlanIdentifier(String executionPlanIdentifier) {
        this.executionPlanIdentifier = executionPlanIdentifier;
    }

    public String getExecutionPlanIdentifier() {
        return executionPlanIdentifier;
    }
}
