package br.com.mindcash.financial.driving.http.expense;

import br.com.mindcash.financial.application.commands.RegisterExpense;
import br.com.mindcash.financial.application.ports.inbound.CommandHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/expense")
public class Endpoint {

    private final CommandHandler<RegisterExpense> handler;

    public Endpoint(CommandHandler<RegisterExpense> handler) {
        this.handler = handler;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> post(@RequestBody @Validated Request request) {
        RegisterExpense command = request.toCommand();
        handler.handler(command);
        return ResponseEntity.status(HttpStatus.CREATED).body("Expense saved successfully");
    }
}
