package net.t7seven7t.intakesnippets;

import com.sk89q.intake.Command;

import net.t7seven7t.intakesnippets.provider.Sender;

import org.bukkit.entity.Player;

import java.util.List;

/**
 *
 */
public class Commands {

    @Command(
            aliases = "players",
            desc = "Gets a list of players on the server",
            min = 1
    )
    public void getPlayers(List<Player> players, @Sender Player sender) {
        String result = "";
        for (Player player : players) {
            result += player + ", ";
        }
        sender.sendMessage(result);
    }

}
