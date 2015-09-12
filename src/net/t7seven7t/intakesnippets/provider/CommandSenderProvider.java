package net.t7seven7t.intakesnippets.provider;

import com.sk89q.intake.argument.ArgumentException;
import com.sk89q.intake.argument.CommandArgs;
import com.sk89q.intake.parametric.Provider;
import com.sk89q.intake.parametric.ProvisionException;

import org.bukkit.command.CommandSender;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Provides the CommandSender that is invoking the command.
 */
public class CommandSenderProvider implements Provider<CommandSender> {
    @Override
    public boolean isProvided() {
        return true;
    }

    @Nullable
    @Override
    public CommandSender get(CommandArgs commandArgs,
                             List<? extends Annotation> list) throws ArgumentException, ProvisionException {
        return commandArgs.getNamespace().get(CommandSender.class);
    }

    @Override
    public List<String> getSuggestions(String s) {
        return Collections.emptyList();
    }
}
