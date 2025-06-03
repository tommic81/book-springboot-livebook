package pl.bykowski.invoicespringmvcexample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bykowski.invoicespringmvcexample.repository.entity.Address;
import pl.bykowski.invoicespringmvcexample.repository.entity.Invoice;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
