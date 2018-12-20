package com.cham.akkaspringboot;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TweetActor extends AbstractActor {

    @Autowired
    private TweetService tweetService;

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(Tweet.class, r ->
        {
            log.info("Inside TestActor.createReceive()..");

            try {
                String name = r.getName();
                getSender().tell(tweetService.tweet(name), getSelf());
            }catch(Exception ex){
                getSender().tell(new akka.actor.Status.Failure(ex), getSelf());
                throw ex;
            }
        }).build();
    }

    public static class Tweet {
        private String name;
        public Tweet(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
    }
}
