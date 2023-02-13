package pl.envelo.moovelo.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.Hashtag;
import pl.envelo.moovelo.repository.HashtagRepository;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class HashTagService {
    private static final int INCREMENT_HASHTAG_OCCURRENCE = 1;
    private final HashtagRepository hashtagRepository;

    public List<Hashtag> getHashtagsToAssign(List<Hashtag> hashtags) {
        List<Hashtag> hashtagsToAssign = new ArrayList<>();
        hashtags.forEach(hashtag -> {
            if (checkIfHashtagExistByHashTagValue(hashtag)) {
                Hashtag incrementedHashtag = incrementHashTagOccurrence(hashtag);
                hashtagsToAssign.add(incrementedHashtag);
            }
            if (!checkIfHashtagExistByHashTagValue(hashtag)) {
                Hashtag createdHashtag = createHashTag(hashtag);
                hashtagsToAssign.add(createdHashtag);
            }
        });
        return hashtagsToAssign;
    }

    private Hashtag incrementHashTagOccurrence(Hashtag hashtag) {
        Hashtag hashtagToIncrement = hashtagRepository.findHashtagByHashtagValue(hashtag.getHashtagValue());
        hashtagToIncrement.setOccurrences(hashtagToIncrement.getOccurrences() + INCREMENT_HASHTAG_OCCURRENCE);
        return hashtagRepository.save(hashtagToIncrement);
    }

    public void decrementHashtagOccurrence(Hashtag hashtag) {
        if (hashtag.getOccurrences() > 0) {
            hashtag.setOccurrences(hashtag.getOccurrences() - 1);
            hashtagRepository.save(hashtag);
        }
    }

    private Hashtag createHashTag(Hashtag hashtag) {
        hashtag.setVisible(true);
        hashtag.setOccurrences(INCREMENT_HASHTAG_OCCURRENCE);
        return hashtagRepository.save(hashtag);
    }

    private boolean checkIfHashtagExistByHashTagValue(Hashtag hashtag) {
        return hashtagRepository.findHashtagByHashtagValue(hashtag.getHashtagValue()) != null;
    }
}
