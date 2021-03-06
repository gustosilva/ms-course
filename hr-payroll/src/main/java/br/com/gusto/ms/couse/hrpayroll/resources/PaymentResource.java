package br.com.gusto.ms.couse.hrpayroll.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import br.com.gusto.ms.couse.hrpayroll.entities.Payment;
import br.com.gusto.ms.couse.hrpayroll.services.PaymentService;

@RestController
@RequestMapping("/payments")
public class PaymentResource {

	private final PaymentService service;

	@Autowired
	public PaymentResource(PaymentService service) {
		this.service = service;
	}

	// O Hystrix já é um projeto depreciado!!! Não usem em nenhum projeto, uma
	// alternativa é o Resilience4j
	@HystrixCommand(fallbackMethod = "getPaymentFallback")
	@GetMapping("/{workerId}/days/{days}")
	public ResponseEntity<Payment> getPayment(@PathVariable("workerId") long workerId, @PathVariable("days") int days) {
		return ResponseEntity.ok(service.getPayment(workerId, days));
	}

	public ResponseEntity<Payment> getPaymentFallback(long workerId, int days) {
		return ResponseEntity.ok(new Payment("Error", 0.0, 0));
	}
}
