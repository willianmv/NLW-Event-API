package br.com.nlw.events.repository;

import br.com.nlw.events.dto.SubscriptionRankingItem;
import br.com.nlw.events.model.Event;
import br.com.nlw.events.model.Subscription;
import br.com.nlw.events.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {

    public Subscription findByEventAndSubscriber(Event evt, User user);

    @Query( value = """
            select count(subscription_number) as quantidade, indication_user_id, user_name
            from tbl_subscription inner join tbl_user
            on tbl_subscription.indication_user_id = tbl_user.user_id
            where indication_user_id is not null and event_id = :eventId
            group by indication_user_id
            order by quantidade desc;
            """, nativeQuery = true)
    public List<SubscriptionRankingItem> generateRanking(@Param("eventId") Integer eventId);

}
