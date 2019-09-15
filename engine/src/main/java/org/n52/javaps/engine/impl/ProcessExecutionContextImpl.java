/*
 * Copyright 2016-2019 52°North Initiative for Geospatial Open Source
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
package org.n52.javaps.engine.impl;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.n52.javaps.algorithm.ProcessInputs;
import org.n52.javaps.algorithm.ProcessOutputs;
import org.n52.javaps.description.TypedProcessDescription;
import org.n52.javaps.engine.EngineProcessExecutionContext;
import org.n52.shetland.ogc.ows.OwsCode;
import org.n52.shetland.ogc.wps.JobId;
import org.n52.shetland.ogc.wps.JobStatus;
import org.n52.shetland.ogc.wps.OutputDefinition;
import org.n52.shetland.ogc.wps.ResponseMode;
import org.n52.shetland.ogc.wps.data.ProcessData;

public class ProcessExecutionContextImpl implements EngineProcessExecutionContext {

	private List<OutputDefinition> outputDefinitions;
	private JobId jobId;
	private List<ProcessData> inputData;
	private List<ProcessData> outputData = new ArrayList<ProcessData>();
	private ProcessOutputs outputs;
	private ProcessInputs inputs;
	private TypedProcessDescription description;
    private Short percentCompleted;
    private OffsetDateTime estimatedCompletion;
    private Map<OwsCode, OutputDefinition> outputDefinitionMaps = new HashMap<OwsCode, OutputDefinition>();
    private OffsetDateTime nextPoll;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
	ProcessExecutionContextImpl(TypedProcessDescription description, JobId jobId, List<ProcessData> inputData, ProcessInputs inputs,
            List<OutputDefinition> outputDefinitions) {

        this.jobId = Objects.requireNonNull(jobId, "jobId");
        this.inputData = inputData;
        this.outputDefinitions = Objects.requireNonNull(outputDefinitions, "outputDefinitions");
        this.outputs = new ProcessOutputs();
        this.description = description;
        this.inputs = inputs;
        this.outputDefinitions.forEach((outputDef)->outputDefinitionMaps.put(outputDef.getId(), outputDef));
    }


	@Override
	public JobId getJobId() {
		return this.jobId;
	}

	@Override
	public ProcessInputs getInputs() {
		return this.inputs;
	}

	@Override
	public ProcessOutputs getOutputs() {
		// TODO Auto-generated method stub
		return this.outputs;
	}

	@Override
	public Optional<OutputDefinition> getOutputDefinition(OwsCode output) {
		for(OutputDefinition outputDefinition : this.outputDefinitions) {
			if(outputDefinition.getId().equals(output))
				return Optional.of(outputDefinition);
		}
		return Optional.empty();
	}

	@Override
    public void setPercentCompleted(Short percentCompleted) {
        this.lock.writeLock().lock();
        try {
            this.percentCompleted = percentCompleted;
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    @Override
    public void setEstimatedCompletion(OffsetDateTime estimatedCompletion) {
        this.lock.writeLock().lock();
        try {
            this.estimatedCompletion = estimatedCompletion;
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    @Override
    public void setNextPoll(OffsetDateTime nextPoll) {
        this.lock.writeLock().lock();
        try {
            this.nextPoll = nextPoll;
        } finally {
            this.lock.writeLock().unlock();
        }
    }


	@Override
	public List<ProcessData> getEncodedOutputs() throws Throwable {
		return this.outputData;
	}


	@Override
	public ResponseMode getResponseMode() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public JobStatus getJobStatus() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public TypedProcessDescription getDescription() {
		// TODO Auto-generated method stub
		return this.description;
	}


	@Override
	public Map<OwsCode, OutputDefinition> getOutputDefinitions() {
		// TODO Auto-generated method stub

		return this.outputDefinitionMaps;
	}

}
