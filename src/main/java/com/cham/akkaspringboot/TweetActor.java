package com.cham.akkaspringboot;

import akka.actor.UntypedActor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TweetActor extends UntypedActor {

    private TweetService tweetService;

    public TweetActor(TweetService greetingService) {
        this.tweetService = greetingService;
    }

    @Override
    public void onReceive(Object message) {
        if (message instanceof Tweet) {
            String name = ((Tweet) message).getName();
            getSender().tell(tweetService.tweet(name), getSelf());
        } else {
            unhandled(message);
        }
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
