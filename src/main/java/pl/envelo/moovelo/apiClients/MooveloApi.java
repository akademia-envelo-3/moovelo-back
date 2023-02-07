package pl.envelo.moovelo.apiClients;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.RestController;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.repository.actors.BasicUserRepository;

@RestController
@AllArgsConstructor
public class MooveloApi {

    BasicUserRepository basicUserRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        BasicUser basicUser = new BasicUser();
        basicUser.setFirstname("Marek");
        BasicUser basicUser1 = new BasicUser();
        basicUser1.setFirstname("Darek");
        BasicUser basicUser2 = new BasicUser();
        basicUser2.setFirstname("Karola");
        BasicUser basicUser3 = new BasicUser();
        basicUser3.setFirstname("Czesiek");

        basicUserRepository.save(basicUser);
        basicUserRepository.save(basicUser1);
        basicUserRepository.save(basicUser2);
        basicUserRepository.save(basicUser3);
    }
}
