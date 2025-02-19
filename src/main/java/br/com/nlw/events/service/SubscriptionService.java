package br.com.nlw.events.service;

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

}
