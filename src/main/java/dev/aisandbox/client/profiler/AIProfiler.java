package dev.aisandbox.client.profiler;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class AIProfiler {

    @Getter
    long stepCount=0;

    Map<String,Double> cumulativeStepTiming = new HashMap<>();

    public void addProfileStep(ProfileStep step) {
        stepCount++;
        step.getTimings().forEach((name,value)->{
            Double v = cumulativeStepTiming.get(name);
            if (v==null) {
                v=0.0;
            }
            cumulativeStepTiming.put(name,v+value);
        });
    }

    public Map<String,Double> getAverageTime() {
        Map<String,Double>result = new HashMap<>();
        cumulativeStepTiming.forEach((name,value)->{
            result.put(name,value/stepCount);
        });
        return result;
    }
}
