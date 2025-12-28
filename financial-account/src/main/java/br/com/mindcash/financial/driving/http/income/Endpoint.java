package br.com.mindcash.financial.driving.http.income;

import br.com.mindcash.financial.driving.http.expense.Request;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/clientes")
@Component("incomeEndpoint")
public class Endpoint {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> registerExpense(@RequestBody @Validated Request request ){

        return ResponseEntity.status(HttpStatus.CREATED).body("Expense saved successfully");
    }
/*
    @GetMapping("/{id}")
    public ResponseEntity<Parking> getParkingById(@PathVariable UUID id) {
        return parkingService
                .findById(id)
                .map(parking -> new ResponseEntity<>(parking, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }*/

}
