package br.com.nlw.events.service;

import br.com.nlw.events.dto.SubscriptionRankingByUser;
import br.com.nlw.events.dto.SubscriptionRankingItem;
import br.com.nlw.events.dto.SubscriptionResponse;
import br.com.nlw.events.exception.EventNotFoundException;
import br.com.nlw.events.exception.SubscriptionConflictException;
import br.com.nlw.events.exception.UserIndicadorNotFoundException;
import br.com.nlw.events.model.Event;
import br.com.nlw.events.model.Subscription;
import br.com.nlw.events.model.User;
import br.com.nlw.events.repository.EventRepository;
import br.com.nlw.events.repository.SubscriptionRepository;
import br.com.nlw.events.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class SubscriptionService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public SubscriptionResponse createNewSubscription(String eventName, User user, Integer userId){
        Event evt = eventRepository.findByPrettyName(eventName);
        if(evt == null){
            throw new EventNotFoundException("Evento "+eventName+" não existe");
        }

        User userRec = userRepository.findByEmail(user.getEmail());
        if(userRec == null){
            userRec = userRepository.save(user);
        }

        User indicador = null;
        if (userId != null) {
            indicador = userRepository.findById(userId).orElse(null);
            if (indicador == null) {
                throw new UserIndicadorNotFoundException("Usuário indicador com ID " + userId + " não existe");
            }
        }

        Subscription subscription = new Subscription();
        subscription.setEvent(evt);
        subscription.setSubscriber(userRec);
        subscription.setIndication(indicador);

        Subscription subAux = subscriptionRepository.findByEventAndSubscriber(evt, userRec);
        if(subAux != null){
            throw new SubscriptionConflictException("Já existe uma inscrição para o usuário "+userRec.getName()+" no evento "+subscription.getEvent().getTitle());
        }

        Subscription res = subscriptionRepository.save(subscription);

        return new SubscriptionResponse(res.getSubscriptionNumber(),
                "http://codecraft.com/subscriptions/"+res.getEvent().getPrettyName()+"/"+res.getSubscriber().getUserId());
    }

    public List<SubscriptionRankingItem> getCompleteRanking(String prettyName){
        Event eventByPrettyName = eventRepository.findByPrettyName(prettyName);
        if(eventByPrettyName == null) throw new EventNotFoundException("Ranking do evento "+prettyName+" não existe");
        return subscriptionRepository.generateRanking(eventByPrettyName.getEventId()).subList(0, 3);
    }

    public SubscriptionRankingByUser getRankingByUser(String prettyName, Integer userId){
        List<SubscriptionRankingItem> ranking = getCompleteRanking(prettyName);
        SubscriptionRankingItem item = ranking.stream()
                .filter(i -> i.userId().equals(userId))
                .findFirst().orElse(null);
        if(item == null){
            throw new UserIndicadorNotFoundException("Não há inscrições com indicação do usuário com ID "+ userId);
        }
        int position = IntStream.range(0, ranking.size())
                .filter(pos -> ranking.get(pos).userId().equals(userId))
                .findFirst().getAsInt();
        return new SubscriptionRankingByUser(item, position+1);
    }

}
