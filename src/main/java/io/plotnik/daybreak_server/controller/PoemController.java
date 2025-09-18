package io.plotnik.daybreak_server.controller;

import io.plotnik.daybreak_server.model.Poem;
import io.plotnik.daybreak_server.service.PoemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
@Tag(name = "Poems API", description = "API for accessing poems from markdown files")
public class PoemController {

    private final PoemService poemService;

    public PoemController(PoemService poemService) {
        this.poemService = poemService;
    }

    @GetMapping("/poems")
    @Operation(summary = "Get all available file names")
    public ResponseEntity<List<String>> getFileNames() {
        return ResponseEntity.ok(poemService.getAvailableFileNames());
    }

    @GetMapping("/poems/{fileName}")
    @Operation(summary = "Get the number of poems in a file")
    public ResponseEntity<Integer> getPoemCount(
            @Parameter(description = "Name of the markdown file containing poems", 
                      example = "titanik")
            @PathVariable String fileName) {
        return poemService.getPoemCount(fileName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/poems/{fileName}/{poemNumber}")
    @Operation(summary = "Get a specific poem by its number in a file (1-based index)")
    public ResponseEntity<Poem> getPoem(
            @Parameter(description = "Name of the markdown file containing poems", 
                      example = "titanik")
            @PathVariable String fileName,
            @Parameter(description = "Poem number in the file (1-based index)", 
                      example = "1")
            @PathVariable int poemNumber) {
        return poemService.getPoem(fileName, poemNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/admin/reload")
    @Operation(summary = "Reload poem files from the filesystem")
    public ResponseEntity<Void> reloadPoemFiles() {
        poemService.reloadPoemFiles();
        return ResponseEntity.ok().build();
    }
}