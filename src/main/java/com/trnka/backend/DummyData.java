package com.trnka.backend;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.trnka.backend.dto.VstClassDto;
import com.trnka.backend.dto.VstTestDto;

// todo remove after production ready
public class DummyData {

    public List<VstClassDto> getClasses(int requiredSize) {
        return IntStream.range(0, requiredSize).mapToObj(i -> getVstClass(i)).collect(Collectors.toList());
    }

    private VstClassDto getVstClass(Integer id) {
        VstClassDto c = new VstClassDto();
        c.setId(Long.valueOf(id));
        c.setName("prvaci");
        c.setStudentCount(id);
        return c;
    }

    public List<VstTestDto> getTests(final int requiredSize) {
        return IntStream.range(0, requiredSize).mapToObj(i -> getVstTest(i)).collect(Collectors.toList());
    }

    private VstTestDto getVstTest(Integer id) {
        VstTestDto c = new VstTestDto();
        c.setId(Long.valueOf(id));
        c.setName("random name");
        c.setClassName("class x");
        c.setFinalTest(true);
        c.setLevel(id);
        return c;
    }
}
