package net.t7seven7t.intakesnippets;

import com.google.common.base.Joiner;

import com.sk89q.intake.CommandCallable;
import com.sk89q.intake.CommandException;
import com.sk89q.intake.Intake;
import com.sk89q.intake.InvocationCommandException;
import com.sk89q.intake.argument.Namespace;
import com.sk89q.intake.fluent.CommandGraph;
import com.sk89q.intake.parametric.Injector;
import com.sk89q.intake.parametric.ParametricBuilder;
import com.sk89q.intake.parametric.provider.PrimitivesModule;
import com.sk89q.intake.util.auth.AuthorizationException;
import com.sk89q.intake.util.auth.Authorizer;

import net.t7seven7t.intakesnippets.provider.BukkitModule;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.List;

public class IntakeSnippets extends JavaPlugin {

    /**
     * This Joiner instance will be used to join command args together as that is how Intake accepts
     * them
     */
    private static final Joiner SPACE_JOINER = Joiner.on(" ");
    private static IntakeSnippets instance;
    /**
     * This is the primary method for dispatching commands
     */
    private CommandCallable dispatcher;

    public static IntakeSnippets getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Instance is registered for an example in command arguments
        instance = this;

        // Do your usual plugin enable stuff
        setupCommands();
    }

    private void setupCommands() {
        Injector injector = Intake.createInjector();
        injector.install(new PrimitivesModule());
        injector.install(new BukkitModule());

        ParametricBuilder builder = new ParametricBuilder(injector);
        builder.setAuthorizer(new Authorizer() {
            @Override
            public boolean testPermission(Namespace namespace, String permission) {
                return namespace.get(CommandSender.class).hasPermission(permission);
            }
        });

        dispatcher = new CommandGraph()
                .builder(builder)
                .commands()
                .registerMethods(new Commands())
                .group("example")
                .registerMethods(new PrefixedCommands())
                .parent()
                .graph()
                .getDispatcher();
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias,
                                      String[] args) {
        try {
            return dispatcher.getSuggestions(joinCommandArgs(command.getName(), args),
                    createNamespace(sender));
        } catch (CommandException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
        }

        return Collections.emptyList();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            return dispatcher.call(joinCommandArgs(command.getName(), args),
                    createNamespace(sender), Collections.emptyList());
        } catch (AuthorizationException e) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to do this.");
        } catch (InvocationCommandException e) {
            if (e.getCause() instanceof NumberFormatException) {
                sender.sendMessage(ChatColor.RED + e.getMessage());
            } else {
                sender.sendMessage(ChatColor.RED + "An error occurred. See console.");
                e.printStackTrace();
            }
        } catch (CommandException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
        }

        return true;
    }

    private Namespace createNamespace(CommandSender sender) {
        Namespace namespace = new Namespace();
        namespace.put(CommandSender.class, sender);
        if (sender instanceof Player) {
            namespace.put(Player.class, (Player) sender);
        }
        return namespace;
    }

    private String joinCommandArgs(String command, String[] args) {
        return command + " " + SPACE_JOINER.join(args);
    }

}