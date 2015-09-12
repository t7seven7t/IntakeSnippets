package net.t7seven7t.intakesnippets.provider;

import com.sk89q.intake.parametric.annotation.Classifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies that a command argument references the sender and not a player based on arguments
 */
@Retention(RetentionPolicy.RUNTIME)
@Classifier
@Target({ElementType.PARAMETER, ElementType.FIELD})
public @interface Sender {
}
