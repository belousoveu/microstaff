package belousov.eu.companyservice.controller;

import belousov.eu.companyservice.model.dto.CompanyDto;
import belousov.eu.companyservice.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/company")
@AllArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/id/{id}")
    public CompanyDto getCompanyById(@PathVariable("id") int id) {
        return companyService.getCompanyById(id);
    }

    @GetMapping("/name/{name}")
    public CompanyDto getCompanyByName(@PathVariable("name") String name) {
        return companyService.getCompanyByName(name);
    }

    @GetMapping("/all")
    public PagedModel<EntityModel<CompanyDto>> getAllCompanies(
            @RequestParam(required = false, name = "page", defaultValue = "0") int page,
            @RequestParam(required = false, name = "size", defaultValue = "10") int size,
            PagedResourcesAssembler<CompanyDto> assembler) {
        return assembler.toModel(companyService.getAllCompanies(page, size));
    }


}
