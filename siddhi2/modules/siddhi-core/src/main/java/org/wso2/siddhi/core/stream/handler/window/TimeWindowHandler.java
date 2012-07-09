/*
*  Copyright (c) 2005-2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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
package org.wso2.siddhi.core.stream.handler.window;

import org.wso2.siddhi.core.event.ComplexEvent;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.event.ListEvent;
import org.wso2.siddhi.core.event.StreamEvent;
import org.wso2.siddhi.core.event.remove.RemoveEvent;
import org.wso2.siddhi.core.event.remove.RemoveListEvent;
import org.wso2.siddhi.core.event.remove.RemoveStream;
import org.wso2.siddhi.query.api.expression.constant.IntConstant;

import java.util.concurrent.TimeUnit;

public class TimeWindowHandler extends WindowHandler implements Runnable {

    int timeToKeep;

    @Override
    public void setParameters(Object[] parameters) {
        if (parameters[0] instanceof Integer) {
            timeToKeep = (Integer) parameters[0];
        } else {
            timeToKeep = ((IntConstant) parameters[0]).getValue();
        }
    }

    @Override
    public void process(ComplexEvent complexEvent) {
        if (complexEvent instanceof StreamEvent) {
            try {
                StreamEvent streamEvent;
                if (complexEvent instanceof Event) {
                    streamEvent = new RemoveEvent(((Event) complexEvent), System.currentTimeMillis() + timeToKeep);
                } else {
                    streamEvent = new RemoveListEvent(((ListEvent) complexEvent).getEvents(), System.currentTimeMillis() + timeToKeep);
                }
                if (!getWindow().put(streamEvent)) {
                    getEventRemoverScheduler().schedule(this, timeToKeep, TimeUnit.MILLISECONDS);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getNextPreStreamFlowProcessor().process(complexEvent);
        }
    }


    @Override
    public void run() {
        while (true) {
            StreamEvent streamEvent = getWindow().peek();
            try {
                if (streamEvent == null) {
                    break;
                }
                long timeDiff = ((RemoveStream) streamEvent).getExpiryTime() - System.currentTimeMillis();
                if (timeDiff > 0) {
                    getEventRemoverScheduler().schedule(this, timeDiff, TimeUnit.MILLISECONDS);
                    break;
                } else {
                    streamEvent = getWindow().poll();
                    getNextPreStreamFlowProcessor().process(streamEvent);
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
}
