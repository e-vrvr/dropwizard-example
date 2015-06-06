/*
 * Copyright (C) 2015 Marc Rufer (m.rufer@gmx.ch)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package be.rufer.playground.dropwizard.health;

import com.codahale.metrics.health.HealthCheck;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TemplateHealthCheckTest {

    private TemplateHealthCheck templateHealthCheck;

    @Test
    public void checkReturnsHealthyResultForValidTemplate() throws Exception {
        templateHealthCheck = new TemplateHealthCheck("a tempalte %s");
        assertEquals(HealthCheck.Result.healthy(), templateHealthCheck.check());
    }

    @Test
    public void checkReturnsUnhealthyResultForInvalidTemplate() throws Exception {
        templateHealthCheck = new TemplateHealthCheck("another template");
        assertEquals(HealthCheck.Result.unhealthy("template doesn't include a name"), templateHealthCheck.check());
    }
}
