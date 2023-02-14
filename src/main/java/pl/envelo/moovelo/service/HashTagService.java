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
        Hashtag hashtagToIncrement = updateHashtagOccurrences(hashtag);
        return hashtagRepository.save(hashtagToIncrement);
    }

    private Hashtag updateHashtagOccurrences(Hashtag hashtag) {
        Hashtag hashtagToIncrement = hashtagRepository.findByHashtagValueIgnoreCase(hashtag.getHashtagValue());
        hashtagToIncrement.setOccurrences(hashtagToIncrement.getOccurrences() + INCREMENT_HASHTAG_OCCURRENCE);
        return hashtagToIncrement;
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
        return hashtagRepository.findByHashtagValueIgnoreCase(hashtag.getHashtagValue()) != null;
    }

    public List<Hashtag> validateHashtagsForUpdateEvent(List<Hashtag> hashtagsFromDto, List<Hashtag> hashtagsFromEventInDb) {
        log.info("HashtagService - validateHashtagsForUpdateEvent()");
        List<Hashtag> recurringHashtags = getRecurringHashtags(hashtagsFromDto, hashtagsFromEventInDb);
        decrementHashtagsNoLongerPresentInEvent(hashtagsFromDto, hashtagsFromEventInDb);
        List<Hashtag> hashtagsToAssign = joinHashtagListsForUpdateEvent(hashtagsFromDto, recurringHashtags);
        log.info("HashtagService - validateHashtagsForUpdateEvent() - hashtagsToAssign - {}", hashtagsToAssign);
        return hashtagsToAssign;
    }

    private List<Hashtag> getRecurringHashtags(List<Hashtag> hashtagsFromDto, List<Hashtag> hashtagsFromEventInDb) {
        List<Hashtag> recurringHashtags = new ArrayList<>(hashtagsFromDto);
        recurringHashtags.retainAll(hashtagsFromEventInDb);
        return recurringHashtags;
    }

    private void decrementHashtagsNoLongerPresentInEvent(List<Hashtag> hashtagsFromDto, List<Hashtag> hashtagsFromEventInDb) {
        hashtagsFromEventInDb.removeAll(hashtagsFromDto);
        hashtagsFromEventInDb.forEach(this::decrementHashtagOccurrence);
    }

    private List<Hashtag> joinHashtagListsForUpdateEvent(List<Hashtag> hashtagsFromDto, List<Hashtag> recurringHashtags) {
        hashtagsFromDto.removeAll(recurringHashtags);
        List<Hashtag> hashtagsToAssign = getHashtagsToAssign(hashtagsFromDto);
        recurringHashtags = recurringHashtags.stream()
                .map(hashtag -> hashtagRepository.findByHashtagValueIgnoreCase(hashtag.getHashtagValue()))
                .toList();
        hashtagsToAssign.addAll(recurringHashtags);
        return hashtagsToAssign;
    }
}
