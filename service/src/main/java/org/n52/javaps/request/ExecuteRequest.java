/*
 * Copyright 2016 52°North Initiative for Geospatial Open Source
 * Software GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.n52.javaps.request;


import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.n52.iceland.exception.ows.OwsExceptionReport;
import org.n52.iceland.ogc.ows.OwsCodeType;
import org.n52.iceland.request.AbstractServiceRequest;
import org.n52.javaps.ogc.wps.ExecutionMode;
import org.n52.javaps.ogc.wps.OutputDefinition;
import org.n52.javaps.ogc.wps.ResponseMode;
import org.n52.javaps.ogc.wps.WPSConstants;
import org.n52.javaps.ogc.wps.data.Data;
import org.n52.javaps.response.ExecuteResponse;


/**
 * @author Christian Autermann
 */
public class ExecuteRequest extends AbstractServiceRequest<ExecuteResponse> {

    private OwsCodeType id;
    private ExecutionMode executionMode = ExecutionMode.AUTO;
    private ResponseMode responseMode = ResponseMode.DOCUMENT;
    private final List<Data> inputs = new LinkedList<>();
    private final List<OutputDefinition> outputs = new LinkedList<>();


    public void addInput(Data input) {
        this.inputs.add(Objects.requireNonNull(input));
    }

    public void addOutput(OutputDefinition output) {
        this.outputs.add(Objects.requireNonNull(output));
    }

    @Override
    public ExecuteResponse getResponse()
            throws OwsExceptionReport {
        return (ExecuteResponse) new ExecuteResponse().set(this);
    }

    @Override
    public String getOperationName() {
        return WPSConstants.Operations.Execute.name();
    }

    public List<Data> getInputs() {
        return Collections.unmodifiableList(this.inputs);
    }

    public List<OutputDefinition> getOutputs() {
        return Collections.unmodifiableList(this.outputs);
    }

    public ExecutionMode getExecutionMode() {
        return this.executionMode;
    }

    public void setExecutionMode(ExecutionMode executionMode) {
        this.executionMode = executionMode;
    }

    public ResponseMode getResponseMode() {
        return this.responseMode;
    }

    public void setResponseMode(ResponseMode responseMode) {
        this.responseMode = responseMode;
    }

    public OwsCodeType getId() {
        return id;
    }

    public void setId(OwsCodeType id) {
        this.id = id;
    }
}
