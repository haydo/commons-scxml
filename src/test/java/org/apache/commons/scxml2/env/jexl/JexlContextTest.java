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
package org.apache.commons.scxml2.env.jexl;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class JexlContextTest {

    @Test
    public void testNew() {
        JexlContext ctx = new JexlContext();
        Assert.assertNotNull(ctx);
    }
    
    @Test
    public void testPrepopulated() {
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("foo", "bar");
        JexlContext ctx = new JexlContext(null, m);
        Assert.assertNotNull(ctx);
        Assert.assertEquals(1, ctx.getVars().size());
        String fooValue = (String) ctx.get("foo");
        Assert.assertEquals("bar", fooValue);
    }
    
    @Test
    public void testSetVars() {
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("foo", "bar");
        JexlContext ctx = new JexlContext(null, m);
        Assert.assertNotNull(ctx);
        Assert.assertEquals(1, ctx.getVars().size());
        String fooValue = (String) ctx.get("foo");
        Assert.assertEquals("bar", fooValue);
    }

}
