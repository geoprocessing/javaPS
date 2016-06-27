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
package org.n52.javaps;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Future;

import org.n52.iceland.ogc.ows.OwsCode;
import org.n52.javaps.ogc.wps.JobId;
import org.n52.javaps.ogc.wps.OutputDefinition;
import org.n52.javaps.ogc.wps.Result;
import org.n52.javaps.ogc.wps.StatusInfo;
import org.n52.javaps.ogc.wps.data.Data;
import org.n52.javaps.ogc.wps.description.ProcessDescription;

/**
 * TODO JavaDoc
 * @author Christian Autermann
 */
public interface Engine {

    Set<JobId> getJobIdentifiers();

    Set<OwsCode> getProcessIdentifiers();

    Optional<ProcessDescription> getProcessDescription(OwsCode identifier);

    StatusInfo dismiss(JobId identifier);

    JobId execute(OwsCode identifier, List<Data> inputs, List<OutputDefinition> outputs);

    StatusInfo getStatus(JobId jobId);

    Future<Result> getResult(JobId jobId);

}
