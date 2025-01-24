package com.congthanh.taxservice.controller;

import com.congthanh.taxservice.service.TaxClassService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class TaxClassController {

    private final TaxClassService taxClassService;

    @GetMapping("/paging")
    public ResponseEntity<TaxClassListGetVm> getPageableTaxClasses(
            @RequestParam(value = "pageNo", defaultValue = PageableConstant.DEFAULT_PAGE_NUMBER, required = false)
            final int pageNo,
            @RequestParam(value = "pageSize", defaultValue = PageableConstant.DEFAULT_PAGE_SIZE, required = false)
            final int pageSize) {
        return ResponseEntity.ok(taxClassService.getPageableTaxClasses(pageNo, pageSize));
    }

    @GetMapping
    public ResponseEntity<List<TaxClassVm>> listTaxClasses() {
        return ResponseEntity.ok(taxClassService.findAllTaxClasses());
    }

    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ApiConstant.CODE_200, description = ApiConstant.OK,
                    content = @Content(schema = @Schema(implementation = TaxClassVm.class))),
            @ApiResponse(responseCode = ApiConstant.CODE_404, description = ApiConstant.NOT_FOUND,
                    content = @Content(schema = @Schema(implementation = ErrorVm.class)))})
    public ResponseEntity<TaxClassVm> getTaxClass(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(taxClassService.findById(id));
    }

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = ApiConstant.CODE_201, description = ApiConstant.CREATED,
                    content = @Content(schema = @Schema(implementation = TaxClassVm.class))),
            @ApiResponse(responseCode = ApiConstant.CODE_400, description = ApiConstant.BAD_REQUEST,
                    content = @Content(schema = @Schema(implementation = ErrorVm.class)))})
    public ResponseEntity<TaxClassVm> createTaxClass(
            @Valid @RequestBody final TaxClassPostVm taxClassPostVm,
            final UriComponentsBuilder uriComponentsBuilder) {
        final TaxClass taxClass = taxClassService.create(taxClassPostVm);
        return ResponseEntity.created(
                        uriComponentsBuilder
                                .replacePath("/tax-classes/{id}")
                                .buildAndExpand(taxClass.getId())
                                .toUri())
                .body(TaxClassVm.fromModel(taxClass));
    }

    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ApiConstant.CODE_204, description = ApiConstant.NO_CONTENT, content = @Content()),
            @ApiResponse(responseCode = ApiConstant.CODE_404, description = ApiConstant.NOT_FOUND,
                    content = @Content(schema = @Schema(implementation = ErrorVm.class))),
            @ApiResponse(responseCode = ApiConstant.CODE_400, description = ApiConstant.BAD_REQUEST,
                    content = @Content(schema = @Schema(implementation = ErrorVm.class)))})
    public ResponseEntity<Void> updateTaxClass(@PathVariable final Long id,
                                               @Valid @RequestBody final TaxClassPostVm taxClassPostVm) {
        taxClassService.update(taxClassPostVm, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ApiConstant.CODE_204, description = ApiConstant.NO_CONTENT, content = @Content()),
            @ApiResponse(responseCode = ApiConstant.CODE_404, description = ApiConstant.NOT_FOUND,
                    content = @Content(schema = @Schema(implementation = ErrorVm.class))),
            @ApiResponse(responseCode = ApiConstant.CODE_400, description = ApiConstant.BAD_REQUEST,
                    content = @Content(schema = @Schema(implementation = ErrorVm.class)))})
    public ResponseEntity<Void> deleteTaxClass(@PathVariable(name = "id") final Long id) {
        taxClassService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
