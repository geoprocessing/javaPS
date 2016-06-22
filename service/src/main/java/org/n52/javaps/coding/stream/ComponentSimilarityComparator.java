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
package org.n52.javaps.coding.stream;

import org.n52.iceland.component.Component;
import org.n52.iceland.util.Similar;

// TODO move to iceland
public class ComponentSimilarityComparator<
        K extends Similar<K>,
        C extends Component<K>
    >
        extends ProxySimilarityComparator<C, K> {

    public ComponentSimilarityComparator(K ref) {
        super(ref, Component<K>::getKeys);
    }

}
