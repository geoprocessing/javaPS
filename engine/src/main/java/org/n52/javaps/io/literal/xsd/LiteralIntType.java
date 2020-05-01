/*
 * Copyright 2016-2020 52°North Initiative for Geospatial Open Source
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
package org.n52.javaps.io.literal.xsd;

import java.math.BigInteger;

import org.n52.javaps.io.DecodingException;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public class LiteralIntType extends AbstractLiteralIntegerType<Integer> {

    private static final long serialVersionUID = -6420285713082591594L;

    @Override
    public String getName() {
        return INT;
    }

    @Override
    protected BigInteger min() {
        return BigInteger.valueOf(Integer.MIN_VALUE);
    }

    @Override
    protected BigInteger max() {
        return BigInteger.valueOf(Integer.MAX_VALUE);
    }

    @Override
    public Integer parse(String value) throws DecodingException {
        return asBigInteger(value).intValue();
    }

    @Override
    public Class<Integer> getPayloadType() {
        return Integer.class;
    }

    @Override
    public String generate(Integer value) {
        return value.toString();
    }

}
