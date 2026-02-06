package me.a8kj.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.a8kj.config.template.registry.ConfigRegistry;
import me.a8kj.eventbus.manager.EventManager;

@AllArgsConstructor
@Getter
public class BukkitConfigAPI implements ConfigAPI {

    private final EventManager eventManager;
    private final ConfigRegistry configRegistry;
}
