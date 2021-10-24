package com.tridang.assignment.rest;/*
 * @author Tri Dang
 */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tridang.assignment.config.ApiConfiguration;
import com.tridang.assignment.constant.MerriamValidationStatus;
import com.tridang.assignment.model.RestStatus;
import com.tridang.assignment.vo.MerriamVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class MerriamRestService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final RestTemplate restTemplate;

    @Autowired
    private ApiConfiguration apiConfig;

    private ObjectMapper mapper;

    public MerriamRestService(RestTemplateBuilder restTemplateBuilder) {
        this.mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.restTemplate = restTemplateBuilder.build();
    }

    public RestStatus validate(String word) {
        String url = apiConfig.getUrlMerriam();
        String key = apiConfig.getKeyMerriam();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity(headers),
                String.class,
                word,
                key
        );

        // check response
        String status = MerriamValidationStatus.NOT_VALIDATED.getStatus();
        String message = "";
        if (response.getStatusCode() == HttpStatus.OK
            && response.getBody() != null
            && !response.getBody().isEmpty()) {
            logger.trace("API Response: {}", response.getBody());

            try {
                List<MerriamVO> merriamList = mapper.readValue(response.getBody(), new TypeReference<List<MerriamVO>>(){});
                for (MerriamVO merriam : merriamList) {
                    logger.info("{}", mapper.writeValueAsString(merriam));
                    if (merriam.getShortDefinition() != null) {
                        status = MerriamValidationStatus.VALIDATED.getStatus();
                        message = word;
                        return new RestStatus(status, message);
                    }
                }
            } catch (JsonProcessingException jsonException) {
                message = String.format("Unable to validate word: %s. ", word);
                logger.warn("Attempting workaround after parsing error.");
                try {
                    List<String> data = mapper.readValue(response.getBody(), new TypeReference<List<String>>() {});
                    message = message + String.format("Did you mean one of these? %s", Arrays.toString(data.toArray()));
                } catch (JsonProcessingException nestedJsonException) {
                    logger.error("Workaround FAILED. Original error: {}. Current error: ", jsonException.getLocalizedMessage(), nestedJsonException);
                }
            }
        } else {
            message = String.format("Received HttpStatus: %s for word: %s", response.getStatusCode(), word);
            logger.error(message);
        }

        return new RestStatus(status, message);
    }
}
