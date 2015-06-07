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

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

/**
 * Wrapper class for all REST responses
 */
public class ResponseWrapper {

    private long id;

    @Length(max = 3)
    private String data;

    public ResponseWrapper() {
        // Jackson deserialization
    }

    public ResponseWrapper(long id, String data) {
        this.id = id;
        this.data = data;
    }

    @JsonProperty
    public long getId() {
        return id;
    }

    @JsonProperty
    public String getData() {
        return data;
    }
}
