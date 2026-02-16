package me.a8kj.config;

import java.io.Serializable;

record DatabaseSettings(String host, int port) implements Serializable {
}
