package net.t7seven7t.intakesnippets.provider;

import com.google.common.collect.ImmutableList;

import com.sk89q.intake.argument.ArgumentException;
import com.sk89q.intake.argument.CommandArgs;
import com.sk89q.intake.parametric.Provider;
import com.sk89q.intake.parametric.ProvisionException;

import org.bukkit.entity.Player;

import java.lang.annotation.Annotation;
import java.util.List;

import javax.annotation.Nullable;

/**
 *
 */
public class PlayerSenderProvider implements Provider<Player> {
    @Override
    public boolean isProvided() {
        return true;
    }

    @Nullable
    @Override
    public Player get(CommandArgs commandArgs,
                      List<? extends Annotation> list) throws ArgumentException, ProvisionException {
        Player player = commandArgs.getNamespace().get(Player.class);
        if (player != null) {
            return player;
        }
        throw new ProvisionException("You must be a player to perform this command.");
    }

    @Override
    public List<String> getSuggestions(String s) {
        return ImmutableList.of();
    }
}
