package pl.envelo.moovelo.controller.event;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import pl.envelo.moovelo.controller.dto.event.EventListResponseDto;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ExternalEventControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ExternalEventController externalEventController;

    @Test
    void getAllExternalEventsPositiveTest() throws Exception {
        mvc.perform(get("/api/v1/externalEvents"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].city", is("Warsaw")));
    }

    @Disabled
    void getAllExternalEventsNegativeTest() throws Exception {
        //TODO: check the case when Admin is not logged in.
    }

    @Test
    @Transactional
    void getAllExternalEventsUnitTest() {
        // GIVEN

        // WHEN
        ResponseEntity<List<EventListResponseDto>> result = externalEventController.getAllExternalEvents();

        // THEN
        assertTrue(result.hasBody());
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertTrue(Objects.requireNonNull(result.getBody()).size() > 0);
    }
}