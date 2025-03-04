package belousov.eu.companyservice.controller;

import belousov.eu.companyservice.exception.CompanyNotFoundException;
import belousov.eu.companyservice.model.dto.CompanyDto;
import belousov.eu.companyservice.service.CompanyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CompanyController.class)
class CompanyControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    CompanyService companyService;

    @Test
    void test_getCompanyById_success() throws Exception {

        CompanyDto companyDto = CompanyDto.builder().id(1).name("name").build();

        when(companyService.getCompanyById(1)).thenReturn(companyDto);

        mockMvc.perform(get("/api/company/id/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void test_getCompanyById_whenCompanyNotFound() throws Exception {
        doThrow(CompanyNotFoundException.class).when(companyService).getCompanyById(anyInt());

        mockMvc.perform(get("/api/company/id/1"))
                .andExpect(status().isNotFound());
    }


    @Test
    void test_getCompanyById_whenSQLException() throws Exception {

        doThrow(DataRetrievalFailureException.class).when(companyService).getCompanyById(anyInt());

        mockMvc.perform(get("/api/company/id/1"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void test_getCompanyByName_success() throws Exception {
        CompanyDto companyDto = CompanyDto.builder().id(1).name("name").build();
        when(companyService.getCompanyByName("name")).thenReturn(companyDto);
        mockMvc.perform(get("/api/company/name/name"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void test_getCompanyByName_whenCompanyNotFound() throws Exception {
        doThrow(CompanyNotFoundException.class).when(companyService).getCompanyByName(anyString());

        mockMvc.perform(get("/api/company/name/name"))
                .andExpect(status().isNotFound());
    }

    @Test
    void test_getCompanyByName_whenSQLException() throws Exception {
        doThrow(DataRetrievalFailureException.class).when(companyService).getCompanyByName(anyString());
        mockMvc.perform(get("/api/company/name/name"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void test_getAllCompanies_success() throws Exception {

        List<CompanyDto> companyDtoList = List.of(CompanyDto.builder().id(1).name("name").build(),
                CompanyDto.builder().id(2).name("name").build());

        Page<CompanyDto> expected =
                new PageImpl<>(companyDtoList, PageRequest.of(0, 10), companyDtoList.size());

        when(companyService.getAllCompanies(anyInt(), anyInt())).thenReturn(expected);


        mockMvc.perform(get("/api/company/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.companyDtoList").isArray())
                .andExpect(jsonPath("$._embedded.companyDtoList", hasSize(2)))
                .andExpect(jsonPath("$._embedded.companyDtoList[0].id").value(1))
                .andExpect(jsonPath("$._embedded.companyDtoList[1].id").value(2));
    }

    @Test
    void test_getAllCompanies_whenSQLException() throws Exception {
        doThrow(DataRetrievalFailureException.class).when(companyService).getAllCompanies(anyInt(), anyInt());

        mockMvc.perform(get("/api/company/all"))
                .andExpect(status().is5xxServerError());
    }
}