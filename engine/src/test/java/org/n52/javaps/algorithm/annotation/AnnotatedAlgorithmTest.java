package org.n52.javaps.algorithm.annotation;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.n52.javaps.algorithm.ExecutionException;
import org.n52.javaps.algorithm.ProcessInputs;
import org.n52.javaps.engine.ProcessExecutionContext;
import org.n52.javaps.io.Data;
import org.n52.javaps.io.GroupInputData;
import org.n52.javaps.io.InputHandler;
import org.n52.javaps.io.InputHandlerRepository;
import org.n52.javaps.io.OutputHandler;
import org.n52.javaps.io.OutputHandlerRepository;
import org.n52.javaps.io.complex.ComplexData;
import org.n52.javaps.io.literal.LiteralData;
import org.n52.javaps.io.literal.LiteralType;
import org.n52.javaps.io.literal.LiteralTypeRepository;
import org.n52.javaps.io.literal.xsd.LiteralIntType;
import org.n52.javaps.io.literal.xsd.LiteralStringType;
import org.n52.shetland.ogc.ows.OwsCode;
import org.n52.shetland.ogc.wps.Format;

/**
 *
 * @author <a href="mailto:m.rieke@52north.org">Matthes Rieke</a>
 */
public class AnnotatedAlgorithmTest {

    @Test
    public void testExecutionWithGroups() throws ExecutionException {
        IORepo io = new IORepo();
        LocalAlgorithm algoMock = new LocalAlgorithm();
        AnnotatedAlgorithm algo = new AnnotatedAlgorithm(io, io, new LiteralDataManagerImpl(), algoMock);
        ProcessExecutionContext pec = Mockito.mock(ProcessExecutionContext.class);
        ProcessInputs pis = new ProcessInputs();
        pis.put(new OwsCode("OPTICAL_IMAGES_TYPE"), Lists.newArrayList(new LiteralData("1"), new LiteralData("2")));
        pis.put(new OwsCode("OPTICAL_IMAGES_SOURCE"), Lists.newArrayList(new LiteralData("a"), new LiteralData("b")));
        GroupInputData groupData = new GroupInputData(pis);
        ProcessInputs allPis = new ProcessInputs();
        allPis.put(new OwsCode("OPTICAL_IMAGES"), Lists.newArrayList(groupData));
        allPis.put(new OwsCode("OPTICAL_IMAGES_TYPE"), Lists.newArrayList(new LiteralData("1"), new LiteralData("2")));
        allPis.put(new OwsCode("OPTICAL_IMAGES_SOURCE"), Lists.newArrayList(new LiteralData("a"), new LiteralData("b")));
        allPis.put(new OwsCode("NO_GROUP_INPUT_LIST"), Lists.newArrayList(new LiteralData("x"), new LiteralData("y")));
        allPis.put(new OwsCode("NO_GROUP_INPUT"), Lists.newArrayList(new LiteralData("1")));
        Mockito.when(pec.getInputs()).thenReturn(allPis);
        algo.execute(pec);

        Assert.assertThat(algoMock.opticalImagesSources.size(), CoreMatchers.is(2));
        Assert.assertThat(algoMock.opticalImagesSourceType.size(), CoreMatchers.is(2));
        Assert.assertThat(algoMock.noGroupInputList.size(), CoreMatchers.is(2));

        Assert.assertThat(algoMock.opticalImagesSources.get(0), CoreMatchers.equalTo("a"));
        Assert.assertThat(algoMock.opticalImagesSources.get(1), CoreMatchers.equalTo("b"));

        Assert.assertThat(algoMock.opticalImagesSourceType.get(0), CoreMatchers.equalTo("1"));
        Assert.assertThat(algoMock.opticalImagesSourceType.get(1), CoreMatchers.equalTo("2"));

        Assert.assertThat(algoMock.noGroupInputList.get(0), CoreMatchers.equalTo("x"));
        Assert.assertThat(algoMock.noGroupInputList.get(1), CoreMatchers.equalTo("y"));

        Assert.assertThat(algoMock.noGroupInput, CoreMatchers.equalTo("1"));
    }

    @Algorithm(
            identifier = "de.hsbo.wacodis.land_cover_classification",
            title = "Land Cover Classification",
            abstrakt = "Perform a land cover classification for optical images.",
            version = "0.0.1",
            storeSupported = true,
            statusSupported = true)
    public static class LocalAlgorithm {

        private List<String> opticalImageGroupInputs;
        private List<String> opticalImagesSourceType;
        private List<String> opticalImagesSources;
        private List<String> noGroupInputList;
        private String noGroupInput;

