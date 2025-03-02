package br.com.nlw.events.controller;

import br.com.nlw.events.dto.SubscriptionRankingItem;
import br.com.nlw.events.dto.SubscriptionResponse;
import br.com.nlw.events.model.User;
import br.com.nlw.events.service.SubscriptionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping({"/{prettyName}", "/{prettyName}/{userId}"})
    public ResponseEntity<?> createSubscription(
            @PathVariable String prettyName,
            @PathVariable(required = false) Integer userId,
            @RequestBody @Valid User user){

            SubscriptionResponse res = subscriptionService.createNewSubscription(prettyName, user, userId);
            return ResponseEntity.ok(res);
    }

    @GetMapping("/{prettyName}/ranking")
    public  ResponseEntity<?> generateRankingByEvent(@PathVariable("prettyName") String prettyName){
        return ResponseEntity.ok(subscriptionService.getCompleteRanking(prettyName));
    }

    @GetMapping("/{prettyName}/ranking/{userId}")
    public  ResponseEntity<?> generateRankingByEventAndUser(@PathVariable("prettyName") String prettyName,
                                                            @PathVariable("userId") Integer userId){
        return ResponseEntity.ok(subscriptionService.getRankingByUser(prettyName, userId));
    }

}
