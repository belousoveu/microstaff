package belousov.eu.companyservice.service;

import belousov.eu.companyservice.api_client.UserFeignClient;
import belousov.eu.companyservice.exception.CompanyNotFoundException;
import belousov.eu.companyservice.model.dto.CompanyDto;
import belousov.eu.companyservice.model.dto.EmployeeDto;
import belousov.eu.companyservice.model.entity.Company;
import belousov.eu.companyservice.model.mapper.CompanyMapper;
import belousov.eu.companyservice.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompanyServiceImpTest {

    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private CompanyMapper companyMapper;
    @Mock
    private UserFeignClient userFeignClient;

    @InjectMocks
    private CompanyServiceImp companyService;

    private Company company1, company2;
    private CompanyDto companyDto1, companyDto2;

    @BeforeEach
    void setUp() {
        company1 = new Company(1, "name1", 1000L);
        company2 = new Company(2, "name2", 2000L);
        companyDto1 = CompanyDto.builder().id(1).name("name1").budget(1000L).build();
        companyDto2 = CompanyDto.builder().id(2).name("name2").budget(2000L).build();
    }

    @Test
    void test_getCompanyById_success() {

        when(companyRepository.findById(anyInt())).thenReturn(Optional.of(company1));
        when(companyMapper.toDto(company1)).thenReturn(companyDto1);
        when(userFeignClient.getEmployeesByCompanyId(anyInt())).thenReturn(
                List.of(EmployeeDto.builder().build()));

        CompanyDto actual = companyService.getCompanyById(1);

        assertNotNull(actual);
        assertEquals(companyDto1, actual);
    }

    @Test
    void test_getCompanyById_whenCompanyNotFound() {
        when(companyRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(CompanyNotFoundException.class, () -> companyService.getCompanyById(1));
    }

    @Test
    void test_getCompanyById_whenSQLException() {
        doThrow(DataRetrievalFailureException.class).when(companyRepository).findById(1);

        assertThrows(DataAccessException.class, () -> companyService.getCompanyById(1));
    }

    @Test
    void test_getCompanyByName_success() {
        when(companyRepository.findByName("name")).thenReturn(Optional.of(company1));
        when(companyMapper.toDto(company1)).thenReturn(companyDto1);
        when(userFeignClient.getEmployeesByCompanyId(anyInt())).thenReturn(
                List.of(EmployeeDto.builder().build()));

        CompanyDto actual = companyService.getCompanyByName("name");

        assertNotNull(actual);
        assertEquals(companyDto1, actual);
    }

    @Test
    void test_getCompanyByName_whenCompanyNotFound() {
        when(companyRepository.findByName("name")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> companyService.getCompanyByName("name"));
    }

    @Test
    void test_getCompanyByName_whenSQLException() {
        doThrow(DataRetrievalFailureException.class).when(companyRepository).findByName(anyString());

        assertThrows(DataAccessException.class, () -> companyService.getCompanyByName("name"));
    }

    @Test
    void test_getAllCompanies_success() {

        List<Company> companyDtoList = List.of(company1, company2);
        Page<Company> expected =
                new PageImpl<>(companyDtoList, PageRequest.of(0, 10), companyDtoList.size());

        when(companyRepository.findAll(PageRequest.of(0, 10))).thenReturn(expected);
        when(companyMapper.toDto(company1)).thenReturn(companyDto1);
        when(companyMapper.toDto(company2)).thenReturn(companyDto2);

        Page<CompanyDto> actual = companyService.getAllCompanies(0, 10);

        assertNotNull(actual);
        assertEquals(2, actual.getTotalElements());
        assertTrue(actual.get().anyMatch(c -> c.getId() == 1));
        assertTrue(actual.get().anyMatch(c -> c.getId() == 2));
    }

    @Test
    void test_getAllCompanies_whenSQLException() {

        doThrow(DataRetrievalFailureException.class).when(companyRepository).findAll(any(PageRequest.class));

        assertThrows(DataAccessException.class, () -> companyService.getAllCompanies(0, 10));
    }

}