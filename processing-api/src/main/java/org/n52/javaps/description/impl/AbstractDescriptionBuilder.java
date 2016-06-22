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
package org.n52.javaps.description.impl;

import java.util.Objects;
import java.util.Set;

import org.n52.javaps.description.Description;
import org.n52.javaps.description.DescriptionBuilder;
import org.n52.javaps.ogc.ows.OwsCode;
import org.n52.javaps.ogc.ows.OwsKeyword;
import org.n52.javaps.ogc.ows.OwsLanguageString;
import org.n52.javaps.ogc.ows.OwsMetadata;

import com.google.common.collect.ImmutableSet;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 * @param <T>
 * @param <B>
 */
public abstract class AbstractDescriptionBuilder<T extends Description, B extends DescriptionBuilder<T, B>>
        implements DescriptionBuilder<T, B> {

    private OwsCode id;
    private OwsLanguageString title;
    private OwsLanguageString abstrakt;
    private final ImmutableSet.Builder<OwsKeyword> keywords = ImmutableSet.builder();
    private final ImmutableSet.Builder<OwsMetadata> metadata = ImmutableSet.builder();

    @SuppressWarnings(value = "unchecked")
    @Override
    public B withIdentifier(OwsCode id) {
        this.id = Objects.requireNonNull(id);
        return (B) this;
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public B withTitle(OwsLanguageString title) {
        this.title = title;
        return (B) this;
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public B withAbstract(OwsLanguageString abstrakt) {
        this.abstrakt = abstrakt;
        return (B) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public B withKeyword(OwsKeyword keyword) {
        this.keywords.add(Objects.requireNonNull(keyword));
        return (B) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public B withMetadata(OwsMetadata metadata) {
        this.metadata.add(Objects.requireNonNull(metadata));
        return (B) this;
    }

    OwsCode getId() {
        return this.id;
    }

    OwsLanguageString getTitle() {
        return this.title;
    }

    OwsLanguageString getAbstract() {
        return this.abstrakt;
    }

    Set<OwsKeyword> getKeywords() {
        return this.keywords.build();
    }

    Set<OwsMetadata> getMetadata() {
        return this.metadata.build();
    }


}
