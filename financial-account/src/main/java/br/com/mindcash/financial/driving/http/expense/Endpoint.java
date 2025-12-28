package br.com.mindcash.financial.driving.http.expense;

import br.com.mindcash.financial.application.commands.RegisterExpense;
import br.com.mindcash.financial.application.handlers.RegisterExpenseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/expense")
@Component("expenseEndpoint")
public class Endpoint {

    private final RegisterExpenseHandler registerExpense;

    public Endpoint(RegisterExpenseHandler registerExpense) {
        this.registerExpense = registerExpense;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> post(@RequestBody @Validated Request request ){
        RegisterExpense command = request.toCommand();
        registerExpense.handler(command);
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
