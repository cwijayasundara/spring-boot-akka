package com.cham.akkaspringboot;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.util.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;

import java.util.concurrent.TimeUnit;

import static akka.pattern.Patterns.ask;

@SpringBootApplication
@Configuration("AppConfiguration.class")
public class AkkaSpringBootApplication implements CommandLineRunner {

	@Autowired
	private ActorSystem system;

	public static void main(String[] args) {
		SpringApplication.run(AkkaSpringBootApplication.class, args);

	}

	public void run(java.lang.String... args){
		ActorRef tweeter = system.actorOf(SpringExtension.SPRING_EXTENSION.get(system).props("tweetActor"), "greeter");
		FiniteDuration duration = FiniteDuration.create(1, TimeUnit.SECONDS);
		Timeout timeout = Timeout.durationToTimeout(duration);
		Future<Object> result = ask(tweeter, new TweetActor.Tweet("Scala-Akka-Spring-Boot"), timeout);
		System.out.println(result.value());
	}

}

