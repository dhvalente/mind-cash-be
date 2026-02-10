package br.com.mindcash.financial.driving.http.income;

import br.com.mindcash.financial.application.commands.RegisterExpense;
import br.com.mindcash.financial.application.commands.RegisterIncome;
import br.com.mindcash.financial.application.ports.inbound.CommandHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/income")
@Component("incomeEndpoint")
public class Endpoint {

    private final CommandHandler<RegisterIncome> handler;

    public Endpoint(CommandHandler<RegisterIncome> handler) {
        this.handler = handler;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> registerExpense(@RequestBody @Validated Request request ){
        RegisterIncome command = request.toCommand();
        handler.handler(command);
        return ResponseEntity.status(HttpStatus.CREATED).body("Income saved successfully");
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
