package pl.envelo.moovelo.apiClients;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.RestController;
import pl.envelo.moovelo.entity.Hashtag;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.repository.HashtagRepository;
import pl.envelo.moovelo.repository.actors.BasicUserRepository;

@RestController
@AllArgsConstructor
public class MooveloApi {

    BasicUserRepository basicUserRepository;
    HashtagRepository hashtagRepository;

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

    @EventListener(ApplicationReadyEvent.class)
    public void createData() {
        Hashtag hashtag1 = new Hashtag();
        hashtag1.setHashtagValue("Kultura");
        hashtag1.setVisible(true);
        hashtag1.setOccurrences(1);

        Hashtag hashtag2 = new Hashtag();
        hashtag2.setHashtagValue("Picie Wódki");
        hashtag2.setVisible(false);

        Hashtag hashtag3 = new Hashtag();
        hashtag3.setHashtagValue("Najebmy się");
        hashtag3.setVisible(false);

        Hashtag hashtag4 = new Hashtag();
        hashtag4.setHashtagValue("Spotkanie");
        hashtag4.setVisible(true);
        hashtag4.setOccurrences(1);

        hashtagRepository.save(hashtag1);
        hashtagRepository.save(hashtag2);
        hashtagRepository.save(hashtag3);
        hashtagRepository.save(hashtag4);
    }
}
