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
    private static final int INCREMENT_HASHTAG_OCCURRENCE = 1;
    private final HashtagRepository hashtagRepository;


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
