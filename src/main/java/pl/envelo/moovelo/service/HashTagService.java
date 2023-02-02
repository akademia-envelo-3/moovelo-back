package pl.envelo.moovelo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.envelo.moovelo.entity.Hashtag;
import pl.envelo.moovelo.repository.HashtagRepository;

import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class HashTagService {

    private HashtagRepository hashtagRepository;

    @Autowired
    public HashTagService(HashtagRepository hashtagRepository) {
        this.hashtagRepository = hashtagRepository;
    }

    public void removeHashtagWithNoEvents(Hashtag hashtag) {
        log.info("HashtagService - removeHashtagWithNoEvents() - hashtag = {}", hashtag);
        if (hashtag.getEvents().isEmpty()) {
            hashtagRepository.delete(hashtag);
            log.info("HashtagService - removeHashtagWithNoEvents() - hashtag removed");
        }
    }
}