        @GroupInput(
                identifier = "OPTICAL_IMAGES",
                abstrakt = "the source for the optical images",
                title = "Optical images source",
                minOccurs = 1,
                maxOccurs = 10
        )
        public void setOpticalImageGroupInputs(List<String> opticalImageGroupInputs) {
            this.opticalImageGroupInputs = opticalImageGroupInputs;
        }

        @LiteralInput(
                identifier = "OPTICAL_IMAGES_TYPE",
                title = "Optical images source type",
                abstrakt = "The type of the source for the optical images",
                minOccurs = 1,
                maxOccurs = 1,
                defaultValue = "Sentinel-2",
                allowedValues = {"Sentinel-2", "Aerial_Image"},
                group = "OPTICAL_IMAGES")
        public void setOpticalImagesSourceType(List<String> value) {
            this.opticalImagesSourceType = value;
        }

        @LiteralInput(
                identifier = "OPTICAL_IMAGES_SOURCE",
                title = "Optical images sources",
                abstrakt = "Sources for the optical images",
                minOccurs = 1,
                maxOccurs = 10,
                defaultValue = "Sentinel-2",
                group = "OPTICAL_IMAGES")
        public void setOpticalImagesSources(List<String> value) {
            this.opticalImagesSources = value;
        }

        @LiteralInput(
                identifier = "NO_GROUP_INPUT_LIST",
                title = "NO_GROUP_INPUT_LIST title",
                minOccurs = 1,
                maxOccurs = 1,
                defaultValue = "Sentinel-2",
                allowedValues = {"Sentinel-2", "Aerial_Image"})
        public void setNoGroupInputList(List<String> value) {
            this.noGroupInputList = value;
        }

        @LiteralInput(
                identifier = "NO_GROUP_INPUT",
                title = "NO_GROUP_INPUT title",
                minOccurs = 1,
                maxOccurs = 1,
                defaultValue = "Sentinel-2",
                allowedValues = {"Sentinel-2", "Aerial_Image"})
        public void setNoGroupInput(String value) {
            this.noGroupInput = value;
        }

        @Execute
        public void execute() {

        }

    }

    public static class TestIData implements ComplexData<Object> {

        private static final long serialVersionUID = 8586931812896959156L;
        private final Object object;

        public TestIData(Object object) {
            this.object = object;
        }

        @Override
        public Object getPayload() {
            return this.object;
        }

        @Override
        public Class<?> getSupportedClass() {
            return Object.class;
        }
    }

    private static class IORepo implements OutputHandlerRepository, InputHandlerRepository {

        private static final Set<Format> FORMATS = new HashSet<>(Arrays.asList(
                new Format("text/xml", "UTF-8", "http://www.opengis.net/gml/3.2"),
                new Format("text/xml", "UTF-16", "http://www.opengis.net/gml/3.2"),
                new Format("text/xml", (String) null, "http://www.opengis.net/gml/3.2"),
                new Format("text/xml"),
                new Format("text/xml", "UTF-8"),
                new Format("text/xml", "UTF-16")));

        @Override
        public Set<OutputHandler> getOutputHandlers() {
            return Collections.emptySet();
        }

        @Override
        public Optional<OutputHandler> getOutputHandler(
                Format format, Class<? extends Data<?>> binding) {
            return Optional.empty();
        }

        @Override
        public Set<InputHandler> getInputHandlers() {
            return Collections.emptySet();
        }

        @Override
        public Optional<InputHandler> getInputHandler(
                Format format, Class<? extends Data<?>> binding) {
            return Optional.empty();
        }

        @Override
        public Set<Format> getSupportedFormats() {
            return Collections.unmodifiableSet(FORMATS);
        }

        @Override
        public Set<Format> getSupportedFormats(
                Class<? extends Data<?>> binding) {
            return getSupportedFormats();
        }
    }

    static class LiteralDataManagerImpl implements LiteralTypeRepository {

        @Override
        @SuppressWarnings("unchecked")
        public <T> LiteralType<T> getLiteralType(
                Class<? extends LiteralType<?>> literalType, Class<?> payloadType) {

            if (literalType == null || literalType.equals(LiteralType.class)) {
                if (payloadType != null) {
                    if (payloadType.equals(String.class)) {
                        return (LiteralType<T>) new LiteralStringType();
                    } else if (payloadType.equals(Integer.class)) {
                        return (LiteralType<T>) new LiteralIntType();
                    } else {
                        throw new Error("Unsupported payload type");
                    }
                } else {
                    throw new Error("Neither payload type nro literal type given");
                }
            } else {
                try {
                    return (LiteralType<T>) literalType.newInstance();
                } catch (InstantiationException | IllegalAccessException ex) {
                    throw new Error(ex);
                }
            }
        }
    }
}
