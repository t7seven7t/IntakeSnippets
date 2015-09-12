package net.t7seven7t.intakesnippets.provider;

import com.google.common.reflect.TypeToken;

import com.sk89q.intake.parametric.AbstractModule;
import com.sk89q.intake.parametric.Key;

import net.t7seven7t.intakesnippets.IntakeSnippets;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 *
 */
public class BukkitModule extends AbstractModule {
    private static final Key<List<Player>> PLAYER_KEY = Key.get(new TypeToken<List<Player>>() {
    }.getType());

    @Override
    public void configure() {
        bind(PLAYER_KEY).toProvider(new PlayerArgumentProvider<List<Player>>() {
            @Override
            public List<Player> convert(List<Player> list) {
                return list;
            }
        });
        bind(Player.class).toProvider(new PlayerArgumentProvider<Player>() {
            @Override
            public Player convert(List<Player> list) {
                // list size > 1 always; see PlayerArgumentProvider spec
                return list.get(0);
            }
        });
        bind(Player.class).annotatedWith(Sender.class).toProvider(new PlayerSenderProvider());
        bind(IntakeSnippets.class).toInstance(IntakeSnippets.getInstance());
        bind(CommandSender.class).toProvider(new CommandSenderProvider());
        bind(Long.class).annotatedWith(Ticks.class).toProvider(new TimeProvider<Long>() {
            @Override
            public Long convert(long millis) {
                return millis / 50L;
            }
        });
        bind(Long.class).annotatedWith(Milliseconds.class).toProvider(new TimeProvider<Long>() {
            @Override
            public Long convert(long millis) {
                return millis;
            }
        });
    }
}
