package pl.envelo.moovelo.controller.user;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.envelo.moovelo.service.actors.BasicUserService;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class BasicUserController {

}
