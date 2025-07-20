package com.storyAi.story_AI.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.storyAi.story_AI.dto.ModelAiRequest;
import com.storyAi.story_AI.entity.Video;
import com.storyAi.story_AI.repository.UserRepository;
import com.storyAi.story_AI.security.TokenUtil;
import com.storyAi.story_AI.service.CloudinaryService;
import com.storyAi.story_AI.service.ModelAiService;
import com.storyAi.story_AI.service.VideoService;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Component
public class AIWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private ModelAiService modelAiService;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private VideoService videoService;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        Map<String, Object> payloadMap = objectMapper.readValue(payload, Map.class);

        String token = (String) payloadMap.get("token");
       String name=(String)payloadMap.get("name");
       String about=(String)payloadMap.get("about");

       String language=(String)payloadMap.get("language");

        if (token == null || !tokenUtil.validateTokenSignature(token)) {
            session.sendMessage(new TextMessage("Invalid Token"));
            session.close(CloseStatus.POLICY_VIOLATION);
            return;
        }

        if (!userRepository.isAlreadyAtDatabase(token)) {
            session.sendMessage(new TextMessage("Invalid Token"));
            session.close(CloseStatus.POLICY_VIOLATION);
            return;
        }

        Long userId = tokenUtil.getIdFromJwtWithoutBearer(token);
        Long id = Long.parseLong((String) payloadMap.get("id"));

        if (!userId.equals(id)||id.equals(null)||userId.equals(null)) {
            session.sendMessage(new TextMessage("Invalid Token"));
            session.close(CloseStatus.POLICY_VIOLATION);
            return;
        }

        ModelAiRequest modelRequest = new ModelAiRequest();
        modelRequest.setPrompts((List<String>) payloadMap.get("prompts"));
        modelRequest.setAccent(String.valueOf(payloadMap.get("accent")));
        modelRequest.setOutput_type(String.valueOf(payloadMap.get("output_type")));

        modelAiService.sendFirstRequestToKnowPlaceOfVideo(modelRequest)
            .flatMap(firstResponse -> {
                String jobId = (String) firstResponse.get("job_id");

                if (jobId == null) {
                    return Mono.fromRunnable(() -> {
                        try {
                            session.sendMessage(new TextMessage("Failed to retrieve video job ID"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }

                long delayMinutes = 50L * modelRequest.getPrompts().size();

                return Mono.delay(Duration.ofMinutes(delayMinutes))
                        .then(modelAiService.downloadVideo(jobId))
                        .flatMap(videoBytes -> {
                            try {
                                
                                
                                String videoUrl = cloudinaryService.uploadVideoToCloudinary(videoBytes);

                                
                               // saveVideoLinkToDatabase(videoUrl, userId,name,about);
                                
                                Video video= saveVideoLinkToDatabase(videoUrl, userId,name,about,language);
                                Long videoId=video.getId();
                                      String jsonResponse = String.format("{\"videoId\": \"%s\", \"videoUrl\": \"%s\"}", videoId, videoUrl);

                                      session.sendMessage(new TextMessage(jsonResponse));
                                
                                session.sendMessage(new TextMessage("Video URL: " + videoUrl));

                                return Mono.empty();
                            } catch (Exception e) {
                                e.printStackTrace();
                                return Mono.error(e);
                            }
                        });
            })
            .doFinally(signalType -> {
                try {
                    session.close(CloseStatus.NORMAL);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            })
            .subscribe();
    }

    private Video saveVideoLinkToDatabase(String videoUrl, Long userId,String name,String about,String language) throws Exception {
     return   videoService.saveVideoLinkToDatabase(videoUrl, userId, name, about,language);
    }
}
