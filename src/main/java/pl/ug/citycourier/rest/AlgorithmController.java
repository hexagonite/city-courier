package pl.ug.citycourier.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ug.citycourier.internal.algorithm.runner.AlgorithmRunner;

@RestController
@RequestMapping("/api/algorithm")
public class AlgorithmController {

    private AlgorithmRunner algorithmRunner;

    @Autowired
    public AlgorithmController(AlgorithmRunner algorithmRunner) {
        this.algorithmRunner = algorithmRunner;
    }

    @GetMapping("/run")
    public void runAlgorithm() {
        algorithmRunner.run();
    }

}
