package com.gibgab.service.beans;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutoModeratorConfiguration {

    @Value("#{'${yammr.automoderator.flags.maximum}'}")
    @Getter
    private int maxFlagsPerPost;
}
