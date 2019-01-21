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
package org.n52.javaps.description.impl;

import java.util.Set;

import org.n52.shetland.ogc.ows.OwsCRS;
import org.n52.shetland.ogc.ows.OwsCode;
import org.n52.shetland.ogc.ows.OwsKeyword;
import org.n52.shetland.ogc.ows.OwsLanguageString;
import org.n52.shetland.ogc.ows.OwsMetadata;
import org.n52.shetland.ogc.wps.InputOccurence;
import org.n52.shetland.ogc.wps.description.impl.BoundingBoxInputDescriptionImpl;
import org.n52.shetland.ogc.wps.description.impl.BoundingBoxInputDescriptionImpl.AbstractBuilder;
import org.n52.javaps.description.TypedBoundingBoxInputDescription;

public class TypedBoundingBoxInputDescriptionImpl extends BoundingBoxInputDescriptionImpl implements
        TypedBoundingBoxInputDescription {

    private String group;

    public TypedBoundingBoxInputDescriptionImpl(OwsCode id, OwsLanguageString title, OwsLanguageString abstrakt, Set<
            OwsKeyword> keywords, Set<OwsMetadata> metadata, InputOccurence occurence, OwsCRS defaultCRS, Set<
                    OwsCRS> supportedCRS, String group) {
        super(id, title, abstrakt, keywords, metadata, occurence, defaultCRS, supportedCRS);
        this.group = group;
    }

    protected TypedBoundingBoxInputDescriptionImpl(Builder builder) {
        this(builder.getId(), builder.getTitle(), builder.getAbstract(), builder.getKeywords(), builder.getMetadata(),
                new InputOccurence(builder.getMinimalOccurence(), builder.getMaximalOccurence()), builder
                        .getDefaultCRS(), builder.getSupportedCRS(), builder.getGroup());


    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public boolean isGroup() {
        return this.getGroup() != null && !this.getGroup().isEmpty();
    }

    public static class Builder extends AbstractBuilder<TypedBoundingBoxInputDescription, Builder> {

        private String group;

        @Override
        public TypedBoundingBoxInputDescription build() {
            return new TypedBoundingBoxInputDescriptionImpl(this);
        }

        @SuppressWarnings("unchecked")
        public Builder withGroup(String group) {
            this.group = group;
            return  this;
        }

        public String getGroup() {
            return group;
        }
    }
}
