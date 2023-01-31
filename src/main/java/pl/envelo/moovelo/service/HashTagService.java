package pl.envelo.moovelo.service;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.Hashtag;
import pl.envelo.moovelo.repository.HashtagRepository;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class HashTagService {
    private final HashtagRepository hashtagRepository;

    private static final int INCREMENT_HASHTAG_OCCURRENCE = 1;

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

    public List<Hashtag> getAllVisibleHashtags() {
        return hashtagRepository.findHashtagsByVisibleIsTrue();
    }

    public List<Hashtag> validateHashtags(List<Hashtag> hashtags) {
        List<Hashtag> hashtagListToSave = new ArrayList<>();
        List<Hashtag> allVisibleHashtagsInDb = getAllVisibleHashtags();

        hashtags.forEach(hashtag -> {
            if (allVisibleHashtagsInDb.contains(hashtag)) {
                incrementHashTagOccurrence(hashtag);
            } else {
                hashtag.setOccurrences(INCREMENT_HASHTAG_OCCURRENCE);
                hashtag.setVisible(true);
                hashtagListToSave.add(hashtag);
            }
        });
        return hashtagListToSave;
    }

    private void incrementHashTagOccurrence(Hashtag hashtag) {
        Hashtag hashtagByHashtagValue = hashtagRepository.findHashtagByHashtagValue(hashtag.getHashtagValue());
        hashtagByHashtagValue.setOccurrences(hashtagByHashtagValue.getOccurrences() + INCREMENT_HASHTAG_OCCURRENCE);
        hashtagRepository.save(hashtagByHashtagValue);
    }
}
