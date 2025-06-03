package pl.bykowski.invoicespringmvcexample.controler;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.bykowski.invoicespringmvcexample.InvoiceService;
import pl.bykowski.invoicespringmvcexample.controler.dto.OrderDto;
import pl.bykowski.invoicespringmvcexample.repository.ClientRepository;
import pl.bykowski.invoicespringmvcexample.repository.entity.Invoice;

import java.util.List;

@Controller
public class InvoiceController {

    private final InvoiceService invoiceService;
    private ClientRepository clientRepository;

    public InvoiceController(InvoiceService invoiceService, ClientRepository clientRepository) {
        this.invoiceService = invoiceService;
        this.clientRepository = clientRepository;
    }

    @GetMapping
    public String getAllInvoices(Model model) {
        List<OrderDto> allOrders = clientRepository.getAllOrders();
        model.addAttribute("allOrders", allOrders);
        return "gui";
    }

    @PostMapping("/save")
    public String saveInvoice(@ModelAttribute Invoice invoice) {
        invoiceService.saveInvoice(invoice);
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String deleteInvoice(@RequestParam Long id) {
        invoiceService.deleteInvoice(id);
        return "redirect:/";
    }

}
