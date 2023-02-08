package pl.envelo.moovelo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.Hashtag;
import pl.envelo.moovelo.repository.HashtagRepository;

@RequiredArgsConstructor
@Service
@Slf4j
public class HashTagService {

    private HashtagRepository hashtagRepository;

    @Autowired
    public HashTagService(HashtagRepository hashtagRepository) {
        this.hashtagRepository = hashtagRepository;
    }

    public void decrementHashtagOccurrence(Hashtag hashtag) {
        if (hashtag.getOccurrences() > 0) {
            hashtag.setOccurrences(hashtag.getOccurrences() - 1);
            hashtagRepository.save(hashtag);
        }
    }
}
