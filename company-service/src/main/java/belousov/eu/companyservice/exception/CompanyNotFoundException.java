package belousov.eu.companyservice.exception;

public class CompanyNotFoundException extends RuntimeException {

    public CompanyNotFoundException(int id) {
        super(String.format("Company with id %d not found", id));
    }

    public CompanyNotFoundException(String name) {
        super(String.format("Company %s not found", name));
    }
}
