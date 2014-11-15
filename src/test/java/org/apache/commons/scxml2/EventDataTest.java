/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.scxml2;

import java.util.Set;

import org.apache.commons.scxml2.env.Tracer;
import org.apache.commons.scxml2.model.EnterableState;
import org.apache.commons.scxml2.model.SCXML;
import org.junit.Assert;
import org.junit.Test;
/**
 * Unit tests {@link org.apache.commons.scxml2.SCXMLExecutor}.
 * Testing special variable "_event.data"
 */
public class EventDataTest {

    /**
     * Test the SCXML documents, usage of "_event.data"
     */
    @Test
    public void testEventdata01Sample() throws Exception {
    	SCXMLExecutor exec = SCXMLTestHelper.getExecutor("org/apache/commons/scxml2/env/jexl/eventdata-01.xml");
        exec.go();
        Set<EnterableState> currentStates = exec.getCurrentStatus().getStates();
        Assert.assertEquals(1, currentStates.size());
        Assert.assertEquals("state1", currentStates.iterator().next().getId());
        TriggerEvent te = new TriggerEvent("event.foo",
            TriggerEvent.SIGNAL_EVENT, new Integer(3));
        currentStates = SCXMLTestHelper.fireEvent(exec, te);
        Assert.assertEquals(1, currentStates.size());
        Assert.assertEquals("state3", currentStates.iterator().next().getId());
        TriggerEvent[] evts = new TriggerEvent[] { te,
            new TriggerEvent("event.bar", TriggerEvent.SIGNAL_EVENT,
            new Integer(6))};
        currentStates = SCXMLTestHelper.fireEvents(exec, evts);
        Assert.assertEquals(1, currentStates.size());
        Assert.assertEquals("state6", currentStates.iterator().next().getId());
        te = new TriggerEvent("event.baz",
            TriggerEvent.SIGNAL_EVENT, new Integer(7));
        currentStates = SCXMLTestHelper.fireEvent(exec, te);
        Assert.assertEquals(1, currentStates.size());
        Assert.assertEquals("state7", currentStates.iterator().next().getId());
    }

    @Test
    public void testEventdata02Sample() throws Exception {
        SCXMLExecutor exec = SCXMLTestHelper.getExecutor("org/apache/commons/scxml2/env/jexl/eventdata-02.xml");
        exec.go();
        Set<EnterableState> currentStates = exec.getCurrentStatus().getStates();
        Assert.assertEquals(1, currentStates.size());
        Assert.assertEquals("state0", currentStates.iterator().next().getId());
        TriggerEvent te1 = new TriggerEvent("connection.alerting",
            TriggerEvent.SIGNAL_EVENT, "line2");
        currentStates = SCXMLTestHelper.fireEvent(exec, te1);
        Assert.assertEquals(1, currentStates.size());
        Assert.assertEquals("state2", currentStates.iterator().next().getId());
        TriggerEvent te2 = new TriggerEvent("connection.alerting",
            TriggerEvent.SIGNAL_EVENT,
            new ConnectionAlertingPayload(4));
        currentStates = SCXMLTestHelper.fireEvent(exec, te2);
        Assert.assertEquals(1, currentStates.size());
        Assert.assertEquals("state4", currentStates.iterator().next().getId());
    }

    @Test
    public void testEventdata03Sample() throws Exception {
        SCXMLExecutor exec = SCXMLTestHelper.getExecutor("org/apache/commons/scxml2/env/jexl/eventdata-03.xml");
        exec.go();
        Set<EnterableState> currentStates = exec.getCurrentStatus().getStates();
        Assert.assertEquals(1, currentStates.size());
        Assert.assertEquals("ten", currentStates.iterator().next().getId());
        TriggerEvent te = new TriggerEvent("event.foo",
            TriggerEvent.SIGNAL_EVENT);
        currentStates = SCXMLTestHelper.fireEvent(exec, te);
        Assert.assertEquals(1, currentStates.size());
        Assert.assertEquals("thirty", currentStates.iterator().next().getId());
    }

    @Test
    public void testEventdata04Sample() throws Exception {
        SCXML scxml = SCXMLTestHelper.parse("org/apache/commons/scxml2/env/jexl/eventdata-03.xml");
        Tracer trc = new Tracer();
        SCXMLExecutor exec = new SCXMLExecutor(null, null, trc);
        exec.addListener(scxml, trc);
        exec.setStateMachine(scxml);
        exec.go();
        Thread.sleep(200); // let the 100 delay lapse
    }

    public static class ConnectionAlertingPayload {
        private int line;
        public ConnectionAlertingPayload(int line) {
            this.line = line;
        }
        public void setLine(int line) {
            this.line = line;
        }
        public int getLine() {
            return line;
        }
    }
}
