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
package org.n52.javaps.algorithm.annotation;


import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Member;
import java.util.function.Function;
import org.n52.javaps.description.TypedGroupInputDescription;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.n52.javaps.description.impl.TypedProcessDescriptionFactory;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 * @param <M>
 *            the accessible member type
 * @param <B>
 *            the binding type
 */
class GroupInputAnnotationParser<M extends AccessibleObject & Member, B extends AbstractInputBinding<M>> extends
        AbstractInputAnnotationParser<GroupInput, M, B> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationParser.class);

    GroupInputAnnotationParser(Function<M, B> bindingFunction) {
        super(bindingFunction);
    }



    @Override
    public Class<? extends GroupInput> getSupportedAnnotation() {
        return GroupInput.class;
    }

    @Override
    public TypedGroupInputDescription createDescription(GroupInput annotation,
            B binding) {
        // auto generate binding if it's not explicitly declared
        TypedProcessDescriptionFactory descriptionFactory = new TypedProcessDescriptionFactory();

        return descriptionFactory.groupInput()
                .withIdentifier(annotation.identifier())
                .withTitle(annotation.title())
                .withAbstract(annotation.abstrakt())
                .withMinimalOccurence(annotation.minOccurs())
                .withMaximalOccurence(annotation.maxOccurs()).build();
    }

}
