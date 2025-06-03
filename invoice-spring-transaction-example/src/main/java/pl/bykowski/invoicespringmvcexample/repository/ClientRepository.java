package pl.bykowski.invoicespringmvcexample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.bykowski.invoicespringmvcexample.controler.dto.OrderDto;
import pl.bykowski.invoicespringmvcexample.repository.entity.Client;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("select new pl.bykowski.invoicespringmvcexample.controler.dto.OrderDto(sum(product.price), client.clientName, invoice.date) " +
            "from Client client " +
            "join client.invoice invoice " +
            "join invoice.product product group by client.clientName")
    List<OrderDto> getAllOrders();
}
