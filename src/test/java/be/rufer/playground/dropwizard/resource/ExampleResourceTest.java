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
package be.rufer.playground.dropwizard.resource;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;

import static org.junit.Assert.assertEquals;


public class ExampleResourceTest {

    private ExampleResource exampleResource;

    @Before
    public void init() {
        exampleResource = new ExampleResource("testtemplate %s", Optional.of("name").get());
    }

    @Test
         public void getExampleWithEmptyOptionalReturnsResponsWrapperContainingTemplateWithDefaultValue() {
        assertEquals("testtemplate name", exampleResource.getExample(Optional.absent()).getData());
    }

    @Test
    public void getExampleWithNameReturnsResponsWrapperContainingTemplateWithGivenName() {
        assertEquals("testtemplate user", exampleResource.getExample(Optional.of("user")).getData());
    }
}
