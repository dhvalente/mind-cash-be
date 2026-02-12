package br.com.mindcash.financial.driving.http.income;

import br.com.mindcash.financial.application.commands.RegisterIncome;
import br.com.mindcash.financial.application.ports.inbound.CommandHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EndpointTest {

    @Mock
    private CommandHandler<RegisterIncome> handler;

    private Endpoint endpoint;

    @BeforeEach
    void setUp() {
        endpoint = new Endpoint(handler);
    }

    @Test
    @DisplayName("registerExpense should call handler with command from request and return 201 Created with success message")
    void registerExpense_callsHandler_andReturnsCreated() {
        // Arrange
        Request request = mock(Request.class);
        RegisterIncome command = mock(RegisterIncome.class);
        when(request.toCommand()).thenReturn(command);

        // Act
        ResponseEntity<String> response = endpoint.registerExpense(request);

        // Assert response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo("Income saved successfully");

        // Assert interaction
        ArgumentCaptor<RegisterIncome> captor = ArgumentCaptor.forClass(RegisterIncome.class);
        verify(handler, times(1)).handler(captor.capture());
        assertThat(captor.getValue()).isSameAs(command);

        // Ensure no further interactions
        verifyNoMoreInteractions(handler, request);
    }
}