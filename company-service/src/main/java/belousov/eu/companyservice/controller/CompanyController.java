package belousov.eu.companyservice.controller;

import belousov.eu.companyservice.model.dto.CompanyDto;
import belousov.eu.companyservice.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/company")
@AllArgsConstructor
@Tag(name = "Компании", description = "Операции с компаниями")
public class CompanyController {

    private final CompanyService companyService;

    @Operation(summary = "Получить компанию по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Компания успешно получена"),
            @ApiResponse(responseCode = "404", description = "Компания не найдена"),
            @ApiResponse(responseCode = "503", description = "Сервис временно недоступен"),
    })
    @GetMapping("/id/{id}")
    public CompanyDto getCompanyById(@PathVariable("id") int id) {
        return companyService.getCompanyById(id);
    }

    @Operation(summary = "Получить компанию по наименованию")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Компания успешно получена"),
            @ApiResponse(responseCode = "404", description = "Компания не найдена"),
            @ApiResponse(responseCode = "503", description = "Сервис временно недоступен"),
    })
    @GetMapping("/name/{name}")
    public CompanyDto getCompanyByName(@PathVariable("name") String name) {
        return companyService.getCompanyByName(name);
    }

    @Operation(summary = "Получить список всех компаний")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список компаний успешно получен"),
            @ApiResponse(responseCode = "503", description = "Сервис временно недоступен"),
    })
    @GetMapping("/all")
    public PagedModel<EntityModel<CompanyDto>> getAllCompanies(
            @RequestParam(required = false, name = "page", defaultValue = "0") int page,
            @RequestParam(required = false, name = "size", defaultValue = "10") int size,
            PagedResourcesAssembler<CompanyDto> assembler) {
        return assembler.toModel(companyService.getAllCompanies(page, size));
    }


}
