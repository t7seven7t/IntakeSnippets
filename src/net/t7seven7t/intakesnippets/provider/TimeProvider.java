package net.t7seven7t.intakesnippets.provider;

import com.google.common.collect.ImmutableList;

import com.mysql.jdbc.TimeUtil;
import com.sk89q.intake.argument.ArgumentException;
import com.sk89q.intake.argument.CommandArgs;
import com.sk89q.intake.parametric.Provider;
import com.sk89q.intake.parametric.ProvisionException;

import org.bukkit.util.NumberConversions;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

/**
 *
 */
public abstract class TimeProvider<T> implements Provider<T> {
    private static final Pattern TIME_PATTERN = Pattern.compile("(\\d+)(\\w)");

    @Override
    public boolean isProvided() {
        return false;
    }

    @Nullable
    @Override
    public T get(CommandArgs commandArgs,
                    List<? extends Annotation> list) throws ArgumentException, ProvisionException {
        String argument = commandArgs.next();

        Matcher m = TIME_PATTERN.matcher(argument);
        long time = 0;
        while (m.find()) {
            int num = NumberConversions.toInt(m.group(1));
            String suffix = m.group(2);
            if (suffix.equals("y")) {
                time += 3.15569e10 * num;
            } else if (suffix.equals("w")) {
                time += 604800000L * num;
            } else if (suffix.equals("d")) {
                time += TimeUnit.DAYS.toMillis(num);
            } else if (suffix.equals("h")) {
                time += TimeUnit.HOURS.toMillis(num);
            } else if (suffix.equals("m")) {
                time += TimeUnit.MINUTES.toMillis(num);
            } else if (suffix.equals("s")) {
                time += TimeUnit.SECONDS.toMillis(num);
            } else if (suffix.equals("t")) {
                time += 50L * num;
            }
        }

        return convert(time);
    }

    public abstract T convert(long millis);

    @Override
    public List<String> getSuggestions(String s) {
        return ImmutableList.of();
    }
}
