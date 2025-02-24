/*
 *     Blaze - Animation Library
 *     Copyright (C) 2021  SunStorm (aka. MrExplode)
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package me.sunstorm.blaze;

import org.jetbrains.annotations.NotNull;

/**
 * Represents an animation.
 */
public interface Animation {
    /**
     * Returns the value of this animation.
     *
     * @return the value
     */
    double value();

    /**
     * Sets the value of this animation.
     *
     * @param value the new value.
     * @return this animation
     */
    @NotNull
    Animation value(double value);

    /**
     * Returns the progress of this animation.
     *
     * @return the progress
     */
    double progress();

    /**
     * Sets the progress of this animation.
     *
     * @param value the new progress
     * @return this animation
     */
    @NotNull
    Animation progress(double value);

    /**
     * Returns if the animation is currently paused.
     *
     * @return true if its paused, false otherwise
     */
    boolean paused();

    /**
     * Sets the pause state of this animation. While paused, the animation doesn't tick.
     *
     * @param state the new stack
     * @return this animation
     */
    @NotNull
    Animation paused(boolean state);

    /**
     * Returns the remaining value of this animation.
     *
     * @return the remaining value
     */
    default double remaining() {
        return 1 - value();
    }

    /**
     * Returns the speed of this animation.
     *
     * @return the speed
     */
    default double speed() {
        return 0.1;
    }

    /**
     * Returns the type of this animation.
     *
     * @return the type
     */
    @NotNull
    default AnimationType type() {
        return AnimationType.once();
    }

    /**
     * Returns the ease of this animation.
     *
     * @return the ease
     */
    @NotNull
    default Ease ease() {
        return x -> x;
    }

    /**
     * Creates an {@link Animation animation} with the given parameters.
     *
     * @param ease the ease of the animation
     * @return the created animation
     */
    @NotNull
    static Animation animation(@NotNull Ease ease) {
        return animation(ease, 0.1);
    }

    /**
     * Creates an {@link Animation animation} with the given parameters.
     *
     * @param ease the ease of the animation
     * @param speed the speed of the animation
     * @return the created animation
     */
    @NotNull
    static Animation animation(@NotNull Ease ease, double speed) {
        return animation(ease, AnimationType.once(), speed);
    }

    /**
     * Creates an {@link Animation animation} with the given parameters.
     *
     * @param ease  the ease of the animation
     * @param type  the type of this animation
     * @param speed the speed of this animation
     * @return the created animation
     */
    @NotNull
    static Animation animation(@NotNull Ease ease, @NotNull AnimationType type, double speed) {
        return animation(ease, type, false, speed, 0);
    }

    /**
     * Creates an {@link Animation animation} with the given parameters.
     *
     * @param ease  the ease of the animation
     * @param type  the type of this animation
     * @param speed the speed of this animation
     * @param progress the progress value of this animation
     * @return the created animation
     */
    @NotNull
    static Animation animation(@NotNull Ease ease, @NotNull AnimationType type, double speed, double progress) {
        return animation(ease, type, false, speed, progress);
    }

    /**
     * Creates an {@link Animation animation} with the given parameters.
     *
     * @param ease     the ease of the animation
     * @param type     the type of this animation
     * @param paused   the pause state of this animation
     * @param speed    the speed of this animation
     * @param progress the starting progress of this animation
     * @return the created animation
     */
    static Animation animation(@NotNull Ease ease, @NotNull AnimationType type, boolean paused, double speed, double progress) {
        //@formatter:off
        return new Animation() {
            double value, progress;
            boolean paused;

            @Override public double value() { return value; }
            @Override @NotNull public Animation value(double value) { this.value = value; return this; }
            @Override public double progress() { return progress; }
            @Override @NotNull public Animation progress(double progress) { this.progress = progress; return value(type.getValue(this)); }
            @Override public boolean paused() { return paused; }
            @Override @NotNull public Animation paused(boolean paused) { this.paused = paused; return this; }
            @Override public double speed() { return speed; }
            @Override @NotNull public AnimationType type() { return type; }
            @Override @NotNull public Ease ease() { return ease; }
        }.paused(paused).progress(progress);
        //@formatter:on
    }
}
