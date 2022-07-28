package com.example.demo.application;

import com.example.demo.domain.ShortenUrl;
import com.example.demo.domain.exception.UrlFormatException;
import com.example.demo.infrastructure.ShortenUrlListRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service // UrlShortenerService 빈이 생성이 되어 관리된다.
public class UrlShortenerService {

    private ShortenUrlListRepository shortenUrlListRepository;

    public UrlShortenerService(ShortenUrlListRepository shortenUrlListRepository){
        this.shortenUrlListRepository = shortenUrlListRepository;
    }

    public String createUrl(String destination) { //단축 url 생성
        checkValidation(destination);
        //checkDestination(destination); 같은 url요청인 경우 중복 체크하여 이전에 만들었던 newUrl 반환해야함
        String newUrl = UUID.randomUUID().toString().substring(0, 7); //랜덤 문자열 생성

        while (true) {
            if (!isDuplicated(newUrl)) { //랜덤 문자열 중복 체크
                ShortenUrl shortenUrl = new ShortenUrl(destination, newUrl);
                shortenUrlListRepository.createShortenUrl(shortenUrl);
                return newUrl;
            }
        }
    }

    public String getDestination(String newUrl){// 리다이렉트
        return shortenUrlListRepository.getDestination(newUrl);
    }

    private void getNewUrl(String destination){
        //return shortenUrlListRepository.getNewUrl();
    }

    private boolean isDuplicated(String newUrl){
        if(shortenUrlListRepository.checkUrl(newUrl))
            return true;
        else
            return true;
    }

    private void checkValidation(String text) {
        Pattern p = Pattern.compile("^(?:https?:\\/\\/)?(?:www\\.)?[a-zA-Z0-9./]+$");
        Matcher m = p.matcher(text);

        if(Boolean.FALSE == m.matches())
            throw new UrlFormatException("URL 형식이 맞지 않습니다.");
    }

}
